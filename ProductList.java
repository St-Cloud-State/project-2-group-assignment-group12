import java.util.ArrayList;

public class ProductList {
    private ArrayList<Product> products;

    public ProductList() {
        products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    public void removeProduct(Product product) {
        if (product != null) {
            products.remove(product);
        }
    }

    public Product searchProductById(String id) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public int getQtyById(String productId) {
        Product searchResult = null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                searchResult = products.get(i);
                break;
            }
        }
        if (searchResult != null) {
            return searchResult.getQuantity();
        } else {
            return 0;
        }
    }

    public Product getProductById(String productId) {
        Product searchResult = null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                searchResult = products.get(i);
                break;
            }
        }
        if (searchResult != null) {
            return searchResult;
        } else {
            return null;
        }
    }

    public void setQtyById(String productId, int qty) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getId().equals(productId)) {
                p.setQuantity(qty);
                break;
            }
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean isEmpty() {
        if (products.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void displayAllProducts() {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println("ID: " + p.getId() + ", Name: " + p.getName() +
                    ", Quantity: " + p.getQuantity() + ", Price: $" + p.getPrice());
        }
    }
}
