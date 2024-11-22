package funcmath.object;

import funcmath.exceptions.MathObjectException;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

public interface MathObject extends Serializable {
  MathContext DEFAULT_MATHCONTEXT = new MathContext(10, RoundingMode.HALF_UP);
  HashMap<String, MathObject> MATH_OBJECT_HASH_MAP = new HashMap<>();

  Object get();

  String getType(); // вроде "natural", "integer"...

  String getTypeForLevel(); // вроде "натуральных", "целых"...

  String getName(); // название числа (типо A, B, ...), null если само число

  void setName(String name);

  static void loadMathObject(MathObject x) {
    String type = x.getType();
    MathObject typeInstance;
    try {
      typeInstance = x.getClass().getConstructor(new Class[] {}).newInstance();
    } catch (NoSuchMethodException e) {
      throw new MathObjectException(e.getMessage());
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    MATH_OBJECT_HASH_MAP.put(type, typeInstance);
    new File("data/functions/" + type).mkdirs();
  }

  static MathObject getMathObject(String type) {
    return MATH_OBJECT_HASH_MAP.get(type);
  }

  static MathObject parseMathObject(String s, String type, int level) {
    try {
      return MATH_OBJECT_HASH_MAP
          .get(type)
          .getClass()
          .getConstructor(new Class[] {String.class, int.class})
          .newInstance(s, level);
    } catch (NoSuchMethodException e) {
      throw new MathObjectException(e.getMessage());
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  static MathObject[] ignore(MathObject ignoredA) {
    return new MathObject[] {};
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
