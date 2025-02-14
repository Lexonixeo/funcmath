package funcmath.object;

import funcmath.exceptions.MathObjectException;
import funcmath.game.Logger;
import funcmath.utility.Pair;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class TypeRegister {
  private static final HashMap<String, MathObject> TYPE_HASH_MAP = new HashMap<>();
  private static final HashMap<Pair<String, String>, Method> CONVERT_HASH_MAP = new HashMap<>();

  public static void registerType(MathObject m) {
    Logger.write("Регистрация типа " + m.getType());
    try {
      TYPE_HASH_MAP.put(m.getType(), m.getClass().getConstructor().newInstance());
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new MathObjectException(
          "Отсутствует пустой конструктор у математического объекта типа " + m.getType());
    }
  }

  // convert должен быть static и принимать только input
  public static void registerConvert(MathObject input, MathObject output, Method convert) {
    Logger.write("Регистрация перевода " + input.getType() + " в " + output.getType());
    CONVERT_HASH_MAP.put(new Pair<>(input.getType(), output.getType()), convert);
  }

  // у каждого MathObject должен быть пустой конструктор
  public static MathObject getMathObject(String type) {
    return TYPE_HASH_MAP.get(type);
  }

  // у каждого MathObject должен быть конструктор с String
  public static MathObject parseMathObject(String s, String type) {
    try {
      return TYPE_HASH_MAP
          .get(type)
          .getClass()
          .getConstructor(new Class[] {String.class})
          .newInstance(s);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new MathObjectException(
          "Отсутствует конструктор у математического объекта типа "
              + type
              + ", принимающий String!");
    }
  }

  public static MathObject parseMathObject(String s) {
    int i = 0;
    StringBuilder sb = new StringBuilder();
    while (s.charAt(i) != '=') {
      sb.append(s.charAt(i));
      i++;
    }
    String type = sb.toString();
    String other = s.substring(i + 1);
    return parseMathObject(other, type);
  }

  public static MathObject convert(MathObject input, String outputType) {
    Logger.write("Конвертация " + input + " в " + outputType);
    Pair<String, String> convertPair = new Pair<>(input.getType(), outputType);
    if (!CONVERT_HASH_MAP.containsKey(convertPair)) {
      throw new MathObjectException(
          "Не существует прямого способа конвертации " + input.getType() + " в " + outputType);
    }
    Method convert = CONVERT_HASH_MAP.get(convertPair);
    try {
      return (MathObject) convert.invoke(null, input);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static boolean isConvertibleTypes(String[] typesIn, String[] typesOut) {
    if (typesIn.length != typesOut.length) {
      return false;
    }
    for (int i = 0; i < typesIn.length; i++) {
      if (!CONVERT_HASH_MAP.containsKey(new Pair<>(typesIn[i], typesOut[i]))) {
        return false;
      }
    }
    return true;
  }
}
