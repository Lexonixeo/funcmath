package funcmath.defaultpack.functions;

import funcmath.defaultpack.objects.FNatural;
import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.object.TypeRegister;

public class FNaturalInitializer extends Initializer {
    @Override
    protected void initSimpleFunctions() throws NoSuchMethodException {
        SimpleFunction sum = new SimpleFunction("sum", FNatural.class.getMethod("sum", FNatural.class, FNatural.class));
        String sumHash = sum.getHash().toString();
        SimpleFunctionRegister.registerSimpleFunction(sum);

        SimpleFunction sub = new SimpleFunction("sub", FNatural.class.getMethod("sub", FNatural.class, FNatural.class));
        String subHash = sub.getHash().toString();
        SimpleFunctionRegister.registerSimpleFunction(sub);

        SimpleFunction mul = new SimpleFunction("mul", FNatural.class.getMethod("mul", FNatural.class, FNatural.class));
        String mulHash = sub.getHash().toString();
        SimpleFunctionRegister.registerSimpleFunction(mul);

        // TODO
    }

    @Override
    protected void initTypeAndConverts() throws NoSuchMethodException {
        TypeRegister.registerType(new FNatural());
    }
}
