package funcmath.defaultpack.functions;

import funcmath.defaultpack.objects.FComplex;
import funcmath.defaultpack.objects.FInteger;
import funcmath.defaultpack.objects.FRational;
import funcmath.defaultpack.objects.FReal;
import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;

public class FComplexInitializer extends Initializer {
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
  }

  private void initSimpleFunction1(String name) {
    try {
      SimpleFunctionRegister.registerSimpleFunction(
          new SimpleFunction(name, FComplex.class.getMethod(name, FComplex.class)));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private void initSimpleFunction2(String name) {
    try {
      SimpleFunctionRegister.registerSimpleFunction(
          new SimpleFunction(name, FComplex.class.getMethod(name, FComplex.class, FComplex.class)));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void initTypeAndConverts() throws NoSuchMethodException {
    TypeRegister.registerType(new FReal());
    TypeRegister.registerConvert(
        new FReal(), new FComplex(), this.getClass().getMethod("convertFrealFcomp", FReal.class));
  }

  public static FComplex convertFrealFcomp(FReal x) {
    return new FComplex(x, FReal.ZERO);
  }
}
