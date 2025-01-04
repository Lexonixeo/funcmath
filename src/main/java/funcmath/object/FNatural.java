package funcmath.object;

import funcmath.exceptions.FunctionException;
import funcmath.exceptions.MathException;
import java.io.Serial;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class FNatural implements MathObject, Comparable<FNatural> {
  @Serial private static final long serialVersionUID = -1322027344216575257L;

  public static final FNatural ZERO = new FNatural(0);
  public static final FNatural ONE = new FNatural(1);
  public static final FNatural TWO = new FNatural(2);

  protected BigInteger number;

  public FNatural() {
    this.number = ZERO.get();
  }

  public FNatural(long number) {
    if (number < 0) {
      throw new MathException("Не существует натуральных чисел, меньших нуля: " + number);
    }
    this.number = BigInteger.valueOf(number);
  }

  public FNatural(BigInteger number) {
    if (number.compareTo(BigInteger.ZERO) < 0) {
      throw new MathException("Не существует натуральных чисел, меньших нуля: " + number);
    }
    this.number = number;
  }

  public FNatural(String s) {
    BigInteger number = new BigInteger(s);
    if (number.compareTo(BigInteger.ZERO) < 0) {
      throw new MathException("Не существует натуральных чисел, меньших нуля: " + number);
    }
    this.number = number;
  }

  @Override
  public BigInteger get() {
    return this.number;
  }

  @Override
  public String getType() {
    return "natural";
  }

  @Override
  public String getTypeForLevel() {
    return "натуральные числа";
  }

  @Override
  public String getName() {
    return null;
  }

  public static FNatural sum(FNatural addend1, FNatural addend2) {
    return new FNatural(addend1.get().add(addend2.get()));
  }

  public static FNatural sub(FNatural minuend, FNatural subtrahend) {
    return new FNatural(minuend.get().add(subtrahend.get().negate()));
  }

  public static FNatural mul(FNatural multiplicand, FNatural multiplier) {
    return new FNatural(multiplicand.get().multiply(multiplier.get()));
  }

  public static FNatural div(FNatural dividend, FNatural divisor) {
    if (divisor.equals(ZERO)) {
      throw new MathException("Dividing by zero doesn't make sense: " + dividend + "/" + divisor);
    } else {
      return new FNatural(dividend.get().divide(divisor.get()));
    }
  }

  public static FNatural mod(FNatural dividend, FNatural divisor) {
    if (divisor.equals(ZERO)) {
      throw new MathException("Dividing by zero doesn't make sense: " + dividend + "%" + divisor);
    } else {
      return new FNatural(dividend.get().mod(divisor.get()));
    }
  }

  public static FNatural pow(FNatural base, FNatural power) {
    if (power.equals(ZERO)) {
      return ONE;
    } else if (mod(power, TWO).equals(ZERO)) {
      FNatural tempPower = div(power, TWO);
      try {
        FNatural tempMultiplier = pow(base, tempPower);
        return mul(tempMultiplier, tempMultiplier);
      } catch (StackOverflowError e) {
        throw new FunctionException("Стек вызова функций переполнился для функции pow.");
      }
    } else {
      FNatural tempPower = div(power, TWO);
      try {
        FNatural tempMultiplier = pow(base, tempPower);
        return mul(mul(tempMultiplier, tempMultiplier), base);
      } catch (StackOverflowError e) {
        throw new FunctionException("Стек вызова функций переполнился для функции pow.");
      }
    }
  }

  public static FNatural root(FNatural radicand, FNatural degree) {
    if (degree.equals(ZERO)) {
      throw new MathException(
          "Dividing by zero doesn't make sense: " + radicand + "^(1/" + degree + ")");
    }

    FNatural left = ZERO;
    FNatural right = sum(radicand, ONE);
    while (sub(right, left).compareTo(ONE) > 0) {
      FNatural medium = div(sum(right, left), TWO);
      FNatural result = pow(medium, degree);
      if (result.compareTo(radicand) > 0) {
        right = medium;
      } else {
        left = medium;
      }
    }
    return left;
  }

  public static FNatural log(FNatural base, FNatural antilogarithm) {
    if (antilogarithm.equals(ZERO)) {
      throw new MathException("Логарифм от нуля не определен.");
    } else if (base.equals(ZERO)) {
      throw new MathException("Логарифм по основанию нуля не определен.");
    } else if (base.equals(ONE)) {
      throw new MathException("Логарифм по основанию единицы не определен.");
    }

    FNatural left = ZERO;
    FNatural right = new FNatural(antilogarithm.get().bitLength() * 4L);
    while (sub(right, left).compareTo(ONE) > 0) {
      FNatural medium = div(sum(right, left), TWO);
      FNatural result = pow(base, medium);
      if (result.get().compareTo(antilogarithm.get()) > 0) {
        right = medium;
      } else {
        left = medium;
      }
    }
    return left;
  }

  // MathObject hyper(MathObject base, MathObject exponent, MathObject grade); // гиперфункция
  // подобно sum, mul, pow...
  // MathObject hroot(MathObject radicand, MathObject degree, MathObject grade);
  // MathObject hlog(MathObject base, MathObject antilogarithm, MathObject grade);

  public static FNatural gcd(FNatural number1, FNatural number2) {
    return new FNatural(number1.get().gcd(number2.get()));
  }

  public static FNatural lcm(FNatural number1, FNatural number2) {
    return div(mul(number1, number2), gcd(number1, number2));
  }

  public static FNatural fact(FNatural number) {
    if (number.equals(ZERO)) {
      return ONE;
    }

    try {
      return mul(fact(sub(number, ONE)), number);
    } catch (StackOverflowError e) {
      throw new FunctionException("Стек вызова функций переполнился для функции fact.");
    }
  }

  public static FNatural concat(FNatural leftNumber, FNatural rightNumber) {
    String leftNumberString = leftNumber.get().toString();
    String rightNumberString = rightNumber.get().toString();
    return new FNatural(leftNumberString + rightNumberString);
  }

  // рандомные числа в [min, max]
  public static FNatural rand(FNatural min, FNatural max) {
    if (min.compareTo(max) > 0) {
      return rand(max, min);
    }

    SecureRandom random = new SecureRandom();
    FNatural upperLimit = sum(sub(max, min), ONE); // он не достигается, поэтому +1

    BigInteger randomNumber;
    do {
      randomNumber = new BigInteger(upperLimit.get().bitLength(), random);
    } while (randomNumber.compareTo(upperLimit.get()) >= 0);

    return sum(new FNatural(randomNumber), min);
  }

  public static FNatural and(FNatural number1, FNatural number2) {
    return new FNatural(number1.get().and(number2.get()));
  }

  public static FNatural or(FNatural number1, FNatural number2) {
    return new FNatural(number1.get().or(number2.get()));
  }

  public static FNatural xor(FNatural number1, FNatural number2) {
    return new FNatural(number1.get().xor(number2.get()));
  }

  public static FNatural min(FNatural number1, FNatural number2) {
    if (number1.compareTo(number2) <= 0) {
      return number1;
    } else {
      return number2;
    }
  }

  public static FNatural max(FNatural number1, FNatural number2) {
    if (number1.compareTo(number2) <= 0) {
      return number2;
    } else {
      return number1;
    }
  }

  public static FNatural sign(FNatural number) {
    return new FNatural(number.compareTo(ZERO));
  }

  // MathObject phi(MathObject n); // функция Эйлера
  // MathObject tau(MathObject n); // кол-во делителей числа
  // MathObject sigma(MathObject n); // сумма натуральных делителей числа
  // MathObject pi(MathObject n);

  public static FNatural[] primes(FNatural number) {
    ArrayList<FNatural> factor = new ArrayList<>();
    FNatural tempMultiplier = TWO;

    while (mul(tempMultiplier, tempMultiplier).compareTo(number) <= 0) {
      if (mod(number, tempMultiplier).equals(ZERO)) {
        factor.add(tempMultiplier);
        number = div(number, tempMultiplier);
      } else {
        tempMultiplier = sum(tempMultiplier, ONE);
      }
    }

    if (!number.equals(ONE)) {
      factor.add(number);
    }
    return factor.toArray(new FNatural[] {});
  }

  // MathObject A(MathObject m, MathObject n); // функция Аккермана

  public static FNatural abs(FNatural number) {
    return number;
  }

  public static FNatural conj(FNatural number) {
    return number;
  }

  public static FNatural arg(FNatural number) {
    return ZERO;
  }

  public static FNatural norm(FNatural number) {
    return mul(number, number);
  }

  @Override
  public int compareTo(FNatural number) {
    return this.get().compareTo(number.get());
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
