package funcmath.object;

import funcmath.exceptions.FunctionException;
import funcmath.exceptions.MathException;
import funcmath.utility.Helper;
import java.io.Serial;
import java.math.BigInteger;
import java.util.ArrayList;

public class FRational implements MathObject {
  @Serial private static final long serialVersionUID = 8367368182099731988L;

  public static final FRational NEGATIVE_ONE = new FRational(-1, 1);
  public static final FRational ZERO = new FRational(0, 1);
  public static final FRational ONE = new FRational(1, 1);
  public static final FRational TWO = new FRational(2, 1);

  protected FInteger numerator;
  protected FInteger denominator;

  public FRational() {
    this.numerator = FInteger.ZERO;
    this.denominator = FInteger.ONE;
  }

  public FRational(long numerator, long denominator) {
    if (denominator < 0) {
      numerator *= -1;
      denominator *= -1;
    }
    if (denominator == 0) {
      throw new MathException(
          "Dividing by zero doesn't make sense: " + numerator + "/" + denominator);
    }

    this.numerator = new FInteger(numerator);
    this.denominator = new FInteger(denominator);

    FInteger gcd = FInteger.gcd(this.numerator, this.denominator);
    this.numerator = FInteger.div(this.numerator, gcd);
    this.denominator = FInteger.div(this.denominator, gcd);
  }

  public FRational(FInteger numerator, FInteger denominator) {
    if (denominator.compareTo(FInteger.ZERO) < 0) {
      numerator = FInteger.mul(numerator, FInteger.NEGATIVE_ONE);
      denominator = FInteger.mul(denominator, FInteger.NEGATIVE_ONE);
    }
    if (denominator.equals(FInteger.ZERO)) {
      throw new MathException(
          "Dividing by zero doesn't make sense: " + numerator + "/" + denominator);
    }

    this.numerator = numerator;
    this.denominator = denominator;

    FInteger gcd = FInteger.gcd(this.numerator, this.denominator);
    this.numerator = FInteger.div(this.numerator, gcd);
    this.denominator = FInteger.div(this.denominator, gcd);
  }

  public FRational(BigInteger numerator, BigInteger denominator) {
    if (denominator.compareTo(BigInteger.ZERO) < 0) {
      numerator = numerator.multiply(BigInteger.ONE.negate());
      denominator = denominator.multiply(BigInteger.ONE.negate());
    }
    if (denominator.equals(BigInteger.ZERO)) {
      throw new MathException(
          "Dividing by zero doesn't make sense: " + numerator + "/" + denominator);
    }

    this.numerator = new FInteger(numerator);
    this.denominator = new FInteger(denominator);

    FInteger gcd = FInteger.gcd(this.numerator, this.denominator);
    this.numerator = FInteger.div(this.numerator, gcd);
    this.denominator = FInteger.div(this.denominator, gcd);
  }

  public FRational(String s) {
    ArrayList<String> numbers = Helper.wordsFromString(s.replace('/', ' '));
    if (numbers.size() == 1) {
      numbers.add("1");
    }
    FRational num = new FRational(new BigInteger(numbers.get(0)), new BigInteger(numbers.get(1)));
    this.numerator = num.getNum();
    this.denominator = num.getDen();
  }

  @Override
  public BigInteger[] get() {
    return new BigInteger[] {this.numerator.get(), this.denominator.get()};
  }

  @Override
  public String getType() {
    return "rational";
  }

  @Override
  public String getTypeForLevel() {
    return "рациональные числа";
  }

  @Override
  public String getName() {
    return null;
  }

  public FInteger getNum() { // сокращено из-за длинных выражений
    return numerator;
  }

  public FInteger getDen() { // сокращено из-за длинных выражений
    return denominator;
  }

  public static FRational sum(FRational addend1, FRational addend2) {
    return new FRational(
        FInteger.sum(
            FInteger.mul(addend1.getNum(), addend2.getDen()),
            FInteger.mul(addend1.getDen(), addend2.getNum())),
        FInteger.mul(addend1.getDen(), addend2.getDen()));
  }

  public static FRational sub(FRational minuend, FRational subtrahend) {
    return new FRational(
        FInteger.sub(
            FInteger.mul(minuend.getNum(), subtrahend.getDen()),
            FInteger.mul(minuend.getDen(), subtrahend.getNum())),
        FInteger.mul(minuend.getDen(), subtrahend.getDen()));
  }

  public static FRational mul(FRational multiplicand, FRational multiplier) {
    return new FRational(
        FInteger.mul(multiplicand.getNum(), multiplier.getNum()),
        FInteger.mul(multiplicand.getDen(), multiplier.getDen()));
  }

  public static FRational div(FRational dividend, FRational divisor) {
    return new FRational(
        FInteger.mul(dividend.getNum(), divisor.getDen()),
        FInteger.mul(dividend.getDen(), divisor.getNum()));
  }

  public static FRational pow(FRational base, FRational power) {
    if (power.getNum().compareTo(FInteger.ZERO) < 0) {
      return pow(div(ONE, base), mul(power, NEGATIVE_ONE));
    }

    return new FRational(
        FInteger.root(
            FInteger.pow(
                FInteger.mul(base.getNum(), FInteger.pow(FInteger.TEN, power.getDen())),
                power.getNum()),
            power.getDen()),
        FInteger.root(
            FInteger.pow(
                FInteger.mul(base.getDen(), FInteger.pow(FInteger.TEN, power.getDen())),
                power.getNum()),
            power.getDen()));
  }

  public static FRational root(FRational radicand, FRational degree) {
    return pow(radicand, div(ONE, degree));
  }

  public static FRational log(FRational base, FRational antilogarithm) {
    throw new FunctionException("Функция log временно не введена для рациональных чисел.");
  }

  // MathObject hyper(MathObject base, MathObject exponent, MathObject grade); // гиперфункция
  // подобно sum, mul, pow...
  // MathObject hroot(MathObject radicand, MathObject degree, MathObject grade);
  // MathObject hlog(MathObject base, MathObject antilogarithm, MathObject grade);

  public static FRational gcd(FRational number1, FRational number2) {
    FInteger lcm = FInteger.lcm(number1.getDen(), number2.getDen());
    FInteger updatedNumber1 = mul(number1, new FRational(lcm, FInteger.ONE)).getNum();
    FInteger updatedNumber2 = mul(number2, new FRational(lcm, FInteger.ONE)).getNum();
    FInteger gcd = FInteger.gcd(updatedNumber1, updatedNumber2);
    return new FRational(gcd, lcm);
    // умножаем дроби на НОК знаменателей, находим НОД, делим на НОК знаменателей
  }

  public static FRational lcm(FRational number1, FRational number2) {
    return div(mul(number1, number2), gcd(number1, number2));
  }

  public static FRational fact(FRational number) {
    throw new FunctionException("Функция fact временно не введена для рациональных чисел.");
  }

  public static FRational rand(FRational number1, FRational number2) {
    throw new FunctionException("Функция rand временно не введена для рациональных чисел.");
  }

  public static FRational min(FRational number1, FRational number2) {
    if (number1.compareTo(number2) <= 0) {
      return number1;
    } else {
      return number2;
    }
  }

  public static FRational max(FRational number1, FRational number2) {
    if (number1.compareTo(number2) >= 0) {
      return number1;
    } else {
      return number2;
    }
  }

  public static FRational sign(FRational number) {
    return new FRational(number.compareTo(ZERO), 1);
  }

  public static FRational abs(FRational number) {
    return new FRational(FInteger.abs(number.getNum()), FInteger.abs(number.getDen()));
  }

  public static FRational med(FRational number1, FRational number2) {
    return new FRational(
        FInteger.sum(number1.getNum(), number2.getNum()),
        FInteger.sum(number1.getDen(), number2.getDen()));
  }

  // MathObject mink(MathObject a); // функция Минковского (непрерывно отображает рациональные числа
  // на отрезке [0,1] в двоичные рациональные на этом же отрезке)
  // MathObject[] contfrac(MathObject a); // разложение дроби в цепную дробь

  public static FRational conj(FRational number) {
    return number;
  }

  public static FRational norm(FRational number) {
    return mul(number, number);
  }

  public int compareTo(FRational number) {
    return sub(this, number).getNum().compareTo(FInteger.ZERO);
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FRational.class
        && this.numerator.equals(((FRational) obj).getNum())
        && this.denominator.equals(((FRational) obj).getDen());
  }

  @Override
  public String toString() {
    return this.numerator + (this.denominator.equals(FInteger.ONE) ? "" : "/" + this.denominator);
  }
}
