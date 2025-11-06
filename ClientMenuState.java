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

        System.out.println("In client menu state which is not done yet, going back to login");
        context.changeState(0);

    }
}
