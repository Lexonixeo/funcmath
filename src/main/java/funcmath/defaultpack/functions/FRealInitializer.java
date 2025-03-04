package funcmath.defaultpack.functions;

import funcmath.defaultpack.objects.FInteger;
import funcmath.defaultpack.objects.FRational;
import funcmath.defaultpack.objects.FReal;
import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;

public class FRealInitializer extends Initializer {
  @Override
  protected void initSimpleFunctions() throws NoSuchMethodException {
    initSimpleFunction2("sum");
    initSimpleFunction2("sub");
    initSimpleFunction2("mul");
    initSimpleFunction2("div");
    initSimpleFunction2("pow");
    initSimpleFunction2("root");
    initSimpleFunction2("log");
    initSimpleFunction1("fact");
    initSimpleFunction2("rand");
    initSimpleFunction2("min");
    initSimpleFunction2("max");
    initSimpleFunction1("sign");
    initSimpleFunction1("abs");
    initSimpleFunction1("sin");
    initSimpleFunction1("cos");
    initSimpleFunction1("tan");
    initSimpleFunction1("arcsin");
    initSimpleFunction1("arccos");
    initSimpleFunction1("arctan");
    initSimpleFunction2("arctan2");
    initSimpleFunction1("conj");
    initSimpleFunction1("arg");
    initSimpleFunction1("norm");
    initSimpleFunction1("floor");
    initSimpleFunction1("ceil");
  }

  private void initSimpleFunction1(String name) {
    try {
      SimpleFunctionRegister.registerSimpleFunction(
          new SimpleFunction(name, FReal.class.getMethod(name, FReal.class)));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private void initSimpleFunction2(String name) {
    try {
      SimpleFunctionRegister.registerSimpleFunction(
          new SimpleFunction(name, FReal.class.getMethod(name, FReal.class, FReal.class)));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void initTypeAndConverts() throws NoSuchMethodException {
    TypeRegister.registerType(new FReal());
    TypeRegister.registerConvert(
        new FInteger(), new FReal(), this.getClass().getMethod("convertFintFreal", FInteger.class));
    TypeRegister.registerConvert(
        new FReal(), new FInteger(), this.getClass().getMethod("convertFrealFint", FReal.class));
    TypeRegister.registerConvert(
        new FRational(),
        new FReal(),
        this.getClass().getMethod("convertFRatFreal", FRational.class));
  }

  public static FReal convertFintFreal(FInteger x) {
    return new FReal(x.get());
  }

  public static FInteger convertFrealFint(FReal x) {
    return FReal.floor(x);
  }

  public static FReal convertFRatFreal(FRational x) {
    return FReal.div(convertFintFreal(x.getNum()), convertFintFreal(x.getDen()));
  }
}
