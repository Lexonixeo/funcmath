package funcmath.object;

import org.nevec.rjm.BigDecimalMath;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.SecureRandom;

public class FReal implements MathObject {
    @Serial
    private static final long serialVersionUID = -1314616813911607893L;

    protected BigDecimal number;
    private final BigDecimal eps = BigDecimal.valueOf(0.001);

    public FReal(double number) {
        this.number = BigDecimal.valueOf(number);
    }

    public FReal(BigInteger number) {
        this.number = new BigDecimal(number);
    }

    public FReal(BigDecimal number) {
        this.number = number;
    }

    public FReal(String s) {
        this.number = switch (s) {
            case "e", "E", "е", "Е" -> BigDecimalMath.exp(new MathContext(5));
            case "pi", "PI", "Pi", "pI", "π", "пи", "ПИ", "Пи", "пИ" -> BigDecimalMath.pi(new MathContext(5));
            default -> new BigDecimal(s);
        };
    }

    public FReal(MathObject number) {
        this.number = number.getReal().get();
    }

    @Override
    public BigDecimal get() {
        return number;
    }

    @Override
    public FNatural getNatural() {
        return this.getInteger().getNatural();
    }

    @Override
    public FInteger getInteger() {
        return new FInteger(this.number.round(new MathContext(5, RoundingMode.HALF_UP)).toBigInteger());
    }

    @Override
    public FRational getRational() {
        FInteger den = new FInteger(239);
        return new FRational(this.mul(this, den).getInteger().get(), den.get());
    }

    @Override
    public FReal getReal() {
        return this;
    }

    @Override
    public FComplex getComplex() {
        return new FComplex(this.number, BigDecimal.ZERO); // to do...
    }

    @Override
    public FReal sum(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(an.get().add(bn.get()));
    }

    @Override
    public FReal sub(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(an.get().add(bn.get().negate()));
    }

    @Override
    public FReal mul(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(an.get().multiply(bn.get()));
    }

    @Override
    public FReal div(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        if (bn.get().equals(BigDecimal.ZERO))
            throw new ArithmeticException("Деление на ноль не имеет смысла: " + an + "/" + bn);
        return new FReal(an.get().divide(bn.get(), RoundingMode.HALF_UP));
    }

    @Override
    public MathObject mod(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция mod не определена для действительных чисел.");
    }

    @Override
    public FReal pow(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(BigDecimalMath.pow(an.get(), bn.get()));
    }

    @Override
    public FReal root(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(BigDecimalMath.pow(an.get(), BigDecimal.ONE.divide(bn.get(), RoundingMode.HALF_UP)));
    }

    @Override
    public FReal log(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        return new FReal(BigDecimalMath.log(an.get()).divide(BigDecimalMath.log(bn.get()), RoundingMode.HALF_UP));
    }

    @Override
    public MathObject gcd(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция gcd не определена для действительных чисел.");
    }

    @Override
    public MathObject lcm(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция lcm не определена для действительных чисел.");
    }

    @Override
    public MathObject fact(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(BigDecimalMath.Gamma(an.get()));
    }

    @Override
    public MathObject conc(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция conc не определена для действительных чисел.");
    }

    @Override
    public FReal rand(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        SecureRandom random = new SecureRandom();
        return new FReal(random.nextDouble(an.get().doubleValue(), bn.get().doubleValue())); // потом исправить
    }

    @Override
    public FReal abs(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(an.get().abs());
    }

    @Override
    public MathObject and(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция and не определена для действительных чисел.");
    }

    @Override
    public MathObject or(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция or не определена для действительных чисел.");
    }

    @Override
    public MathObject xor(MathObject a, MathObject b) {
        throw new ArithmeticException("Функция xor не определена для действительных чисел.");
    }

    @Override
    public FReal min(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        FReal c = this.sub(an, bn);
        if (c.get().compareTo(BigDecimal.ZERO) <= 0) return an;
        else return bn;
    }

    @Override
    public FReal max(MathObject a, MathObject b) {
        FReal an = new FReal(a);
        FReal bn = new FReal(b);
        FReal c = this.sub(an, bn);
        if (c.get().compareTo(BigDecimal.ZERO) <= 0) return bn;
        else return an;
    }

    @Override
    public FReal sign(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(an.get().compareTo(BigDecimal.ZERO));
    }

    @Override
    public MathObject[] primes(MathObject a) {
        throw new ArithmeticException("Функция primes не определена для действительных чисел.");
    }

    @Override
    public MathObject not(MathObject a) {
        throw new ArithmeticException("Функция not не определена для действительных чисел.");
    }

    @Override
    public FReal sin(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(BigDecimalMath.sin(an.get()));
    }

    @Override
    public FReal cos(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(BigDecimalMath.cos(an.get()));
    }

    @Override
    public FReal tan(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(BigDecimalMath.tan(an.get()));
    }

    @Override
    public FReal arcsin(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(BigDecimalMath.asin(an.get()));
    }

    @Override
    public FReal arccos(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(BigDecimalMath.acos(an.get()));
    }

    @Override
    public FReal arctan(MathObject a) {
        FReal an = new FReal(a);
        return new FReal(BigDecimalMath.atan(an.get()));
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
        return obj.getClass() == FReal.class
                && this.number.add(eps.negate()).compareTo(((FReal) obj).get()) < 0
                && this.number.add(eps).compareTo(((FReal) obj).get()) > 0;
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
