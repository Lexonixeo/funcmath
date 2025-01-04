package funcmath.game.defaultlevel;

import funcmath.exceptions.FunctionException;
import funcmath.exceptions.MathObjectException;
import funcmath.function.Function;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DLevelState implements Serializable {
  @Serial private static final long serialVersionUID = -6599822850782573322L;

  int level;
  ArrayList<MathObject> numbers;
  HashMap<String, Function> functions;
  HashSet<String> usingNames = new HashSet<>();

  public DLevelState() {}

  public DLevelState(
      int level, ArrayList<MathObject> numbers, HashMap<String, Function> functions) {
    this.level = level;
    this.numbers = Helper.deepClone(numbers);
    this.functions = Helper.deepClone(functions);
  }

  private void updateUsingNames() {
    usingNames.clear();
    for (Function f : functions.values()) {
      if (usingNames.contains(f.getName())) {
        throw new FunctionException("Function names must not be repeated: " + f.getName());
      }
      usingNames.add(f.getName());
    }
    for (MathObject n : numbers) {
      if (n.getName() != null && usingNames.contains(n.getName())) {
        throw new MathObjectException(
            "MathObject names must not be repeated with Functions or other MathObjects: "
                + n.getName());
      }
      if (n.getName() != null) {
        usingNames.add(n.getName());
      }
    }
  }

  public void save() {
    updateUsingNames();
    Helper.write(this, "data/currentLevel.dat");
  }

  public HashSet<String> getUsingNames() {
    return usingNames;
  }

  public int getLevel() {
    return level;
  }

  public void setFunctions(HashMap<String, Function> functions) {
    this.functions = functions;
  }

  public void setNumbers(ArrayList<MathObject> numbers) {
    this.numbers = numbers;
  }

  public ArrayList<MathObject> getNumbers() {
    return numbers;
  }

  public HashMap<String, Function> getFunctions() {
    return functions;
  }
}
