public class ClientMenuState extends BaseState {
    private static ClientMenuState instance;

    private ClientMenuState() {
    }

    public static ClientMenuState instance() {
        if (instance == null)
            instance = new ClientMenuState();
        return instance;
    }

    @Override
    public void run() {
        WarehouseContext context = WarehouseContext.instance();
        WarehouseBL warehouse = context.getWarehouse();
        String clientId = context.getClientID();
        int option = -1;

        do {
            System.out.println("\nCLIENT MENU");
            System.out.println("1. Show Client Details");
            System.out.println("2. Show List of Products");
            System.out.println("3. Show Client Transactions");
            System.out.println("4. Add Item to Wishlist");
            System.out.println("5. Display Wishlist");
            System.out.println("6. Place Order");
            System.out.println("7. Logout");

            try 
            {
                String input = context.getToken("Enter option: ");
                option = Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) 
            {
                case 1:
                    showClientDetails(warehouse, clientId);
                    break;

                case 2:
                    showProducts(warehouse);
                    break;

                case 3:
                    showClientTransactions(warehouse, clientId);
                    break;

                case 4:
                    addItemToWishlist(context, warehouse, clientId);
                    break;

                case 5:
                    displayWishlist(warehouse, clientId);
                    break;

                case 6:
                    placeOrder(warehouse, clientId);
                    break;

                case 7:
                    logout(context);
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        } 
        while (true);
    }

    // Option 1
    private void showClientDetails(WarehouseBL warehouse, String clientId) 
    {
        Client client = warehouse.getClientById(clientId);
        if (client == null) 
        {
            System.out.println("Client not found.");
            return;
        }
        System.out.println("\nCLIENT DETAILS");
        client.displayClientInfo();
    }

    // Option 2
    private void showProducts(WarehouseBL warehouse) 
    {
        System.out.println("\nAVAILABLE PRODUCTS");
        warehouse.displayProducts();
    }

    // Option 3
    private void showClientTransactions(WarehouseBL warehouse, String clientId) 
    {
        System.out.println("\nCLIENT TRANSACTIONS");
        warehouse.displayTransactionsForClient(clientId);
    }

    // Option 4
    private void addItemToWishlist(WarehouseContext context, WarehouseBL warehouse, String clientId) 
    {
        System.out.println("\nAdd Item to Wishlist");
        String productId = context.getToken("Enter Product ID: ");

        int quantity;
        while (true) 
        {
            try 
            {
                quantity = Integer.parseInt(context.getToken("Enter quantity: "));
                break;
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Please enter a valid integer for quantity.");
            }
        }

        boolean success = warehouse.addItemToWishlist(clientId, productId, quantity);
        if (success) 
        {
            System.out.println("Item added to wishlist successfully.");
        } 
        else 
        {
            System.out.println("Failed to add item. Check Product ID and Quantity.");
        }
    }

    // Option 5
    private void displayWishlist(WarehouseBL warehouse, String clientId) 
    {
        System.out.println("\nCLIENT WISHLIST");
        warehouse.displayWishlist(clientId);
    }

    // Option 6
    private void placeOrder(WarehouseBL warehouse, String clientId) 
    {
        System.out.println("\nPlacing Order...");
        boolean success = warehouse.checkout(clientId);
        if (success) 
        {
            System.out.println("Order placed successfully.");
        } 
        else 
        {
            System.out.println("Order could not be completed.");
        }
    }

    // Option 7
    private void logout(WarehouseContext context) 
    {
        System.out.println("\nLogging out...");

        int previousLogin = context.getLogin();

        if (previousLogin == WarehouseContext.CLERK_STATE)
        {
            System.out.println("Returning to Clerk Menu...");
            context.changeState(1);
        } 
        else 
        {
            System.out.println("Returning to Login Menu...");
            context.changeState(0);
        }
    }
}
