import java.util.*;

public class WarehouseBL {

    private ClientList clientList;
    private ProductList productList;
    private TransactionList transactionList;
    private Waitlist waitlist;

    public WarehouseBL() {
        clientList = new ClientList();
        productList = new ProductList();
        transactionList = new TransactionList();
        waitlist = new Waitlist();
    }

    public boolean addClient(String name, String address) {
        Client client = new Client(name, address);
        return clientList.addClient(client);
    }

    public boolean addProduct(String name, int quantity, double price) {
        Product product = new Product(name, quantity, price);
        productList.addProduct(product);
        return true;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactionList.addTransaction(transaction);
        }
    }

    public boolean addItemToWishlist(String clientId, String productId, int quantity) {
        Client client = clientList.getClient(clientId);
        Product product = productList.searchProductById(productId);

        if (client == null || product == null || quantity <= 0) {
            return false;
        }

        client.addToWishlist(product, quantity);
        return true;
    }

    public void displayClients() {
        System.out.println("=== Clients ===");
        clientList.displayAllClients();
    }

    public void displayProducts() {
        System.out.println("=== Products ===");
        productList.displayAllProducts();
    }

    public void displayWishlist(String clientId) {
        Client client = clientList.getClient(clientId);
        if (client != null) {
            System.out.println("=== Wishlist for Client " + clientId + " - " + client.getName() + " ===");
            Wishlist wishlist = client.getWishlist();
            if (wishlist == null || wishlist.getWishlistItems().isEmpty()) {
                System.out.println("No items in wishlist.");
            } else {
                for (WishlistItem wishlistItem : wishlist.getWishlistItems()) {
                    Product product = productList.getProductById(wishlistItem.getProductId());
                    System.out.println("Product ID: " + wishlistItem.getProductId() +
                            "; Qty: " + wishlistItem.getQuantity() +
                            "\n " + product.toString());
                }
            }
        } else {
            System.out.println("Client not found.");
        }
    }

    public void displayWaitlist() {
        System.out.println("=== WAITLIST ===");
        waitlist.displayAllWaitlistItems();
    }

    public Client getClientById(String clientId) {
        return clientList.getClient(clientId);
    }

    public Product getProductById(String productId) {
        return productList.searchProductById(productId);
    }

    public boolean checkout(String clientId) {
        Client client = clientList.getClient(clientId);
        if (client == null) {
            System.out.println("Client not found.");
            return false;
        }

        Wishlist wishlist = client.getWishlist();
        if (wishlist == null || wishlist.getWishlistItems().isEmpty()) {
            System.out.println("Wishlist is empty.");
            return false;
        }

        double totalPrice = 0.0;
        List<String> purchasedProducts = new ArrayList<>();

        for (WishlistItem item : wishlist.getWishlistItems()) {
            Product product = productList.searchProductById(item.getProductId());
            int requestedQty = item.getQuantity();

            if (product == null) {
                System.out.println("Product " + item.getProductId() + " not found.");
                continue;
            }

            int availableQty = product.getQuantity();

            if (availableQty >= requestedQty) {
                product.setQuantity(availableQty - requestedQty);
                totalPrice += product.getPrice() * requestedQty;
                purchasedProducts.add(product.getId());
            } else if (availableQty > 0) {
                System.out.println("Partial stock for " + product.getName() + ". " +
                        availableQty + " purchased, " + (requestedQty - availableQty) + " waitlisted.");

                totalPrice += product.getPrice() * availableQty;
                product.setQuantity(0);

                WaitlistItem wItem = new WaitlistItem(product.getId(), requestedQty - availableQty, clientId);
                waitlist.addToWaitlist(wItem);

                purchasedProducts.add(product.getId());
            } else {
                System.out.println("Product " + product.getName() + " out of stock. Added to waitlist.");
                WaitlistItem wItem = new WaitlistItem(product.getId(), requestedQty, clientId);
                waitlist.addToWaitlist(wItem);
            }
        }

        client.setBalance(client.getBalance() + totalPrice);
        System.out.println(
                "Client " + client.getName() + " charged $" + totalPrice + ". New balance: $" + client.getBalance());

        if (!purchasedProducts.isEmpty()) {
            Transaction transaction = new Transaction(clientId, purchasedProducts, totalPrice);
            transactionList.addTransaction(transaction);
            System.out.println("Transaction completed. Total: $" + totalPrice);
        }

        wishlist.clear();
        return true;
    }

    public void receiveShipment(String productId, int addedQuantity) {
        Product product = productList.searchProductById(productId);
        if (product == null) {
            System.out.println("Product ID not found.");
            return;
        }

        int oldQty = product.getQuantity();
        product.setQuantity(oldQty + addedQuantity);
        System.out.println("Shipment received for " + product.getName() +
                ". Stock increased from " + oldQty + " to " + product.getQuantity() + ".");

        fulfillWaitlist(productId);
    }

    // waitlist logic

    private void fulfillWaitlist(String productId) {
        List<WaitlistItem> waitingClients = waitlist.getWaitlistForProduct(productId);

        if (waitingClients == null || waitingClients.isEmpty()) {
            System.out.println("No pending waitlist for product " + productId + ".");
            return;
        }

        Product product = productList.searchProductById(productId);
        if (product == null)
            return;

        int availableQty = product.getQuantity();

        System.out.println("Attempting to fulfill waitlist for product " + product.getName() +
                " (available: " + availableQty + ").");

        Iterator<WaitlistItem> iterator = waitingClients.iterator();
        while (iterator.hasNext() && availableQty > 0) {
            WaitlistItem wItem = iterator.next();
            int needed = wItem.getQuantity();
            Client client = clientList.getClient(wItem.getClientId());

            if (client == null)
                continue;

            if (availableQty >= needed) {
                System.out.println("Fulfilling waitlist for client " + client.getId() +
                        " (" + client.getName() + "): " + needed + " units of " + product.getName());
                availableQty -= needed;

                Transaction t = new Transaction(client.getId(),
                        Arrays.asList(product.getId()), product.getPrice() * needed);
                transactionList.addTransaction(t);

                iterator.remove();
            } else {
                System.out.println("Partially fulfilling waitlist for client " + client.getId() +
                        " (" + client.getName() + "): " + availableQty + " units fulfilled, " +
                        (needed - availableQty) + " still waiting.");
                wItem.setQuantity(needed - availableQty);
                availableQty = 0;
            }
        }

        product.setQuantity(availableQty);
        System.out.println("Waitlist fulfillment complete. Remaining stock: " + availableQty);
    }

    // transaction logic

    public void displayTransactions() {
        System.out.println("=== TRANSACTIONS ===");
        transactionList.displayAllTransactions();
    }

    public void displayTransactionsForClient(String clientId) {
        List<Transaction> list = transactionList.getTransactionsByClientId(clientId);
        if (list.isEmpty()) {
            System.out.println("No transactions for client " + clientId);
            return;
        }
        System.out.println("=== Transactions for Client " + clientId + " ===");
        for (Transaction t : list)
            System.out.println(t);
    }

    public void recordPayment(String clientId, double amount) {
        Client client = clientList.getClient(clientId);
        if (client == null) {
            System.out.println("Client not found.");
            return;
        }
        client.setBalance(client.getBalance() - amount);
        System.out.println("Payment of $" + amount + " recorded for " + client.getName() +
                ". New balance: $" + client.getBalance());
    }

}
