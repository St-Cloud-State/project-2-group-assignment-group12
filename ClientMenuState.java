public class ClerkMenuState extends BaseState {
    private static ClerkMenuState instance;
    private WarehouseBL warehouse;

    private ClerkMenuState() {
        warehouse = WarehouseContext.instance().getWarehouse();
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
        boolean exit = false;

        while (!exit) {
            displayMenu();
            String choice = context.getToken("Enter choice: ");

            switch (choice) {
                case "1":
                    addAClient();
                    break;
                case "2":
                    revealProductList();
                    break;
                case "3":
                    showALLclient();
                    break;
                case "4":
                    ClientsWOBalance();
                    break;
                case "5":
                    RecordPayment();
                    break;
                case "6":
                    BeClient(context);
                    break;
                case "0":
                    exit = true;
                    Logout(context);
                    break;
                default:
                    System.out.println("Invalid choice");
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

    private void addAClient() {
        WarehouseContext context = WarehouseContext.instance();
        String name = context.getToken("Enter client name: ");
        String address = context.getToken("Enter client address: ");

        boolean added = warehouse.addClient(name, address);
        System.out.println(added ? "Client added." : "Couldn't add client.");
    }

    private void revealProductList() {
        warehouse.displayProducts();
    }

    private void showALLclient() {
        warehouse.displayClients();
    }

    private void ClientsWOBalance() {
        System.out.println("\nClients with Outstanding Balances:");
        
        for (Client client : warehouse.getClientList()) { 
            if (client.getBalance() > 0) {
                client.displayClientInfo();
            }
        }
    }

    private void RecordPayment() {
        WarehouseContext context = WarehouseContext.instance();
        String clientId = context.getToken("Enter client ID: ");
        double amount = readDouble("Enter payment amount: ");
        warehouse.recordPayment(clientId, amount);
    }

    private void BeClient(WarehouseContext context) {
        String clientId = context.getToken("Enter client ID to act as: ");
        Client client = warehouse.getClientById(clientId);

        if (client != null) {
            context.setClientID(clientId);
            System.out.println("NOW ACTING AS CLIENT: " + client.getName());
            context.changeState(WarehouseContext.CLIENT_STATE);
        } else {
            System.out.println("Invalid client ID.");
        }
    }

    private void Logout(WarehouseContext context) {
        System.out.println("Logging out...");
        context.changeState(WarehouseContext.LOGIN_STATE);
    }

    private double readDouble(String prompt) {
        WarehouseContext context = WarehouseContext.instance();
        while (true) {
            String input = context.getToken(prompt);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number");
            }
        }
    }
}

