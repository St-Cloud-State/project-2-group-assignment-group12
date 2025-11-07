import java.util.Scanner;

public class ManagerMenuState extends BaseState {
    private static ManagerMenuState instance;

    private ManagerMenuState() { }

    public static ManagerMenuState instance() {
        if (instance == null)
            instance = new ManagerMenuState();
        return instance;
    }

    @Override
    public void run() {
        WarehouseContext context = WarehouseContext.instance();
        WarehouseBL warehouse = context.getWarehouse();
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n MANAGER MENU ");
            System.out.println("1. Add Product");
            System.out.println("2. Display Waitlist");
            System.out.println("3. Receive Shipment");
            System.out.println("4. Become Clerk");
            System.out.println("5. Logout");
            System.out.print("Enter option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a number: ");
                scanner.next();
            }

            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    addProduct(scanner, warehouse);
                    break;
                case 2:
                    displayWaitlist(warehouse);
                    break;
                case 3:
                    receiveShipment(scanner, warehouse);
                    break;
                case 4:
                    becomeClerk(context);
                    break;
                case 5:
                    logout(context);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (option != 5);
    }

    //Manager Operations

    private void addProduct(Scanner scanner, WarehouseBL warehouse) {
        System.out.println("\nAdd New Product ");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = readInt(scanner);

        System.out.print("Enter unit price: ");
        double price = readDouble(scanner);

        boolean success = warehouse.addProduct(name, quantity, price);
        if (success) {
            System.out.println("Product added successfully: " + name);
        } else {
            System.out.println("Failed to add product.");
        }
    }

    private void displayWaitlist(WarehouseBL warehouse) {
        System.out.println("\nDisplay Waitlist ");
        warehouse.displayWaitlist();
    }

    private void receiveShipment(Scanner scanner, WarehouseBL warehouse) {
        System.out.println("\nReceive Shipment ");
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();

        System.out.print("Enter quantity received: ");
        int qtyReceived = readInt(scanner);

        warehouse.receiveShipment(productId, qtyReceived);
    }

    private void becomeClerk(WarehouseContext context) {
        System.out.println("\nSwitching to Clerk Menu...");
        context.changeState(WarehouseContext.CLERK_STATE);
    }

    private void logout(WarehouseContext context) {
    System.out.println("\nLogging out to Login Menu...");
    context.changeState(0); // 0 corresponds to LOGIN_STATE transition
    }


    //Utility input methods 

    private int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid integer: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private double readDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
