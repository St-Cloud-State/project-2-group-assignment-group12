import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Waitlist {
    private List<WaitlistItem> waitlistItems;

    public Waitlist() {
        this.waitlistItems = new ArrayList<>();
    }

    public boolean addToWaitlist(WaitlistItem waitlistItem) {
        if (waitlistItem != null) {
            return waitlistItems.add(waitlistItem);
        }
        return false;
    }

    public List<WaitlistItem> getWaitlistForClient(String clientId) {
        List<WaitlistItem> result = new ArrayList<>();
        for (WaitlistItem item : waitlistItems) {
            if (item.getClientId().equals(clientId)) {
                result.add(item);
            }
        }
        return result;
    }

    public List<WaitlistItem> getWaitlistForProduct(String productId) {
        List<WaitlistItem> result = new ArrayList<>();
        for (WaitlistItem item : waitlistItems) {
            if (item.getProductId().equals(productId)) {
                result.add(item);
            }
        }
        return result;
    }

    public void fulfillProductWaitlist(String productId, WarehouseBL warehouseBL) {
        Product product = warehouseBL.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found while fulfilling waitlist.");
            return;
        }

        int availableQty = product.getQuantity();
        if (availableQty <= 0) {
            System.out.println("No available stock for product " + product.getName());
            return;
        }

        Iterator<WaitlistItem> iterator = waitlistItems.iterator();
        while (iterator.hasNext()) {
            WaitlistItem item = iterator.next();

            if (!item.getProductId().equals(productId))
                continue;

            if (availableQty <= 0)
                break;

            Client client = warehouseBL.getClientById(item.getClientId());
            if (client == null) {
                System.out.println("Skipping waitlist item for unknown client " + item.getClientId());
                continue;
            }

            int requestedQty = item.getQuantity();
            int fulfilledQty = Math.min(availableQty, requestedQty);

            availableQty -= fulfilledQty;
            product.setQuantity(availableQty);

            System.out.println("Waitlist fulfilled for client " + client.getName() +
                    ": " + fulfilledQty + " units of " + product.getName());

            double totalPrice = fulfilledQty * product.getPrice();
            List<String> purchasedProducts = new ArrayList<>();
            purchasedProducts.add(product.getId());
            Transaction transaction = new Transaction(client.getId(), purchasedProducts, totalPrice);
            warehouseBL.addTransaction(transaction);

            if (fulfilledQty == requestedQty) {
                iterator.remove();
            } else {
                item.setQuantity(requestedQty - fulfilledQty);
            }
        }

        product.setQuantity(availableQty); // update final remaining stock
    }

    public List<WaitlistItem> getAllWaitlistItems() {
        return waitlistItems;
    }

    public int size() {
        return waitlistItems.size();
    }

    public void displayAllWaitlistItems() {
        if (waitlistItems.isEmpty()) {
            System.out.println("Waitlist is empty.");
            return;
        }
        for (WaitlistItem item : waitlistItems) {
            System.out.println("Client ID: " + item.getClientId() +
                    ", Product ID: " + item.getProductId() +
                    ", Quantity: " + item.getQuantity());
        }
    }

    public int completeProduct(WaitlistItem waitlistItem, WarehouseBL warehouseBL) {
        fulfillProductWaitlist(waitlistItem.getProductId(), warehouseBL);
        return 1;
    }
}
