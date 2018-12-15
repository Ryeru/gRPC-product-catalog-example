package client;

import helloworld.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;


public class Client {

    public static void main(String[] args) {
        // Initialize a new connection with the server
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        CatalogGrpc.CatalogBlockingStub stub = CatalogGrpc.newBlockingStub(channel);


        // Try to add a new item to the database
        StatusRes statusResAdd = stub.addProduct(
                AddProductReq.newBuilder()
                        .setPrice(100)
                        .setName("Jacket")
                        .build());
        System.out.println(statusResAdd.getMessage());


        // List the amount of items in the database
        List<Product> products = stub.getAllProducts(GetProductsReq.newBuilder().build()).getProductsList();
        System.out.println(products.size());


        // Delete an item that does not exist in the database.
        StatusRes statusResDelete = stub.deleteProduct(
                DeleteProductReq.newBuilder()
                        .setId(200)
                        .build());
        System.out.println(statusResDelete.getMessage());
    }
}
