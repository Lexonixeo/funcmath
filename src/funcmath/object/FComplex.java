package funcmath.object;

import java.util.Arrays;

public class FComplex implements MathObject {
    FReal real, imaginary;

    public FComplex(double real, double imaginary) {
        this.real = new FReal(real);
        this.imaginary = new FReal(imaginary);
    }

    public FComplex(String s) {
        // ...
    }

    public FComplex(MathObject number) {
        FComplex complex = number.getComplex();
        this.real = new FReal(complex.get()[0]);
        this.imaginary = new FReal(complex.get()[1]);
    }

    private void update() {

    }

    @Override
    public Double[] get() {
        return new Double[]{this.real.get(), this.imaginary.get()};
    }

    @Override
    public FNatural getNatural() {
        return null;
    }

    @Override
    public FInteger getInteger() {
        return null;
    }

    @Override
    public FRational getRational() {
        return null;
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
    public MathObject sum(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject sub(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject mul(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject div(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject mod(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject pow(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject root(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject log(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject gcd(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject lcm(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject rand(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject xor(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject not(MathObject a) {
        return null;
    }

    @Override
    public MathObject and(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public MathObject or(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public FComplex abs(MathObject a) {
        return new FComplex(
                Math.sqrt(this.real.get() * this.real.get() + this.imaginary.get() * this.imaginary.get()),
                0
        );
    }

    @Override
    public FComplex conj(MathObject a) {
        FComplex an = new FComplex(a);
        return new FComplex(an.get()[0], -an.get()[1]);
    }

    @Override
    public FComplex arg(MathObject a) {
        FComplex an = new FComplex(a);
        return new FComplex(Math.atan2(an.get()[1], an.get()[0]), 0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == FComplex.class && Arrays.equals(((FComplex) obj).get(), this.get());
    }

    @Override
    public String toString() {
        return this.real.toString() + (this.imaginary.get() >= 0 ? "+" : "") + this.imaginary.toString() + "i";
    }
}
