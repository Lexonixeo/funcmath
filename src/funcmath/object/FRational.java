package funcmath.object;

import java.util.Arrays;

public class FRational implements MathObject {
    protected FInteger numerator;
    protected FNatural denominator;

    public FRational(int numerator, int denominator) {
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        if (denominator == 0) {
            denominator = 1;
            numerator = numerator == 0 ? 0 : (numerator > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE);
        }
        this.numerator = new FInteger(numerator);
        this.denominator = new FNatural(denominator);
        update();
    }

    public FRational(int number) {
        this.numerator = new FInteger(number);
        this.denominator = new FNatural(1);
    }

    public FRational(Integer[] fraction) {
        int numerator = fraction[0];
        int denominator = fraction[1];
        FRational t = new FRational(numerator, denominator);
        this.numerator = t.getNumerator();
        this.denominator = t.getDenominator();
    }

    public FRational(MathObject number) {
        if (number.getClass() == FRational.class) {
            FRational t = new FRational((Integer[]) number.get());
            this.numerator = t.getNumerator();
            this.denominator = t.getDenominator();
        } else if (number.getClass() == FInteger.class || number.getClass() == FNatural.class) {
            this.numerator = new FInteger(number);
            this.denominator = new FNatural(1);
        }
    }

    public FRational(String s) {
        // to do...
    }

    protected FInteger getNumerator() {
        return this.numerator;
    }

    protected FNatural getDenominator() {
        return this.denominator;
    }

    private void update() {
        FInteger gcd = FInteger.getInstance().gcd(this.numerator, this.denominator);
        this.numerator = FInteger.getInstance().div(this.numerator, gcd);
        this.denominator = FNatural.getInstance().div(this.denominator, gcd);
    }

    @Override
    public Integer[] get() {
        return new Integer[]{this.numerator.get(), this.denominator.get()};
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
    public MathObject abs(MathObject a) {
        FRational an = new FRational(a);
        return new FRational(Math.abs(an.get()[0]), an.get()[1]);
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
    public boolean equals(Object obj) {
        return obj.getClass() == FRational.class && Arrays.equals(((FRational) obj).get(), this.get());
    }

    @Override
    public String toString() {
        return this.numerator + "/" + this.denominator;
    }

    public static FRational getInstance() {
        return new FRational(1, 1);
    }
}
