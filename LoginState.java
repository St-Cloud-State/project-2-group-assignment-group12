public class LoginState extends BaseState {
    private static LoginState instance;

    private LoginState() {
    }

    public static LoginState instance() {
        if (instance == null)
            instance = new LoginState();
        return instance;
    }

    @Override
    public void run() {
        WarehouseContext context = WarehouseContext.instance();
        while (true) {
            System.out.println("\nLOGIN MENU");
            System.out.println("1. Login as Client");
            System.out.println("2. Login as Clerk");
            System.out.println("3. Login as Manager");
            System.out.println("0. Exit");
            String choice = context.getToken("Enter option: ");

            switch (choice) {
                case "1": // transition to client
                    context.setClientID(context.getToken("Enter Client ID: "));
                    context.setLogin(WarehouseContext.CLIENT_STATE);
                    context.changeState(1); // [2][1] transition #1 = client
                    return;

                case "2": // transition to clerk
                    context.setLogin(WarehouseContext.CLERK_STATE);
                    context.changeState(0); // [2][0] transition #0 = clerk
                    return;

                case "3": // transition to manager
                    context.setLogin(WarehouseContext.MANAGER_STATE);
                    context.changeState(2); // [2][2] transition #2 = manager
                    return;
                case "0":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
