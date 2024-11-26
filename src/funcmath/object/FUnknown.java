package funcmath.object;

import funcmath.Helper;
import funcmath.exceptions.MathObjectException;
import java.io.Serial;
import java.util.ArrayList;

public class FUnknown implements MathObject {
  @Serial private static final long serialVersionUID = -3316114815988684843L;

  protected String unknownName;
  protected FInteger number;
  protected boolean isKnown = true;
  protected boolean isFound = false;

  public FUnknown() {
    this.number = FInteger.ZERO;
  }

  public FUnknown(FInteger number) {
    this.number = number;
  }

  public FUnknown(FInteger number, boolean isKnown) {
    this.number = number;
    this.isKnown = isKnown;
    this.unknownName = MathObject.makeMathObjectName();
  }

  public FUnknown(String s) {
    // a=30 or B or 239 or FOUND_a
    if (!Character.isDigit(s.charAt(0))) {
      ArrayList<String> words = Helper.wordsFromString(s.replace('=', ' ').replace('_', ' '));
      this.isKnown = false;
      this.unknownName = "";
      int add = 0;
      if (words.get(0).equals("FOUND")) {
        add++;
        this.unknownName += "FOUND_";
        this.isFound = true;
      }
      this.unknownName += words.get(add);
      if (words.size() == 2 + add) {
        this.number = new FInteger(words.get(1 + add));
      } else if (words.size() == 1 + add) {
        FUnknown n = Helper.cast(MathObject.getMathObjectFromLevel(unknownName), new FUnknown());
        this.number = n.get();
      } else {
        throw new MathObjectException("Избыток аргументов для FUnknown!");
      }
    } else {
      this.number = new FInteger(s);
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

  public static FUnknown known(FUnknown number) {
    return new FUnknown(number.get(), true);
  }

  public static FUnknown unknown(FUnknown number) {
    return new FUnknown(number.get(), false);
  }

  public static FUnknown sum(FUnknown addend1, FUnknown addend2) {
    return new FUnknown(FInteger.sum(addend1.get(), addend2.get()));
  }

  public static FUnknown sub(FUnknown minuend, FUnknown subtrahend) {
    return new FUnknown(FInteger.sub(minuend.get(), subtrahend.get()));
  }

  public static FUnknown mul(FUnknown multiplicand, FUnknown multiplier) {
    return new FUnknown(FInteger.mul(multiplicand.get(), multiplier.get()));
  }

  public static FUnknown div(FUnknown dividend, FUnknown divisor) {
    return new FUnknown(FInteger.div(dividend.get(), divisor.get()));
  }

  public static FUnknown mod(FUnknown dividend, FUnknown divisor) {
    return new FUnknown(FInteger.mod(dividend.get(), divisor.get()));
  }

  public static FUnknown pow(FUnknown base, FUnknown power) {
    return new FUnknown(FInteger.pow(base.get(), power.get()));
  }

  public static FUnknown root(FUnknown radicand, FUnknown degree) {
    return new FUnknown(FInteger.root(radicand.get(), degree.get()));
  }

  public static FUnknown log(FUnknown base, FUnknown antilogarithm) {
    return new FUnknown(FInteger.log(base.get(), antilogarithm.get()));
  }

  public static FUnknown gcd(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.gcd(number1.get(), number2.get()));
  }

  public static FUnknown lcm(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.lcm(number1.get(), number2.get()));
  }

  public static FUnknown fact(FUnknown number) {
    return new FUnknown(FInteger.fact(number.get()));
  }

  public static FUnknown concat(FUnknown leftNumber, FUnknown rightNumber) {
    return new FUnknown(FInteger.concat(leftNumber.get(), rightNumber.get()));
  }

  public static FUnknown rand(FUnknown min, FUnknown max) {
    return new FUnknown(FInteger.rand(min.get(), max.get()));
  }

  public static FUnknown and(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.and(number1.get(), number2.get()));
  }

  public static FUnknown or(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.or(number1.get(), number2.get()));
  }

  public static FUnknown xor(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.xor(number1.get(), number2.get()));
  }

  public static FUnknown min(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.min(number1.get(), number2.get()));
  }

  public static FUnknown max(FUnknown number1, FUnknown number2) {
    return new FUnknown(FInteger.max(number1.get(), number2.get()));
  }

  public static FUnknown sign(FUnknown number) {
    return new FUnknown(FInteger.sign(number.get()));
  }

  public static FUnknown abs(FUnknown number) {
    return new FUnknown(FInteger.abs(number.get()));
  }

  public static FUnknown not(FUnknown number) {
    return new FUnknown(FInteger.not(number.get()));
  }

  public static FUnknown conj(FUnknown number) {
    return new FUnknown(FInteger.conj(number.get()));
  }

  public static FUnknown arg(FUnknown number) {
    return new FUnknown(FInteger.arg(number.get()));
  }

  public static FUnknown norm(FUnknown number) {
    return new FUnknown(FInteger.norm(number.get()));
  }

  public static FUnknown[] equal(FUnknown number1, FUnknown number2, FUnknown returnNumber) {
    if (number1.compareTo(number2) == 0) {
      return new FUnknown[] {returnNumber};
    } else {
      return new FUnknown[] {};
    }
  }

  public int compareTo(FUnknown number) {
    return this.get().compareTo(number.get());
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FUnknown.class
        && this.isKnown == ((FUnknown) obj).isKnown
        && this.isFound == ((FUnknown) obj).isFound
        && (!this.isFound && this.get().equals(((FUnknown) obj).get())
            || (this.isFound && this.unknownName.equals(((FUnknown) obj).unknownName)));
  }

  @Override
  public String toString() {
    if (isKnown) {
      return this.get().toString();
    } else {
      return this.unknownName;
    }
  }

  /*
  Я не доделал этот вид математического объекта.
  Я хотел, чтобы я с его помощью мог делать задачи двух видов:
  1. угадайка (см. уровни 43 и 44)
  2. конструктивы (см. https://sochisirius.ru/uploads/2024/02/math_0124_9_2.pdf, Квадратный трёхчлен - 2 задача 10)

  Я смог реализовать только задачи первого вида.
  Я оставляю читателям решение задач второго вида - пришлите потом пулл реквест с решением
   */
}
