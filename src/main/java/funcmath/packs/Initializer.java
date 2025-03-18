package funcmath.packs;

public abstract class Initializer {
  public Initializer() {}

  public void init() throws NoSuchMethodException {
    initTypeAndConverts();
    initSimpleFunctions();
  }

  protected abstract void initSimpleFunctions() throws NoSuchMethodException;

  protected abstract void initTypeAndConverts() throws NoSuchMethodException;
}
