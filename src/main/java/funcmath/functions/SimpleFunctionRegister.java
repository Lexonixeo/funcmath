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
    SF_HASH_MAP.put(f.getHash().toString(), f);
  }

  public static SimpleFunction getSimpleFunction(String hash) {
    return SF_HASH_MAP.get(hash);
  }

  public static HashSet<String> getSfStringHashes() {
    return new HashSet<>(SF_HASH_MAP.keySet());
  }

  public static HashMap<String, SimpleFunction> getSfHashMap() {
    return SF_HASH_MAP;
  }
}
