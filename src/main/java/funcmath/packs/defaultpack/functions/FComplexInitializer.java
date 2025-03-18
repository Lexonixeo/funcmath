package funcmath.packs.defaultpack.functions;

import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;
import funcmath.packs.Initializer;
import funcmath.packs.defaultpack.objects.FComplex;
import funcmath.packs.defaultpack.objects.FReal;

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
    initSimpleFunction1("re");
    initSimpleFunction1("im");
  }

  private void initSimpleFunction1(String name) throws NoSuchMethodException {
    SimpleFunctionRegister.registerSimpleFunction(
        new SimpleFunction(name, FComplex.class.getMethod(name, FComplex.class)));
  }

  private void initSimpleFunction2(String name) throws NoSuchMethodException {
    SimpleFunctionRegister.registerSimpleFunction(
        new SimpleFunction(name, FComplex.class.getMethod(name, FComplex.class, FComplex.class)));
  }

  @Override
  protected void initTypeAndConverts() throws NoSuchMethodException {
    TypeRegister.registerType(new FComplex());
    TypeRegister.registerConvert(
        new FReal(), new FComplex(), this.getClass().getMethod("convertFrealFcomp", FReal.class));
  }

  public static FComplex convertFrealFcomp(FReal x) {
    return new FComplex(x, FReal.ZERO);
  }
}
