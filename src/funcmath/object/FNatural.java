package funcmath.object;

import java.security.SecureRandom;
import java.util.Objects;

public class FNatural implements MathObject {
    protected int number;

    public FNatural(int number) {
        if (number < 0) throw new ArithmeticException();
        this.number = number;
    }

    public FNatural(MathObject number) {
        if ((Integer) number.get() < 0) throw new ArithmeticException();
        this.number = (Integer) number.get();
    }

    public FNatural(String s) {
        int number = Integer.parseInt(s);
        if (number < 0) throw new ArithmeticException();
        this.number = number;
    }

    @Override
    public Integer get() {
        return this.number;
    }

    @Override
    public FNatural sum(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().sum(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural sub(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().sub(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural mul(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().mul(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural div(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().div(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural mod(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().mod(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural pow(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().pow(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural abs(MathObject a) {
        return new FNatural(FInteger.getInstance().abs(new FNatural(a)));
    }

    @Override
    public FNatural gcd(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().gcd(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural lcm(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().lcm(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural rand(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().rand(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural xor(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().xor(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural not(MathObject a) {
        return new FNatural(FInteger.getInstance().not(new FNatural(a)));
    }

    @Override
    public FNatural and(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().and(new FNatural(a), new FNatural(b)));
    }

    @Override
    public FNatural or(MathObject a, MathObject b) {
        return new FNatural(FInteger.getInstance().or(new FNatural(a), new FNatural(b)));
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == FNatural.class && Objects.equals(((FNatural) obj).get(), this.get());
    }
    
    @Override
    public String toString() {
        return this.get().toString();
    }

    public static FNatural getInstance() {
        return new FNatural(0);
    }
}
