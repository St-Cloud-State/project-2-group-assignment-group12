public class ManagerMenuState extends BaseState {
    private static ManagerMenuState instance;

    private ManagerMenuState() {
    }

    public static ManagerMenuState instance() {
        if (instance == null)
            instance = new ManagerMenuState();
        return instance;
    }

    @Override
    public void run() {
        WarehouseContext context = WarehouseContext.instance();
        WarehouseBL warehouse = context.getWarehouse();
        int option = -1;

        do {
            System.out.println("\nMANAGER MENU");
            System.out.println("1. Add Product");
            System.out.println("2. Display Waitlist");
            System.out.println("3. Receive Shipment");
            System.out.println("4. Become Clerk");
            System.out.println("0. Logout");

            try {
                String input = context.getToken("Enter option: ");
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {
                case 1:
                    addProduct(context, warehouse);
                    break;
                case 2:
                    displayWaitlist(warehouse);
                    break;
                case 3:
                    receiveShipment(context, warehouse);
                    break;
                case 4:
                    becomeClerk(context);
                    return; // switch state
                case 0:
                    logout(context);
                    return; // switch state
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (true);
    }

    // Manager Operations

    private void addProduct(WarehouseContext context, WarehouseBL warehouse) {
        System.out.println("\nAdd New Product");
        String name = context.getToken("Enter product name: ");

        int quantity;
        while (true) {
            try {
                quantity = Integer.parseInt(context.getToken("Enter quantity: "));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer for quantity.");
            }
        }

        double price;
        while (true) {
            try {
                price = Double.parseDouble(context.getToken("Enter unit price: "));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for price.");
            }
        }

        boolean success = warehouse.addProduct(name, quantity, price);
        if (success) {
            System.out.println("Product added successfully: " + name);
        } else {
            System.out.println("Failed to add product.");
        }
    }

    private void displayWaitlist(WarehouseBL warehouse) {
        System.out.println("\nDisplay Waitlist");
        warehouse.displayWaitlist();
    }

    private void receiveShipment(WarehouseContext context, WarehouseBL warehouse) {
        System.out.println("\nReceive Shipment");
        String productId = context.getToken("Enter product ID: ");

        int qtyReceived;
        while (true) {
            try {
                qtyReceived = Integer.parseInt(context.getToken("Enter quantity received: "));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer for quantity.");
            }
        }

        warehouse.receiveShipment(productId, qtyReceived);
    }

    private void becomeClerk(WarehouseContext context) {
        System.out.println("\nSwitching to Clerk Menu...");
        context.changeState(1);
    }

    private void logout(WarehouseContext context) {
        System.out.println("\nLogging out to Login Menu...");
        context.changeState(0);
    }
}
