package funcmath.object;

import java.io.Serial;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class FInteger implements MathObject {
  @Serial private static final long serialVersionUID = 4465117069025671299L;

  public static final FInteger NEGATIVE_ONE = new FInteger(-1);
  public static final FInteger ZERO = new FInteger(0);
  public static final FInteger ONE = new FInteger(1);
  public static final FInteger TWO = new FInteger(2);

  protected BigInteger number;

  public FInteger(long number) {
    this.number = BigInteger.valueOf(number);
  }

  public FInteger(BigInteger number) {
    this.number = number;
  }

  public FInteger(MathObject number) {
    this.number = number.getInteger().get();
  }

  public FInteger(String s) {
    this.number = new BigInteger(s);
  }

  @Override
  public BigInteger get() {
    return this.number;
  }

  @Override
  public FNatural getNatural() {
    return new FNatural(this.number);
  }

  @Override
  public FInteger getInteger() {
    return this;
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
  public FInteger sum(MathObject addend1, MathObject addend2) {
    FInteger an = new FInteger(addend1);
    FInteger bn = new FInteger(addend2);
    return new FInteger(an.get().add(bn.get()));
  }

  @Override
  public FInteger sub(MathObject minuend, MathObject subtrahend) {
    FInteger an = new FInteger(minuend);
    FInteger bn = new FInteger(subtrahend);
    return new FInteger(an.get().add(bn.get().negate()));
  }

  @Override
  public FInteger mul(MathObject multiplicand, MathObject multiplier) {
    FInteger an = new FInteger(multiplicand);
    FInteger bn = new FInteger(multiplier);
    return new FInteger(an.get().multiply(bn.get()));
  }

  @Override
  public FInteger div(MathObject dividend, MathObject divisor) {
    FInteger an = new FInteger(dividend);
    FInteger bn = new FInteger(divisor);
    if (bn.equals(ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
    } else {
      return new FInteger(this.sub(an, this.mod(an, bn)).get().divide(bn.get()));
    }
  }

  @Override
  public FInteger mod(MathObject dividend, MathObject divisor) {
    FInteger an = new FInteger(dividend);
    FInteger bn = new FInteger(divisor);
    if (bn.equals(ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "%" + bn);
    } else {
      return new FInteger(an.get().mod(bn.get().abs()));
    }
  }

  @Override
  public FInteger pow(MathObject base, MathObject power) {
    FInteger b = new FInteger(base);
    FInteger p = new FInteger(power);
    if (p.compareTo(ZERO) < 0 && b.compareTo(ZERO) == 0) {
      throw new ArithmeticException(
          "Деление на ноль не имеет смысла: 1/(0^" + p.get().negate() + ")");
    }

    if (p.compareTo(ZERO) < 0) {
      return b.compareTo(ZERO) > 0 || this.mod(p, TWO).equals(ZERO) ? ZERO : NEGATIVE_ONE;
    }

    if (p.equals(ZERO)) {
      return ONE;
    } else if (this.mod(p, TWO).equals(ZERO)) {
      FInteger cn = this.div(p, TWO);
      try {
        FInteger dn = this.pow(b, cn);
        return this.mul(dn, dn);
      } catch (StackOverflowError e) {
        throw new RuntimeException("Стек вызова функций переполнился для функции pow.");
      }
    } else {
      FInteger cn = this.div(p, TWO);
      try {
        FInteger dn = this.pow(b, cn);
        return this.mul(this.mul(dn, dn), b);
      } catch (StackOverflowError e) {
        throw new RuntimeException("Стек вызова функций переполнился для функции pow.");
      }
    }
  }

  @Override
  public FInteger root(MathObject radicand, MathObject degree) {
    FInteger rad = new FInteger(radicand);
    FInteger deg = new FInteger(degree);
    if (deg.compareTo(ZERO) < 0) {
      return this.root(this.pow(rad, NEGATIVE_ONE), this.mul(deg, NEGATIVE_ONE));
    } else if (deg.equals(ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + rad + "^(1/" + deg + ")");
    } else if (rad.compareTo(ZERO) < 0 && this.mod(deg, TWO).equals(ZERO)) {
      throw new ArithmeticException(
          "Корни степени, делящейся на 2, не определены для отрицательных чисел");
    }

    if (rad.compareTo(ZERO) < 0) {
      FInteger l = new FInteger(this.sub(rad, ONE));
      FInteger r = ZERO;
      while (this.sub(r, l).compareTo(ONE) > 0) {
        FInteger m = this.div(this.sum(r, l), TWO);
        FInteger res = this.pow(m, deg);
        if (res.compareTo(rad) > 0) {
          r = m;
        } else {
          l = m;
        }
      }
      return l;
    } else {
      FInteger l = NEGATIVE_ONE;
      FInteger r = this.sum(rad, ONE);
      while (this.sub(r, l).compareTo(ONE) > 0) {
        FInteger m = this.div(this.sum(r, l), TWO);
        FInteger res = this.pow(m, deg);
        if (res.compareTo(rad) > 0) {
          r = m;
        } else {
          l = m;
        }
      }
      return l;
    }
  }

  @Override
  public FInteger log(MathObject base, MathObject antilogarithm) {
    throw new NullPointerException("Функция log временно не введена для целых чисел.");
  }

  @Override
  public FInteger gcd(MathObject a, MathObject b) {
    FInteger an = new FInteger(a);
    FInteger bn = new FInteger(b);
    return new FInteger(an.get().gcd(bn.get()));
  }

  @Override
  public FInteger lcm(MathObject a, MathObject b) {
    return this.div(this.mul(a, b), this.gcd(a, b));
  }

  @Override
  public FInteger fact(MathObject a) {
    FInteger an = new FInteger(a);
    if (an.compareTo(ZERO) < 0) {
      throw new ArithmeticException("Факториал для отрицательных целых чисел не определен.");
    } else if (an.equals(ZERO)) {
      return ONE;
    }

    try {
      return this.mul(fact(sub(a, ONE)), a);
    } catch (StackOverflowError e) {
      throw new RuntimeException("Стек вызова функций переполнился для функции fact.");
    }
  }

  @Override
  public MathObject conc(MathObject a, MathObject b) {
    FInteger an = new FInteger(a);
    FInteger bn = new FInteger(b);
    if (bn.compareTo(ZERO) < 0) {
      throw new ArithmeticException(
          "Конкатенация для отрицательного второго аргумента не определена.");
    }
    String as = an.get().toString();
    String bs = bn.get().toString();
    return new FInteger(as + bs);
  }

  @Override
  public FInteger rand(MathObject a, MathObject b) {
    FInteger an = new FInteger(a);
    FInteger bn = new FInteger(b);
    if (an.compareTo(bn) > 0) {
      return this.rand(b, a);
    }

    SecureRandom random = new SecureRandom();
    FInteger upperLimit = this.sum(this.sub(bn, an), ONE);

    BigInteger randomNumber;
    do {
      randomNumber = new BigInteger(upperLimit.get().bitLength(), random);
    } while (randomNumber.compareTo(upperLimit.get()) >= 0);

    return this.sum(new FInteger(randomNumber), an);
  }

  @Override
  public FInteger and(MathObject a, MathObject b) {
    FInteger an = new FInteger(a);
    FInteger bn = new FInteger(b);
    return new FInteger(an.get().and(bn.get()));
  }

  @Override
  public FInteger or(MathObject a, MathObject b) {
    FInteger an = new FInteger(a);
    FInteger bn = new FInteger(b);
    return new FInteger(an.get().or(bn.get()));
  }

  @Override
  public FInteger xor(MathObject a, MathObject b) {
    FInteger an = new FInteger(a);
    FInteger bn = new FInteger(b);
    return new FInteger(an.get().xor(bn.get()));
  }

  @Override
  public FInteger min(MathObject a, MathObject b) {
    FInteger an = new FInteger(a);
    FInteger bn = new FInteger(b);
    if (an.compareTo(bn) <= 0) {
      return an;
    } else {
      return bn;
    }
  }

  @Override
  public FInteger max(MathObject a, MathObject b) {
    FInteger an = new FInteger(a);
    FInteger bn = new FInteger(b);
    if (an.compareTo(bn) <= 0) {
      return bn;
    } else {
      return an;
    }
  }

  @Override
  public FInteger sign(MathObject a) {
    FInteger an = new FInteger(a);
    return new FInteger(an.compareTo(ZERO));
  }

  @Override
  public FInteger[] primes(MathObject n) {
    ArrayList<FInteger> factor = new ArrayList<>();
    FInteger an = this.abs(new FInteger(n));
    FInteger bn = TWO;

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
    return factor.toArray(new FInteger[] {});
  }

  @Override
  public FInteger abs(MathObject a) {
    FInteger an = new FInteger(a);
    return new FInteger(an.get().abs());
  }

  @Override
  public FInteger not(MathObject a) {
    FInteger an = new FInteger(a);
    return new FInteger(an.get().not());
  }

  @Override
  public MathObject med(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция med не определена для целых чисел.");
  }

  @Override
  public MathObject sin(MathObject a) {
    throw new ArithmeticException("Функция sin не определена для целых чисел.");
  }

  @Override
  public MathObject cos(MathObject a) {
    throw new ArithmeticException("Функция cos не определена для целых чисел.");
  }

  @Override
  public MathObject tan(MathObject a) {
    throw new ArithmeticException("Функция tan не определена для целых чисел.");
  }

  @Override
  public MathObject arcsin(MathObject a) {
    throw new ArithmeticException("Функция arcsin не определена для целых чисел.");
  }

  @Override
  public MathObject arccos(MathObject a) {
    throw new ArithmeticException("Функция arccos не определена для целых чисел.");
  }

  @Override
  public MathObject arctan(MathObject a) {
    throw new ArithmeticException("Функция arctan не определена для целых чисел.");
  }

  @Override
  public FInteger conj(MathObject a) {
    return new FInteger(a);
  }

  @Override
  public FInteger arg(MathObject a) {
    FInteger an = new FInteger(a);
    return new FInteger(an.compareTo(ZERO) >= 0 ? 0 : -4);
  }

  @Override
  public FInteger norm(MathObject a) {
    FInteger an = new FInteger(a);
    return this.mul(an, an);
  }

  @Override
  public int compareTo(MathObject a) {
    FInteger an = new FInteger(a);
    return this.get().compareTo(an.get());
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FInteger.class && this.get().equals(((FInteger) obj).get());
  }

  @Override
  public String toString() {
    return this.get().toString();
  }
}
