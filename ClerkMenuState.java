public class ClerkMenuState extends BaseState {
    private static ClerkMenuState instance;

    private ClerkMenuState() {
    }

    public static ClerkMenuState instance() {
        if (instance == null)
            instance = new ClerkMenuState();
        return instance;
    }

    @Override
    public void run() {
        WarehouseContext context = WarehouseContext.instance();

        System.out.println("In clerk menu state which is not done yet, going back to login");
        context.changeState(0);
    }
}
