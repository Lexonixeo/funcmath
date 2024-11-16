package funcmath.object;

import funcmath.exceptions.FunctionException;

import java.io.Serializable;

public interface MathObject extends Serializable {
  Object get();

  // для поддержки модов их нужно убрать
  FNatural getNatural();
  FInteger getInteger();
  FRational getRational();
  FReal getReal();
  FComplex getComplex();

  // Функции натуральных чисел
  MathObject sum(MathObject addend1, MathObject addend2);
  MathObject sub(MathObject minuend, MathObject subtrahend);
  MathObject mul(MathObject multiplicand, MathObject multiplier);
  MathObject div(MathObject dividend, MathObject divisor);
  MathObject mod(MathObject dividend, MathObject divisor);
  MathObject pow(MathObject base, MathObject power);
  MathObject root(MathObject radicand, MathObject degree);
  MathObject log(MathObject base, MathObject antilogarithm);
  // MathObject hyper(MathObject base, MathObject exponent, MathObject grade); // гипер-функция подобно sum, mul, pow...
  // MathObject hroot(MathObject radicand, MathObject degree, MathObject grade);
  // MathObject hlog(MathObject base, MathObject antilogarithm, MathObject grade);
  MathObject gcd(MathObject a, MathObject b);
  MathObject lcm(MathObject a, MathObject b);
  MathObject fact(MathObject a);
  MathObject conc(MathObject a, MathObject b);
  MathObject rand(MathObject a, MathObject b);
  MathObject and(MathObject a, MathObject b);
  MathObject or(MathObject a, MathObject b);
  MathObject xor(MathObject a, MathObject b);
  MathObject min(MathObject a, MathObject b);
  MathObject max(MathObject a, MathObject b);
  MathObject sign(MathObject a);
  // MathObject phi(MathObject n); // функция Эйлера
  // MathObject tau(MathObject n); // кол-во делителей числа
  // MathObject sigma(MathObject n); // сумма натуральных делителей числа
  // MathObject pi(MathObject n);
  MathObject[] primes(MathObject n); // разложение на простые множители
  // MathObject A(MathObject m, MathObject n); // функция Аккермана

  // Функции целых чисел
  MathObject abs(MathObject a);
  MathObject not(MathObject a);

  // Функции рациональных чисел
  MathObject med(MathObject a, MathObject b); // медианта (a + b) / (c + d)
  // MathObject mink(MathObject a); // функция Минковского (непрерывно отображает рациональные числа
  // на отрезке [0,1] в двоичные рациональные на этом же отрезке)
  // MathObject[] contfrac(MathObject a); // разложение дроби в цепную дробь

  // Функции действительных чисел
  MathObject sin(MathObject a);
  MathObject cos(MathObject a);
  MathObject tan(MathObject a);
  MathObject arcsin(MathObject a);
  MathObject arccos(MathObject a);
  MathObject arctan(MathObject a);

  // Функции комплексных чисел
  MathObject conj(MathObject a);
  MathObject arg(MathObject a);
  MathObject norm(MathObject a);

  int compareTo(MathObject a);

  static MathObject parseMathObject(String s, String resultClassName) {
    return switch (resultClassName) {
      case "integer" -> new FInteger(s);
      case "natural" -> new FNatural(s);
      case "rational" -> new FRational(s);
      case "real" -> new FReal(s);
      case "complex" -> new FComplex(s);
      default ->
          throw new FunctionException(
              "Не существует такой области определения: " + resultClassName);
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
  FContinuedFraction (число в виде цепной дроби)
  FGroup
  FTriangle и т.д.
  FString
  FColor
  FPicture
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
