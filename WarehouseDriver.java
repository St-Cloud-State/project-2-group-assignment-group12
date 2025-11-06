import java.util.Scanner;

public class WarehouseDriver {

    public static void main(String[] args) {
        WarehouseBL warehouse = new WarehouseBL();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n==== WAREHOUSE MENU ====");
            System.out.println("1. Add Client");
            System.out.println("2. Add Product");
            System.out.println("3. Add Item to Client Wishlist");
            System.out.println("4. Display All Clients");
            System.out.println("5. Display All Products");
            System.out.println("6. Display Client Wishlist");
            System.out.println("7. Checkout Client");
            System.out.println("8. Receive Shipment");
            System.out.println("9. Display Waitlist");
            System.out.println("10. Record Payment");
            System.out.println("11. Display All Transactions");
            System.out.println("12. Display Transactions for Client");

            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter client name: ");
                    String cName = scanner.nextLine().trim();
                    System.out.print("Enter client address: ");
                    String cAddress = scanner.nextLine().trim();
                    boolean clientAdded = warehouse.addClient(cName, cAddress);
                    System.out.println(clientAdded ? "Client added successfully." : "Failed to add client.");
                    break;

                case "2":
                    System.out.print("Enter product name: ");
                    String pName = scanner.nextLine().trim();
                    System.out.print("Enter product quantity: ");
                    int pQty = readInt(scanner);
                    System.out.print("Enter product price: ");
                    double pPrice = readDouble(scanner);
                    boolean productAdded = warehouse.addProduct(pName, pQty, pPrice);
                    System.out.println(productAdded ? "Product added successfully." : "Failed to add product.");
                    break;

                case "3":
                    System.out.print("Enter client ID: ");
                    String clientId = scanner.nextLine().trim();
                    System.out.print("Enter product ID: ");
                    String productId = scanner.nextLine().trim();
                    System.out.print("Enter quantity: ");
                    int qty = readInt(scanner);
                    boolean wishlistAdded = warehouse.addItemToWishlist(clientId, productId, qty);
                    System.out.println(wishlistAdded ? "Wishlist item added." : "Failed to add wishlist item.");
                    break;

                case "4":
                    warehouse.displayClients();
                    break;

                case "5":
                    warehouse.displayProducts();
                    break;

                case "6":
                    System.out.print("Enter client ID: ");
                    String cId = scanner.nextLine().trim();
                    warehouse.displayWishlist(cId);
                    break;

                case "7":
                    System.out.print("Enter client ID for checkout: ");
                    String checkoutClientId = scanner.nextLine().trim();
                    boolean checkoutSuccess = warehouse.checkout(checkoutClientId);
                    System.out.println(checkoutSuccess ? "Checkout completed successfully."
                            : "Checkout failed or wishlist empty.");
                    break;

                case "8":
                    System.out.print("Enter product ID: ");
                    String shipmentProductId = scanner.nextLine().trim();
                    System.out.print("Enter quantity received: ");
                    int shipmentQty = readInt(scanner);
                    warehouse.receiveShipment(shipmentProductId, shipmentQty);
                    break;

                case "9":
                    warehouse.displayWaitlist();
                    break;

                case "10":
                    System.out.print("Enter client ID: ");
                    String payId = scanner.nextLine().trim();
                    System.out.print("Enter payment amount: ");
                    double payAmt = readDouble(scanner);
                    warehouse.recordPayment(payId, payAmt);
                    break;

                case "11":
                    warehouse.displayTransactions();
                    break;

                case "12":
                    System.out.print("Enter client ID: ");
                    String invClient = scanner.nextLine().trim();
                    warehouse.displayTransactionsForClient(invClient);
                    break;

                case "0":
                    running = false;
                    System.out.println("Exiting Warehouse System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Garbage input, try again: ");
            }
        }
    }

    private static double readDouble(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Garbage input, try again: ");
            }
        }
    }
}
