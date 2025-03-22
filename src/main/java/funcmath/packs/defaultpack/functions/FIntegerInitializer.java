package funcmath.packs.defaultpack.functions;

import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;
import funcmath.packs.Initializer;
import funcmath.packs.defaultpack.objects.FInteger;
import funcmath.packs.defaultpack.objects.FNatural;

public class FIntegerInitializer extends Initializer {
  @Override
  protected void initSimpleFunctions() throws NoSuchMethodException {
    initSimpleFunction2("sum");
    initSimpleFunction2("sub");
    initSimpleFunction2("mul");
    initSimpleFunction2("div");
    initSimpleFunction2("mod");
    initSimpleFunction2("pow");
    initSimpleFunction2("root");
    initSimpleFunction2("log");
    initSimpleFunction2("gcd");
    initSimpleFunction2("lcm");
    initSimpleFunction1("fact");
    initSimpleFunction2("concat");
    initSimpleFunction2("rand");
    initSimpleFunction2("and");
    initSimpleFunction2("or");
    initSimpleFunction2("xor");
    initSimpleFunction2("min");
    initSimpleFunction2("max");
    initSimpleFunction1("sign");
    initSimpleFunction1("primes");
    initSimpleFunction1("abs");
    initSimpleFunction1("not");
    initSimpleFunction1("conj");
    initSimpleFunction1("arg");
    initSimpleFunction1("norm");
  }

  private void initSimpleFunction1(String name) throws NoSuchMethodException {
    SimpleFunctionRegister.registerSimpleFunction(
        new SimpleFunction(name, FInteger.class.getMethod(name, FInteger.class)));
  }

  private void initSimpleFunction2(String name) throws NoSuchMethodException {
    SimpleFunctionRegister.registerSimpleFunction(
        new SimpleFunction(name, FInteger.class.getMethod(name, FInteger.class, FInteger.class)));
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

  public static FNatural convertFintFnat(FInteger x) {
    return new FNatural(x.get());
  }

  public static FInteger convertFnatFint(FNatural x) {
    return new FInteger(x.get());
  }
}
