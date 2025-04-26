package funcmath.packs.defaultpack.functions;

import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;
import funcmath.packs.Initializer;
import funcmath.packs.defaultpack.objects.FInteger;
import funcmath.packs.defaultpack.objects.FRational;
import funcmath.packs.defaultpack.objects.FReal;

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

  private void initSimpleFunction1(String name) throws NoSuchMethodException {
    SimpleFunctionRegister.registerSimpleFunction(
        new SimpleFunction(name, FReal.class.getMethod(name, FReal.class)));
  }

  private void initSimpleFunction2(String name) throws NoSuchMethodException {
    SimpleFunctionRegister.registerSimpleFunction(
        new SimpleFunction(name, FReal.class.getMethod(name, FReal.class, FReal.class)));
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
    TypeRegister.registerConvert(
        new FReal(), new FRational(), this.getClass().getMethod("convertFrealFrat", FReal.class));
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

  public static FRational convertFrealFrat(FReal x) {
    FInteger den = FInteger.rand(FInteger.ONE, new FInteger(1000));
    FInteger num = convertFrealFint(FReal.mul(x, new FReal(den.get())));
    return new FRational(num, den);
  }
}
