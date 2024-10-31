package funcmath.object;

import java.util.Objects;

public class FNatural implements MathObject {
    protected int number;

    public FNatural(int number) {
        // if (number < 0) throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        if (number < 0) number = 0;
        this.number = number;
    }

    public FNatural(MathObject number) {
        this.number = number.getNatural().get();
    }

    public FNatural(String s) {
        int number = Integer.parseInt(s);
        // if (number < 0) throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        if (number < 0) number = 0;
        this.number = number;
    }

    @Override
    public Integer get() {
        return this.number;
    }

    @Override
    public FNatural getNatural() {
        return this;
    }

    @Override
    public FInteger getInteger() {
        return new FInteger(this.number);
    }

    @Override
    public FRational getRational() {
        return new FRational(this.number, 1);
    }

    @Override
    public FReal getReal() {
        return new FReal(this.number);
    }

    @Override
    public FComplex getComplex() {
        return new FComplex(this.number, 0);
    }

    @Override
    public FNatural sum(MathObject a, MathObject b) {
        return (new FInteger(0)).sum(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural sub(MathObject a, MathObject b) {
        return (new FInteger(0)).sub(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural mul(MathObject a, MathObject b) {
        return (new FInteger(0)).mul(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural div(MathObject a, MathObject b) {
        return (new FInteger(0)).div(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural mod(MathObject a, MathObject b) {
        return (new FInteger(0)).mod(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural pow(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get() == 0) return new FNatural(1);
        else if (bn.get() % 2 == 0) {
            FNatural cn = this.div(bn, new FNatural(2));
            FNatural dn = this.pow(an, cn);
            return this.mul(dn, dn);
        } else {
            FNatural cn = this.div(bn, new FNatural(2));
            FNatural dn = this.pow(an, cn);
            return this.mul(this.mul(dn, dn), an);
        }
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
    public FNatural gcd(MathObject a, MathObject b) {
        return (new FInteger(0)).gcd(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural lcm(MathObject a, MathObject b) {
        return (new FInteger(0)).lcm(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural rand(MathObject a, MathObject b) {
        return (new FInteger(0)).rand(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural abs(MathObject a) {
        return (new FInteger(0)).abs(new FNatural(a)).getNatural();
    }

    @Override
    public FNatural xor(MathObject a, MathObject b) {
        return (new FInteger(0)).xor(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural not(MathObject a) {
        return (new FInteger(0)).not(new FNatural(a)).getNatural();
    }

    @Override
    public FNatural and(MathObject a, MathObject b) {
        return (new FInteger(0)).and(new FNatural(a), new FNatural(b)).getNatural();
    }

    @Override
    public FNatural or(MathObject a, MathObject b) {
        return (new FInteger(0)).or(new FNatural(a), new FNatural(b)).getNatural();
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
        return obj.getClass() == FNatural.class && Objects.equals(((FNatural) obj).get(), this.get());
    }
    
    @Override
    public String toString() {
        return this.get().toString();
    }
}
