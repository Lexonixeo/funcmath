package funcmath.game;

import funcmath.function.Function;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LevelState implements Serializable {
  @Serial private static final long serialVersionUID = -6599822850782573322L;

  int level;
  ArrayList<MathObject> numbers;
  HashMap<String, Function> functions;

  public LevelState(int level, ArrayList<MathObject> numbers, HashMap<String, Function> functions) {
    this.level = level;
    this.numbers = Helper.deepClone(numbers);
    this.functions = Helper.deepClone(functions);
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
