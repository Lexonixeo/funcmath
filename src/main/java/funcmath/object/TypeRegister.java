package funcmath.object;

import funcmath.exceptions.MathObjectException;
import funcmath.exceptions.TypeRegisterException;
import funcmath.game.Logger;
import funcmath.utility.Pair;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypeRegister {
  private static final HashMap<String, MathObject> TYPE_HASH_MAP = new HashMap<>();
  private static final HashMap<String, ArrayList<String>> CONVERT_GRAPH = new HashMap<>();
  private static final HashMap<Pair<String, String>, Method> CONVERT_HASH_MAP = new HashMap<>();

  public static void registerType(MathObject m) {
    Logger.write("Регистрация типа " + m.getType());
    try {
      TYPE_HASH_MAP.put(m.getType(), m.getClass().getConstructor().newInstance());
      CONVERT_GRAPH.put(m.getType(), new ArrayList<>());
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
    CONVERT_GRAPH.get(input.getType()).add(output.getType());
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

  // natural=45
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
    MathObject ans = input;
    ArrayList<String> way = findShortestWay(input.getType(), outputType);
    if (way == null) {
      throw new MathObjectException(
          "Не существует прямого способа конвертации " + input.getType() + " в " + outputType);
    }
    ArrayList<String> ourWay = new ArrayList<>(way.subList(1, way.size()));
    for (String node : ourWay) {
      ans = directConvert(ans, node);
    }
    return ans;
  }

  // start и end включительно
  // bfs
  private static ArrayList<String> findShortestWay(String start, String end) {
    ArrayList<String> queue = new ArrayList<>();
    queue.add(start);
    HashMap<String, ArrayList<String>> ways = new HashMap<>();
    ways.put(start, new ArrayList<>(List.of(new String[] {start})));
    for (int i = 0; i < queue.size(); i++) {
      String node = queue.get(i);
      for (String neighbour : CONVERT_GRAPH.get(node)) {
        if (ways.get(neighbour) == null) {
          queue.add(neighbour);
          ArrayList<String> wayToNeighbour = new ArrayList<>(ways.get(node));
          wayToNeighbour.add(neighbour);
          ways.put(neighbour, wayToNeighbour);
        }
      }
    }
    return ways.get(end);
  }

  private static MathObject directConvert(MathObject input, String outputType) {
    Logger.write("Конвертация " + input + " в " + outputType);
    Pair<String, String> convertPair = new Pair<>(input.getType(), outputType);
    if (!CONVERT_HASH_MAP.containsKey(convertPair)) {
      throw new MathObjectException(
          "Не существует прямого способа конвертации " + input.getType() + " в " + outputType);
    }
    Method convert = CONVERT_HASH_MAP.get(convertPair);
    try {
      return (MathObject) convert.invoke(null, input);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new TypeRegisterException(
          "Не получилось конвертировать " + input + " в " + outputType, e);
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

  public static boolean isTypeExists(String type) {
    return TYPE_HASH_MAP.containsKey(type);
  }
}
