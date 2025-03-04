package funcmath.defaultpack.level;

import funcmath.functions.Function;
import funcmath.level.LevelState;
import funcmath.level.PlayFlag;
import funcmath.object.MathObject;
import funcmath.utility.Hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public final class DLState implements LevelState {
  private final Stack<DLState> history;
  private final ArrayList<MathObject> numbers;
  private final HashMap<String, Function> functions;
  private final HashSet<String> usingNames;
  private final PlayFlag pf;
  private final Long levelID;

  public DLState() {
    this(new Stack<>(), new ArrayList<>(), new HashMap<>(), PlayFlag.PRELEVELS, 0L);
  }

  public DLState(
      Stack<DLState> historyWithoutHim,
      ArrayList<MathObject> numbers,
      HashMap<String, Function> functions, PlayFlag pf, Long levelID) {
    this.pf = pf;
    this.levelID = levelID;
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
  public Long getLevelID() {
    return levelID;
  }

  @Override
  public PlayFlag getPlayFlag() {
    return pf;
  }

  @Override
  public String getType() {
    return "default";
  }
}
