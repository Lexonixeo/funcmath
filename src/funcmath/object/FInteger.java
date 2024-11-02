package funcmath.object;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;

public class FInteger implements MathObject {
    protected BigInteger number;

    public FInteger(long number) {
        this.number = BigInteger.valueOf(number);
    }

    public FInteger(BigInteger number) {
        this.number = number;
    }

    public FInteger(MathObject number) {
        this.number = number.getInteger().get();
    }

    public FInteger(String s) {
        this.number = new BigInteger(s);
    }

    @Override
    public BigInteger get() {
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
        return new FRational(this.number, BigInteger.ONE);
    }

    @Override
    public FReal getReal() {
        return new FReal(this.number.doubleValue());
    }

    @Override
    public FComplex getComplex() {
        return new FComplex(this.number.doubleValue(), 0);
    }

    @Override
    public FInteger sum(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get().add(bn.get()));
    }

    @Override
    public FInteger sub(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get().add(bn.get().negate()));
    }

    @Override
    public FInteger mul(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get().multiply(bn.get()));
    }

    @Override
    public FInteger div(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get().equals(BigInteger.ZERO)) throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
        else return new FInteger(this.sub(an, this.mod(an, bn)).get().divide(bn.get()));
    }

    @Override
    public FInteger mod(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get().equals(BigInteger.ZERO)) throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "%" + bn);
        else return new FInteger(an.get().mod(bn.get().abs()));
    }

    @Override
    public FInteger pow(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get().compareTo(BigInteger.ZERO) < 0 && an.get().compareTo(BigInteger.ZERO) == 0)
            throw new ArithmeticException("Деление на ноль не имеет смысла: 1/(0^" + bn.get().negate() + ")");

        if (bn.get().compareTo(BigInteger.ZERO) < 0) return new FInteger(
                (an.get().compareTo(BigInteger.ZERO) > 0 || this.mod(bn, new FInteger(2)).get().equals(BigInteger.ZERO) ? 0 : -1)
        );

        if (bn.get().equals(BigInteger.ZERO)) return new FInteger(1);
        else if (this.mod(bn, new FInteger(2)).get().equals(BigInteger.ZERO)) {
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
        throw new NullPointerException("Функция root временно не введена для целых чисел.");
    }

    @Override
    public MathObject log(MathObject a, MathObject b) {
        throw new NullPointerException("Функция log временно не введена для целых чисел.");
    }

    @Override
    public FInteger gcd(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (an.get().compareTo(bn.get()) < 0) return this.gcd(bn, an);
        else if (bn.get().equals(BigInteger.ZERO)) return an;
        else return this.gcd(bn, this.mod(an, bn));
    }

    @Override
    public FInteger fact(MathObject a) {
        FInteger an = new FInteger(a);
        if (an.get().compareTo(BigInteger.ZERO) < 0) throw new ArithmeticException("Факториал для отрицательных целых чисел не определен.");
        else if (an.get().equals(BigInteger.ZERO)) return new FInteger(1);
        return this.mul(fact(sub(a, new FInteger(1))), a);
    }

    @Override
    public FInteger rand(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (an.get().compareTo(bn.get()) > 0) return this.rand(b, a);

        SecureRandom random = new SecureRandom();
        FInteger upperLimit = this.sum(new FInteger(1), this.sub(bn, an));

        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(upperLimit.get().bitLength(), random);
        } while (randomNumber.compareTo(upperLimit.get()) >= 0);

        return this.sum(new FInteger(randomNumber), an);
    }

    @Override
    public FInteger abs(MathObject a) {
        FInteger an = new FInteger(a);
        return new FInteger(an.get().abs());
    }

    @Override
    public FInteger not(MathObject a) {
        FInteger an = new FInteger(a);
        return new FInteger(an.get().not());
    }

    @Override
    public FInteger and(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get().and(bn.get()));
    }

    @Override
    public FInteger or(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get().or(bn.get()));
    }

    @Override
    public FInteger conj(MathObject a) {
        return new FInteger(a);
    }

    @Override
    public FInteger arg(MathObject a) {
        FInteger an = new FInteger(a);
        return new FInteger(an.get().compareTo(BigInteger.ZERO) >= 0 ? 0 : -4);
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
