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
    MathObject fact(MathObject a);
    MathObject rand(MathObject a, MathObject b);
    MathObject abs(MathObject a);
    MathObject not(MathObject a);
    MathObject and(MathObject a, MathObject b);
    MathObject or(MathObject a, MathObject b);
    MathObject conj(MathObject a);
    MathObject arg(MathObject a);

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

    /*
    В будущем добавить:
    FErrorInteger (число с погрешностью)
    FModuloInteger (по какому-то модулю)
    FGauss
    FVector
    FMatrix
    FTensor
    FTriangle и т.д.
    FString
    FColor
    FMinecraftItem
    FWord - это то, из-за чего я придумал эту игру :)
     */

    // xor a b = and or a b not and a b
    // lcm a b = div mul a b gcd a b
}
