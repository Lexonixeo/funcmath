package funcmath.object;

import ch.obermuhlner.math.big.BigDecimalMath;
import funcmath.exceptions.MathException;
import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.SecureRandom;

public class FReal implements MathObject, Comparable<FReal> {
  @Serial private static final long serialVersionUID = -1314616813911607893L;

  public static final FReal EPS = new FReal(new BigDecimal("0.0001"));
  public static final FReal ZERO = new FReal(0);
  public static final FReal ONE = new FReal(1);
  public static final FReal TWO = new FReal(2);
  public static final FReal E = new FReal(BigDecimalMath.e(DEFAULT_MATHCONTEXT));
  public static final FReal PI = new FReal(BigDecimalMath.pi(DEFAULT_MATHCONTEXT));

  protected BigDecimal number;

  public FReal() {
    this.number = ZERO.get();
  }

  public FReal(double number) {
    this.number = BigDecimal.valueOf(number);
  }

  public FReal(BigInteger number) {
    this.number = new BigDecimal(number, DEFAULT_MATHCONTEXT);
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

  @Override
  public BigDecimal get() {
    return number;
  }

  @Override
  public String getType() {
    return "real";
  }

  @Override
  public String getTypeForLevel() {
    return "действительные числа";
  }

  @Override
  public String getName() {
    return null;
  }

  public static FReal sum(FReal addend1, FReal addend2) {
    return new FReal(addend1.get().add(addend2.get()));
  }

  public static FReal sub(FReal minuend, FReal subtrahend) {
    return new FReal(minuend.get().add(subtrahend.get().negate()));
  }

  public static FReal mul(FReal multiplicand, FReal multiplier) {
    return new FReal(multiplicand.get().multiply(multiplier.get()));
  }

  public static FReal div(FReal dividend, FReal divisor) {
    if (divisor.get().equals(BigDecimal.ZERO)) {
      throw new MathException("Деление на ноль не имеет смысла: " + dividend + "/" + divisor);
    }
    return new FReal(dividend.get().divide(divisor.get(), RoundingMode.HALF_UP));
  }

  public static FReal pow(FReal base, FReal power) {
    return new FReal(BigDecimalMath.pow(base.get(), power.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal root(FReal radicand, FReal degree) {
    return pow(radicand, div(ONE, degree));
  }

  public static FReal log(FReal base, FReal antilogarithm) {
    return new FReal(
        BigDecimalMath.log(antilogarithm.get(), DEFAULT_MATHCONTEXT)
            .divide(BigDecimalMath.log(base.get(), DEFAULT_MATHCONTEXT), DEFAULT_MATHCONTEXT));
  }

  // MathObject hyper(MathObject base, MathObject exponent, MathObject grade); // гиперфункция
  // подобно sum, mul, pow...
  // MathObject hroot(MathObject radicand, MathObject degree, MathObject grade);
  // MathObject hlog(MathObject base, MathObject antilogarithm, MathObject grade);

  public static FReal fact(FReal number) {
    return new FReal(BigDecimalMath.factorial(number.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal rand(FReal min, FReal max) {
    if (min.compareTo(max) > 0) {
      return rand(max, min);
    }
    SecureRandom random = new SecureRandom();
    return new FReal(
        random.nextDouble(min.get().doubleValue(), max.get().doubleValue())); // потом исправить
  }

  public static FReal min(FReal number1, FReal number2) {
    if (number1.compareTo(number2) <= 0) {
      return number1;
    } else {
      return number2;
    }
  }

  public static FReal max(FReal number1, FReal number2) {
    if (number1.compareTo(number2) <= 0) {
      return number2;
    } else {
      return number1;
    }
  }

  public static FReal sign(FReal number) {
    return new FReal(number.compareTo(ZERO));
  }

  public static FReal abs(FReal number) {
    return new FReal(number.get().abs());
  }

  public static FReal sin(FReal number) {
    return new FReal(BigDecimalMath.sin(number.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal cos(FReal number) {
    return new FReal(BigDecimalMath.cos(number.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal tan(FReal number) {
    return new FReal(BigDecimalMath.tan(number.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal arcsin(FReal number) {
    return new FReal(BigDecimalMath.asin(number.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal arccos(FReal number) {
    return new FReal(BigDecimalMath.acos(number.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal arctan(FReal number) {
    return new FReal(BigDecimalMath.atan(number.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal arctan2(FReal y, FReal x) {
    return new FReal(BigDecimalMath.atan2(y.get(), x.get(), DEFAULT_MATHCONTEXT));
  }

  public static FReal conj(FReal number) {
    return number;
  }

  public static FReal arg(FReal number) {
    return (number.compareTo(ZERO) >= 0 ? ZERO : sub(ZERO, PI));
  }

  public static FReal norm(FReal number) {
    return mul(number, number);
  }

  @Override
  public int compareTo(FReal number) {
    return this.get().compareTo(number.get());
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FReal.class
        && sub(this, EPS).compareTo((FReal) obj) < 0
        && sum(this, EPS).compareTo((FReal) obj) > 0;
  }

  @Override
  public String toString() {
    return this.get().toString();
  }
}
