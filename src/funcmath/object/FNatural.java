package funcmath.object;

import java.io.Serial;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class FNatural implements MathObject {
  @Serial private static final long serialVersionUID = -1322027344216575257L;

  public static final FNatural ZERO = new FNatural(0);
  public static final FNatural ONE = new FNatural(1);
  public static final FNatural TWO = new FNatural(2);

  protected BigInteger number;

  public FNatural(long number) {
    if (number < 0) {
      throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
    }
    this.number = BigInteger.valueOf(number);
  }

  public FNatural(BigInteger number) {
    if (number.compareTo(BigInteger.ZERO) < 0) {
      throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
    }
    this.number = number;
  }

  public FNatural(MathObject number) {
    this.number = number.getNatural().get();
  }

  public FNatural(String s) {
    BigInteger number = new BigInteger(s);
    if (number.compareTo(BigInteger.ZERO) < 0) {
      throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
    }
    this.number = number;
  }

  @Override
  public BigInteger get() {
    return this.number;
  }

  @Override
  public FNatural getNatural() {
    return this;
  }

  @Override
  public FInteger getInteger() {
    return new FInteger(this.number);
  }

  @Override
  public FRational getRational() {
    return new FRational(this.number, BigInteger.ONE);
  }

  @Override
  public FReal getReal() {
    return new FReal(this.number);
  }

  @Override
  public FComplex getComplex() {
    return new FComplex(this.number, BigInteger.ZERO);
  }

  @Override
  public FNatural sum(MathObject addend1, MathObject addend2) {
    FNatural an = new FNatural(addend1);
    FNatural bn = new FNatural(addend2);
    return new FNatural(an.get().add(bn.get()));
  }

  @Override
  public FNatural sub(MathObject minuend, MathObject subtrahend) {
    FNatural an = new FNatural(minuend);
    FNatural bn = new FNatural(subtrahend);
    return new FNatural(an.get().add(bn.get().negate()));
  }

  @Override
  public FNatural mul(MathObject multiplicand, MathObject multiplier) {
    FNatural an = new FNatural(multiplicand);
    FNatural bn = new FNatural(multiplier);
    return new FNatural(an.get().multiply(bn.get()));
  }

  @Override
  public FNatural div(MathObject dividend, MathObject divisor) {
    FNatural an = new FNatural(dividend);
    FNatural bn = new FNatural(divisor);
    if (bn.equals(ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
    } else {
      return new FNatural(an.get().divide(bn.get()));
    }
  }

  @Override
  public FNatural mod(MathObject dividend, MathObject divisor) {
    FNatural an = new FNatural(dividend);
    FNatural bn = new FNatural(divisor);
    if (bn.equals(ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "%" + bn);
    } else {
      return new FNatural(an.get().mod(bn.get()));
    }
  }

  @Override
  public FNatural pow(MathObject base, MathObject power) {
    FNatural an = new FNatural(base);
    FNatural bn = new FNatural(power);
    if (bn.equals(ZERO)) {
      return ONE;
    } else if (this.mod(bn, TWO).equals(ZERO)) {
      FNatural cn = this.div(bn, TWO);
      try {
        FNatural dn = this.pow(an, cn);
        return this.mul(dn, dn);
      } catch (StackOverflowError e) {
        throw new RuntimeException("Стек вызова функций переполнился для функции pow.");
      }
    } else {
      FNatural cn = this.div(bn, TWO);
      try {
        FNatural dn = this.pow(an, cn);
        return this.mul(this.mul(dn, dn), an);
      } catch (StackOverflowError e) {
        throw new RuntimeException("Стек вызова функций переполнился для функции pow.");
      }
    }
  }

  @Override
  public FNatural root(MathObject radicand, MathObject degree) {
    FNatural an = new FNatural(radicand);
    FNatural bn = new FNatural(degree);
    if (bn.equals(ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "^(1/" + bn + ")");
    }

    FNatural l = ZERO;
    FNatural r = this.sum(an, ONE);
    while (this.sub(r, l).compareTo(ONE) > 0) {
      FNatural m = this.div(this.sum(r, l), TWO);
      FNatural res = this.pow(m, bn);
      if (res.compareTo(an) > 0) {
        r = m;
      } else {
        l = m;
      }
    }
    return l;
  }

  @Override
  public FNatural log(MathObject base, MathObject antilogarithm) { // TO DO
    FNatural an = new FNatural(base);
    FNatural bn = new FNatural(antilogarithm);
    if (bn.equals(ZERO)) {
      throw new ArithmeticException("Логарифм от нуля не определен.");
    } else if (an.equals(ZERO)) {
      throw new ArithmeticException("Логарифм по основанию нуля не определен.");
    } else if (an.equals(ONE)) {
      throw new ArithmeticException("Логарифм по основанию единицы не определен.");
    }

    FNatural l = ZERO;
    FNatural r = new FNatural(bn.get().bitLength() * 4L);
    while (this.sub(r, l).compareTo(ONE) > 0) {
      FNatural m = this.div(this.sum(r, l), TWO);
      FNatural res = this.pow(an, m);
      if (res.get().compareTo(bn.get()) > 0) {
        r = m;
      } else {
        l = m;
      }
    }
    return l;
  }

  @Override
  public FNatural gcd(MathObject a, MathObject b) {
    FNatural an = new FNatural(a);
    FNatural bn = new FNatural(b);
    return new FNatural(an.get().gcd(bn.get()));
  }

  @Override
  public FNatural lcm(MathObject a, MathObject b) {
    return this.div(this.mul(a, b), this.gcd(a, b));
  }

  @Override
  public FNatural fact(MathObject a) {
    FNatural an = new FNatural(a);
    if (an.equals(ZERO)) {
      return ONE;
    }

    try {
      return this.mul(fact(sub(a, ONE)), a);
    } catch (StackOverflowError e) {
      throw new RuntimeException("Стек вызова функций переполнился для функции fact.");
    }
  }

  @Override
  public FNatural conc(MathObject a, MathObject b) {
    String an = new FNatural(a).get().toString();
    String bn = new FNatural(b).get().toString();
    return new FNatural(an + bn);
  }

  @Override
  public FNatural rand(MathObject a, MathObject b) {
    FNatural an = new FNatural(a);
    FNatural bn = new FNatural(b);
    if (an.compareTo(bn) > 0) {
      return this.rand(b, a);
    }

    SecureRandom random = new SecureRandom();
    FNatural upperLimit = this.sum(this.sub(bn, an), ONE);

    BigInteger randomNumber;
    do {
      randomNumber = new BigInteger(upperLimit.get().bitLength(), random);
    } while (randomNumber.compareTo(upperLimit.get()) >= 0);

    return this.sum(new FNatural(randomNumber), an);
  }

  @Override
  public FNatural and(MathObject a, MathObject b) {
    FNatural an = new FNatural(a);
    FNatural bn = new FNatural(b);
    return new FNatural(an.get().and(bn.get()));
  }

  @Override
  public FNatural or(MathObject a, MathObject b) {
    FNatural an = new FNatural(a);
    FNatural bn = new FNatural(b);
    return new FNatural(an.get().or(bn.get()));
  }

  @Override
  public FNatural xor(MathObject a, MathObject b) {
    FNatural an = new FNatural(a);
    FNatural bn = new FNatural(b);
    return new FNatural(an.get().xor(bn.get()));
  }

  @Override
  public FNatural min(MathObject a, MathObject b) {
    FNatural an = new FNatural(a);
    FNatural bn = new FNatural(b);
    if (an.compareTo(bn) <= 0) {
      return an;
    } else {
      return bn;
    }
  }

  @Override
  public FNatural max(MathObject a, MathObject b) {
    FNatural an = new FNatural(a);
    FNatural bn = new FNatural(b);
    if (an.compareTo(bn) <= 0) {
      return bn;
    } else {
      return an;
    }
  }

  @Override
  public FNatural sign(MathObject a) {
    FNatural an = new FNatural(a);
    return new FNatural(an.compareTo(ZERO));
  }

  @Override
  public FNatural[] primes(MathObject n) {
    ArrayList<FNatural> factor = new ArrayList<>();
    FNatural an = new FNatural(n);
    FNatural bn = TWO;

    while (bn.compareTo(this.root(an, TWO)) <= 0) {
      if (this.mod(an, bn).equals(ZERO)) {
        factor.add(bn);
        an = this.div(an, bn);
      } else {
        bn = this.sum(bn, ONE);
      }
    }

    if (!an.equals(ONE)) {
      factor.add(an);
    }
    return factor.toArray(new FNatural[] {});
  }

  @Override
  public FNatural abs(MathObject a) {
    return new FNatural(a);
  }

  @Override
  public MathObject not(MathObject a) {
    throw new ArithmeticException("Функция not не определена для натуральных чисел.");
  }

  @Override
  public MathObject med(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция med не определена для натуральных чисел.");
  }

  @Override
  public MathObject sin(MathObject a) {
    throw new ArithmeticException("Функция sin не определена для натуральных чисел.");
  }

  @Override
  public MathObject cos(MathObject a) {
    throw new ArithmeticException("Функция cos не определена для натуральных чисел.");
  }

  @Override
  public MathObject tan(MathObject a) {
    throw new ArithmeticException("Функция tan не определена для натуральных чисел.");
  }

  @Override
  public MathObject arcsin(MathObject a) {
    throw new ArithmeticException("Функция arcsin не определена для натуральных чисел.");
  }

  @Override
  public MathObject arccos(MathObject a) {
    throw new ArithmeticException("Функция arccos не определена для натуральных чисел.");
  }

  @Override
  public MathObject arctan(MathObject a) {
    throw new ArithmeticException("Функция arctan не определена для натуральных чисел.");
  }

  @Override
  public FNatural conj(MathObject a) {
    return new FNatural(a);
  }

  @Override
  public FNatural arg(MathObject a) {
    return ZERO;
  }

  @Override
  public FNatural norm(MathObject a) {
    FNatural an = new FNatural(a);
    return this.mul(an, an);
  }

  @Override
  public int compareTo(MathObject a) {
    FNatural an = new FNatural(a);
    return this.get().compareTo(an.get());
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FNatural.class && this.get().equals(((FNatural) obj).get());
  }

  @Override
  public String toString() {
    return this.get().toString();
  }
}
