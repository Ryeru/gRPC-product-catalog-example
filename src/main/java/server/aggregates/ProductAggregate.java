package server.aggregates;

//Nothing more than a representation of a product.
// Java auto-magically defines a no-arg constructor for you if no other constructor is defined!
public class ProductAggregate {
    private long id;
    private String name;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
