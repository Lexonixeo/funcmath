package funcmath.object;

import java.io.Serial;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class FInteger implements MathObject {
    @Serial
    private static final long serialVersionUID = 4465117069025671299L;

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
        return new FReal(this.number);
    }

    @Override
    public FComplex getComplex() {
        return new FComplex(this.number, BigInteger.ZERO);
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
        if (bn.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
        else return new FInteger(this.sub(an, this.mod(an, bn)).get().divide(bn.get()));
    }

    @Override
    public FInteger mod(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "%" + bn);
        else return new FInteger(an.get().mod(bn.get().abs()));
    }

    @Override
    public FInteger pow(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get().compareTo(BigInteger.ZERO) < 0 && an.get().compareTo(BigInteger.ZERO) == 0)
            throw new ArithmeticException("Деление на ноль не имеет смысла: 1/(0^" + bn.get().negate() + ")");

        if (bn.get().compareTo(BigInteger.ZERO) < 0) return new FInteger((
                an.get().compareTo(BigInteger.ZERO) > 0
                        || this.mod(bn, new FInteger(2)).get().equals(BigInteger.ZERO)
                        ? 0 : -1
        ));

        if (bn.get().equals(BigInteger.ZERO)) return new FInteger(1);
        else if (this.mod(bn, new FInteger(2)).get().equals(BigInteger.ZERO)) {
            FInteger cn = this.div(bn, new FInteger(2));
            try {
                FInteger dn = this.pow(an, cn);
                return this.mul(dn, dn);
            } catch (StackOverflowError e) {
                throw new RuntimeException("Стек вызова функций переполнился для функции pow.");
            }
        } else {
            FInteger cn = this.div(bn, new FInteger(2));
            try {
                FInteger dn = this.pow(an, cn);
                return this.mul(this.mul(dn, dn), an);
            } catch (StackOverflowError e) {
                throw new RuntimeException("Стек вызова функций переполнился для функции pow.");
            }
        }
    }

    @Override
    public FInteger root(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (bn.get().compareTo(BigInteger.ZERO) < 0)
            return this.root(this.pow(an, new FInteger(-1)), this.mul(bn, new FInteger(-1)));
        if (bn.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "^(1/" + bn + ")");
        if (an.get().compareTo(BigInteger.ZERO) < 0 && bn.get().mod(BigInteger.TWO).equals(BigInteger.ZERO))
            throw new ArithmeticException("Корни степени, делящейся на 2, не определены для отрицательных чисел");

        if (an.get().compareTo(BigInteger.ZERO) < 0) {
            FInteger l = new FInteger(this.sub(an, new FInteger(1)));
            FInteger r = new FInteger(0);
            while (this.sub(r, l).get().compareTo(BigInteger.ONE) > 0) {
                FInteger m = this.div(this.sum(r, l), new FInteger(2));
                FInteger res = this.pow(m, bn);
                if (res.get().compareTo(an.get()) > 0) r = m;
                else l = m;
            }
            return l;
        } else {
            FInteger l = new FInteger(-1);
            FInteger r = new FInteger(this.sum(an, new FInteger(1)));
            while (this.sub(r, l).get().compareTo(BigInteger.ONE) > 0) {
                FInteger m = this.div(this.sum(r, l), new FInteger(2));
                FInteger res = this.pow(m, bn);
                if (res.get().compareTo(an.get()) > 0) r = m;
                else l = m;
            }
            return l;
        }
    }

    @Override
    public FInteger log(MathObject a, MathObject b) {
        throw new NullPointerException("Функция log временно не введена для целых чисел.");
    }

    @Override
    public FInteger gcd(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get().gcd(bn.get()));
        /*
        if (an.get().compareTo(bn.get()) < 0) return this.gcd(bn, an);
        else if (bn.get().equals(BigInteger.ZERO)) return an;
        else return this.gcd(bn, this.mod(an, bn));
         */
    }

    @Override
    public FInteger lcm(MathObject a, MathObject b) {
        return this.div(this.mul(a, b), this.gcd(a, b));
    }

    @Override
    public FInteger fact(MathObject a) {
        FInteger an = new FInteger(a);
        if (an.get().compareTo(BigInteger.ZERO) < 0)
            throw new ArithmeticException("Факториал для отрицательных целых чисел не определен.");
        else if (an.get().equals(BigInteger.ZERO)) return new FInteger(1);
        try {
            return this.mul(fact(sub(a, new FInteger(1))), a);
        } catch (StackOverflowError e) {
            throw new RuntimeException("Стек вызова функций переполнился для функции fact.");
        }
    }

    @Override
    public MathObject conc(MathObject a, MathObject b) {
        if (new FInteger(b).get().compareTo(BigInteger.ZERO) < 0)
            throw new ArithmeticException("Конкатенация для отрицательного второго аргумента не определена.");
        String an = new FInteger(a).get().toString();
        String bn = new FInteger(b).get().toString();
        return new FInteger(an + bn);
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
    public FInteger xor(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        return new FInteger(an.get().xor(bn.get()));
    }

    @Override
    public FInteger min(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (an.get().compareTo(bn.get()) <= 0) return an;
        else return bn;
    }

    @Override
    public FInteger max(MathObject a, MathObject b) {
        FInteger an = new FInteger(a);
        FInteger bn = new FInteger(b);
        if (an.get().compareTo(bn.get()) <= 0) return bn;
        else return an;
    }

    @Override
    public FInteger sign(MathObject a) {
        FInteger an = new FInteger(a);
        return new FInteger(an.get().compareTo(BigInteger.ZERO));
    }

    @Override
    public FInteger[] primes(MathObject a) {
        ArrayList<FInteger> factor = new ArrayList<>();
        FInteger an = this.abs(new FInteger(a));
        while (an.get().compareTo(BigInteger.ONE) > 0) {
            FInteger bn = new FInteger(2);
            while (bn.get().compareTo(this.root(an, new FInteger(2)).get()) <= 0) {
                if (this.mod(an, bn).equals(new FInteger(0))) {
                    factor.add(bn);
                    an = this.div(an, bn);
                } else {
                    bn = this.sum(bn, new FInteger(1));
                }
            }
            if (!an.get().equals(BigInteger.ONE)) factor.add(an);
        }
        return factor.toArray(new FInteger[]{});
    }

    @Override
    public FInteger not(MathObject a) {
        FInteger an = new FInteger(a);
        return new FInteger(an.get().not());
    }

    @Override
    public MathObject med(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция med не определена для целых чисел.");
    }

    @Override
    public MathObject sin(MathObject a) {
        throw new ArithmeticException("Функция sin не определена для целых чисел.");
    }

    @Override
    public MathObject cos(MathObject a) {
        throw new ArithmeticException("Функция cos не определена для целых чисел.");
    }

    @Override
    public MathObject tan(MathObject a) {
        throw new ArithmeticException("Функция tan не определена для целых чисел.");
    }

    @Override
    public MathObject arcsin(MathObject a) {
        throw new ArithmeticException("Функция arcsin не определена для целых чисел.");
    }

    @Override
    public MathObject arccos(MathObject a) {
        throw new ArithmeticException("Функция arccos не определена для целых чисел.");
    }

    @Override
    public MathObject arctan(MathObject a) {
        throw new ArithmeticException("Функция arctan не определена для целых чисел.");
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
    public FInteger norm(MathObject a) {
        FInteger an = new FInteger(a);
        return this.mul(an, an);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == FInteger.class && this.get().equals(((FInteger) obj).get());
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
