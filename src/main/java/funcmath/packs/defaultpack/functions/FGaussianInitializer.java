package funcmath.packs.defaultpack.functions;

import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;
import funcmath.packs.Initializer;
import funcmath.packs.defaultpack.objects.FComplex;
import funcmath.packs.defaultpack.objects.FGaussian;
import funcmath.packs.defaultpack.objects.FInteger;

public class FGaussianInitializer extends Initializer {
  protected void initSimpleFunctions() throws NoSuchMethodException {
    /*
    initSimpleFunction2("sum");
    initSimpleFunction2("sub");
    initSimpleFunction2("mul");
    initSimpleFunction2("div");
    initSimpleFunction2("pow");
    initSimpleFunction2("root");
    initSimpleFunction2("log");
    initSimpleFunction1("fact");
    initSimpleFunction1("sign");
    initSimpleFunction1("abs");
    initSimpleFunction1("sin");
    initSimpleFunction1("cos");
    initSimpleFunction1("tan");
    initSimpleFunction1("arcsin");
    initSimpleFunction1("arccos");
    initSimpleFunction1("arctan");
    initSimpleFunction1("conj");
    initSimpleFunction1("arg");
    initSimpleFunction1("norm");
    */
    initSimpleFunction1("re");
    initSimpleFunction1("im");
  }

  private void initSimpleFunction1(String name) throws NoSuchMethodException {
    SimpleFunctionRegister.registerSimpleFunction(
        new SimpleFunction(name, FGaussian.class.getMethod(name, FGaussian.class)));
  }

  private void initSimpleFunction2(String name) throws NoSuchMethodException {
    SimpleFunctionRegister.registerSimpleFunction(
        new SimpleFunction(
            name, FGaussian.class.getMethod(name, FGaussian.class, FGaussian.class)));
  }

  @Override
  protected void initTypeAndConverts() throws NoSuchMethodException {
    TypeRegister.registerType(new FGaussian());
    TypeRegister.registerConvert(
        new FInteger(),
        new FGaussian(),
        this.getClass().getMethod("convertFintFgauss", FInteger.class));
    TypeRegister.registerConvert(
        new FGaussian(),
        new FComplex(),
        this.getClass().getMethod("convertFgaussFcmplx", FGaussian.class));
  }

  public static FGaussian convertFintFgauss(FInteger x) {
    return new FGaussian(x, FInteger.ZERO);
  }

  public static FComplex convertFgaussFcmplx(FGaussian x) {
    return new FComplex(x.getRe().get(), x.getIm().get());
  }
}
