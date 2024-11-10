package funcmath.object;

import java.io.Serial;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class FNatural implements MathObject {
    @Serial
    private static final long serialVersionUID = -1322027344216575257L;

    protected BigInteger number;

    public FNatural(long number) {
        if (number < 0) throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        this.number = BigInteger.valueOf(number);
    }

    public FNatural(BigInteger number) {
        if (number.compareTo(BigInteger.ZERO) < 0)
            throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
        this.number = number;
    }

    public FNatural(MathObject number) {
        this.number = number.getNatural().get();
    }

    public FNatural(String s) {
        BigInteger number = new BigInteger(s);
        if (number.compareTo(BigInteger.ZERO) < 0)
            throw new ArithmeticException("Не существует натуральных чисел, меньших нуля: " + number);
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
        if (bn.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
        else return new FNatural(an.get().divide(bn.get()));
    }

    @Override
    public FNatural mod(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "%" + bn);
        else return new FNatural(an.get().mod(bn.get()));
    }

    @Override
    public FNatural pow(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get().equals(BigInteger.ZERO)) return new FNatural(1);
        else if (bn.get().mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            FNatural cn = this.div(bn, new FNatural(2));
            try {
                FNatural dn = this.pow(an, cn);
                return this.mul(dn, dn);
            } catch (StackOverflowError e) {
                throw new RuntimeException("Стек вызова функций переполнился для функции pow.");
            }
        } else {
            FNatural cn = this.div(bn, new FNatural(2));
            try {
                FNatural dn = this.pow(an, cn);
                return this.mul(this.mul(dn, dn), an);
            } catch (StackOverflowError e) {
                throw new RuntimeException("Стек вызова функций переполнился для функции pow.");
            }
        }
    }

    @Override
    public FNatural root(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (bn.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "^(1/" + bn + ")");

        FNatural l = new FNatural(BigInteger.ZERO);
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
        if (bn.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Логарифм от нуля не определен.");
        if (an.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Логарифм по основанию нуля не определен.");
        if (an.get().equals(BigInteger.ONE))
            throw new ArithmeticException("Логарифм по основанию единицы не определен.");

        FNatural l = new FNatural(BigInteger.ZERO);
        FNatural r = new FNatural(bn.get().bitLength() * 4L);
        while (this.sub(r, l).get().compareTo(BigInteger.ONE) > 0) {
            FNatural m = this.div(this.sum(r, l), new FNatural(2));
            FNatural res = this.pow(an, m);
            if (res.get().compareTo(bn.get()) > 0) r = m;
            else l = m;
        }
        return l;
    }

    @Override
    public FNatural gcd(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get().gcd(bn.get()));
        /*
        if (an.get().compareTo(bn.get()) < 0) return this.gcd(bn, an);
        else if (bn.get().equals(BigInteger.ZERO)) return an;
        else return this.gcd(bn, this.mod(an, bn));
         */
    }

    @Override
    public FNatural lcm(MathObject a, MathObject b) {
        return this.div(this.mul(a, b), this.gcd(a, b));
    }

    @Override
    public FNatural fact(MathObject a) {
        FNatural an = new FNatural(a);
        if (an.get().equals(BigInteger.ZERO)) return new FNatural(1);
        try {
            return this.mul(fact(sub(a, new FNatural(1))), a);
        } catch (StackOverflowError e) {
            throw new RuntimeException("Стек вызова функций переполнился для функции fact.");
        }
    }

    @Override
    public FNatural conc(MathObject a, MathObject b) {
        String an = new FNatural(a).get().toString();
        String bn = new FNatural(b).get().toString();
        return new FNatural(an + bn);
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
    public FNatural xor(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        return new FNatural(an.get().xor(bn.get()));
    }

    @Override
    public FNatural min(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (an.get().compareTo(bn.get()) <= 0) return an;
        else return bn;
    }

    @Override
    public FNatural max(MathObject a, MathObject b) {
        FNatural an = new FNatural(a);
        FNatural bn = new FNatural(b);
        if (an.get().compareTo(bn.get()) <= 0) return bn;
        else return an;
    }

    @Override
    public FNatural sign(MathObject a) {
        FNatural an = new FNatural(a);
        return new FNatural(an.get().compareTo(BigInteger.ZERO));
    }

    @Override
    public FNatural[] primes(MathObject a) {
        ArrayList<FNatural> factor = new ArrayList<>();
        FNatural an = new FNatural(a);
        while (an.get().compareTo(BigInteger.ONE) > 0) {
            FNatural bn = new FNatural(2);
            while (bn.get().compareTo(this.root(an, new FNatural(2)).get()) <= 0) {
                if (this.mod(an, bn).equals(new FNatural(0))) {
                    factor.add(bn);
                    an = this.div(an, bn);
                } else {
                    bn = this.sum(bn, new FNatural(1));
                }
            }
            if (!an.get().equals(BigInteger.ONE)) {
                factor.add(an);
                an = new FNatural(1);
            }
        }
        return factor.toArray(new FNatural[]{});
    }

    @Override
    public MathObject not(MathObject a) {
        throw new ArithmeticException("Функция not не определена для натуральных чисел.");
    }

    @Override
    public MathObject med(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция med не определена для натуральных чисел.");
    }

    @Override
    public MathObject sin(MathObject a) {
        throw new ArithmeticException("Функция sin не определена для натуральных чисел.");
    }

    @Override
    public MathObject cos(MathObject a) {
        throw new ArithmeticException("Функция cos не определена для натуральных чисел.");
    }

    @Override
    public MathObject tan(MathObject a) {
        throw new ArithmeticException("Функция tan не определена для натуральных чисел.");
    }

    @Override
    public MathObject arcsin(MathObject a) {
        throw new ArithmeticException("Функция arcsin не определена для натуральных чисел.");
    }

    @Override
    public MathObject arccos(MathObject a) {
        throw new ArithmeticException("Функция arccos не определена для натуральных чисел.");
    }

    @Override
    public MathObject arctan(MathObject a) {
        throw new ArithmeticException("Функция arctan не определена для натуральных чисел.");
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
    public FNatural norm(MathObject a) {
        FNatural an = new FNatural(a);
        return this.mul(an, an);
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
