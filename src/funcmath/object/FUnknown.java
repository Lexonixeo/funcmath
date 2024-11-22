package funcmath.object;

import funcmath.Helper;
import funcmath.exceptions.MathObjectException;

import java.io.Serial;
import java.util.ArrayList;

public class FUnknown implements MathObject {
  @Serial private static final long serialVersionUID = -3316114815988684843L;

  protected String unknownName;
  protected FInteger number;
  protected boolean isKnown;
  protected Integer level;

  public FUnknown() {
    this.number = FInteger.ZERO;
    this.isKnown = true;
  }

  public FUnknown(FInteger number, int level) {
    this.number = number;
    this.isKnown = true;
    this.level = level;
  }

  public FUnknown(FInteger number, boolean isKnown, int level) {
    this.number = number;
    this.isKnown = isKnown;
    this.level = level;
    if (!isKnown) {
        // TODO
        // Проблема: нужно придумать название числу, чтобы в уровне оно ни с кем не совпало
      // Проблема 2: нужно брать не из levelInfo, а из самого уровня => надо как-то сохранять ходовое состояние уровня
      // а зачем я тогда всем числам уровни поставил???????????7777777
      // возврат к прошлому коммиту
    }
  }

  public FUnknown(String s, int level) {
    this.level = level;
    // A=239 or 30 or B
    if (!Character.isDigit(s.charAt(0))) {
      ArrayList<String> words = Helper.wordsFromString(s.replace('=', ' '));
      this.isKnown = false;
      this.unknownName = words.get(0);
      if (words.size() == 2) {
          this.number = new FInteger(words.get(1), level);
      } else if (words.size() == 1) {
          // TODO
          // Проблема: нужно получить неизвестное число из уровня, зная лишь название числа
      } else {
          throw new MathObjectException("Избыток аргументов для FUnknown!");
      }
    } else {
      this.isKnown = true;
      this.number = new FInteger(s, level);
    }
  }

  @Override
  public FInteger get() {
    return this.number;
  }

  @Override
  public String getType() {
    return "unknown";
  }

  @Override
  public String getTypeForLevel() {
    return "целые неизвестные числа";
  }

  @Override
  public String getName() {
    return (isKnown ? null : unknownName);
  }

  @Override
  public void setName(String name) {
      this.unknownName = name;
  }

  public static FUnknown known(FUnknown number) {
    return new FUnknown(number.get(), true, number.level);
  }

  public static FUnknown unknown(FUnknown number) {
    return new FUnknown(number.get(), false, number.level);
  }

  public static FUnknown sum(FUnknown addend1, FUnknown addend2) {
    return new FUnknown(FInteger.sum(addend1.get(), addend2.get()), Helper.chooseNotNull(addend1.level, addend2.level));
  }

  public static FUnknown sub(FUnknown minuend, FUnknown subtrahend) {
    return new FUnknown(FInteger.sub(minuend.get(), subtrahend.get()), Helper.chooseNotNull(minuend.level, subtrahend.level));
  }

  public static FUnknown mul(FUnknown multiplicand, FUnknown multiplier) {
    return new FUnknown(FInteger.mul(multiplicand.get(), multiplier.get()), Helper.chooseNotNull(multiplicand.level, multiplier.level));
  }

  public static FUnknown div(FUnknown dividend, FUnknown divisor) {
    return new FUnknown(FInteger.div(dividend.get(), divisor.get()), Helper.chooseNotNull(dividend.level, divisor.level));
  }

  public static FUnknown mod(FUnknown dividend, FUnknown divisor) {
    return new FUnknown(FInteger.mod(dividend.get(), divisor.get()), Helper.chooseNotNull(dividend.level, divisor.level));
  }

  public static FUnknown pow(FUnknown base, FUnknown power) {
    return new FUnknown(FInteger.pow(base.get(), power.get()), Helper.chooseNotNull(base.level, power.level));
  }

  public static FUnknown root(FUnknown radicand, FUnknown degree) {
    return new FUnknown(FInteger.root(radicand.get(), degree.get()), Helper.chooseNotNull(radicand.level, degree.level));
  }

  public static FUnknown log(FUnknown base, FUnknown antilogarithm) {
    return new FUnknown(FInteger.log(base.get(), antilogarithm.get()), Helper.chooseNotNull(base.level, antilogarithm.level));
  }

  public static FUnknown gcd(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.gcd(number1.get(), number2.get()), Helper.chooseNotNull(number1.level, number2.level));
  }

  public static FUnknown lcm(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.lcm(number1.get(), number2.get()), Helper.chooseNotNull(number1.level, number2.level));
  }

  public static FUnknown fact(FUnknown number) {
    return new FUnknown(FInteger.fact(number.get()), number.level);
  }

  public static FUnknown concat(FUnknown leftNumber, FUnknown rightNumber) {
    return new FUnknown(FInteger.concat(leftNumber.get(), rightNumber.get()), Helper.chooseNotNull(leftNumber.level, rightNumber.level));
  }

  public static FUnknown rand(FUnknown min, FUnknown max) {
    return new FUnknown(FInteger.rand(min.get(), max.get()), Helper.chooseNotNull(min.level, max.level));
  }

  public static FUnknown and(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.and(number1.get(), number2.get()), Helper.chooseNotNull(number1.level, number2.level));
  }

  public static FUnknown or(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.or(number1.get(), number2.get()), Helper.chooseNotNull(number1.level, number2.level));
  }

  public static FUnknown xor(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.xor(number1.get(), number2.get()), Helper.chooseNotNull(number1.level, number2.level));
  }

  public static FUnknown min(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.min(number1.get(), number2.get()), Helper.chooseNotNull(number1.level, number2.level));
  }

  public static FUnknown max(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.max(number1.get(), number2.get()), Helper.chooseNotNull(number1.level, number2.level));
  }

  public static FUnknown sign(FUnknown number) {
    return new FUnknown(FInteger.sign(number.get()), number.level);
  }

  public static FUnknown abs(FUnknown number) {
    return new FUnknown(FInteger.abs(number.get()), number.level);
  }

  public static FUnknown not(FUnknown number) {
    return new FUnknown(FInteger.not(number.get()), number.level);
  }

  public static FUnknown conj(FUnknown number) {
    return new FUnknown(FInteger.conj(number.get()), number.level);
  }

  public static FUnknown arg(FUnknown number) {
    return new FUnknown(FInteger.arg(number.get()), number.level);
  }

  public static FUnknown norm(FUnknown number) {
    return new FUnknown(FInteger.norm(number.get()), number.level);
  }

  public static FUnknown[] equal(FUnknown number1, FUnknown number2, FUnknown returnNumber) {
    if (number1.compareTo(number2) == 0) return new FUnknown[] {returnNumber};
    else return new FUnknown[] {};
  }

  public int compareTo(FUnknown number) {
    return this.get().compareTo(number.get());
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FUnknown.class
        && this.get().equals(((FUnknown) obj).get())
        && this.isKnown == ((FUnknown) obj).isKnown;
  }

  @Override
  public String toString() {
    if (isKnown) {
      return this.get().toString();
    } else {
      return this.unknownName;
    }
  }
}
