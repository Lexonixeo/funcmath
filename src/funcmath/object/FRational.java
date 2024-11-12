package funcmath.object;

import funcmath.Helper;
import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class FRational implements MathObject {
  @Serial private static final long serialVersionUID = 8367368182099731988L;

  public static final FRational NEGATIVE_ONE = new FRational(-1, 1);
  public static final FRational ZERO = new FRational(0, 1);
  public static final FRational ONE = new FRational(1, 1);
  public static final FRational TWO = new FRational(2, 1);

  protected FInteger numerator;
  protected FNatural denominator;
  private final FInteger fint = new FInteger(0);
  private final FNatural fnat = new FNatural(0);

  public FRational(long numerator, long denominator) {
    if (denominator < 0) {
      numerator *= -1;
      denominator *= -1;
    }
    if (denominator == 0) {
      throw new ArithmeticException(
          "Деление на ноль не имеет смысла: " + numerator + "/" + denominator);
    }

    this.numerator = new FInteger(numerator);
    this.denominator = new FNatural(denominator);

    FInteger gcd = fint.gcd(this.numerator, this.denominator);
    this.numerator = fint.div(this.numerator, gcd);
    this.denominator = fnat.div(this.denominator, gcd);
  }

  public FRational(FInteger numerator, FInteger denominator) {
    if (denominator.compareTo(FInteger.ZERO) < 0) {
      numerator = fint.mul(numerator, FInteger.NEGATIVE_ONE);
      denominator = fint.mul(denominator, FInteger.NEGATIVE_ONE);
    }
    if (denominator.equals(FInteger.ZERO)) {
      throw new ArithmeticException(
          "Деление на ноль не имеет смысла: " + numerator + "/" + denominator);
    }

    this.numerator = new FInteger(numerator);
    this.denominator = new FNatural(denominator);

    FInteger gcd = fint.gcd(this.numerator, this.denominator);
    this.numerator = fint.div(this.numerator, gcd);
    this.denominator = fnat.div(this.denominator, gcd);
  }

  public FRational(FInteger numerator, FNatural denominator) {
    this.numerator = numerator;
    this.denominator = denominator;
    if (denominator.equals(FNatural.ZERO)) {
      throw new ArithmeticException(
          "Деление на ноль не имеет смысла: " + numerator + "/" + denominator);
    }

    FInteger gcd = fint.gcd(this.numerator, this.denominator);
    this.numerator = fint.div(this.numerator, gcd);
    this.denominator = fnat.div(this.denominator, gcd);
  }

  public FRational(BigInteger numerator, BigInteger denominator) {
    if (denominator.compareTo(BigInteger.ZERO) < 0) {
      numerator = numerator.multiply(BigInteger.ONE.negate());
      denominator = denominator.multiply(BigInteger.ONE.negate());
    }
    if (denominator.equals(BigInteger.ZERO)) {
      throw new ArithmeticException(
          "Деление на ноль не имеет смысла: " + numerator + "/" + denominator);
    }

    this.numerator = new FInteger(numerator);
    this.denominator = new FNatural(denominator);

    FInteger gcd = fint.gcd(this.numerator, this.denominator);
    this.numerator = fint.div(this.numerator, gcd);
    this.denominator = fnat.div(this.denominator, gcd);
  }

  public FRational(MathObject number) {
    FRational num = number.getRational();
    this.numerator = num.getNum();
    this.denominator = num.getDen();
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
  public FNatural getNatural() {
    return this.getInteger().getNatural();
  }

  @Override
  public FInteger getInteger() {
    return fint.div(this.numerator, this.denominator);
  }

  @Override
  public FRational getRational() {
    return this;
  }

  @Override
  public FReal getReal() {
    return (new FReal(0)).div(this.numerator, this.denominator);
  }

  @Override
  public FComplex getComplex() {
    return new FComplex(this.getReal().get(), BigDecimal.ZERO);
  }

  public FInteger getNum() { // сокращено из-за длинных выражений
    return numerator;
  }

  public FNatural getDen() { // сокращено из-за длинных выражений
    return denominator;
  }

  @Override
  public FRational sum(MathObject addend1, MathObject addend2) {
    FRational an = new FRational(addend1);
    FRational bn = new FRational(addend2);
    return new FRational(
        fint.sum(fint.mul(an.getNum(), bn.getDen()), fint.mul(an.getDen(), bn.getNum())),
        fnat.mul(an.getDen(), bn.getDen()));
  }

  @Override
  public FRational sub(MathObject minuend, MathObject subtrahend) {
    FRational an = new FRational(minuend);
    FRational bn = new FRational(subtrahend);
    return new FRational(
        fint.sub(fint.mul(an.getNum(), bn.getDen()), fint.mul(an.getDen(), bn.getNum())),
        fnat.mul(an.getDen(), bn.getDen()));
  }

  @Override
  public FRational mul(MathObject multiplicand, MathObject multiplier) {
    FRational an = new FRational(multiplicand);
    FRational bn = new FRational(multiplier);
    return new FRational(fint.mul(an.getNum(), bn.getNum()), fnat.mul(an.getDen(), bn.getDen()));
  }

  @Override
  public FRational div(MathObject dividend, MathObject divisor) {
    FRational an = new FRational(dividend);
    FRational bn = new FRational(divisor);
    return new FRational(fint.mul(an.getNum(), bn.getDen()), fint.mul(an.getDen(), bn.getNum()));
  }

  @Override
  public MathObject mod(MathObject dividend, MathObject divisor) {
    throw new ArithmeticException("Функция mod не определена для рациональных чисел.");
  }

  @Override
  public FRational pow(MathObject base, MathObject power) {
    FRational an = new FRational(base);
    FRational bn = new FRational(power);
    if (new FInteger(bn.getNum()).get().compareTo(BigInteger.ZERO) < 0) {
      return this.pow(this.div(new FRational(1, 1), an), this.mul(bn, new FRational(-1, 1)));
    }

    return new FRational(
        fint.root(
            fint.pow(this.mul(an.getNum(), fint.pow(new FInteger(10), bn.getDen())), bn.getNum()),
            bn.getDen()),
        fint.root(
            fint.pow(this.mul(an.getDen(), fint.pow(new FInteger(10), bn.getDen())), bn.getNum()),
            bn.getDen()));
  }

  @Override
  public FRational root(MathObject radicand, MathObject degree) {
    FRational an = new FRational(radicand);
    FRational bn = new FRational(degree);
    return this.pow(an, this.div(ONE, bn));
  }

  @Override
  public FRational log(MathObject base, MathObject antilogarithm) {
    throw new NullPointerException("Функция log временно не введена для рациональных чисел.");
  }

  @Override
  public FRational gcd(MathObject a, MathObject b) {
    FRational an = new FRational(a);
    FRational bn = new FRational(b);
    FNatural lcm = fnat.div(fnat.mul(an.getDen(), bn.getDen()), fnat.gcd(an.getDen(), bn.getDen()));
    FInteger ann = this.mul(an, lcm).getInteger();
    FInteger bnn = this.mul(bn, lcm).getInteger();
    FInteger gcd = fint.gcd(ann, bnn);
    return new FRational(gcd, lcm);
    // умножаем дроби на НОК знаменателей, находим НОД, делим на НОК знаменателей
  }

  @Override
  public FRational lcm(MathObject a, MathObject b) {
    return this.div(this.mul(a, b), this.gcd(a, b));
  }

  @Override
  public FRational fact(MathObject a) {
    throw new NullPointerException("Функция fact временно не введена для рациональных чисел.");
  }

  @Override
  public MathObject conc(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция conc не определена для рациональных чисел.");
  }

  @Override
  public FRational rand(MathObject a, MathObject b) {
    throw new NullPointerException("Функция rand временно не введена для рациональных чисел.");
  }

  @Override
  public MathObject and(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция and не определена для рациональных чисел.");
  }

  @Override
  public MathObject or(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция or не определена для рациональных чисел.");
  }

  @Override
  public MathObject xor(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция xor не определена для рациональных чисел.");
  }

  @Override
  public FRational min(MathObject a, MathObject b) {
    FRational an = new FRational(a);
    FRational bn = new FRational(b);
    if (this.sub(an, bn).getNum().compareTo(FInteger.ZERO) <= 0) {
      return an;
    } else {
      return bn;
    }
  }

  @Override
  public FRational max(MathObject a, MathObject b) {
    FRational an = new FRational(a);
    FRational bn = new FRational(b);
    if (this.sub(an, bn).getNum().compareTo(FInteger.ZERO) <= 0) {
      return bn;
    } else {
      return an;
    }
  }

  @Override
  public FRational sign(MathObject a) {
    FRational an = new FRational(a);
    return new FRational(an.getNum().compareTo(FInteger.ZERO), 1);
  }

  @Override
  public MathObject[] primes(MathObject n) {
    throw new ArithmeticException("Функция primes не определена для рациональных чисел.");
  }

  @Override
  public FRational abs(MathObject a) {
    FRational an = new FRational(a);
    return new FRational(fint.abs(an.getNum()), fnat.abs(an.getDen()));
  }

  @Override
  public MathObject not(MathObject a) {
    throw new ArithmeticException("Функция not не определена для рациональных чисел.");
  }

  @Override
  public FRational med(MathObject a, MathObject b) {
    FRational an = new FRational(a);
    FRational bn = new FRational(b);
    return new FRational(fint.sum(an.getNum(), bn.getNum()), fnat.sum(an.getDen(), bn.getDen()));
  }

  @Override
  public MathObject sin(MathObject a) {
    throw new ArithmeticException("Функция sin не определена для рациональных чисел.");
  }

  @Override
  public MathObject cos(MathObject a) {
    throw new ArithmeticException("Функция cos не определена для рациональных чисел.");
  }

  @Override
  public MathObject tan(MathObject a) {
    throw new ArithmeticException("Функция tan не определена для рациональных чисел.");
  }

  @Override
  public MathObject arcsin(MathObject a) {
    throw new ArithmeticException("Функция arcsin не определена для рациональных чисел.");
  }

  @Override
  public MathObject arccos(MathObject a) {
    throw new ArithmeticException("Функция arccos не определена для рациональных чисел.");
  }

  @Override
  public MathObject arctan(MathObject a) {
    throw new ArithmeticException("Функция arctan не определена для рациональных чисел.");
  }

  @Override
  public FRational conj(MathObject a) {
    return new FRational(a);
  }

  @Override
  public FRational arg(MathObject a) {
    FRational an = new FRational(a);
    return an.getNum().compareTo(FInteger.ZERO) >= 0 ? ZERO : FReal.PI.getRational();
  }

  @Override
  public FRational norm(MathObject a) {
    FRational an = new FRational(a);
    return this.mul(an, an);
  }

  @Override
  public int compareTo(MathObject a) {
    FRational an = new FRational(a);
    return this.sub(this, an).getNum().compareTo(FInteger.ZERO);
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FRational.class
        && this.numerator.equals(((FRational) obj).getNum())
        && this.denominator.equals(((FRational) obj).getDen());
  }

  @Override
  public String toString() {
    return this.numerator + (this.denominator.equals(FNatural.ONE) ? "" : "/" + this.denominator);
  }
}
