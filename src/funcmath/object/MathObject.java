package funcmath.object;

import funcmath.utility.Helper;
import funcmath.exceptions.JavaException;
import funcmath.exceptions.LevelException;
import funcmath.exceptions.MathObjectException;
import funcmath.utility.Log;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface MathObject extends Serializable {
  MathContext DEFAULT_MATHCONTEXT = new MathContext(10, RoundingMode.HALF_UP);
  HashMap<String, MathObject> MATH_OBJECT_HASH_MAP = new HashMap<>();

  Object get();

  String getType(); // вроде "natural", "integer"...

  String getTypeForLevel(); // вроде "натуральных", "целых"...

  String getName(); // название числа (типа A, B, ...), null если само число не требует названия

  static MathObject[] ignore(MathObject ignoredA) {
    return new MathObject[] {};
  }

  static boolean checkResultClassName(String resultClassName) {
    return MATH_OBJECT_HASH_MAP.containsKey(resultClassName);
  }

  static MathObject getMathObjectFromLevel(String name) {
    ArrayList<Object> generated =
        Helper.cast(Helper.read("data/currentLevel.dat"), new ArrayList<>());
    ArrayList<MathObject> numbers = Helper.cast(generated.get(0), new ArrayList<>());
    for (MathObject n : numbers) {
      if (n.getName().equals(name)) {
        return n;
      }
    }
    throw new LevelException("Число " + name + " не обнаружено!");
  }

  static String makeMathObjectName() {
    ArrayList<Object> generated =
        Helper.cast(Helper.read("data/currentLevel.dat"), new ArrayList<>());
    HashSet<String> usingNames = Helper.cast(generated.get(1), new HashSet<>());
    String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String newName = "";
    for (int i = 1; i <= usingNames.size(); i++) {
      StringBuilder name = new StringBuilder();
      int j = i;
      while (j != 0) {
        j--;
        name.append(alphabet.charAt(j % alphabet.length()));
        j++;
        j /= alphabet.length();
      }
      if (!usingNames.contains(name.toString())) {
        newName = name.toString();
        break;
      }
    }
    usingNames.add(newName);
    generated.set(1, usingNames);
    Helper.write(generated, "data/currentLevel.dat");
    return newName;
  }

  static void loadMathObject(MathObject x) {
    String type = x.getType();
    MathObject typeInstance;
    try {
      typeInstance = x.getClass().getConstructor(new Class[] {}).newInstance();
    } catch (NoSuchMethodException e) {
      throw new MathObjectException(e);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new JavaException(e);
    }
    MATH_OBJECT_HASH_MAP.put(type, typeInstance);
    new File("data/functions/" + type).mkdirs();
  }

  static MathObject getMathObject(String type) {
    return MATH_OBJECT_HASH_MAP.get(type);
  }

  static MathObject parseMathObject(String s, String type) {
    try {
      return MATH_OBJECT_HASH_MAP
          .get(type)
          .getClass()
          .getConstructor(new Class[] {String.class})
          .newInstance(s);
    } catch (NoSuchMethodException e) {
      throw new MathObjectException(e);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new JavaException(e);
    }
  }

  static void loadMathObjects() {
    Log.getInstance().write("Математические объекты загружаются...");
    MathObject.loadMathObject(new FNatural());
    MathObject.loadMathObject(new FInteger());
    MathObject.loadMathObject(new FRational());
    MathObject.loadMathObject(new FReal());
    MathObject.loadMathObject(new FComplex());
    MathObject.loadMathObject(new FUnknown());
  }

  /*
  В будущем добавить:
  FUnknownInteger (мы не видим число, но оно есть)
  FBinaryInteger (с другой системой счисления)
  FFibonacciInteger (с фибоначчиевой системой счисления)
  FErrorInteger (число с погрешностью)
  FModuloInteger (по какому-то модулю, Полная система вычетов)
  (Приведенная система вычетов)
  FGauss
  FVector
  FMatrix
  FTensor
  FContinuedFraction (число в виде цепной дроби)
  FGroup
  FTriangle и т.д.
  FString
  FColor
  FPicture
  FFile
  FMoney (деньги с разными валютами - курсы валют меняются с каждым ходом)
  FMeasure (физическая величина с размерностью)
  FAtom (или нейтроны, электроны и т.д. - частицы)
  FMolecule
  FErrorMeasure
  FMinecraftItem
  FWord (это то, из-за чего я придумал эту игру)
  FNumeral (число в виде слова)
  FMonomial
  FPolynomial
   */
}
