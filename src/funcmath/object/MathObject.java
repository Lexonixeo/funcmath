package funcmath.object;

import java.io.Serializable;

public interface MathObject extends Serializable {
    Object get();
    FNatural getNatural();
    FInteger getInteger();
    FRational getRational();
    FReal getReal();
    FComplex getComplex();
    MathObject sum(MathObject a, MathObject b);
    MathObject sub(MathObject a, MathObject b);
    MathObject mul(MathObject a, MathObject b);
    MathObject div(MathObject a, MathObject b);
    MathObject mod(MathObject a, MathObject b);
    MathObject pow(MathObject a, MathObject b);
    MathObject root(MathObject a, MathObject b);
    MathObject log(MathObject a, MathObject b);
    MathObject gcd(MathObject a, MathObject b);
    MathObject lcm(MathObject a, MathObject b);
    MathObject rand(MathObject a, MathObject b);
    MathObject abs(MathObject a);
    MathObject xor(MathObject a, MathObject b);
    MathObject not(MathObject a);
    MathObject and(MathObject a, MathObject b);
    MathObject or(MathObject a, MathObject b);
    MathObject conj(MathObject a);
    MathObject arg(MathObject a);

    static MathObject parseMathObject(String s) {
        String ss = s.substring(0, s.length() - 1);
        return switch (s.charAt(s.length() - 1)) {
            case 'z' -> new FInteger(ss);
            case 'n' -> new FNatural(ss);
            case 'q' -> new FRational(ss);
            case 'r' -> new FReal(ss);
            case 'c' -> new FComplex(ss);
            default -> throw new IllegalArgumentException("Не существует такого числа");
        };
    }

    static MathObject parseMathObject(String s, String resultClassName) {
        return switch (resultClassName) {
            case "integer" -> new FInteger(s);
            case "natural" -> new FNatural(s);
            case "rational" -> new FRational(s);
            case "real" -> new FReal(s);
            case "complex" -> new FComplex(s);
            default -> throw new IllegalArgumentException("Не существует такой области определения");
        };
    }
}
