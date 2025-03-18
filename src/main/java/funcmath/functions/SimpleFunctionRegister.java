package funcmath.functions;

import funcmath.game.Logger;
import java.util.*;

public class SimpleFunctionRegister {
  private static final HashMap<String, SimpleFunction> SF_HASH_MAP = new HashMap<>();

  public static void registerSimpleFunction(SimpleFunction f) {
    Logger.write("Регистрация простейшей функции " + f.getName());
    /*
    if (SF_HASH_MAP.containsKey(f.getHash())) {
        throw new FunctionException("Коллизия простейших функций: " + f + " и " + SF_HASH_MAP.get(f.getInfo()));
    }
     */
    SF_HASH_MAP.put(encodeSimpleFunction(f), f);
  }

  public static String encodeSimpleFunction(SimpleFunction f) {
    return "f:" + f.getName() + ":" + String.join(":", f.getTypes());
  }

  public static SimpleFunction getSimpleFunction(String key) {
    return SF_HASH_MAP.get(key);
  }

  public static HashSet<String> getSfStringKeys() {
    return new HashSet<>(SF_HASH_MAP.keySet());
  }

  public static HashMap<String, SimpleFunction> getSfHashMap() {
    return SF_HASH_MAP;
  }
}
