package funcmath.defaultpack.level;

import funcmath.exceptions.LevelException;
import funcmath.functions.Function;
import funcmath.gui.swing.GPanel;
import funcmath.level.*;
import funcmath.object.MathObject;
import funcmath.object.TypeRegister;
import funcmath.utility.Hash;
import funcmath.utility.Helper;
import java.util.*;

public class DefaultLevel implements Level {
  private final DLInfo levelInfo;
  private DLState currentLevelState;
  // Stack<DLState> history;
  private boolean isCompleted;

  private DLStats levelStats;

  private final GConsoleLevelPanel panel;

  private final Hash hash;

  public DefaultLevel() {
    this(new DLInfo());
  }

  public DefaultLevel(LevelInfo li) {
    this((DLInfo) li);
  }

  private DefaultLevel(DLInfo li) {
    this.levelInfo = li;
    setLevelState(levelInfo.getDefaultLevelState());
    // history +
    this.hash = Hash.encode(levelInfo);
    final DLConsoleEngine[] engine = new DLConsoleEngine[1];
    this.panel =
        new GConsoleLevelPanel() {
          @Override
          protected void runInterrupt() {
            engine[0].interrupt();
          }

          @Override
          protected void game() {
            engine[0] = new DLConsoleEngine(DefaultLevel.this, panel);
            engine[0].game();
          }
        };
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
    setLevelState(levelInfo.getDefaultLevelState());
  }

  public ArrayList<MathObject> compute(ArrayList<String> expression) {
    HashMap<String, Function> newFunctions = Helper.deepClone(currentLevelState.getFunctions());
    HashSet<String> fNames = new HashSet<>(newFunctions.keySet());
    Stack<Function> fStack = new Stack<>();
    Stack<ArrayList<MathObject>> numsStack = new Stack<>();
    numsStack.push(new ArrayList<>());

    // проверяем, что выражение содержит только математические объекты и функции
    ArrayList<MathObject> globalArgs = new ArrayList<>();
    for (String word : expression) {
      if (!currentLevelState.getUsingNames().contains(word)) {
        throw new LevelException("Выражение содержит что-то несуществующее");
      }
      MathObject n = currentLevelState.getNumber(word);
      if (n != null) {
        globalArgs.add(n);
      }
    }

    if (!numsCheck(globalArgs)) {
      throw new LevelException("В выражении есть несуществующее число");
    }

    int i = 0;
    LinkedList<MathObject> temp = new LinkedList<>();
    for (String word : expression) {
      if (fNames.contains(word)) {
        // Функция
        fStack.push(newFunctions.get(word));
        numsStack.push(new ArrayList<>());
      } else {
        // Число
        temp.add(globalArgs.get(i));
        i++;
        while (!temp.isEmpty()) {
          numsStack.peek().add(temp.pop());
          // Возможно ли вычисление?
          while (!fStack.isEmpty() && numsStack.peek().size() >= fStack.peek().getArgsNumber()) {
            MathObject[] args = new MathObject[numsStack.peek().size()];
            ArrayList<MathObject> ans;
            try {
              ans =
                  fStack
                      .peek()
                      .use(
                          levelInfo.getRules().infinityFunctions(), numsStack.peek().toArray(args));
            } catch (RuntimeException e) {
              currentLevelState = Helper.deepClone(currentLevelState.getHistory().peek());
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
      currentLevelState = Helper.deepClone(currentLevelState.getHistory().peek());
      throw new LevelException("Недостаточно аргументов!");
    }

    ArrayList<MathObject> newNumbers = Helper.deepClone(currentLevelState.getNumbers());

    if (!levelInfo.getRules().infinityNumbers()) {
      for (MathObject arg : globalArgs) {
        newNumbers.remove(arg);
      }
    }

    ArrayList<MathObject> generatedNumbers = numsStack.peek();

    currentLevelState =
        new DLState(
            currentLevelState.getHistory(),
            add(newNumbers, generatedNumbers),
            newFunctions, levelInfo.getPlayFlag(), levelInfo.getID());

    isCompleted = numsCheck(levelInfo.getAns());

    return numsStack.peek();
  }

  private ArrayList<MathObject> add(ArrayList<MathObject> first, ArrayList<MathObject> second) {
    HashSet<String> usingNames = new HashSet<>(currentLevelState.getFunctions().keySet());
    for (MathObject n : first) {
      usingNames.add(n.getName());
    }

    ArrayList<MathObject> ans = Helper.deepClone(first);
    for (MathObject n : second) {
      for (MathObject m : ans) {
        if (n.getName().equals(m.getName()) && !n.equals(m)) {
          n.generateName(usingNames);
          break;
        }
      }
      usingNames.add(n.getName());
      ans.add(n);
    }
    return ans;
  }

  // добавить в кальк Simple Functions
  public ArrayList<MathObject> calc(ArrayList<String> expression) {
    if (!levelInfo.getRules().allowCalc()) {
      throw new LevelException("Калькулятор выключен для этого уровня.");
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
        globalArgs.add(TypeRegister.parseMathObject(word));
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
          while (!fStack.isEmpty() && numsStack.peek().size() >= fStack.peek().getArgsNumber()) {
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
      throw new LevelException("Недостаточно аргументов!");
    }

    return numsStack.peek();
  }

  public void back() {
    if (!levelInfo.getRules().allowBack()) {
      throw new LevelException("Откат отключен для этого уровня");
    }
    Stack<DLState> history = currentLevelState.getHistory();
    history.pop();
    if (history.isEmpty()) {
      history.push(Helper.deepClone(currentLevelState));
      throw new LevelException("Нет хода назад!");
    }
    currentLevelState = history.peek();
  }

  @Override
  public String getType() {
    return "default";
  }

  @Override
  public LevelInfo getLevelInfo() {
    return this.levelInfo;
  }

  @Override
  public LevelState getCurrentLevelState() {
    return currentLevelState;
  }

  @Override
  public LevelStatistics getStatistics() {
    return levelStats;
  }

  @Override
  public PlayFlag getPlayFlag() {
    return this.levelInfo.getPlayFlag();
  }

  @Override
  public String getName() {
    return this.levelInfo.getName();
  }

  @Override
  public GPanel getLevelPanel() {
    return panel;
  }

  @Override
  public boolean isCompleted() {
    return isCompleted;
  }

  @Override
  public Hash getHash() {
    return hash;
  }

  @Override
  public void setLevelState(LevelState state) {
    currentLevelState = (DLState) state;
  }

  void setLevelStats(DLStats stats) {
    this.levelStats = stats;
  }
}
