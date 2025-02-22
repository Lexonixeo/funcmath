package funcmath.defaultpack.functions;

import funcmath.defaultpack.objects.FInteger;
import funcmath.defaultpack.objects.FNatural;
import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;

public class FIntegerInitializer extends Initializer {
  @Override
  protected void initSimpleFunctions() throws NoSuchMethodException {
    SimpleFunction sum =
        new SimpleFunction("sum", FInteger.class.getMethod("sum", FInteger.class, FInteger.class));
    String sumHash = sum.getHash().toString();
    SimpleFunctionRegister.registerSimpleFunction(sum);

    SimpleFunction sub =
        new SimpleFunction("sub", FInteger.class.getMethod("sub", FInteger.class, FInteger.class));
    String subHash = sub.getHash().toString();
    SimpleFunctionRegister.registerSimpleFunction(sub);

    SimpleFunction mul =
        new SimpleFunction("mul", FInteger.class.getMethod("mul", FInteger.class, FInteger.class));
    String mulHash = sub.getHash().toString();
    SimpleFunctionRegister.registerSimpleFunction(mul);

    // TODO
  }

  @Override
  protected void initTypeAndConverts() throws NoSuchMethodException {
    TypeRegister.registerType(new FInteger());
    TypeRegister.registerConvert(
        new FInteger(),
        new FNatural(),
        this.getClass().getMethod("convertFintFnat", FInteger.class));
    TypeRegister.registerConvert(
        new FNatural(),
        new FInteger(),
        this.getClass().getMethod("convertFnatFint", FNatural.class));
  }

  public FNatural convertFintFnat(FInteger x) {
    return new FNatural(x.get());
  }

  public FInteger convertFnatFint(FNatural x) {
    return new FInteger(x.get());
  }
}
