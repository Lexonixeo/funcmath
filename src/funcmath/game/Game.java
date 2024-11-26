package funcmath.game;

import funcmath.Helper;
import funcmath.function.FunctionMaker;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
  Player player;
  boolean tutorial = true;
  boolean levelMakerTutorial = true;
  boolean functionMakerTutorial = true;

  Game(Player player) {
    this.player = player;
  }

  private void level(int level) {
    int levels = Helper.filesCount("data\\levels\\");
    if (level > levels || level < 1) {
      System.out.println("Такого уровня не существует!");
      return;
    }
    Level l = new Level(level, tutorial, 0);
    int[] result = l.game();
    tutorial = (result[0] == 1);
    player.addLevel((result[1] == 1), level, result[2], result[3]);
  }

  private void customLevel(int level) {
    if (Helper.isNotFileExists("data\\customLevels\\level" + level + ".dat")) {
      System.out.println("Такого уровня не существует!");
      return;
    }
    Level l = new Level(level, tutorial, 1);
    int[] result = l.game();
    tutorial = (result[0] == 1);
    player.addLevel((result[1] == 1), level, result[2], result[3]);
  }

  private void play() {
    int levels = Helper.filesCount("data\\levels");
    boolean isAnyNotCompletedLevel = false;
    for (int level = 1; level <= levels; level++) {
      if (!player.getCompletedLevels().contains(level)) {
        this.level(level);
        isAnyNotCompletedLevel = true;
        break;
      }
    }
    if (!isAnyNotCompletedLevel) {
      System.out.println("Вы прошли все уровни!");
    }
  }

  private void last() {
    int lastLevel = player.getLastLevel();
    if (lastLevel == 0) {
      System.out.println("Вы ещё не заходили в какой-либо уровень!");
      return;
    }
    if (lastLevel < 0) {
      customLevel(-lastLevel);
    } else {
      level(lastLevel);
    }
  }

  private void next() {
    int lastLevel = player.getLastLevel();
    if (lastLevel == 0) {
      System.out.println(
          "Вы ещё не заходили в какой-либо уровень!"); // переформулировать когда добавлю случайные
      // уровни
      return;
    }
    if (lastLevel < 0) {
      System.out.println("Не существует следующего уровня!");
      return;
    }
    level(lastLevel + 1);
  }

  private void prev() {
    int lastLevel = player.getLastLevel();
    if (lastLevel == 0) {
      System.out.println(
          "Вы ещё не заходили в какой-либо уровень!"); // переформулировать когда добавлю случайные
      // уровни
      return;
    }
    if (lastLevel < 0) {
      System.out.println("Не существует предыдущего уровня!");
      return;
    }
    level(lastLevel - 1);
  }

  private void help() {
    Helper.clear();
    System.out.println("Список команд:");
    System.out.println("menu          - Перейти в главное меню");
    System.out.println("help          - Узнать список команд");
    System.out.println("play          - Начать/продолжить игру");
    System.out.println("level {lvl}   - Перейти к уровню {lvl}");
    System.out.println("custom {lvl}  - Играть в пользовательский уровень {lvl}");
    System.out.println("random {dfc}  - Играть в случайный уровень сложности {dfc} (НЕ РАБОТАЕТ)");
    System.out.println("list {n}      - Открыть список обычных уровней на странице {n}");
    System.out.println("clist {n}     - Открыть список пользовательских уровней на странице {n}");
    System.out.println("last          - Перейти к последнему уровню");
    System.out.println("prev          - Перейти к предыдущему уровню");
    System.out.println("next          - Перейти к следующему уровню");
    System.out.println("stats         - Узнать свою статистику");
    System.out.println("make level    - Создать свой уровень");
    System.out.println("make function - Создать свою функцию");
    System.out.println("exit          - Выйти из игры");
  }

  private void menu() {
    Helper.clear();
    int levels = Helper.filesCount("data\\levels");
    System.out.println("Добро пожаловать, " + player.getName() + "!");
    System.out.println("Вы сейчас играете в III прототип игры func(math).");
    System.out.println("Он написан для реализации новых фич в игре и вашего оценивания.");
    System.out.println();
    System.out.println("Вы находитесь в главном меню.");
    System.out.println("Игра начинается с 1-ого уровня. Всего в игре " + levels + " уровней.");
    System.out.println(
        "Чтобы "
            + (player.getCompletedLevels().isEmpty()
                ? "начать игру"
                : "продолжить игру с первого непройденного уровня")
            + ", введите команду play.");
    System.out.println("Чтобы играть в конкретный уровень, введите команду level {номер уровня}.");
    System.out.println("Чтобы узнать список команд, введите команду help.");
    System.out.println("Чтобы выйти из игры, введите команду exit.");
  }

  private void list(int page) {
    ArrayList<Integer> levels = Level.getLevelList(Helper.getFileNames("data\\levels\\"));
    if (page <= 0 || levels.size() < 20 * page - 20 + 1) {
      System.out.println("Не бывает такой страницы!");
      return;
    }
    Helper.clear();
    System.out.println("Страница " + page + " из " + ((levels.size() - 1) / 20 + 1));
    for (int i = 20 * (page - 1); i < Math.min(20 * page, levels.size()); i++) {
      System.out.println(new Level(levels.get(i), false, 0));
    }
  }

  private void clist(int page) {
    ArrayList<Integer> levels = Level.getLevelList(Helper.getFileNames("data\\customLevels\\"));
    if (page <= 0 || levels.size() < 20 * page - 20 + 1) {
      System.out.println("Не бывает такой страницы!");
      return;
    }
    Helper.clear();
    System.out.println("Страница " + page + " из " + ((levels.size() - 1) / 20 + 1));
    for (int i = 20 * (page - 1); i < Math.min(20 * page, levels.size()); i++) {
      System.out.println(new Level(levels.get(i), false, 1));
    }
  }

  private void stats() {
    Helper.clear();
    System.out.println("Статистика игрока " + player.getName() + ":");
    System.out.println("Пройденные уровни:");
    int i = 0;
    for (int level : player.getCompletedLevels()) {
      i++;
      int fuses =
          (player.getMinFunctionUsesInLevel().get(level) != null
              ? player.getMinFunctionUsesInLevel().get(level)
              : 10000);
      int time =
          (player.getMinTimeInLevel().get(level) != null
              ? player.getMinTimeInLevel().get(level)
              : 10000);
      System.out.print(level + "/" + fuses + "/" + time + "\t\t");
      if (i % 5 == 0) System.out.println();
    }
    System.out.println("\n");
    System.out.println("(уровень/минимальное число использованных функций/минимальное время (с))");
    System.out.println("(10000 - значит то, что эти данные не сохранились)");
  }

  private void random() {
    // to do...
  }

  public void game() {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    Helper.clear();
    menu();
    boolean running = true;
    while (running) {
      player.writePlayerInfo();
      System.out.println();
      System.out.print("[Commander] Введите команду: ");
      String cmd = scanner.nextLine();
      ArrayList<String> command = Helper.wordsFromString(cmd);
      Log.getInstance().write("Введена команда " + cmd);
      String first_command = command.get(0);
      switch (first_command) {
        case "menu", "clear" -> menu();
        case "help" -> help();
        case "play" -> play();
        case "level" -> {
          if (command.size() > 2) {
            System.out.println("Избыток аргументов! Введите команду ещё раз.");
            continue;
          } else if (command.size() == 1) {
            System.out.println("Недостаточно аргументов! Введите команду ещё раз.");
            continue;
          }
          int level;
          try {
            level = Integer.parseInt(command.get(1));
          } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент: должно быть число!");
            continue;
          }
          level(level);
        }
        case "custom" -> {
          if (command.size() > 2) {
            System.out.println("Избыток аргументов! Введите команду ещё раз.");
            continue;
          } else if (command.size() == 1) {
            System.out.println("Недостаточно аргументов! Введите команду ещё раз.");
            continue;
          }
          int level;
          try {
            level = Integer.parseInt(command.get(1));
          } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент: должно быть число!");
            continue;
          }
          customLevel(level);
        }
        case "list", "levels" -> {
          int page;
          try {
            page = Integer.parseInt(command.get(1));
            list(page);
          } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент: должно быть число!");
          } catch (IndexOutOfBoundsException e) {
            list(1);
          }
        }
        case "clist", "clevels" -> {
          int page;
          try {
            page = Integer.parseInt(command.get(1));
            clist(page);
          } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент: должно быть число!");
          } catch (IndexOutOfBoundsException e) {
            clist(1);
          }
        }
        case "random" -> System.out.println("Скоро будет, но не щас.");
        case "last" -> last();
        case "prev" -> prev();
        case "make" -> {
          switch (command.get(1)) {
            case "function" ->
                this.functionMakerTutorial = FunctionMaker.make(functionMakerTutorial);
            case "level" -> this.levelMakerTutorial = LevelMaker.make(levelMakerTutorial, tutorial);
            default ->
                System.out.println(
                    "Неизвестная команда :( Введите help, чтобы узнать список команд.");
          }
        }
        case "next" -> next();
        case "stats" -> stats();
        case "exit", "quit", "stop" -> running = false;
        default ->
            System.out.println("Неизвестная команда :( Введите help, чтобы узнать список команд.");
      }
    }
  }

  public static Game login() {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    Player player;

    do {
      System.out.print("\nВведите ваш логин: ");
      String name = scanner.nextLine();
      if (!Authorization.isPlayerExists(name)) {
        String password1, password2;
        System.out.print("Придумайте пароль: ");
        password1 = scanner.nextLine();
        System.out.print("Повторите пароль: ");
        password2 = scanner.nextLine();
        if (!password1.equals(password2)) {
          System.out.println("Пароли отличаются!");
        } else {
          try {
            player = Authorization.register(name, password1);
            break;
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
      } else {
        String password;
        System.out.print("Введите пароль: ");
        password = scanner.nextLine();
        try {
          player = Authorization.login(name, password);
          break;
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    } while (true);
    return new Game(player);
  }

  @Override
  public String toString() {
    return "Game. " + player;
  }
}
