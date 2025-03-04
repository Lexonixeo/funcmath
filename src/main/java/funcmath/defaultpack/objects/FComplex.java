package funcmath.defaultpack.objects;

import ch.obermuhlner.math.big.BigComplex;
import ch.obermuhlner.math.big.BigComplexMath;
import funcmath.exceptions.MathException;
import funcmath.object.MathObject;
import funcmath.utility.Hash;
import funcmath.utility.Helper;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

public class FComplex implements MathObject {
    @Serial
    private static final long serialVersionUID = 6419807172147493028L;

    public static final FComplex NEGATIVE_ONE = new FComplex(-1, 0);
    public static final FComplex ZERO = new FComplex(0, 0);
    public static final FComplex ONE = new FComplex(1, 0);
    public static final FComplex TWO = new FComplex(2, 0);
    public static final FComplex IMAGINARY_UNIT = new FComplex(0, 1);

    private String name;
    private final String rawName;

    protected FReal real, imaginary;

    public FComplex() {
        this(0, 0);
    }

    public FComplex(double real, double imaginary) {
        this(new FReal(real), new FReal(imaginary));
    }

    public FComplex(BigInteger real, BigInteger imaginary) {
        this(new FReal(real), new FReal(imaginary));
    }

    public FComplex(BigDecimal real, BigDecimal imaginary) {
        this(new FReal(real), new FReal(imaginary));
    }

    public FComplex(BigComplex complex) {
        this(new FReal(complex.re), new FReal(complex.im));
    }

    public FComplex(FReal real, FReal imaginary) {
        this.real = real;
        this.imaginary = imaginary;
        this.rawName = this.real.toString()
                + (this.imaginary.get().compareTo(BigDecimal.ZERO) >= 0
                ? "+"
                : "") // знак мнимой компоненты
                + this.imaginary.toString()
                + "i";
        this.name = this.rawName;
    }

    public FComplex(String s) {
        ArrayList<String> snumbers =
                Helper.wordsFromString(s.replace('i', ' ').replace('+', ' ').replaceAll("-", " -"));
        this(new FReal(snumbers.get(0)), new FReal(snumbers.get(1)));
    }

    public BigComplex get() {
        return BigComplex.valueOf(this.getRe().get(), this.getIm().get()); // мб это в get()?
    }

    public FReal getRe() {
        return real;
    }

    public FReal getIm() {
        return imaginary;
    }

    @Override
    public String getType() {
        return "complex";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void generateName(HashSet<String> usedNames) {
        String newName = rawName + "(cmplx)";
        int i = 0;
        while (!usedNames.contains(newName)) {
            i++;
            newName = rawName + "(cmplx" + i + ")";
        }
        this.name = newName;
    }

    @Override
    public Hash getHash() {
        return Hash.encode(real, imaginary);
    }

    public static FComplex sum(FComplex addend1, FComplex addend2) {
        return new FComplex(
                FReal.sum(addend1.getRe(), addend2.getRe()), FReal.sum(addend1.getIm(), addend2.getIm()));
    }

    public static FComplex sub(FComplex minuend, FComplex subtrahend) {
        return new FComplex(
                FReal.sub(minuend.getRe(), subtrahend.getRe()),
                FReal.sub(minuend.getIm(), subtrahend.getIm()));
    }

    public static FComplex mul(FComplex multiplicand, FComplex multiplier) {
        return new FComplex(
                FReal.sub(
                        FReal.mul(multiplicand.getRe(), multiplier.getRe()),
                        FReal.mul(multiplicand.getIm(), multiplier.getIm())),
                FReal.sum(
                        FReal.mul(multiplicand.getRe(), multiplier.getIm()),
                        FReal.mul(multiplicand.getIm(), multiplier.getRe())));
    }

    public static FComplex div(FComplex dividend, FComplex divisor) {
        FReal abs = abs(divisor).getRe();
        if (abs(divisor).equals(ZERO)) {
            throw new MathException("Деление на ноль не имеет смысла: " + dividend + "/" + divisor);
        }
        FComplex temp = mul(dividend, conj(divisor));
        return new FComplex(FReal.div(temp.getRe(), abs), FReal.div(temp.getIm(), abs));
    }

    public static FComplex pow(FComplex base, FComplex power) {
        return new FComplex(BigComplexMath.pow(base.get(), power.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex root(FComplex radicand, FComplex degree) {
        return new FComplex(BigComplexMath.root(radicand.get(), degree.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex log(FComplex base, FComplex antilogarithm) {
        return new FComplex(
                BigComplexMath.log(antilogarithm.get(), DEFAULT_MATHCONTEXT)
                        .divide(BigComplexMath.log(base.get(), DEFAULT_MATHCONTEXT), DEFAULT_MATHCONTEXT));
    }

    // MathObject hyper(MathObject base, MathObject exponent, MathObject grade); // гиперфункция
    // подобно sum, mul, pow...
    // MathObject hroot(MathObject radicand, MathObject degree, MathObject grade);
    // MathObject hlog(MathObject base, MathObject antilogarithm, MathObject grade);

    public static FComplex fact(FComplex number) {
        return new FComplex(BigComplexMath.factorial(number.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex sign(FComplex number) {
        if (abs(number).equals(ZERO)) return ZERO;
        return div(number, abs(number));
    }

    public static FComplex abs(FComplex number) {
        return new FComplex(FReal.root(norm(number), FReal.TWO), FReal.ZERO);
    }

    public static FComplex sin(FComplex number) {
        return new FComplex(BigComplexMath.sin(number.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex cos(FComplex number) {
        return new FComplex(BigComplexMath.cos(number.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex tan(FComplex number) {
        return new FComplex(BigComplexMath.tan(number.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex arcsin(FComplex number) {
        return new FComplex(BigComplexMath.asin(number.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex arccos(FComplex number) {
        return new FComplex(BigComplexMath.acos(number.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex arctan(FComplex number) {
        return new FComplex(BigComplexMath.atan(number.get(), DEFAULT_MATHCONTEXT));
    }

    public static FComplex conj(FComplex number) {
        return new FComplex(number.getRe(), FReal.sub(FReal.ZERO, number.getIm()));
    }

    public static FReal arg(FComplex number) {
        return FReal.arctan2(number.getIm(), number.getRe());
    }

    public static FReal norm(FComplex number) {
        return
                FReal.sum(
                        FReal.mul(number.getRe(), number.getRe()), FReal.mul(number.getIm(), number.getIm()));
    }
}
