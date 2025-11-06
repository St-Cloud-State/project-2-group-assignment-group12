import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String transactionId;
    private String clientId;
    private List<String> products;
    private double totalPrice;
    private LocalDateTime timestamp;
    private static int transactionCounter = 1;

    public Transaction(String clientId, List<String> products, double totalPrice) {
        this.transactionId = "T" + transactionCounter;
        transactionCounter++;
        this.clientId = clientId;
        this.products = new ArrayList<>(products);
        this.totalPrice = totalPrice;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getClientId() {
        return clientId;
    }

    public List<String> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------\n");
        sb.append("Transaction ID: ").append(transactionId).append("\n");
        sb.append("Client ID: ").append(clientId).append("\n");
        sb.append("Products: ").append(String.join(", ", products)).append("\n");
        sb.append("Total: $").append(String.format("%.2f", totalPrice)).append("\n");
        sb.append("Date: ").append(timestamp.format(formatter)).append("\n");
        sb.append("--------------------------------------------------");
        return sb.toString();
    }
}
