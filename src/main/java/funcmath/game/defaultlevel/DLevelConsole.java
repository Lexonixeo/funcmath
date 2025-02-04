package funcmath.game.defaultlevel;

import funcmath.exceptions.JavaException;
import funcmath.function.Function;
import funcmath.game.FMPrintStream;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import funcmath.utility.Log;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class DLevelConsole {
  DefaultLevel level;
  InputStream in;
  final FMPrintStream out;

  boolean tutorial = true;
  int hint;

  public DLevelConsole(InputStream in, FMPrintStream out, DefaultLevel level) {
    this.level = level;
    this.out = out;
    this.in = in;
  }

  private void clear() {
    Log.getInstance().write("Clearing console!");
    out.clear();
  }

  private void playCutscene() {
    Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
    clear();
    Log.getInstance().write("Player is reading cutscene...");
    for (String phrase : level.getCutscene().getText()) {
      out.print(phrase + " ");
      scanner.nextLine();
    }
    out.println("\n\nНажмите Enter, чтобы продолжить...");
    scanner.nextLine();
    Log.getInstance().write("Cutscene was completed!");
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

    out.println(
        "В данном уровне вычисления происходят на уровне объектов вида \""
            + MathObject.getMathObject(this.level.getLevelInfo().getResultClassName())
                .getTypeForLevel()
            + "\".\n");

    out.println(
        "У вас есть "
            + (this.level.getLevelInfo().getRules().isInfinityNumbers() ? "не" : "")
            + "ограниченный набор чисел:");
    for (MathObject number : this.level.getCurrentLevelState().getNumbers()) {
      out.print(number + " ");
    }

    out.println("\n\nУ вас есть функции:");
    for (Function function : this.level.getCurrentLevelState().getFunctions().values()) {
      out.println(function);
    }

    out.println(
        "\nВам нужно из данных чисел, используя данные функции, получить число(а) "
            + Helper.collectionToString(this.level.getLevelInfo().getAnswers())
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
    for (MathObject number : this.level.getCurrentLevelState().getNumbers()) {
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
    ArrayList<String> hints = this.level.getLevelInfo().getHints();
    Log.getInstance().write("A player asked for a hint!");
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
      this.level.getCurrentLevelState().save();
      out.print("[Level] Введите выражение: ");
    }
    Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
    String cmd = "";
    try {
      cmd = scanner.nextLine();
    } catch (JavaException e) {
      return -1;
    } catch (Exception ignored) {
    }
    synchronized (out) {
      ArrayList<String> command = Helper.wordsFromString(cmd);
      Log.getInstance().write("An expression is introduced: " + cmd);
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

  public int[] game() {
    if (!this.level.getCutscene().getText().isEmpty()) {
      playCutscene();
    }
    this.level.startTimer();
    start();
    while (!level.isCompleted) {
      int check = turn();
      if (check == -1) break;
    }
    int time = Math.toIntExact(System.currentTimeMillis() / 1000 - this.level.getTimer());

    int fuses = 0;
    for (Function f : this.level.getLevelInfo().getOriginalFunctions().values()) {
      fuses += f.getUses();
    }

    int newFuses = 0;
    for (Function f : this.level.getCurrentLevelState().getFunctions().values()) {
      newFuses += f.getUses();
    }
    int dFuses = fuses - newFuses;

    if (level.isCompleted) {
      out.println(
          "Поздравляем! Вы получили число(а) "
              + Helper.collectionToString(this.level.getLevelInfo().getAnswers())
              + " и прошли уровень!");
      Log.getInstance().write("Level №" + level.getLevel() + " was completed!");
    }
    return new int[] {(tutorial ? 1 : 0), (this.level.isCompleted ? 1 : 0), dFuses, time};
  }
}
