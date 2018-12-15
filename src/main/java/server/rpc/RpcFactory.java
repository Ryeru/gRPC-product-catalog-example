package server.rpc;

public class RpcFactory {

    public static CatalogRpc getCatalogRpc() {
        return new CatalogRpc();
    }

}
