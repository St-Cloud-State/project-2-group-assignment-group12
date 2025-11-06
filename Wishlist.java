import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Wishlist {
    private List<WishlistItem> wishlistItems;
    private String clientId;

    public Wishlist() {
        this.wishlistItems = new ArrayList<>();
        this.clientId = "";
    }

    public Wishlist(String clientId) {
        this();
        this.clientId = clientId;
    }

    public boolean addWishlistItem(WishlistItem wishlistItem) {
        if (wishlistItem != null) {
            return wishlistItems.add(wishlistItem);
        }
        return false;
    }

    public boolean removeWishlistItem(Product product) {
        boolean removed = false;
        Iterator<WishlistItem> iterator = wishlistItems.iterator();
        while (iterator.hasNext()) {
            WishlistItem item = iterator.next();
            if (item.getProductId().equals(product.getId())) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    public List<WishlistItem> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishList(List<WishlistItem> wishlistItems) {
        this.wishlistItems = wishlistItems != null ? wishlistItems : new ArrayList<>();
    }

    public String getClientId() {
        return clientId;
    }

    public void clear() {
        wishlistItems.clear();
    }

    public void displayAllWishlistItems() {
        if (wishlistItems.isEmpty()) {
            System.out.println("Wishlist is empty.");
            return;
        }
        Iterator<WishlistItem> iterator = wishlistItems.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
