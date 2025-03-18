package funcmath.packs.defaultpack.level;

import java.io.Serial;
import java.io.Serializable;

public record DLRules(
    boolean allowBack, boolean allowCalc, boolean infinityNumbers, boolean infinityFunctions)
    implements Serializable {
  @Serial private static final long serialVersionUID = 9161078444026828395L;
}
