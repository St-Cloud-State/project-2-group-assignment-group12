public class Client {
    private String name;
    private String address;
    private String id;
    private Wishlist wishlist;
    private double balance;
    private static int clientCounter = 1;

    public Client(String name, String address) {
        this.name = name;
        this.address = address;
        this.id = "C" + clientCounter;
        clientCounter++;
        this.balance = 0.0;
        this.wishlist = new Wishlist();
    }

    public void addToWishlist(Product product, int qty) {
        WishlistItem wishlistItem = new WishlistItem(product.getId(), qty);
        wishlist.addWishlistItem(wishlistItem);
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public void displayClientInfo() {
        System.out.printf("ID: %s, Name: %s, Address: %s, Balance: $%.2f%n",
                id, name, address, balance);
    }
}
