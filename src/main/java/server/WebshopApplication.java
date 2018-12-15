package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.flywaydb.core.Flyway;
import server.rpc.RpcFactory;

import java.io.IOException;

public class WebshopApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
        // First make sure our database is up
        initializeDatabase();

        // Sets up a new server
        Server server = ServerBuilder.forPort(8080)
                // Registers a new 'endpoint' with the server
                // Must manually add these endpoints because we are not using annotations for clarity's sake!
                .addService(RpcFactory.getCatalogRpc())
                .build();
        server.start();
        server.awaitTermination();
    }

    // Initializes the database, executes the migrations located in /resources/database-migrations/ folder.
    private static void initializeDatabase() {
        String url = "jdbc:postgresql://localhost:5432/webshop";
        String user = "postgres";
        String password = "postgres";

        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .locations("database-migrations")
                .load();

        // Flyway decides what migration to run first depending on the name of the migration.
        // It looks at the V1.0 part, smaller version execute first.
        // It also keeps track of what has already been executed in a separate database table that flyway uses internally.
        flyway.migrate();
    }

}
