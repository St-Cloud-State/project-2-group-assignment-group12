public abstract class BaseState {
    protected static WarehouseContext context;

    protected BaseState() {
    }

    public abstract void run();
}
