package funcmath.game.defaultlevel;

import java.io.Serial;
import java.io.Serializable;

public class DLevelRules implements Serializable {
  @Serial
  private static final long serialVersionUID = 6390760097076472468L;

  boolean allowBack;
  boolean allowCalc;
  boolean infinityNumbers;
  boolean infinityFunctions;

  public DLevelRules() {}

  public void setAllowBack(boolean allowBack) {
    this.allowBack = allowBack;
  }

  public void setAllowCalc(boolean allowCalc) {
    this.allowCalc = allowCalc;
  }

  public void setInfinityFunctions(boolean infinityFunctions) {
    this.infinityFunctions = infinityFunctions;
  }

  public void setInfinityNumbers(boolean infinityNumbers) {
    this.infinityNumbers = infinityNumbers;
  }

  public boolean isAllowBack() {
    return allowBack;
  }

  public boolean isAllowCalc() {
    return allowCalc;
  }

  public boolean isInfinityFunctions() {
    return infinityFunctions;
  }

  public boolean isInfinityNumbers() {
    return infinityNumbers;
  }
}
