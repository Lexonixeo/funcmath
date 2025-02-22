package funcmath.defaultpack.level;

import funcmath.functions.Function;
import funcmath.level.LevelInfo;
import funcmath.level.LevelState;
import funcmath.object.MathObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public final class DLState implements LevelState {
  private final DLInfo info;
  private final Stack<DLState> history;
  private final ArrayList<MathObject> numbers;
  private final HashMap<String, Function> functions;
  private final HashSet<String> usingNames;

  public DLState() {
    this(null, null, null, null);
  }

  public DLState(
      DLInfo info,
      Stack<DLState> historyWithoutHim,
      ArrayList<MathObject> numbers,
      HashMap<String, Function> functions) {
    this.info = info;
    this.history = historyWithoutHim;
    this.numbers = numbers;
    this.functions = functions;
    this.usingNames = new HashSet<>();
    this.usingNames.addAll(functions.keySet());
    for (MathObject n : numbers) {
      this.usingNames.add(n.getName());
    }
    this.history.add(this);
  }

  public Stack<DLState> getHistory() {
    return history;
  }

  public ArrayList<MathObject> getNumbers() {
    return numbers;
  }

  public MathObject getNumber(String name) {
    for (MathObject n : numbers) {
      if (n.getName().equals(name)) {
        return n;
      }
    }
    return null;
  }

  public HashMap<String, Function> getFunctions() {
    return functions;
  }

  public HashSet<String> getUsingNames() {
    return usingNames;
  }

  @Override
  public String getType() {
    return "default";
  }

  @Override
  public LevelInfo getLevelInfo() {
    return this.info;
  }
}
