package funcmath.function;

import funcmath.exceptions.IllegalNumberOfArgumentsException;
import funcmath.object.MathObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleFunction {
    String name;
    int numberOfArgs;
    Method function;
    MathObject resultClass;

    public SimpleFunction(String name, MathObject resultClass) {
        this.name = name;
        Method[] functions = SimpleFunctions.class.getMethods();
        for (Method f : functions) {
            if (this.name.equals(f.getName())) {
                function = f;
                break;
            }
        }
        assert function != null;
        numberOfArgs = function.getParameterCount() - 1;
        this.resultClass = resultClass;
    }

    public int getNumberOfArgs() {
        return numberOfArgs;
    }

    public Object use(MathObject... args) {
        if (numberOfArgs != args.length) {
            throw new IllegalNumberOfArgumentsException(numberOfArgs, args.length);
        }

        MathObject[] newArgs = new MathObject[args.length + 1];
        System.arraycopy(args, 0, newArgs, 1, args.length);
        newArgs[0] = resultClass;

        Object ans;
        try {
            ans = function.invoke(null, (Object[]) newArgs);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
