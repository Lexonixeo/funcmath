package funcmath.object;

import java.io.Serial;
import java.math.BigInteger;
import java.security.SecureRandom;

public class FNatural implements MathObject {
    @Serial
    private static final long serialVersionUID = -1322027344216575257L;

    protected BigInteger number;

    public FNatural(long number) {
        if (number < 0) throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        this.number = BigInteger.valueOf(number);
    }

    public FNatural(BigInteger number) {
        if (number.compareTo(BigInteger.ZERO) < 0) throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        this.number = number;
    }

    public FNatural(MathObject number) {
        this.number = number.getNatural().get();
    }

    public FNatural(String s) {
        BigInteger number = new BigInteger(s);
        if (number.compareTo(BigInteger.ZERO) < 0) throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        this.number = number;
    }

    @Override
    public BigInteger get() {
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
        return new FRational(this.number, BigInteger.ONE);            // to do...
    }

    @Override
    public FReal getReal() {
        return new FReal(this.number);
    }

    @Override
    public FComplex getComplex() {
        return new FComplex(this.number, BigInteger.ZERO);
    }

    @Override
    public FNatural sum(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get().add(bn.get()));
    }

    @Override
    public FNatural sub(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get().add(bn.get().negate()));
    }

    @Override
    public FNatural mul(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get().multiply(bn.get()));
    }

    @Override
    public FNatural div(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get().equals(BigInteger.ZERO)) throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
        else return new FNatural(an.get().divide(bn.get()));
    }

    @Override
    public FNatural mod(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get().equals(BigInteger.ZERO)) throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "%" + bn);
        else return new FNatural(an.get().mod(bn.get()));
    }

    @Override
    public FNatural pow(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get().equals(BigInteger.ZERO)) return new FNatural(1);
        else if (bn.get().mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
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
        if (bn.get().equals(BigInteger.ZERO)) throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "^(1/" + bn + ")");

        FNatural l = new FNatural(BigInteger.ONE.negate());
        FNatural r = new FNatural(an.get().add(BigInteger.ONE));
        while (this.sub(r, l).get().compareTo(BigInteger.ONE) > 0) {
            FNatural m = this.div(this.sum(r, l), new FNatural(2));
            FNatural res = this.pow(m, bn);
            if (res.get().compareTo(an.get()) > 0) r = m;
            else l = m;
        }
        return l;
    }

    @Override
    public FNatural log(MathObject a, MathObject b) { // TO DO
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get().equals(BigInteger.ZERO)) throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "^(1/" + bn + ")");

        FNatural l = new FNatural(BigInteger.ONE.negate());
        FNatural r = new FNatural(an.get().add(BigInteger.ONE));
        while (this.sub(r, l).get().compareTo(BigInteger.ONE) > 0) {
            FNatural m = this.div(this.sum(r, l), new FNatural(2));
            FNatural res = this.pow(an, m);
            if (res.get().compareTo(an.get()) > 0) r = m;
            else l = m;
        }
        return l;
    }

    @Override
    public FNatural gcd(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (an.get().compareTo(bn.get()) < 0) return this.gcd(bn, an);
        else if (bn.get().equals(BigInteger.ZERO)) return an;
        else return this.gcd(bn, this.mod(an, bn));
    }

    @Override
    public FNatural fact(MathObject a) {
        FNatural an = new FNatural(a);
        if (an.get().equals(BigInteger.ZERO)) return new FNatural(1);
        return this.mul(fact(sub(a, new FNatural(1))), a);
    }

    @Override
    public FNatural rand(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (an.get().compareTo(bn.get()) > 0) return this.rand(b, a);

        SecureRandom random = new SecureRandom();
        FNatural upperLimit = this.sum(new FNatural(1), this.sub(bn, an));

        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(upperLimit.get().bitLength(), random);
        } while (randomNumber.compareTo(upperLimit.get()) >= 0);

        return this.sum(new FNatural(randomNumber), an);
    }

    @Override
    public FNatural abs(MathObject a) {
        return new FNatural(a);
    }

    @Override
    public MathObject not(MathObject a) {
        throw new ArithmeticException("Функция not не определена для натуральных чисел.");
    }

    @Override
    public FNatural and(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get().and(bn.get()));
    }

    @Override
    public FNatural or(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get().or(bn.get()));
    }

    @Override
    public FNatural conj(MathObject a) {
        return new FNatural(a);
    }

    @Override
    public FNatural arg(MathObject a) {
        return this.mul(a, new FNatural(0));
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == FNatural.class && this.get().equals(((FNatural) obj).get());
    }
    
    @Override
    public String toString() {
        return this.get().toString();
    }
}
