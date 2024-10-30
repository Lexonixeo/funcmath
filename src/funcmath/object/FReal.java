package funcmath.object;

import java.security.SecureRandom;
import java.util.Objects;

public class FReal implements MathObject {
    double number;

    public FReal(double number) {
        this.number = number;
    }

    public FReal(String s) {
        this.number = Double.parseDouble(s);
    }

    public FReal(MathObject number) {
        this.number = number.getReal().get();
    }

    @Override
    public Double get() {
        return number;
    }

    @Override
    public FNatural getNatural() {
        return this.getInteger().getNatural();
    }

    @Override
    public FInteger getInteger() {
        return new FInteger((int) Math.floor(this.number));
    }

    @Override
    public FRational getRational() {
        SecureRandom random = new SecureRandom();
        FInteger den = new FInteger(random.nextInt(1, 1000));
        return new FRational(this.mul(this, den).getInteger().get(), den.get());
    }

    @Override
    public FReal getReal() {
        return this;
    }

    @Override
    public FComplex getComplex() {
        return new FComplex(this.number, 0);
    }

    @Override
    public MathObject sum(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(an.get() + bn.get());
    }

    @Override
    public FReal sub(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(an.get() - bn.get());
    }

    @Override
    public FReal mul(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(an.get() * bn.get());
    }

    @Override
    public FReal div(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(an.get() / bn.get());
    }

    @Override
    public MathObject mod(MathObject a, MathObject b) {
        return null;
    }

    @Override
    public FReal pow(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(Math.pow(an.get(), bn.get()));
    }

    @Override
    public FReal abs(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(Math.abs(an.get()));
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
    public FReal rand(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        SecureRandom random = new SecureRandom();
        return new FReal(random.nextDouble(an.get(), bn.get()));
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
        return obj.getClass() == FReal.class && Objects.equals(((FReal) obj).get(), this.get());
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
