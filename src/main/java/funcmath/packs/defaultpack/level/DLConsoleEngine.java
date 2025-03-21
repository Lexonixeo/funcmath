package funcmath.packs.defaultpack.level;

import funcmath.functions.Function;
import funcmath.game.Logger;
import funcmath.gui.swing.GPanel;
import funcmath.level.GConsoleLevelPanel;
import funcmath.level.LevelEngine;
import funcmath.level.PlayFlag;
import funcmath.object.MathObject;
import funcmath.utility.FMInputStream;
import funcmath.utility.FMPrintStream;
import funcmath.utility.Helper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class DLConsoleEngine implements LevelEngine {
  private final DefaultLevel level;
  private final GConsoleLevelPanel panel;
  private final FMInputStream in;
  private final FMPrintStream out;

  boolean tutorial = true;
  int hint;

  protected DLConsoleEngine(DefaultLevel level) {
    this.level = level;
    this.panel =
        new GConsoleLevelPanel() {
          @Override
          protected void setNameLabelText() {
            nameLabel.setText("Уровень №" + level.getLevelInfo().getID() + ": " + level.getName());
            repaint();
            validate();
          }

          @Override
          protected void game() {
            DLConsoleEngine.this.startGame();
          }

          @Override
          protected void afterGame() {
            if (level.getPlayFlag() == PlayFlag.PRELEVELS) {
              level.getAct().afterGame();
            } else {
              super.afterGame();
            }
          }
        };
    this.in = this.panel.getIn();
    this.out = this.panel.getOut();
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
    out.println("Уровень №" + this.level.getLevelInfo().getID() + ": " + this.level.getName());

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
    String cmd;
    try {
      cmd = scanner.nextLine();
    } catch (RuntimeException e) {
      return -1;
    } catch (Exception e) {
      // Какая-то иная ошибка, возможно пустая строка и т.д.
      cmd = "";
    }
    synchronized (out) {
      ArrayList<String> command = Helper.wordsFromString(cmd);
      Logger.write("Ввели выражение: " + cmd);
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

  public void startGame() {
    DLStats stats = new DLStats(level);
    stats.startTime();
    start();
    while (!level.isCompleted() && !Thread.currentThread().isInterrupted()) {
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

  @Override
  public GPanel getLevelPanel() {
    return panel;
  }
}
