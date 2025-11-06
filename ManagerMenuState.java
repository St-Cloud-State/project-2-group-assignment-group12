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

        System.out.println("In manager state which is not done yet, going back to login");
        context.changeState(0);

    }
}
