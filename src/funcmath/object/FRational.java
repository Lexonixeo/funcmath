package funcmath.object;

import funcmath.Helper;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class FRational implements MathObject {
    @Serial
    private static final long serialVersionUID = 8367368182099731988L;

    protected FInteger numerator;
    protected FNatural denominator;
    private final FInteger fint = new FInteger(0);
    private final FNatural fnat = new FNatural(0);

    public FRational(long numerator, long denominator) {
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + numerator + "/" + denominator);
        }
        this.numerator = new FInteger(numerator);
        this.denominator = new FNatural(denominator);

        FInteger gcd = fint.gcd(this.numerator, this.denominator);
        this.numerator = fint.div(this.numerator, gcd);
        this.denominator = fnat.div(this.denominator, gcd);
    }

    public FRational(FInteger numerator, FInteger denominator) {
        if (denominator.get().compareTo(BigInteger.ZERO) < 0) {
            numerator = fint.mul(numerator, new FInteger(-1));
            denominator = fint.mul(denominator, new FInteger(-1));
        }
        if (denominator.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + numerator + "/" + denominator);

        this.numerator = new FInteger(numerator);
        this.denominator = new FNatural(denominator);

        FInteger gcd = fint.gcd(this.numerator, this.denominator);
        this.numerator = fint.div(this.numerator, gcd);
        this.denominator = fnat.div(this.denominator, gcd);
    }

    public FRational(FInteger numerator, FNatural denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        if (denominator.get().equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + numerator + "/" + denominator);

        FInteger gcd = fint.gcd(this.numerator, this.denominator);
        this.numerator = fint.div(this.numerator, gcd);
        this.denominator = fnat.div(this.denominator, gcd);
    }

    public FRational(BigInteger numerator, BigInteger denominator) {
        if (denominator.compareTo(BigInteger.ZERO) < 0) {
            numerator = numerator.multiply(BigInteger.ONE.negate());
            denominator = denominator.multiply(BigInteger.ONE.negate());
        }
        if (denominator.equals(BigInteger.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + numerator + "/" + denominator);

        this.numerator = new FInteger(numerator);
        this.denominator = new FNatural(denominator);

        FInteger gcd = fint.gcd(this.numerator, this.denominator);
        this.numerator = fint.div(this.numerator, gcd);
        this.denominator = fnat.div(this.denominator, gcd);
    }

    public FRational(MathObject number) {
        FRational num = number.getRational();
        this.numerator = new FInteger(num.get()[0]);
        this.denominator = new FNatural(num.get()[1]);
    }

    public FRational(String s) {
        ArrayList<String> numbers = Helper.wordsFromString(s.replace('/', ' '));
        if (numbers.size() == 1) numbers.add("1");
        FRational num = new FRational(new BigInteger(numbers.get(0)), new BigInteger(numbers.get(1)));
        this.numerator = new FInteger(num.get()[0]);
        this.denominator = new FNatural(num.get()[1]);
    }

    @Override
    public BigInteger[] get() {
        return new BigInteger[]{this.numerator.get(), this.denominator.get()};
    }

    public MathObject get(int n) {
        if (n == 0) return numerator;
        else return denominator;
    }

    @Override
    public FNatural getNatural() {
        return this.getInteger().getNatural();
    }

    @Override
    public FInteger getInteger() {
        return fint.div(this.numerator, this.denominator);
    }

    @Override
    public FRational getRational() {
        return this;
    }

    @Override
    public FReal getReal() {
        return (new FReal(0)).div(this.numerator, this.denominator);
    }

    @Override
    public FComplex getComplex() {
        return new FComplex(this.getReal().get(), BigDecimal.ZERO); // to do...
    }

    @Override
    public FRational sum(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                fint.sum(fint.mul(an.get(0), bn.get(1)), fint.mul(an.get(1), bn.get(0))),
                fnat.mul(an.get(1), bn.get(1))
        );
    }

    @Override
    public FRational sub(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                fint.sub(fint.mul(an.get(0), bn.get(1)), fint.mul(an.get(1), bn.get(0))),
                fnat.mul(an.get(1), bn.get(1))
        );
    }

    @Override
    public FRational mul(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                fint.mul(an.get(0), bn.get(0)),
                fnat.mul(an.get(1), bn.get(1))
        );
    }

    @Override
    public FRational div(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                fint.mul(an.get(0), bn.get(1)),
                fint.mul(an.get(1), bn.get(0))
        );
    }

    @Override
    public MathObject mod(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция mod не определена для рациональных чисел.");
    }

    @Override
    public FRational pow(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        if (new FInteger(bn.get(0)).get().compareTo(BigInteger.ZERO) < 0)
            return this.pow(
                    this.div(new FRational(1, 1), an),
                    this.mul(bn, new FRational(-1, 1))
            );

        return new FRational(
                fint.root(fint.pow(
                        this.mul(an.get(0), fint.pow(new FInteger(10), bn.get(1))),
                        bn.get(0)), bn.get(1)),
                fint.root(fint.pow(
                        this.mul(an.get(1), fint.pow(new FInteger(10), bn.get(1))),
                        bn.get(0)), bn.get(1))
        );
    }

    @Override
    public FRational root(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return this.pow(an, this.div(new FRational(1, 1), bn));
    }

    @Override
    public FRational log(MathObject a, MathObject b) {
        throw new NullPointerException("Функция log временно не введена для рациональных чисел.");
    }

    @Override
    public FRational gcd(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        FNatural lcm = fnat.div(fnat.mul(an.get(1), bn.get(1)), fnat.gcd(an.get(1), bn.get(1)));
        FInteger ann = this.mul(an, lcm).getInteger();
        FInteger bnn = this.mul(bn, lcm).getInteger();
        FInteger gcd = fint.gcd(ann, bnn);
        return new FRational(gcd, lcm);
        // умножаем дроби на НОК знаменателей, находим НОД, делим на НОК знаменателей
    }

    @Override
    public FRational lcm(MathObject a, MathObject b) {
        return this.div(this.mul(a, b), this.gcd(a, b));
    }

    @Override
    public FRational fact(MathObject a) {
        throw new NullPointerException("Функция fact временно не введена для рациональных чисел.");
    }

    @Override
    public MathObject conc(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция conc не определена для рациональных чисел.");
    }

    @Override
    public FRational rand(MathObject a, MathObject b) {
        throw new NullPointerException("Функция rand временно не введена для рациональных чисел.");
    }

    @Override
    public FRational abs(MathObject a) {
        FRational an = new FRational(a);
        return new FRational(fint.abs(an.get(0)), fnat.abs(an.get(1)));
    }

    @Override
    public MathObject and(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция and не определена для рациональных чисел.");
    }

    @Override
    public MathObject or(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция or не определена для рациональных чисел.");
    }

    @Override
    public MathObject xor(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция xor не определена для рациональных чисел.");
    }

    @Override
    public FRational min(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        FRational c = this.sub(an, bn);
        if (c.get(0).getInteger().get().compareTo(BigInteger.ZERO) <= 0) return an;
        else return bn;
    }

    @Override
    public FRational max(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        FRational c = this.sub(an, bn);
        if (c.get(0).getInteger().get().compareTo(BigInteger.ZERO) <= 0) return bn;
        else return an;
    }

    @Override
    public FRational sign(MathObject a) {
        FRational an = new FRational(a);
        return new FRational(an.get(0).getInteger().get().compareTo(BigInteger.ZERO), 1);
    }

    @Override
    public MathObject[] primes(MathObject a) {
        throw new ArithmeticException("Функция primes не определена для рациональных чисел.");
    }

    @Override
    public MathObject not(MathObject a) {
        throw new ArithmeticException("Функция not не определена для рациональных чисел.");
    }

    @Override
    public FRational med(MathObject a, MathObject b) {
        FRational an = new FRational(a);
        FRational bn = new FRational(b);
        return new FRational(
                fint.sum(an.get(0), bn.get(0)),
                fnat.sum(an.get(1), bn.get(1))
        );
    }

    @Override
    public MathObject sin(MathObject a) {
        throw new ArithmeticException("Функция sin не определена для рациональных чисел.");
    }

    @Override
    public MathObject cos(MathObject a) {
        throw new ArithmeticException("Функция cos не определена для рациональных чисел.");
    }

    @Override
    public MathObject tan(MathObject a) {
        throw new ArithmeticException("Функция tan не определена для рациональных чисел.");
    }

    @Override
    public MathObject arcsin(MathObject a) {
        throw new ArithmeticException("Функция arcsin не определена для рациональных чисел.");
    }

    @Override
    public MathObject arccos(MathObject a) {
        throw new ArithmeticException("Функция arccos не определена для рациональных чисел.");
    }

    @Override
    public MathObject arctan(MathObject a) {
        throw new ArithmeticException("Функция arctan не определена для рациональных чисел.");
    }

    @Override
    public FRational conj(MathObject a) {
        return new FRational(a);
    }

    @Override
    public FRational arg(MathObject a) {
        FRational an = new FRational(a);
        return new FRational(((BigInteger) an.get(0).get()).compareTo(BigInteger.ZERO) >= 0 ? new FReal(0) : (new FReal(Math.PI)).getRational());
    }

    @Override
    public FRational norm(MathObject a) {
        FRational an = new FRational(a);
        return this.mul(an, an);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == FRational.class
                && this.numerator.equals(((FRational) obj).numerator)
                && this.denominator.equals(((FRational) obj).denominator);
    }

    @Override
    public String toString() {
        return this.numerator + (this.denominator.get().equals(BigInteger.ONE) ? "" : "/" + this.denominator);
    }
}
