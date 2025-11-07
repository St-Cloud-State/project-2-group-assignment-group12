public class ClerkMenuState extends BaseState {
    private static ClerkMenuState instance;

    private ClerkMenuState() {

    }

    public static ClerkMenuState instance() {
        if (instance == null) {
            instance = new ClerkMenuState();
        }
        return instance;
    }

    @Override
    public void run() {
        WarehouseContext context = WarehouseContext.instance();
        WarehouseBL warehouse = context.getWarehouse();

        boolean exit = false;
        while (!exit) {
            displayMenu();
            String choice = context.getToken("Enter option: ").trim();

            switch (choice) {
                case "1":
                    addAClient(context, warehouse);
                    break;
                case "2":
                    revealProductList(warehouse);
                    break;
                case "3":
                    showALLclient(warehouse);
                    break;
                case "4":
                    ClientsWOBalance(warehouse);
                    break;
                case "5":
                    RecordPayment(context, warehouse);
                    break;
                case "6":
                    BeClient(context, warehouse);
                    break;
                case "0":
                    exit = true;
                    Logout(context);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    private void addAClient(WarehouseContext context, WarehouseBL warehouse) {
        String name = context.getToken("Enter client name: ").trim();
        String address = context.getToken("Enter client address: ").trim();

        boolean added = warehouse.addClient(name, address);
        System.out.println(added ? "Client added" : "Couldn't add client.");
    }

    private void revealProductList(WarehouseBL warehouse) {
        warehouse.displayProducts();
    }

    private void showALLclient(WarehouseBL warehouse) {
        warehouse.displayClients();
    }

    private void ClientsWOBalance(WarehouseBL warehouse) {
        System.out.println("\nClients with outstanding balances:");
        warehouse.displayClients();
    }

    private void RecordPayment(WarehouseContext context, WarehouseBL warehouse) {
        String clientId = context.getToken("Enter client ID: ").trim();
        double amount = readDouble(context, "Enter payment amount: ");
        warehouse.recordPayment(clientId, amount);
    }

    private void BeClient(WarehouseContext context, WarehouseBL warehouse) {
        String clientId = context.getToken("Enter client ID to act as: ").trim();
        if (warehouse.getClientById(clientId) != null) {
            context.setClientID(clientId);
            System.out.println("Becoming Client " + clientId);
            context.changeState(1);
        } else {
            System.out.println("Invalid client ID");
        }
    }

    private void Logout(WarehouseContext context) {
        System.out.println("Logging out");
        context.changeState(0);
    }

    private double readDouble(WarehouseContext context, String prompt) {
        while (true) {
            try {
                String input = context.getToken(prompt);
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n--- CLERK MENU ---");
        System.out.println("1. Add a client");
        System.out.println("2. Show list of products");
        System.out.println("3. Show all clients");
        System.out.println("4. Show clients with outstanding balance");
        System.out.println("5. Record payment from a client");
        System.out.println("6. Become a client");
        System.out.println("0. Logout");
    }
}
