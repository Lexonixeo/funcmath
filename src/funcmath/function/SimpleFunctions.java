package funcmath.function;

import funcmath.object.MathObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class SimpleFunctions {
    static ArrayList<String> names = new ArrayList<>();

    static {
        Method[] functions = SimpleFunctions.class.getMethods();
        for (Method f : functions) {
            names.add(f.getName());
        }
    }

    public static MathObject sum(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.sum(a, b);
    }

    public static MathObject sub(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.sub(a, b);
    }

    public static MathObject mul(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.mul(a, b);
    }

    public static MathObject div(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.div(a, b);
    }

    public static MathObject mod(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.mod(a, b);
    }

    public static MathObject pow(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.pow(a, b);
    }

    public static MathObject root(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.root(a, b);
    }

    public static MathObject log(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.log(a, b);
    }

    public static MathObject gcd(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.gcd(a, b);
    }

    public static MathObject lcm(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.lcm(a, b);
    }

    public static MathObject fact(MathObject resultClass, MathObject a) {
        return resultClass.fact(a);
    }

    public static MathObject conc(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.conc(a, b);
    }

    public static MathObject rand(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.rand(a, b);
    }

    public static MathObject abs(MathObject resultClass, MathObject a) {
        return resultClass.abs(a);
    }

    public static MathObject and(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.and(a, b);
    }

    public static MathObject or(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.or(a, b);
    }

    public static MathObject xor(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.xor(a, b);
    }

    public static MathObject min(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.min(a, b);
    }

    public static MathObject max(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.max(a, b);
    }

    public static MathObject sign(MathObject resultClass, MathObject a) {
        return resultClass.sign(a);
    }

    public static MathObject[] primes(MathObject resultClass, MathObject a) {
        return resultClass.primes(a);
    }

    public static MathObject not(MathObject resultClass, MathObject a) {
        return resultClass.not(a);
    }

    public static MathObject med(MathObject resultClass, MathObject a, MathObject b) {
        return resultClass.med(a, b);
    }

    public static MathObject sin(MathObject resultClass, MathObject a) {
        return resultClass.sin(a);
    }

    public static MathObject cos(MathObject resultClass, MathObject a) {
        return resultClass.cos(a);
    }

    public static MathObject tan(MathObject resultClass, MathObject a) {
        return resultClass.tan(a);
    }

    public static MathObject arcsin(MathObject resultClass, MathObject a) {
        return resultClass.arcsin(a);
    }

    public static MathObject arccos(MathObject resultClass, MathObject a) {
        return resultClass.arccos(a);
    }

    public static MathObject arctan(MathObject resultClass, MathObject a) {
        return resultClass.arctan(a);
    }

    public static MathObject conj(MathObject resultClass, MathObject a) {
        return resultClass.conj(a);
    }

    public static MathObject arg(MathObject resultClass, MathObject a) {
        return resultClass.arg(a);
    }

    public static MathObject norm(MathObject resultClass, MathObject a) {
        return resultClass.norm(a);
    }

    public static MathObject[] ignore(MathObject ignoredResultClass, MathObject ignoredA) {
        return new MathObject[]{};
    }
}
