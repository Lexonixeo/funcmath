package funcmath.object;

import funcmath.Helper;
import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class FComplex implements MathObject {
  @Serial private static final long serialVersionUID = -6539369190993703598L;

  public static final FComplex NEGATIVE_ONE = new FComplex(-1, 0);
  public static final FComplex ZERO = new FComplex(0, 0);
  public static final FComplex ONE = new FComplex(1, 0);
  public static final FComplex TWO = new FComplex(2, 0);
  public static final FComplex IMAGINARY_UNIT = new FComplex(0, 1);

  protected FReal real, imaginary;
  private final FReal freal = new FReal(0);

  public FComplex(double real, double imaginary) {
    this.real = new FReal(real);
    this.imaginary = new FReal(imaginary);
  }

  public FComplex(BigInteger real, BigInteger imaginary) {
    this.real = new FReal(real);
    this.imaginary = new FReal(imaginary);
  }

  public FComplex(BigDecimal real, BigDecimal imaginary) {
    this.real = new FReal(real);
    this.imaginary = new FReal(imaginary);
  }

  public FComplex(FReal real, FReal imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  public FComplex(String s) {
    ArrayList<String> snumbers =
        Helper.wordsFromString(s.replace('i', ' ').replace('+', ' ').replaceAll("-", " -"));
    this.real = new FReal(snumbers.get(0));
    this.imaginary = new FReal(snumbers.get(1));
  }

  public FComplex(MathObject number) {
    FComplex complex = number.getComplex();
    this.real = complex.getRe();
    this.imaginary = complex.getIm();
  }

  @Override
  public BigDecimal[] get() {
    return new BigDecimal[] {this.real.get(), this.imaginary.get()};
  }

  @Override
  public FNatural getNatural() {
    return this.getReal().getNatural();
  }

  @Override
  public FInteger getInteger() {
    return this.getReal().getInteger();
  }

  @Override
  public FRational getRational() {
    return this.getReal().getRational();
  }

  @Override
  public FReal getReal() {
    return real;
  }

  public FReal getRe() {
    return real;
  }

  public FReal getIm() {
    return imaginary;
  }

  @Override
  public FComplex getComplex() {
    return this;
  }

  @Override
  public FComplex sum(MathObject addend1, MathObject addend2) {
    FComplex an = new FComplex(addend1);
    FComplex bn = new FComplex(addend2);
    return new FComplex(freal.sum(an.getRe(), bn.getRe()), freal.sum(an.getIm(), bn.getIm()));
  }

  @Override
  public FComplex sub(MathObject minuend, MathObject subtrahend) {
    FComplex an = new FComplex(minuend);
    FComplex bn = new FComplex(subtrahend);
    return new FComplex(freal.sub(an.getRe(), bn.getRe()), freal.sub(an.getIm(), bn.getIm()));
  }

  @Override
  public FComplex mul(MathObject multiplicand, MathObject multiplier) {
    FComplex an = new FComplex(multiplicand);
    FComplex bn = new FComplex(multiplier);
    return new FComplex(
        freal.sub(freal.mul(an.getRe(), bn.getRe()), freal.mul(an.getIm(), bn.getIm())),
        freal.sum(freal.mul(an.getRe(), bn.getIm()), freal.mul(an.getIm(), bn.getRe())));
  }

  @Override
  public FComplex div(MathObject dividend, MathObject divisor) {
    FComplex an = new FComplex(dividend);
    FComplex bn = new FComplex(divisor);
    FReal mod = this.abs(bn).getRe();
    if (mod.equals(FReal.ZERO)) {
      throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
    }
    FComplex c = this.mul(an, this.conj(bn));
    return new FComplex(freal.div(c.getRe(), mod), freal.div(c.getIm(), mod));
  }

  @Override
  public MathObject mod(MathObject dividend, MathObject divisor) {
    throw new ArithmeticException("Функция mod не определена для комплексных чисел.");
  }

  @Override
  public MathObject pow(MathObject base, MathObject power) {
    throw new NullPointerException("Функция pow временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject root(MathObject radicand, MathObject degree) {
    throw new NullPointerException("Функция root временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject log(MathObject base, MathObject antilogarithm) {
    throw new NullPointerException("Функция log временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject gcd(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция gcd не определена для комплексных чисел.");
  }

  @Override
  public MathObject lcm(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция lcm не определена для комплексных чисел.");
  }

  @Override
  public MathObject fact(MathObject a) {
    throw new NullPointerException("Функция fact временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject conc(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция conc не определена для комплексных чисел.");
  }

  @Override
  public MathObject rand(MathObject a, MathObject b) {
    throw new NullPointerException("Функция rand временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject and(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция and не определена для комплексных чисел.");
  }

  @Override
  public MathObject or(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция or не определена для комплексных чисел.");
  }

  @Override
  public MathObject xor(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция xor не определена для комплексных чисел.");
  }

  @Override
  public MathObject min(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция min не определена для комплексных чисел.");
  }

  @Override
  public MathObject max(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция max не определена для комплексных чисел.");
  }

  @Override
  public FComplex sign(MathObject a) {
    FComplex an = new FComplex(a);
    if (this.abs(an).equals(ZERO)) return ZERO;
    return this.div(this, this.abs(an));
  }

  @Override
  public MathObject[] primes(MathObject n) {
    throw new ArithmeticException("Функция primes не определена для комплексных чисел.");
  }

  @Override
  public FComplex abs(MathObject a) {
    FComplex an = new FComplex(a);
    return new FComplex(freal.root(this.norm(an).getRe(), FReal.TWO));
  }

  @Override
  public MathObject not(MathObject a) {
    throw new ArithmeticException("Функция not не определена для комплексных чисел.");
  }

  @Override
  public MathObject med(MathObject a, MathObject b) {
    throw new ArithmeticException("Функция med не определена для комплексных чисел.");
  }

  @Override
  public MathObject sin(MathObject a) {
    throw new NullPointerException("Функция sin временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject cos(MathObject a) {
    throw new NullPointerException("Функция cos временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject tan(MathObject a) {
    throw new NullPointerException("Функция tan временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject arcsin(MathObject a) {
    throw new NullPointerException("Функция arcsin временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject arccos(MathObject a) {
    throw new NullPointerException("Функция arccos временно не введена для комплексных чисел.");
  }

  @Override
  public MathObject arctan(MathObject a) {
    throw new NullPointerException("Функция arctan временно не введена для комплексных чисел.");
  }

  @Override
  public FComplex conj(MathObject a) {
    FComplex an = new FComplex(a);
    return new FComplex(an.getRe(), freal.sub(FReal.ZERO, an.getIm()));
  }

  @Override
  public FComplex arg(MathObject a) {
    FComplex an = new FComplex(a);
    return new FComplex(freal.arctan(freal.div(an.getIm(), an.getRe())), FReal.ZERO);
  }

  @Override
  public FComplex norm(MathObject a) {
    FComplex an = new FComplex(a);
    return this.sum(this.mul(an.getRe(), an.getRe()), this.mul(an.getIm(), an.getIm()));
  }

  @Override
  public int compareTo(MathObject a) {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    return obj.getClass() == FComplex.class
        && this.getRe().equals(((FComplex) obj).getRe())
        && this.getIm().equals(((FComplex) obj).getIm());
  }

  @Override
  public String toString() {
    return this.real.toString()
        + (this.imaginary.get().compareTo(BigDecimal.ZERO) >= 0
            ? "+"
            : "") // знак мнимой компоненты
        + this.imaginary.toString()
        + "i";
  }
}
