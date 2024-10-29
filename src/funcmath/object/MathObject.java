package funcmath.object;

import java.io.Serializable;

public interface MathObject extends Serializable {
    Object get();
    MathObject sum(MathObject a, MathObject b);
    MathObject sub(MathObject a, MathObject b);
    MathObject mul(MathObject a, MathObject b);
    MathObject div(MathObject a, MathObject b);
    MathObject mod(MathObject a, MathObject b);
    MathObject pow(MathObject a, MathObject b);
    MathObject abs(MathObject a);
    MathObject gcd(MathObject a, MathObject b);
    MathObject lcm(MathObject a, MathObject b);
    MathObject rand(MathObject a, MathObject b);
    MathObject xor(MathObject a, MathObject b);
    MathObject not(MathObject a);
    MathObject and(MathObject a, MathObject b);
    MathObject or(MathObject a, MathObject b);

}
