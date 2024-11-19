package funcmath.object;

import funcmath.exceptions.FunctionException;
import java.io.Serial;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class FInteger extends MathObject {
  @Serial private static final long serialVersionUID = 4465117069025671299L;

  public static final FInteger NEGATIVE_ONE = new FInteger(-1);
  public static final FInteger ZERO = new FInteger(0);
  public static final FInteger ONE = new FInteger(1);
  public static final FInteger TWO = new FInteger(2);
  public static final FInteger TEN = new FInteger(10);

  protected BigInteger number;

  public FInteger(long number) {
    this.number = BigInteger.valueOf(number);
  }

  public FInteger(BigInteger number) {
    this.number = number;
  }

  public FInteger(String s) {
    this.number = new BigInteger(s);
  }

  @Override
  public BigInteger get() {
    return this.number;
  }

  public static FInteger sum(FInteger addend1, FInteger addend2) {
    return new FInteger(addend1.get().add(addend2.get()));
  }

  public static FInteger sub(FInteger minuend, FInteger subtrahend) {
    return new FInteger(minuend.get().add(subtrahend.get().negate()));
  }

  public static FInteger mul(FInteger multiplicand, FInteger multiplier) {
    return new FInteger(multiplicand.get().multiply(multiplier.get()));
  }

  public static FInteger div(FInteger dividend, FInteger divisor) {
    if (divisor.equals(ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + dividend + "/" + divisor);
    } else {
      return new FInteger(sub(dividend, mod(dividend, divisor)).get().divide(dividend.get()));
    }
  }

  public static FInteger mod(FInteger dividend, FInteger divisor) {
    if (divisor.equals(ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + dividend + "%" + divisor);
    } else {
      return new FInteger(dividend.get().mod(divisor.get().abs()));
    }
  }

  public static FInteger pow(FInteger base, FInteger power) {
    if (power.compareTo(ZERO) < 0 && base.compareTo(ZERO) == 0) {
      throw new ArithmeticException(
          "Деление на ноль не имеет смысла: 1/(0^" + power.get().negate() + ")");
    }

    if (power.equals(ZERO)) {
      return ZERO;
    } else if (base.equals(ONE) || base.equals(NEGATIVE_ONE) && mod(power, TWO).equals(ZERO)) {
      return ONE;
    } else if (base.equals(NEGATIVE_ONE) && mod(power, TWO).equals(ONE)) {
      return NEGATIVE_ONE;
    } else if (power.compareTo(ZERO) < 0) {
      return (base.compareTo(ZERO) > 1 || mod(power, TWO).equals(ZERO) ? ZERO : NEGATIVE_ONE);
    } else if (mod(power, TWO).equals(ZERO)) {
      FInteger tempPower = div(power, TWO);
      try {
        FInteger tempMultiplier = pow(base, tempPower);
        return mul(tempMultiplier, tempMultiplier);
      } catch (StackOverflowError e) {
        throw new FunctionException("Стек вызова функций переполнился для функции pow.");
      }
    } else {
      FInteger tempPower = div(power, TWO);
      try {
        FInteger tempMultiplier = pow(base, tempPower);
        return mul(mul(tempMultiplier, tempMultiplier), base);
      } catch (StackOverflowError e) {
        throw new FunctionException("Стек вызова функций переполнился для функции pow.");
      }
    }
  }

  public static FInteger root(FInteger radicand, FInteger degree) {
    if (degree.compareTo(ZERO) < 0) {
      return root(pow(radicand, NEGATIVE_ONE), mul(degree, NEGATIVE_ONE));
    } else if (degree.equals(ZERO)) {
      throw new ArithmeticException(
          "Деление на ноль не имеет смысла: " + radicand + "^(1/" + degree + ")");
    } else if (radicand.compareTo(ZERO) < 0 && mod(degree, TWO).equals(ZERO)) {
      throw new ArithmeticException(
          "Корни степени, делящейся на 2, не определены для отрицательных чисел");
    } else if (radicand.compareTo(ZERO) < 0) {
      FInteger left = sub(radicand, ONE);
      FInteger right = ZERO;
      while (sub(right, left).compareTo(ONE) > 0) {
        FInteger medium = div(sum(right, left), TWO);
        FInteger result = pow(medium, degree);
        if (result.compareTo(radicand) > 0) {
          right = medium;
        } else {
          left = medium;
        }
      }
      return left;
    } else {
      FInteger left = NEGATIVE_ONE;
      FInteger right = sum(radicand, ONE);
      while (sub(right, left).compareTo(ONE) > 0) {
        FInteger medium = div(sum(right, left), TWO);
        FInteger result = pow(medium, degree);
        if (result.compareTo(radicand) > 0) {
          right = medium;
        } else {
          left = medium;
        }
      }
      return left;
    }
  }

  public static FInteger log(FInteger base, FInteger antilogarithm) {
    throw new NullPointerException("Функция log временно не введена для целых чисел.");
  }

  // MathObject hyper(MathObject base, MathObject exponent, MathObject grade); // гиперфункция
  // подобно sum, mul, pow...
  // MathObject hroot(MathObject radicand, MathObject degree, MathObject grade);
  // MathObject hlog(MathObject base, MathObject antilogarithm, MathObject grade);

  public static FInteger gcd(FInteger number1, FInteger number2) {
    return new FInteger(number1.get().gcd(number2.get()));
  }

  public static FInteger lcm(FInteger number1, FInteger number2) {
    return div(mul(number1, number2), gcd(number1, number2));
  }

  public static FInteger fact(FInteger number) {
    if (number.compareTo(ZERO) < 0) {
      throw new ArithmeticException("Факториал для отрицательных целых чисел не определен.");
    } else if (number.equals(ZERO)) {
      return ONE;
    }

    try {
      return mul(fact(sub(number, ONE)), number);
    } catch (StackOverflowError e) {
      throw new RuntimeException("Стек вызова функций переполнился для функции fact.");
    }
  }

  public static FInteger concat(FInteger leftNumber, FInteger rightNumber) {
    if (rightNumber.compareTo(ZERO) < 0) {
      throw new ArithmeticException(
          "Конкатенация для отрицательного второго аргумента не определена.");
    }
    String leftNumberString = leftNumber.get().toString();
    String rightNumberString = rightNumber.get().toString();
    return new FInteger(leftNumberString + rightNumberString);
  }

  public static FInteger rand(FInteger min, FInteger max) {
    if (min.compareTo(max) > 0) {
      return rand(max, min);
    }

    SecureRandom random = new SecureRandom();
    FInteger upperLimit = sum(sub(max, min), ONE);

    BigInteger randomNumber;
    do {
      randomNumber = new BigInteger(upperLimit.get().bitLength(), random);
    } while (randomNumber.compareTo(upperLimit.get()) >= 0);

    return sum(new FInteger(randomNumber), min);
  }

  public static FInteger and(FInteger number1, FInteger number2) {
    return new FInteger(number1.get().and(number2.get()));
  }

  public static FInteger or(FInteger number1, FInteger number2) {
    return new FInteger(number1.get().or(number2.get()));
  }

  public static FInteger xor(FInteger number1, FInteger number2) {
    return new FInteger(number1.get().xor(number2.get()));
  }

  public static FInteger min(FInteger number1, FInteger number2) {
    if (number1.compareTo(number2) <= 0) {
      return number1;
    } else {
      return number2;
    }
  }

  public static FInteger max(FInteger number1, FInteger number2) {
    if (number1.compareTo(number2) <= 0) {
      return number2;
    } else {
      return number1;
    }
  }

  public static FInteger sign(FInteger number) {
    return new FInteger(number.compareTo(ZERO));
  }

  public static FInteger[] primes(FInteger number) {
    ArrayList<FInteger> factor = new ArrayList<>();
    FInteger tempMultiplier = TWO;
    FInteger sqrt = root(number, TWO);

    while (tempMultiplier.compareTo(sqrt) <= 0) {
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
    return factor.toArray(new FInteger[] {});
  }

  public static FInteger abs(FInteger number) {
    return new FInteger(number.get().abs());
  }

  public static FInteger not(FInteger number) {
    return new FInteger(number.get().not());
  }

  public static FInteger conj(FInteger number) {
    return number;
  }

  public static FInteger arg(FInteger number) {
    return new FInteger(number.compareTo(ZERO) >= 0 ? 0 : -4);
  }

  public static FInteger norm(FInteger number) {
    return mul(number, number);
  }

  public int compareTo(FInteger number) {
    return this.get().compareTo(number.get());
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
