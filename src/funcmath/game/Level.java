package funcmath.game;

import funcmath.Helper;
import funcmath.exceptions.FunctionException;
import funcmath.exceptions.LevelException;
import funcmath.exceptions.MathObjectException;
import funcmath.function.Function;
import funcmath.object.*;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Level implements Serializable {
  @Serial private static final long serialVersionUID = 7967415798449468220L;

  LevelInfo levelInfo;
  ArrayList<MathObject> numbers;
  ArrayList<MathObject> originalNumbers;
  HashMap<String, Function> originalFunctions;
  HashMap<String, Function> functions;
  Stack<ArrayList<MathObject>> numbersStack = new Stack<>();
  Stack<HashMap<String, Function>> functionsStack = new Stack<>();
  ArrayList<String> hints;
  ArrayList<String> cutscene;
  ArrayList<MathObject> answers;
  int level;
  int hint = 0;
  long startTime;
  int fuses;
  // int difficulty;
  boolean tutorial;
  String resultClassName;
  String name;
  boolean completed = false;
  HashSet<String> usingNames = new HashSet<>();

  public Level(int level, boolean tutorial, int customFlag) {
    this.tutorial = tutorial;
    this.level = level;

    levelInfo = new LevelInfo(level, customFlag);

    if (!levelInfo.getCompleted() && customFlag != 2) {
      throw new LevelException("Уровень " + level + " ещё не пройден создателем!");
    }

    originalNumbers = levelInfo.getOriginalNumbers();
    numbers = Helper.deepClone(originalNumbers);
    numbersStack.push(Helper.deepClone(numbers));

    originalFunctions = levelInfo.getOriginalFunctions();
    functions = Helper.deepClone(originalFunctions);
    functionsStack.push(Helper.deepClone(functions));

    answers = levelInfo.getAnswers();
    hints = levelInfo.getHints();
    resultClassName = levelInfo.getResultClassName();
    cutscene = levelInfo.getCutscene();
    name = levelInfo.getName();

    for (Function f : functions.values()) {
      fuses += f.getUses();
    }
  }

  private void playCutscene() {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    Helper.clear();
    for (String phrase : cutscene) {
      System.out.print(phrase + " ");
      scanner.nextLine();
    }
    System.out.println("\n\nНажмите Enter, чтобы продолжить...");
    scanner.nextLine();
  }

  private boolean numsCheck(ArrayList<MathObject> args) {
    ArrayList<MathObject> nums = Helper.deepClone(numbers);
    for (MathObject arg : args) {
      if (nums.contains(arg)) nums.remove(arg);
      else return false;
    }
    return true;
  }

  private void tutorial() {
    System.out.println(
        "Используя функцию над двумя числами, вы забираете эти два числа из набора и в набор добавляется результат функции.");
    System.out.println(
        "Запись выражения происходит так:   func a b    / это выполнение функции func над параметрами a и b.");
    System.out.println(
        "Пример одного из выражений:        sum 1 3     / удалит из набора числа 1 и 3 и добавит в него 4.");
    System.out.println("Обратите внимание: скобки и запятые в выражении должны отсутствовать.\n");

    System.out.println("Введите help, чтобы узнать полный список команд.");
    System.out.println("Введите clear, чтобы очистить экран от лишних выражений.");
    System.out.println("Введите nums, чтобы узнать текущий набор чисел.");
    System.out.println("Введите reset, чтобы перезагрузить уровень.");
    System.out.println("Введите exit, чтобы выйти из уровня.\n");

    tutorial = false;
  }

  private void help() {
    System.out.println(
        "Используя функцию над двумя числами, вы забираете эти два числа из набора и в набор добавляется результат функции.");
    System.out.println(
        "Запись выражения происходит так:   func a b    / это выполнение функции func над параметрами a и b.");
    System.out.println(
        "Пример одного из выражений:        sum 1 3     / удалит из набора числа 1 и 3 и добавит в него 4.");
    System.out.println("Обратите внимание: скобки и запятые в выражении должны отсутствовать.\n");

    System.out.println("Введите help, чтобы узнать полный список команд.");
    System.out.println("Введите clear, чтобы очистить экран от лишних выражений.");
    System.out.println("Введите nums, чтобы узнать текущий набор чисел.");
    System.out.println("Введите hint, чтобы получить подсказку.");
    System.out.println(
        "Введите calc {выражение}, чтобы посчитать независимое выражение. (не забирает и не добавляет числа в набор)");
    System.out.println("Введите back, чтобы откатить предыдущее выражение.");
    System.out.println("Введите reset, чтобы перезагрузить уровень.");
    System.out.println("Введите exit, чтобы выйти из уровня.\n");

    tutorial = true;
  }

  private void start() {
    Helper.clear();
    System.out.println(this);

    System.out.println(
        "В данном уровне вычисления происходят на уровне объектов вида \""
            + MathObject.getMathObject(resultClassName).getTypeForLevel()
            + "\".\n");

    System.out.println("У вас есть ограниченный набор чисел:");
    for (MathObject number : numbers) {
      System.out.print(number + " ");
    }

    System.out.println("\n\nУ вас есть функции:");
    for (Function function : functions.values()) {
      System.out.println(function);
    }

    System.out.println(
        "\nВам нужно из данных чисел, используя данные функции, получить число(а) "
            + Helper.arrayListToString(answers)
            + ".\n");
    if (tutorial) this.tutorial();
  }

  private void restart() {
    numbers = Helper.deepClone(originalNumbers);
    numbersStack.clear();
    numbersStack.push(Helper.deepClone(numbers));

    functions = Helper.deepClone(originalFunctions);
    functionsStack.clear();
    functionsStack.push(Helper.deepClone(functions));

    Helper.clear();
    this.start();
  }

  private void nums() {
    System.out.print("=");
    for (MathObject number : numbers) {
      System.out.print(" " + number);
    }
    System.out.println();
  }

  private void computation(int mode, ArrayList<String> expression) {
    ArrayList<String> fNames = new ArrayList<>(functions.keySet());
    Stack<Function> fStack = new Stack<>();
    Stack<ArrayList<MathObject>> nums = new Stack<>();
    nums.push(new ArrayList<>()); // конечный

    // в будущем: при mode = 1 добавить simple functions

    ArrayList<MathObject> globalArgs = new ArrayList<>();
    for (String word : expression) {
      if (!fNames.contains(word)) {
        try {
          globalArgs.add(MathObject.parseMathObject(word, resultClassName));
        } catch (RuntimeException e) {
          System.out.println(
              "= Неверные аргументы: не существует какой-то функции или числа! Введите выражение заново.");
          return;
        }
      }
    }
    if (!numsCheck(globalArgs) && (mode == 0 || mode == 2)) {
      System.out.println(
          "= Неверные аргументы: какие-то числа не существуют в наборе! Введите выражение заново.");
      return;
    }
    // избыток аргументов - ну и ладно, они всё равно вернутся

    int i = 0;
    LinkedList<MathObject> timed = new LinkedList<>();
    for (String word : expression) {
      if (fNames.contains(word)) {
        fStack.push(functions.get(word));
        nums.push(new ArrayList<>());
      } else {
        timed.add(globalArgs.get(i));
        i++;
        while (!timed.isEmpty()) {
          nums.peek().add(timed.pop());
          while (!fStack.isEmpty() && nums.peek().size() >= fStack.peek().getNumberOfArgs()) {
            MathObject[] args = new MathObject[nums.peek().size()];
            ArrayList<MathObject> ans;
            try {
              ans = fStack.peek().use(mode, nums.peek().toArray(args));
            } catch (RuntimeException e) {
              try {
                System.out.println("= " + e.getCause().getCause().getMessage());
              } catch (RuntimeException ex) {
                try {
                  System.out.println("= " + e.getCause().getMessage());
                } catch (RuntimeException exc) {
                  System.out.println("= " + e.getMessage());
                }
              }
              functions = Helper.deepClone(functionsStack.peek());
              return;
            }
            fStack.pop();
            nums.pop();

            timed.addAll(ans);
          }
        }
      }
    }
    if (!fStack.isEmpty()) {
      System.out.println("= Недостаточно аргументов! Введите выражение заново.");
      return;
    }

    if (mode == 0) {
      for (MathObject arg : globalArgs) {
        numbers.remove(arg);
      }
    }
    System.out.print("=");
    for (MathObject answerNum : nums.peek()) {
      if (mode == 0 || mode == 2) {
        numbers.add(answerNum);
      }
      System.out.print(" " + answerNum);
    }
    System.out.println();

    if (mode == 0 || mode == 2) {
      completed = numsCheck(answers);
      numbersStack.push(Helper.deepClone(numbers));
      functionsStack.push(Helper.deepClone(functions));
    }
  }

  private void hint() {
    if (hints.isEmpty()) {
      System.out.println("В этом уровне нет подсказок :(");
    }
    hint++;
    if (hint >= hints.size()) {
      hint = hints.size();
      for (int i = 0; i < hint; i++) {
        System.out.println("Подсказка №" + (i + 1) + ": " + hints.get(i));
      }
    } else {
      System.out.println("Подсказка №" + hint + ": " + hints.get(hint - 1));
    }
  }

  private void back() {
    numbersStack.pop();
    functionsStack.pop();
    if (numbersStack.empty()) {
      System.out.println("= Нет хода назад!");
      numbersStack.push(Helper.deepClone(numbers));
      functionsStack.push(Helper.deepClone(functions));
      return;
    }
    numbers = Helper.deepClone(numbersStack.peek());
    functions = Helper.deepClone(functionsStack.peek());
    nums();
  }

  private int turn() {
    turnSave();
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    System.out.print("[Level] Введите выражение: ");
    ArrayList<String> command = Helper.wordsFromString(scanner.nextLine());
    String first_command = command.get(0);
    switch (first_command) {
      case "exit", "menu", "stop" -> {
        return -1;
      }
      case "nums" -> nums();
      case "reset" -> restart();
      case "help" -> {
        start();
        help();
      }
      case "clear" -> start();
      case "hint" -> hint();
      case "calc" -> {
        if (resultClassName.equals("unknown")) {
          System.out.println("Пупупу...");
        } else {
          computation(1, new ArrayList<>(command.subList(1, command.size())));
        }
      }
      case "back" -> {
        if (resultClassName.equals("unknown")) {
          System.out.println("Пупупу...");
        } else {
          back();
        }
      }
      default -> {
        if (resultClassName.equals("unknown")) {
          computation(2, command);
        } else {
          computation(0, command);
        }
      }
    }
    return 0;
  }

  private void updateUsingNames() {
    usingNames.clear();
    for (Function f : functions.values()) {
      if (usingNames.contains(f.getName())) {
        throw new FunctionException("Названия функций не должны повторяться: " + f.getName());
      }
      usingNames.add(f.getName());
    }
    for (MathObject n : numbers) {
      if (n.getName() != null && usingNames.contains(n.getName())) {
        throw new MathObjectException(
            "Названия объектов не должны повторяться с функциями или другими объектами: "
                + n.getName());
      }
      if (n.getName() != null) {
        usingNames.add(n.getName());
      }
    }
  }

  private void turnSave() {
    updateUsingNames();
    ArrayList<Object> generated = new ArrayList<>();
    generated.add(Helper.deepClone(numbers));
    generated.add(Helper.deepClone(usingNames));
    Helper.write(generated, "data/currentLevel.dat");
  }

  public int[] game() {
    if (!cutscene.isEmpty()) playCutscene();
    startTime = System.currentTimeMillis() / 1000;
    start();
    while (!completed) {
      int check = turn();
      if (check == -1) break;
    }
    int time = Math.toIntExact(System.currentTimeMillis() / 1000 - startTime);
    int newFuses = 0;
    for (Function f : functions.values()) {
      newFuses += f.getUses();
    }
    int dFuses = fuses - newFuses;
    if (completed) {
      System.out.println(
          "Поздравляем! Вы получили число(а) "
              + Helper.arrayListToString(answers)
              + " и прошли уровень!");
    }
    return new int[] {(tutorial ? 1 : 0), (completed ? 1 : 0), dFuses, time};
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Level level = (Level) o;
    return Objects.equals(originalNumbers, level.originalNumbers)
        && Objects.equals(originalFunctions, level.originalFunctions)
        && Objects.equals(hints, level.hints)
        && Objects.equals(answers, level.answers)
        && Objects.equals(resultClassName, level.resultClassName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originalNumbers, originalFunctions, hints, answers, resultClassName);
  }

  @Override
  public String toString() {
    return "Уровень " + this.level + ": " + this.name;
  }
}
