package funcmath.object;

import java.io.Serializable;

public interface MathObject extends Serializable {
    Object get();
    FNatural getNatural();
    FInteger getInteger();
    FRational getRational();
    FReal getReal();
    FComplex getComplex();

    // Функции натуральных чисел
    MathObject sum(MathObject a, MathObject b);
    MathObject sub(MathObject a, MathObject b);
    MathObject mul(MathObject a, MathObject b);
    MathObject div(MathObject a, MathObject b);
    MathObject mod(MathObject a, MathObject b);
    MathObject pow(MathObject a, MathObject b);
    MathObject root(MathObject a, MathObject b);
    MathObject log(MathObject a, MathObject b);
    // MathObject hyper(MathObject a, MathObject b, MathObject c); // гипер-функция подобно sum, mul, pow...
    // MathObject hroot(MathObject a, MathObject b, MathObject c);
    // MathObject hlog(MathObject a, MathObject b, MathObject c);
    MathObject gcd(MathObject a, MathObject b);
    MathObject lcm(MathObject a, MathObject b);
    MathObject fact(MathObject a);
    MathObject conc(MathObject a, MathObject b);
    MathObject rand(MathObject a, MathObject b);
    MathObject abs(MathObject a);
    MathObject and(MathObject a, MathObject b);
    MathObject or(MathObject a, MathObject b);
    MathObject xor(MathObject a, MathObject b);
    MathObject min(MathObject a, MathObject b);
    MathObject max(MathObject a, MathObject b);
    MathObject sign(MathObject a);
    // MathObject phi(MathObject a); // функция Эйлера
    // MathObject primes(MathObject a); // разложение на простые множители
    // MathObject A(MathObject a, MathObject b); // функция Аккермана

    // Функции целых чисел
    MathObject not(MathObject a);

    // Функции рациональных чисел

    // Функции действительных чисел
    // MathObject sin(MathObject a);
    // MathObject cos(MathObject a);
    // MathObject tan(MathObject a);
    // MathObject arcsin(MathObject a);
    // MathObject arccos(MathObject a);
    // MathObject arctan2(MathObject a, MathObject b);

    // Функции комплексных чисел
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
    FUnknownInteger (мы не видим число, но оно есть)
    FBinaryInteger (с другой системой счисления)
    FFibonacciInteger (с фибоначчиевой системой счисления)
    FErrorInteger (число с погрешностью)
    FModuloInteger (по какому-то модулю, Полная система вычетов)
    (Приведенная система вычетов)
    FGauss
    FVector
    FMatrix
    FTensor
    FTriangle и т.д.
    FString
    FColor
    FFile
    FMoney (деньги с разными валютами - курсы валют меняются с каждым ходом)
    FMeasure (физическая величина с размерностью)
    FAtom (или нейтроны, электроны и т.д. - частицы)
    FMolecule
    FErrorMeasure
    FMinecraftItem
    FWord (это то, из-за чего я придумал эту игру)
    FNumeral (число в виде слова)
     */
}
