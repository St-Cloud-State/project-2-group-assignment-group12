public class WaitlistItem {
    private String productId;
    private int quantity;
    private String clientId;

    public WaitlistItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.clientId = "";
    }

    public WaitlistItem(String productId, int quantity, String clientId) {
        this.productId = productId;
        this.quantity = quantity;
        this.clientId = clientId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getClientId() {
        return clientId;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId +
                ", Quantity: " + quantity +
                ", Client ID: " + clientId;
    }
}
