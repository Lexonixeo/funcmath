package funcmath.object;

import java.security.SecureRandom;
import java.util.Objects;

public class FInteger implements MathObject {
    protected long number;

    public FInteger(long number) {
        this.number = number;
    }

    public FInteger(MathObject number) {
        this.number = number.getInteger().get();
    }

    public FInteger(String s) {
        this.number = Integer.parseInt(s);
    }

    @Override
    public Long get() {
        return this.number;
    }

    @Override
    public FNatural getNatural() {
        return new FNatural(this.number);
    }

    @Override
    public FInteger getInteger() {
        return this;
    }

    @Override
    public FRational getRational() {
        return new FRational(this.number, 1);
    }

    public FReal getReal() {
        return new FReal(this.number);
    }

    @Override
    public FComplex getComplex() {
        return new FComplex(this.number, 0);
    }

    @Override
    public FInteger sum(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get() + bn.get());
    }

    @Override
    public FInteger sub(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get() - bn.get());
    }

    @Override
    public FInteger mul(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get() * bn.get());
    }

    @Override
    public FInteger div(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get() == 0) return new FInteger(
                an.get() == 0 ? 0 : (an.get() > 0 ? Long.MAX_VALUE : Long.MIN_VALUE)
        );
        else return new FInteger((an.get() - this.mod(a, b).get()) / bn.get());
    }

    @Override
    public FInteger mod(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get() == 0) return new FInteger(0);
        else return new FInteger((an.get() % Math.abs(bn.get()) + Math.abs(bn.get())) % Math.abs(bn.get()));
    }

    @Override
    public FInteger pow(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get() < 0 && an.get() == 0) return new FInteger(Long.MAX_VALUE);
        if (bn.get() < 0) return new FInteger((an.get() > 0 || bn.get() % 2 == 0 ? 0 : -1));
        if (bn.get() == 0) return new FInteger(1);
        else if (bn.get() % 2 == 0) {
            FInteger cn = this.div(bn, new FInteger(2));
            FInteger dn = this.pow(an, cn);
            return this.mul(dn, dn);
        } else {
            FInteger cn = this.div(bn, new FInteger(2));
            FInteger dn = this.pow(an, cn);
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
    public FInteger gcd(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (an.get() < bn.get()) return this.gcd(bn, an);
        else if (bn.get() == 0) return an;
        else return this.gcd(bn, this.mod(an, bn));
    }

    @Override
    public MathObject fact(MathObject a) {
        return null;
    }

    @Override
    public FInteger rand(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        SecureRandom random = new SecureRandom();
        return new FInteger(
                random.nextLong(Math.abs(bn.get() - an.get()) + 1) + Math.min(an.get(), bn.get())
        );
    }

    @Override
    public FInteger abs(MathObject a) {
        FInteger an = new FInteger(a);
        return new FInteger(Math.abs(an.get()));
    }

    @Override
    public FInteger not(MathObject a) {
        FInteger an = new FInteger(a);
        return new FInteger(~an.get());
    }

    @Override
    public FInteger and(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get() & bn.get());
    }

    @Override
    public FInteger or(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get() | bn.get());
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
        return obj.getClass() == FInteger.class && Objects.equals(((FInteger) obj).get(), this.get());
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
