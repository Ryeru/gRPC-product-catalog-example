This is a super simple example that simulates the product catalog of a webshop!

### Step 1
Make sure you have docker installed.

To run this example, you first need to have a postgres database running.
I added a shell script that will run a docker image of postgresql for you.

The file to start the database is called `start-database.sh`

Start the shell script and a database should be up!

Database conection data if you want to verify that it is up (or do docker ps):

    "url": jdbc:postgresql://localhost:5432/webshop
    "username" postgres
    "password": postgres

### Step 2
Run `mvn:compile` to generate concrete gRPC classes from all .proto files.
Otherwise you will not be able to start the server quickly from within intellij.

### Step 3
Read the documentation in `/src/main/proto/product.proto` file. 
That will kick start your understanding for proto!

### Step 4
Start reading from `WebshopAplication.java` and see how everything is initialized!

### Step 5
Inspect how `CatalogRpc.java` works!

### Step 6
Start looking at `CatalogMapper.java`!

### Step 7
Check all files in the `/resource` folder.

### Step 8
Run the server `WebshopApplication.java`.

### Step 9
Run the client `Client.java`.
Inspect the lines that it prints!

### Step 10
Usually you want to make layers between CatalogRpc and CatalogMapper that contain your business logic.

But to keep it simple and comprehensible, I decided not to do that.
So step 10 is forgive me :)
