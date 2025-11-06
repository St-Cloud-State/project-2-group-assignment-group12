public class Product {
    private String name;
    private int quantity;
    private double price;
    private String id;
    private static int productCounter = 1;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.id = "P" + productCounter; // using this style of id assignment because professor sarnath demonstrated
                                        // something liek this in his example
        productCounter++;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    @Override
    public String toString() {
        return "Product name: " + name + "; Product Price: $" + price;
    }
}
