package funcmath.function;

import funcmath.object.FNatural;

public class FunctionTester {
    public static void main(String[] args) {
        FNatural a = new FNatural(240);
        FNatural b = new FNatural(14);
        FNatural c = new FNatural(62);
        Function f = new Function(587453148);
        System.out.println(f.definition);
        System.out.println(f.use(a, b, c));
    }
}
