package funcmath.defaultpack.level;

import funcmath.functions.Function;
import funcmath.game.Logger;
import funcmath.gui.swing.GInputStream;
import funcmath.level.FMPrintStream;
import funcmath.level.GConsoleLevelPanel;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class DLConsoleEngine {
  private final DefaultLevel level;
  private final GInputStream in;
  private final FMPrintStream out;

  boolean tutorial = true;
  int hint;

  protected DLConsoleEngine(DefaultLevel level, GConsoleLevelPanel panel) {
    this.in = panel.getIn();
    this.out = panel.getOut();
    this.level = level;
  }

  private void tutorial() {
    out.println(
        "Используя функцию над двумя числами, вы забираете эти два числа из набора и в набор добавляется результат функции.");
    out.println(
        "Запись выражения происходит так:   func a b    / это выполнение функции func над параметрами a и b.");
    out.println(
        "Пример одного из выражений:        sum 1 3     / удалит из набора числа 1 и 3 и добавит в него 4.");
    out.println("Обратите внимание: скобки и запятые в выражении должны отсутствовать.\n");

    out.println("Введите help, чтобы узнать полный список команд.");
    out.println("Введите clear, чтобы очистить экран от лишних выражений.");
    out.println("Введите nums, чтобы узнать текущий набор чисел.");
    out.println("Введите reset, чтобы перезагрузить уровень.");
    out.println("Введите exit, чтобы выйти из уровня.\n");

    tutorial = false;
  }

  private void help() {
    out.println(
        "Используя функцию над двумя числами, вы забираете эти два числа из набора и в набор добавляется результат функции.");
    out.println(
        "Запись выражения происходит так:   func a b    / это выполнение функции func над параметрами a и b.");
    out.println(
        "Пример одного из выражений:        sum 1 3     / удалит из набора числа 1 и 3 и добавит в него 4.");
    out.println("Обратите внимание: скобки и запятые в выражении должны отсутствовать.\n");

    out.println("Введите help, чтобы узнать полный список команд.");
    out.println("Введите clear, чтобы очистить экран от лишних выражений.");
    out.println("Введите nums, чтобы узнать текущий набор чисел.");
    out.println("Введите hint, чтобы получить подсказку.");
    out.println(
        "Введите calc {выражение}, чтобы посчитать независимое выражение. (не забирает и не добавляет числа в набор)");
    out.println("Введите back, чтобы откатить предыдущее выражение.");
    out.println("Введите reset, чтобы перезагрузить уровень.");
    out.println("Введите exit, чтобы выйти из уровня.\n");

    tutorial = true;
  }

  private void start() {
    clear();
    out.println(this.level);

    /*
    out.println(
            "В данном уровне вычисления происходят на уровне объектов вида \""
                    + MathObject.getMathObject(this.level.getLevelInfo().getResultClassName())
                    .getTypeForLevel()
                    + "\".\n");
     */

    out.println(
        "У вас есть "
            + (((DLInfo) this.level.getLevelInfo()).getRules().infinityNumbers() ? "не" : "")
            + "ограниченный набор чисел:");
    for (MathObject number : ((DLState) this.level.getCurrentLevelState()).getNumbers()) {
      out.print(number + " ");
    }

    out.println("\n\nУ вас есть функции:");
    for (Function function :
        ((DLState) this.level.getCurrentLevelState()).getFunctions().values()) {
      out.println(function);
    }

    out.println(
        "\nВам нужно из данных чисел, используя данные функции, получить число(а) "
            + Helper.collectionToString(((DLInfo) this.level.getLevelInfo()).getAns())
            + ".\n");
    if (tutorial) this.tutorial();
  }

  private void restart() {
    this.level.restart();
    clear();
    start();
  }

  private void nums() {
    out.print("=");
    for (MathObject number : ((DLState) this.level.getCurrentLevelState()).getNumbers()) {
      out.print(" " + number);
    }
    out.println();
  }

  private void compute(ArrayList<String> expression) {
    try {
      ArrayList<MathObject> result = this.level.compute(expression);
      out.print("=");
      for (MathObject number : result) {
        out.print(" " + number);
      }
      out.println();
    } catch (RuntimeException e) {
      out.println("= " + Helper.getLastMessage(e));
    }
  }

  private void calc(ArrayList<String> expression) {
    try {
      ArrayList<MathObject> result = this.level.calc(expression);
      out.print("=");
      for (MathObject number : result) {
        out.print(" " + number);
      }
      out.println();
    } catch (RuntimeException e) {
      out.println("= " + Helper.getLastMessage(e));
    }
  }

  private void hint() {
    ArrayList<String> hints = ((DLInfo) this.level.getLevelInfo()).getHints();
    Logger.write("Игрок попросил подсказку!");
    if (hints.isEmpty()) {
      out.println("В этом уровне нет подсказок :(");
    }
    hint++;
    if (hint >= hints.size()) {
      hint = hints.size();
      for (int i = 0; i < hint; i++) {
        out.println("Подсказка №" + (i + 1) + ": " + hints.get(i));
      }
    } else {
      out.println("Подсказка №" + hint + ": " + hints.get(hint - 1));
    }
  }

  private void back() {
    try {
      this.level.back();
      nums();
    } catch (RuntimeException e) {
      out.println("= " + e.getMessage());
    }
  }

  private int turn() {
    synchronized (out) {
      // this.level.getCurrentLevelState().save();
      out.print("[Level] Введите выражение: ");
    }
    Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
    String cmd = "";
    try {
      cmd = scanner.nextLine();
    } catch (RuntimeException e) {
      return -1;
    } catch (Exception ignored) {
    }
    synchronized (out) {
      ArrayList<String> command = wordsFromString(cmd);
      Logger.write("An expression is introduced: " + cmd);
      String first_command = command.getFirst();
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
        case "calc" -> calc(new ArrayList<>(command.subList(1, command.size())));
        case "back" -> back();
        default -> compute(command);
      }
      return 0;
    }
  }

  private void clear() {
    Logger.write("Очистка консоли!");
    out.clear();
  }

  private static ArrayList<Character> stringProcessing(String str) {
    char[] first_str = str.toCharArray();
    ArrayList<Character> second_str = new ArrayList<>();
    for (char i : first_str) {
      if (i == ',' || i == '(' || i == ')') {
        second_str.add(' ');
      } else {
        second_str.add(i);
      }
    }
    ArrayList<Character> third_str = new ArrayList<>();
    char last_char = ' ';
    for (char i : second_str) {
      if (!(i == ' ' && last_char == ' ')) {
        third_str.add(i);
        last_char = i;
      }
    }
    if (!third_str.isEmpty() && third_str.getLast() == ' ') {
      third_str.removeLast();
    }
    return third_str;
  }

  private static ArrayList<String> wordsFromString(String str) {
    ArrayList<Character> our_str = stringProcessing(str);
    StringBuilder builder = new StringBuilder();
    ArrayList<String> words = new ArrayList<>();
    for (char i : our_str) {
      if (i != ' ') builder.append(i);
      else {
        words.add(builder.toString());
        builder.delete(0, builder.length());
      }
    }
    words.add(builder.toString());
    return words;
  }

  public void game() {
    DLStats stats = new DLStats(level);
    stats.startTime();
    start();
    while (!level.isCompleted()) {
      int check = turn();
      if (check == -1) break;
    }
    stats.endTime();

    int fuses = 0;
    for (Function f :
        ((DLState) this.level.getLevelInfo().getDefaultLevelState()).getFunctions().values()) {
      fuses += f.getRemainingUses();
    }

    int newFuses = 0;
    for (Function f : ((DLState) this.level.getCurrentLevelState()).getFunctions().values()) {
      newFuses += f.getRemainingUses();
    }
    int dFuses = fuses - newFuses;

    stats.setFunctionUses(dFuses);

    stats.setCompleted(level.isCompleted());

    if (level.isCompleted()) {
      out.println(
          "Поздравляем! Вы получили число(а) "
              + Helper.collectionToString(((DLInfo) this.level.getLevelInfo()).getAns())
              + " и прошли уровень!");
      Logger.write("Уровень №" + level.getLevelInfo().getID() + " пройден!");
    }
    this.level.setLevelStats(stats);
  }
}
