package funcmath.function;

import funcmath.exceptions.FunctionException;
import funcmath.exceptions.JavaException;
import funcmath.object.MathObject;
import funcmath.utility.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleFunction {
  String name;
  int numberOfArgs;
  Method function;
  MathObject resultClass;

  public SimpleFunction(String name, MathObject resultClass) {
    this.name = name;
    Method[] functions = resultClass.getClass().getMethods();
    for (Method f : functions) {
      if (this.name.equals("ignore")) {
        try {
          function = MathObject.class.getMethod("ignore", MathObject.class);
        } catch (NoSuchMethodException e) {
          // how it happened?
          Log.getInstance().write("THE UNTHINKABLE HAPPENED: IT HAPPENED (SFnoignore)");
          throw new JavaException(e);
        }
      }
      if (this.name.equals(f.getName())) {
        function = f;
        break;
      }
    }
    assert function != null;
    numberOfArgs = function.getParameterCount();
    this.resultClass = resultClass;
  }

  public int getNumberOfArgs() {
    return numberOfArgs;
  }

  public Object use(MathObject... args) {
    if (numberOfArgs != args.length) {
      throw new FunctionException(
          "Number of function arguments does not match: should be "
              + numberOfArgs
              + ", but there is: "
              + args.length);
    }

    Object ans;
    try {
      ans = function.invoke(null, (Object[]) args);
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new JavaException(e);
    }
    return ans;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
