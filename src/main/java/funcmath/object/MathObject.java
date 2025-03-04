package funcmath.object;

import funcmath.utility.Hash;
import java.io.Serializable;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashSet;

public interface MathObject extends Serializable {
  MathContext DEFAULT_MATHCONTEXT = new MathContext(10, RoundingMode.HALF_UP);

  // если произошла ошибка в математике, вызывайте, пожалуйста, MathException
  // пожалуйста, в каждом MathObject генерируйте serialVersionUID!

  // const для каждого вида MathObject
  String getType();

  // String getShowableType();

  // хочется, чтобы можно было сделать общий класс FModulo, и сделать общий тип modulo{x}, и modulo7
  // дает FModulo(7, s) и т.д.

  // для каждого объекта MathObject
  // void setName(); - заменяем через generateName
  String getName(); // ставьте какое-нибудь имя по умолчанию,

  // будем вызывать generateName если будут коллизии при !equals
  // мы потом добавляем в usedNames сгенерированное имя
  void generateName(HashSet<String> usedNames);

  Hash getHash();
}
