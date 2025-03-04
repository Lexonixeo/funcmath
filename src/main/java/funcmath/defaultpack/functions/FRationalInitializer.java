package funcmath.defaultpack.functions;

import funcmath.defaultpack.objects.FInteger;
import funcmath.defaultpack.objects.FRational;
import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;

public class FRationalInitializer extends Initializer {
  @Override
  protected void initSimpleFunctions() throws NoSuchMethodException {
    initSimpleFunction2("sum");
    initSimpleFunction2("sub");
    initSimpleFunction2("mul");
    initSimpleFunction2("div");
    initSimpleFunction2("pow");
    initSimpleFunction2("root");
    initSimpleFunction2("log");
    initSimpleFunction2("gcd");
    initSimpleFunction2("lcm");
    initSimpleFunction1("fact");
    initSimpleFunction2("rand");
    initSimpleFunction2("min");
    initSimpleFunction2("max");
    initSimpleFunction1("sign");
    initSimpleFunction1("abs");
    initSimpleFunction2("med");
    initSimpleFunction1("conj");
    initSimpleFunction1("norm");
    initSimpleFunction1("floor");
    initSimpleFunction1("ceil");
  }

  private void initSimpleFunction1(String name) {
    try {
      SimpleFunctionRegister.registerSimpleFunction(
          new SimpleFunction(name, FRational.class.getMethod(name, FRational.class)));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private void initSimpleFunction2(String name) {
    try {
      SimpleFunctionRegister.registerSimpleFunction(
          new SimpleFunction(
              name, FRational.class.getMethod(name, FRational.class, FRational.class)));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void initTypeAndConverts() throws NoSuchMethodException {
    TypeRegister.registerType(new FRational());
    TypeRegister.registerConvert(
        new FInteger(),
        new FRational(),
        this.getClass().getMethod("convertFintFrat", FInteger.class));
    TypeRegister.registerConvert(
        new FRational(),
        new FInteger(),
        this.getClass().getMethod("convertFratFint", FRational.class));
  }

  public static FRational convertFintFrat(FInteger x) {
    return new FRational(x, FInteger.ONE);
  }

  public static FInteger convertFratFint(FRational x) {
    return FInteger.div(x.getNum(), x.getDen());
  }
}
