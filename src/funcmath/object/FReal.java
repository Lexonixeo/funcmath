package funcmath.object;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.SecureRandom;
import org.nevec.rjm.BigDecimalMath;

public class FReal implements MathObject {
  @Serial private static final long serialVersionUID = -1314616813911607893L;

  public static final FReal EPS = new FReal(new BigDecimal("0.001"));
  public static final FReal ZERO = new FReal(0);
  public static final FReal ONE = new FReal(1);
  public static final FReal TWO = new FReal(2);
  public static final FReal E = new FReal(BigDecimalMath.exp(new MathContext(5)));
  public static final FReal PI = new FReal(BigDecimalMath.pi(new MathContext(5)));

  protected BigDecimal number;

  public FReal(double number) {
    this.number = BigDecimal.valueOf(number);
  }

  public FReal(BigInteger number) {
    this.number = new BigDecimal(number);
  }

  public FReal(BigDecimal number) {
    this.number = number;
  }

  public FReal(String s) {
    this.number =
        switch (s) {
          case "e", "E", "е", "Е" -> E.get();
          case "pi", "PI", "Pi", "pI", "π", "пи", "ПИ", "Пи", "пИ" -> PI.get();
          default -> new BigDecimal(s);
        };
  }

  public FReal(MathObject number) {
    this.number = number.getReal().get();
  }

  @Override
  public BigDecimal get() {
    return number;
  }

  @Override
  public FNatural getNatural() {
    return this.getInteger().getNatural();
  }

  @Override
  public FInteger getInteger() {
    return new FInteger(this.number.round(new MathContext(5, RoundingMode.HALF_UP)).toBigInteger());
  }

  @Override
  public FRational getRational() {
    FInteger den = new FInteger(239);
    return new FRational(this.mul(this, den).getInteger().get(), den.get());
  }

  @Override
  public FReal getReal() {
    return this;
  }

  @Override
  public FComplex getComplex() {
    return new FComplex(this.number, BigDecimal.ZERO); // to do...
  }

  @Override
  public FReal sum(MathObject addend1, MathObject addend2) {
    FReal an = new FReal(addend1);
    FReal bn = new FReal(addend2);
    return new FReal(an.get().add(bn.get()));
  }

  @Override
  public FReal sub(MathObject minuend, MathObject subtrahend) {
    FReal an = new FReal(minuend);
    FReal bn = new FReal(subtrahend);
    return new FReal(an.get().add(bn.get().negate()));
  }

  @Override
  public FReal mul(MathObject multiplicand, MathObject multiplier) {
    FReal an = new FReal(multiplicand);
    FReal bn = new FReal(multiplier);
    return new FReal(an.get().multiply(bn.get()));
  }

  @Override
  public FReal div(MathObject dividend, MathObject divisor) {
    FReal an = new FReal(dividend);
    FReal bn = new FReal(divisor);
    if (bn.get().equals(BigDecimal.ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
    }
    return new FReal(an.get().divide(bn.get(), RoundingMode.HALF_UP));
  }

  @Override
  public MathObject mod(MathObject dividend, MathObject divisor) {
    throw new ArithmeticException("Функция mod не определена для действительных чисел.");
  }

  @Override
  public FReal pow(MathObject base, MathObject power) {
    FReal b = new FReal(base);
    FReal p = new FReal(power);
    return new FReal(BigDecimalMath.pow(b.get(), p.get()));
  }

  @Override
  public FReal root(MathObject radicand, MathObject degree) {
    FReal r = new FReal(radicand);
    FReal d = new FReal(degree);
    return this.pow(r, this.div(ONE, d));
  }

  @Override
  public FReal log(MathObject base, MathObject antilogarithm) {
    FReal b = new FReal(base);
    FReal a = new FReal(antilogarithm);
    return new FReal(
        BigDecimalMath.log(a.get()).divide(BigDecimalMath.log(b.get()), RoundingMode.HALF_UP));
  }

  @Override
  public MathObject gcd(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция gcd не определена для действительных чисел.");
  }

  @Override
  public MathObject lcm(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция lcm не определена для действительных чисел.");
  }

  @Override
  public MathObject fact(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(BigDecimalMath.Gamma(an.get()));
  }

  @Override
  public MathObject conc(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция conc не определена для действительных чисел.");
  }

  @Override
  public FReal rand(MathObject a, MathObject b) {
    FReal an = new FReal(a);
    FReal bn = new FReal(b);
    if (an.compareTo(bn) > 0) {
      return this.rand(b, a);
    }
    SecureRandom random = new SecureRandom();
    return new FReal(
        random.nextDouble(an.get().doubleValue(), bn.get().doubleValue())); // потом исправить
  }

  @Override
  public MathObject and(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция and не определена для действительных чисел.");
  }

  @Override
  public MathObject or(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция or не определена для действительных чисел.");
  }

  @Override
  public MathObject xor(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция xor не определена для действительных чисел.");
  }

  @Override
  public FReal min(MathObject a, MathObject b) {
    FReal an = new FReal(a);
    FReal bn = new FReal(b);
    if (an.compareTo(bn) <= 0) {
      return an;
    } else {
      return bn;
    }
  }

  @Override
  public FReal max(MathObject a, MathObject b) {
    FReal an = new FReal(a);
    FReal bn = new FReal(b);
    if (an.compareTo(bn) <= 0) {
      return bn;
    } else {
      return an;
    }
  }

  @Override
  public FReal sign(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(an.compareTo(ZERO));
  }

  @Override
  public MathObject[] primes(MathObject n) {
    throw new ArithmeticException("Функция primes не определена для действительных чисел.");
  }

  @Override
  public FReal abs(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(an.get().abs());
  }

  @Override
  public MathObject not(MathObject a) {
    throw new ArithmeticException("Функция not не определена для действительных чисел.");
  }

  @Override
  public MathObject med(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция med не определена для действительных чисел.");
  }

  @Override
  public FReal sin(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(BigDecimalMath.sin(an.get()));
  }

  @Override
  public FReal cos(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(BigDecimalMath.cos(an.get()));
  }

  @Override
  public FReal tan(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(BigDecimalMath.tan(an.get()));
  }

  @Override
  public FReal arcsin(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(BigDecimalMath.asin(an.get()));
  }

  @Override
  public FReal arccos(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(BigDecimalMath.acos(an.get()));
  }

  @Override
  public FReal arctan(MathObject a) {
    FReal an = new FReal(a);
    return new FReal(BigDecimalMath.atan(an.get()));
  }

  @Override
  public FReal conj(MathObject a) {
    return new FReal(a);
  }

  @Override
  public FReal arg(MathObject a) {
    FReal an = new FReal(a);
    return (an.compareTo(ZERO) >= 0 ? ZERO : this.sub(ZERO, PI));
  }

  @Override
  public FReal norm(MathObject a) {
    FReal an = new FReal(a);
    return this.mul(an, an);
  }

  @Override
  public int compareTo(MathObject a) {
    FReal an = new FReal(a);
    return this.get().compareTo(an.get());
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FReal.class
        && this.sub(this, EPS).compareTo((FReal) obj) < 0
        && this.sum(this, EPS).compareTo((FReal) obj) > 0;
  }

  @Override
  public String toString() {
    return this.get().toString();
  }
}
