package funcmath.object;

import funcmath.Helper;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class FComplex implements MathObject {
    @Serial
    private static final long serialVersionUID = -6539369190993703598L;

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
        ArrayList<String> snumbers = Helper.wordsFromString(
                s.replace('i', ' ')
                        .replace('+', ' ')
                        .replaceAll("-", " -")
        );
        this.real = new FReal(snumbers.get(0));
        this.imaginary = new FReal(snumbers.get(1));
    }

    public FComplex(MathObject number) {
        FComplex complex = number.getComplex();
        this.real = new FReal(complex.get()[0]);
        this.imaginary = new FReal(complex.get()[1]);
    }

    @Override
    public BigDecimal[] get() {
        return new BigDecimal[]{this.real.get(), this.imaginary.get()};
    }

    public FReal get(int n) {
        if (n == 0) return this.real;
        else return this.imaginary;
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

    public FReal getImaginary() {
        return imaginary;
    }

    @Override
    public FComplex getComplex() {
        return this;
    }

    @Override
    public FComplex sum(MathObject a, MathObject b) {
        FComplex an = new FComplex(a);
        FComplex bn = new FComplex(b);
        return new FComplex(freal.sum(an.get(0), bn.get(0)), freal.sum(an.get(1), bn.get(1)));
    }

    @Override
    public FComplex sub(MathObject a, MathObject b) {
        FComplex an = new FComplex(a);
        FComplex bn = new FComplex(b);
        return new FComplex(freal.sub(an.get(0), bn.get(0)), freal.sub(an.get(1), bn.get(1)));
    }

    @Override
    public FComplex mul(MathObject a, MathObject b) {
        FComplex an = new FComplex(a);
        FComplex bn = new FComplex(b);
        return new FComplex(
                freal.sub(freal.mul(an.get(0), bn.get(0)), freal.mul(an.get(1), bn.get(1))),
                freal.sum(freal.mul(an.get(0), bn.get(1)), freal.mul(an.get(1), bn.get(0)))
        );
    }

    @Override
    public FComplex div(MathObject a, MathObject b) {
        FComplex an = new FComplex(a);
        FComplex bn = new FComplex(b);
        FReal mod = this.abs(bn).getReal();
        if (mod.get().equals(BigDecimal.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
        FComplex c = this.mul(an, this.conj(bn));
        return new FComplex(freal.div(c.getReal(), mod), freal.div(c.getImaginary(), mod));
    }

    @Override
    public MathObject mod(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция mod не определена для комплексных чисел.");
    }

    @Override
    public MathObject pow(MathObject a, MathObject b) {
        throw new NullPointerException("Функция pow временно не введена для комплексных чисел.");
    }

    @Override
    public MathObject root(MathObject a, MathObject b) {
        throw new NullPointerException("Функция root временно не введена для комплексных чисел.");
    }

    @Override
    public MathObject log(MathObject a, MathObject b) {
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
        if (this.abs(an).get(0).get().equals(BigDecimal.ZERO)) return new FComplex(0, 0);
        return this.div(this, this.abs(an));
    }

    @Override
    public MathObject[] primes(MathObject a) {
        throw new ArithmeticException("Функция primes не определена для комплексных чисел.");
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
    public FComplex abs(MathObject a) {
        FComplex an = new FComplex(a);
        return new FComplex(freal.root(
                freal.sum(freal.mul(an.real, an.real), freal.mul(an.imaginary, an.imaginary)),
                new FReal(2)
        ));
    }

    @Override
    public FComplex conj(MathObject a) {
        FComplex an = new FComplex(a);
        return new FComplex(an.get()[0], an.get()[1].negate());
    }

    @Override
    public FComplex arg(MathObject a) {
        FComplex an = new FComplex(a);
        return new FComplex(Math.atan2(an.get()[1].doubleValue(), an.get()[0].doubleValue()), 0);
        // потом исправить с .doubleValue() на что-то иное, в BigDecimalMath нет atan2 :(
    }

    @Override
    public FComplex norm(MathObject a) {
        FComplex an = new FComplex(a);
        return this.sum(this.mul(an.real, an.real), this.mul(an.imaginary, an.imaginary));
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == FComplex.class
                && this.get(0).equals(((FComplex) obj).getReal())
                && this.get(1).equals(((FComplex) obj).getImaginary());
    }

    @Override
    public String toString() {
        return this.real.toString()
                + (this.imaginary.get().compareTo(BigDecimal.ZERO) >= 0 ? "+" : "") // знак мнимой компоненты
                + this.imaginary.toString() + "i";
    }
}
