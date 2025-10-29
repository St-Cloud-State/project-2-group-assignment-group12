public class WishlistItem {
    private String productId;
    private int quantity;

    public WishlistItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Quantity: " + quantity;
    }
}
