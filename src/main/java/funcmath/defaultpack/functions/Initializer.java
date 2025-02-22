package funcmath.defaultpack.functions;

public abstract class Initializer {
    public Initializer() {}
    public void init() throws NoSuchMethodException {
        initSimpleFunctions();
        initTypeAndConverts();
    }
    abstract protected void initSimpleFunctions() throws NoSuchMethodException;
    abstract protected void initTypeAndConverts() throws NoSuchMethodException;
}
