package server.rpc;

import helloworld.*;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import io.grpc.stub.StreamObserver;
import server.mapper.CatalogMapper;
import server.mapper.MapperFactory;

import java.util.List;
import java.util.stream.Collectors;

// When a client makes a call, this class will be your entry point!
// For example: a client implemented a stub (which is a fake object that looks like the real thing).
// When he executes CatalogRpcStub.getAllProduct(), the stub talks with the server.
// The server delegates the call to CatalogRpc.getAllProducts().
// This is the essence of RPC! It is as if you are directly communicating with the implementation of the backend.

// Another way to look at this is to see this class as your 'rest api'.
public class CatalogRpc extends CatalogGrpc.CatalogImplBase {

    // This class will communicate with our database for us.
    private CatalogMapper catalogMapper = MapperFactory.getCatalogMapper();

    @Override
    // Res is short for response, Req short for request.
    public void getAllProducts(GetProductsReq req, StreamObserver<GetProductsRes> res) {
        // Asks the catalogMapper to query the database.
        // Converts the aggregates (fancy word for model that contains all data) to the desired output type.
        // Turns ProductAggregate into Product basically. Nice explanation of what map is dude.
        List<Product> productList = catalogMapper.getAllProducts().stream()
                .map(it -> Product.newBuilder()
                        .setId(it.getId())
                        .setPrice(it.getPrice())
                        .setName(it.getName())
                        .build())
                .collect(Collectors.toList());

        // Build up the response that we will send to the client.
        GetProductsRes getProductsRes =
                GetProductsRes.newBuilder()
                        .addAllProducts(productList)
                        .build();

        // Used for sending data to the client.
        res.onNext(getProductsRes);

        // Ends the data transfer between client and server (does not close the connection)
        // This is usually used for telling the client that the stream has ended.
        res.onCompleted();
    }

    @Override
    public void addProduct(AddProductReq req, StreamObserver<StatusRes> res) {
        StatusRes.Builder resBuilder = StatusRes.newBuilder();

        if (StringUtil.isNullOrEmpty(req.getName())) {
            resBuilder.setMessage("Name can not be empty.");
            resBuilder.setSuccess(false);

        } else if (req.getPrice() < 0) {
            resBuilder.setMessage("You can not set a negative price.");
            resBuilder.setSuccess(false);

        } else {
            // insertProduct return the amount of affected row, so if there is more than 0, we successfully inserted!
            boolean inserted = catalogMapper.insertProduct(req.getName(), req.getPrice()) > 0;
            resBuilder.setSuccess(inserted);

            if (inserted) {
                resBuilder.setMessage("Added product to catalog successfully.");
            } else {
                resBuilder.setMessage("Failed to insert product into catalog.");
            }
        }

        res.onNext(resBuilder.build());
        res.onCompleted();
    }

    @Override
    public void deleteProduct(DeleteProductReq req, StreamObserver<StatusRes> res) {
        StatusRes.Builder resBuilder = StatusRes.newBuilder();

        boolean deleted = catalogMapper.deleteProduct(req.getId()) > 0;
        resBuilder.setSuccess(deleted);

        if (deleted) {
            resBuilder.setMessage("Deleted product from catalog successfully.");
        } else {
            resBuilder.setMessage("Failed to delete product from catalog.");
        }

        res.onNext(resBuilder.build());
        res.onCompleted();
    }
}