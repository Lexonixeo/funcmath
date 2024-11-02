package funcmath.object;

import java.security.SecureRandom;
import java.util.Objects;

public class FNatural implements MathObject {
    protected long number;

    public FNatural(long number) {
        // if (number < 0) throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        if (number < 0) number = 0;
        this.number = number;
    }

    public FNatural(MathObject number) {
        this.number = number.getNatural().get();
    }

    public FNatural(String s) {
        long number = Long.parseLong(s);
        // if (number < 0) throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        if (number < 0) number = 0;
        this.number = number;
    }

    @Override
    public Long get() {
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
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get() + bn.get());
    }

    @Override
    public FNatural sub(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get() - bn.get());
    }

    @Override
    public FNatural mul(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get() * bn.get());
    }

    @Override
    public FNatural div(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get() == 0) return new FNatural(an.get() == 0 ? 0 : Long.MAX_VALUE);
        else return new FNatural(an.get() / bn.get());
    }

    @Override
    public FNatural mod(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get() == 0) return new FNatural(0);
        else return new FNatural(an.get() % bn.get());
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
    public FNatural root(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get() == 0) return new FNatural(Long.MAX_VALUE);

        long l = -1, r = an.get() + 1;
        while (r - l > 1) {
            long m = (r + l) / 2;
            FNatural cn = new FNatural(m);
            FNatural res = this.pow(cn, bn);
            if (res.get() > an.get()) r = m;
            else l = m;
        }
        return new FNatural(l);
    }

    @Override
    public FNatural log(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get() == 0) return new FNatural(Long.MAX_VALUE);
        long l = -1, r = an.get() + 1;
        while (r - l > 1) {
            long m = (r + l) / 2;
            FNatural cn = new FNatural(m);
            FNatural res = this.pow(an, cn);
            if (res.get() > an.get()) r = m;
            else l = m;
        }
        return new FNatural(l);
    }

    @Override
    public FNatural gcd(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (an.get() < bn.get()) return this.gcd(bn, an);
        else if (bn.get() == 0) return an;
        else return this.gcd(bn, this.mod(an, bn));
    }

    @Override
    public FNatural fact(MathObject a) {
        FNatural an = new FNatural(a);
        if (an.get() == 0) return new FNatural(1);
        return this.mul(fact(sub(a, new FNatural(1))), a);
    }

    @Override
    public FNatural rand(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        SecureRandom random = new SecureRandom();
        return new FNatural(
                random.nextLong(Math.abs(bn.get() - an.get()) + 1) + Math.min(an.get(), bn.get())
        );
    }

    @Override
    public FNatural abs(MathObject a) {
        return this;
    }

    @Override
    public MathObject not(MathObject a) {
        return null;
    }

    @Override
    public FNatural and(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get() & bn.get());
    }

    @Override
    public FNatural or(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get() | bn.get());
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
