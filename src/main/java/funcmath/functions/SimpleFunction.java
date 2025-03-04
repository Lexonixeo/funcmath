package funcmath.functions;

import funcmath.exceptions.FunctionException;
import funcmath.object.MathObject;
import funcmath.object.TypeRegister;
import funcmath.utility.Hash;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class SimpleFunction {
  private final String name;
  private final String[] types;
  // private final String resultType; // а зачем resultType???
  // надо ли добавить description?
  private final Method function;

  public SimpleFunction(String name, Method function) {
    this.name = name;
    try {
      Parameter[] params = function.getParameters();
      types = new String[params.length];

      for (int i = 0; i < params.length; i++) {
        types[i] = ((MathObject) params[i].getType().getConstructor().newInstance()).getType();
      }

      // this.resultType = ((MathObject)
      // function.getReturnType().getConstructor().newInstance()).getType();
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new FunctionException(
          "Отсутствует пустой конструктор у какого-то класса из параметров функции для простейшей функции "
              + name,
          e);
    }
    this.function = function;
  }

  public SimpleFunction(Function f) {
    this.name = f.getName();
    this.types = f.getTypes();
    try {
      this.function = f.getClass().getMethod("compute", MathObject[].class);
    } catch (NoSuchMethodException e) {
      throw new FunctionException(e);
    }
  }

  /*
  public Triplet<String, String[], String> getInfo() {
      return new Triplet<>(name, types, resultType);
  }
   */

  public int getArgsNumber() {
    return types.length;
  }

  // Функция должна возвращать либо MathObject, либо MathObject[]
  public MathObject[] use(MathObject... args) {
    if (getArgsNumber() != args.length) {
      throw new FunctionException(
          "Количество аргументов функции не совпадает: должно быть "
              + getArgsNumber()
              + ", но есть: "
              + args.length);
    }

    // конвертация наших MathObject в нужные типы, так что в function можете в параметрах нормально
    // указывать типы
    // т.е. func(FInteger a, FNatural b) пройдёт нормально
    MathObject[] newArgs = new MathObject[args.length];
    for (int i = 0; i < args.length; i++) {
      MathObject arg = args[i];
      if (!arg.getType().equals(types[i])) {
        arg = TypeRegister.convert(arg, types[i]);
      }
      newArgs[i] = arg;
    }

    Object res;
    try {
      res = function.invoke(null, (Object[]) newArgs);
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    try {
      return (MathObject[]) res;
    } catch (ClassCastException e) {
      return new MathObject[] {(MathObject) res};
    }
  }

  public String getName() {
    return name;
  }

  public Hash getHash() {
    return Hash.encode(
        name,
        types,
        function.hashCode()); // проблема: Method не Serializable, решение: берем hashCode()
  }

  public String[] getTypes() {
    return types;
  }

  @Override
  public String toString() {
    return this.name + "(" + String.join(", ", types) + ")";
  }
}
