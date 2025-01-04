package funcmath.game.defaultlevel;

import funcmath.exceptions.LevelException;
import funcmath.function.Function;
import funcmath.game.*;
import funcmath.gui.swing.GPanel;
import funcmath.object.MathObject;
import funcmath.utility.Helper;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serial;
import java.util.*;

public class DefaultLevel implements Level {
  @Serial private static final long serialVersionUID = -815013050840486071L;

  DLevelInfo levelInfo;
  DLevelState currentLevelState;
  Stack<DLevelState> history;
  ArrayList<String> fNames;

  boolean isCompleted = false;

  public DefaultLevel() {

  }

  public DefaultLevel(Integer level, LevelPlayFlag playFlag) {
    levelInfo = new DLevelInfo(level, playFlag);
    currentLevelState = levelInfo.getLevelState();
    fNames = levelInfo.getFunctionNames();
    history = new Stack<>();
    history.push(Helper.deepClone(currentLevelState));
  }

  public boolean numsCheck(ArrayList<MathObject> args) {
    ArrayList<MathObject> nums = Helper.deepClone(currentLevelState.getNumbers());
    for (MathObject arg : args) {
      if (nums.contains(arg)) nums.remove(arg);
      else return false;
    }
    return true;
  }

  public void restart() {
    currentLevelState = levelInfo.getLevelState();
    history.clear();
    history.push(Helper.deepClone(currentLevelState));
  }

  public ArrayList<MathObject> compute(ArrayList<String> expression) {
    Stack<Function> fStack = new Stack<>();
    Stack<ArrayList<MathObject>> numsStack = new Stack<>();
    numsStack.push(new ArrayList<>());

    // проверяем, что выражение содержит только математические объекты и функции
    ArrayList<MathObject> globalArgs = new ArrayList<>();
    for (String word : expression) {
      if (!fNames.contains(word)) {
        globalArgs.add(MathObject.parseMathObject(word, levelInfo.getResultClassName()));
      }
    }

    if (!numsCheck(globalArgs)) {
      throw new LevelException("Unknown number exists");
    }

    int i = 0;
    LinkedList<MathObject> temp = new LinkedList<>();
    for (String word : expression) {
      if (fNames.contains(word)) {
        // Функция
        fStack.push(currentLevelState.getFunctions().get(word));
        numsStack.push(new ArrayList<>());
      } else {
        // Число
        temp.add(globalArgs.get(i));
        i++;
        while (!temp.isEmpty()) {
          numsStack.peek().add(temp.pop());
          // Возможно ли вычисление?
          while (!fStack.isEmpty() && numsStack.peek().size() >= fStack.peek().getNumberOfArgs()) {
            MathObject[] args = new MathObject[numsStack.peek().size()];
            ArrayList<MathObject> ans;
            try {
              ans =
                  fStack
                      .peek()
                      .use(
                          levelInfo.getRules().isInfinityFunctions(),
                          numsStack.peek().toArray(args));
            } catch (RuntimeException e) {
              currentLevelState = Helper.deepClone(history.peek());
              throw new LevelException(e);
            }
            fStack.pop();
            numsStack.pop();
            temp.addAll(ans);
          }
        }
      }
    }

    if (!fStack.isEmpty()) {
      currentLevelState = Helper.deepClone(history.peek());
      throw new LevelException("Need more arguments");
    }

    if (!levelInfo.getRules().isInfinityNumbers()) {
      for (MathObject arg : globalArgs) {
        currentLevelState.getNumbers().remove(arg);
      }
    }

    currentLevelState.getNumbers().addAll(numsStack.peek());

    isCompleted = numsCheck(levelInfo.getAnswers());
    history.push(Helper.deepClone(currentLevelState));

    return numsStack.peek();
  }

  // добавить в кальк Simple Functions
  public ArrayList<MathObject> calc(ArrayList<String> expression) {
    if (!levelInfo.getRules().allowCalc) {
      throw new LevelException("Calc is turned off for this level.");
    }
    HashMap<String, Function> functions = currentLevelState.getFunctions();
    ArrayList<String> localFNames = new ArrayList<>(functions.keySet());

    Stack<Function> fStack = new Stack<>();
    Stack<ArrayList<MathObject>> numsStack = new Stack<>();
    numsStack.push(new ArrayList<>());

    // проверяем, что выражение содержит только математические объекты и функции
    ArrayList<MathObject> globalArgs = new ArrayList<>();
    for (String word : expression) {
      if (!localFNames.contains(word)) {
        globalArgs.add(MathObject.parseMathObject(word, levelInfo.getResultClassName()));
      }
    }

    int i = 0;
    LinkedList<MathObject> temp = new LinkedList<>();
    for (String word : expression) {
      if (localFNames.contains(word)) {
        // Функция
        fStack.push(functions.get(word));
        numsStack.push(new ArrayList<>());
      } else {
        // Число
        temp.add(globalArgs.get(i));
        i++;
        while (!temp.isEmpty()) {
          numsStack.peek().add(temp.pop());
          // Возможно ли вычисление?
          while (!fStack.isEmpty() && numsStack.peek().size() >= fStack.peek().getNumberOfArgs()) {
            MathObject[] args = new MathObject[numsStack.peek().size()];
            ArrayList<MathObject> ans;
            try {
              ans = fStack.peek().use(true, numsStack.peek().toArray(args));
            } catch (RuntimeException e) {
              throw new LevelException(e);
            }
            fStack.pop();
            numsStack.pop();
            temp.addAll(ans);
          }
        }
      }
    }

    if (!fStack.isEmpty()) {
      throw new LevelException("Need more arguments");
    }

    return numsStack.peek();
  }

  public void back() {
    if (!levelInfo.getRules().isAllowBack()) {
      throw new LevelException("Back is turned off for this level.");
    }
    history.pop();
    if (history.isEmpty()) {
      history.push(Helper.deepClone(currentLevelState));
      throw new LevelException("There is no back move");
    }
    currentLevelState = history.peek();
  }

  public DLevelInfo getLevelInfo() {
    return levelInfo;
  }

  public DLevelState getCurrentLevelState() {
    return currentLevelState;
  }

  public void setLevelState(DLevelState levelState) {
    this.currentLevelState = levelState;
  }

  public Stack<DLevelState> getHistory() {
    return history;
  }

  @Override
  public String getType() {
    return "default";
  }

  @Override
  public String getName() {
    return levelInfo.getName();
  }

  @Override
  public int getLevel() {
    return levelInfo.getLevel();
  }

  @Override
  public Cutscene getCutscene() {
    return levelInfo.getCutscene();
  }

  @Override
  public int[] consoleRun(InputStream in, PrintStream out) {
    DLevelConsole console = new DLevelConsole(in, out, this);
    return console.game();
  }

  @Override
  public int[] guiRun(GPanel panel) {
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DefaultLevel that = (DefaultLevel) o;
    return Objects.equals(getLevelInfo(), that.getLevelInfo())
        && Objects.equals(getCurrentLevelState(), that.getCurrentLevelState());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLevelInfo(), getCurrentLevelState());
  }

  @Override
  public String toString() {
    return "Level " + getLevel() + ": " + getName();
  }
}
