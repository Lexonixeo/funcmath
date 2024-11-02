package funcmath.object;

import funcmath.Helper;

import java.util.ArrayList;
import java.util.Arrays;

public class FRational implements MathObject {
    protected FInteger numerator;
    protected FNatural denominator;

    public FRational(long numerator, long denominator) {
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        if (denominator == 0) {
            denominator = 1;
            numerator = numerator == 0 ? 0 : (numerator > 0 ? Long.MAX_VALUE : Long.MIN_VALUE);
        }
        this.numerator = new FInteger(numerator);
        this.denominator = new FNatural(denominator);

        FInteger gcd = (new FInteger(0)).gcd(this.numerator, this.denominator);
        this.numerator = (new FInteger(0)).div(this.numerator, gcd);
        this.denominator = (new FNatural(0)).div(this.denominator, gcd);
    }

    public FRational(MathObject number) {
        FRational num = number.getRational();
        this.numerator = new FInteger(num.get()[0]);
        this.denominator = new FNatural(num.get()[1]);
    }

    public FRational(String s) {
        ArrayList<Integer> numbers = Helper.integersFromWords(Helper.wordsFromString(s.replace('/', ' ')));
        FRational num = new FRational(numbers.get(0), numbers.get(1));
        this.numerator = new FInteger(num.get()[0]);
        this.denominator = new FNatural(num.get()[1]);
    }

    @Override
    public Long[] get() {
        return new Long[]{this.numerator.get(), this.denominator.get()};
    }

    @Override
    public FNatural getNatural() {
        return this.getInteger().getNatural();
    }

    @Override
    public FInteger getInteger() {
        return (new FInteger(0)).div(this.numerator, this.denominator);
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
        return new FComplex(this.getReal().get(), 0);
    }

    @Override
    public FRational sum(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                an.get()[0] * bn.get()[1] + an.get()[1] * bn.get()[0],
                an.get()[1] * bn.get()[1]
        );
    }

    @Override
    public FRational sub(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                an.get()[0] * bn.get()[1] - an.get()[1] * bn.get()[0],
                an.get()[1] * bn.get()[1]
        );
    }

    @Override
    public FRational mul(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                an.get()[0] * bn.get()[0],
                an.get()[1] * bn.get()[1]
        );
    }

    @Override
    public FRational div(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                an.get()[0] * bn.get()[1],
                an.get()[1] * bn.get()[0]
        );
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
    public MathObject fact(MathObject a) {
        return null;
    }

    @Override
    public MathObject rand(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public FRational abs(MathObject a) {
        FRational an = new FRational(a);
        return new FRational(Math.abs(an.get()[0]), an.get()[1]);
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
    public MathObject conj(MathObject a) {
        return null;
    }

    @Override
    public MathObject arg(MathObject a) {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == FRational.class && Arrays.equals(((FRational) obj).get(), this.get());
    }

    @Override
    public String toString() {
        return this.numerator + "/" + this.denominator;
    }
}
