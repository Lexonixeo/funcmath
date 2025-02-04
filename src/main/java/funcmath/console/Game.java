package funcmath.console;

import funcmath.game.*;
import funcmath.game.defaultlevel.DLevelMaker;
import funcmath.utility.Helper;
import funcmath.utility.Log;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
  Player player;
  boolean tutorial = true;
  boolean levelMakerTutorial = true;
  boolean functionMakerTutorial = true;
  static final FMPrintStream systemOut =
      new FMPrintStream(System.out) {
        @Override
        public void clear() {
          Helper.clear(this);
        }
      };

  Game(Player player) {
    Log.getInstance().write("A game has been created with a player " + player);
    this.player = player;
  }

  private void level(int level) {
    Log.getInstance().write("Attempting to enter level №" + level);
    int levels = Helper.filesCount("data\\levels\\");
    if (level > levels || level < 1) {
      Log.getInstance().write("The player was unable to enter level №" + level);
      systemOut.println("Такого уровня не существует!");
      return;
    }
    Level l = Level.getLevelInstance(level, LevelPlayFlag.DEFAULT);
    // Level l = new DefaultLevel(level, LevelPlayFlag.DEFAULT);
    int[] result = l.consoleRun(System.in, systemOut);
    Log.getInstance().write("Level №" + level + " was completed");
    tutorial = (result[0] == 1);
    player.addLevel((result[1] == 1), level, result[2], result[3]);
  }

  private void customLevel(int level) {
    Log.getInstance().write("Attempting to enter custom level №" + level);
    if (Helper.isNotFileExists("data\\customLevels\\level" + level + ".dat")) {
      Log.getInstance().write("The player was unable to enter custom level №" + level);
      systemOut.println("Такого уровня не существует!");
      return;
    }
    Level l = Level.getLevelInstance(level, LevelPlayFlag.CUSTOM);
    int[] result = l.consoleRun(System.in, systemOut);
    Log.getInstance().write("Custom level №" + level + " was completed");
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
      Log.getInstance()
          .write("The player attempted to invoke play when he completed all the levels");
      systemOut.println("Вы прошли все уровни!");
    }
  }

  private void last() {
    int lastLevel = player.getLastLevel();
    if (lastLevel == 0) {
      Log.getInstance()
          .write("The player attempted to invoke last when he hasn't entered any level");
      systemOut.println("Вы ещё не заходили в какой-либо уровень!");
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
      Log.getInstance()
          .write("The player attempted to invoke next when he hasn't entered any level");
      systemOut.println(
          "Вы ещё не заходили в какой-либо уровень!"); // переформулировать когда добавлю случайные
      // уровни
      return;
    }
    if (lastLevel < 0) {
      Log.getInstance()
          .write(
              "The player attempted to invoke next while playing the last time he was in a custom level");
      systemOut.println("Не существует следующего уровня!");
      return;
    }
    level(lastLevel + 1);
  }

  private void prev() {
    int lastLevel = player.getLastLevel();
    if (lastLevel == 0) {
      Log.getInstance()
          .write("The player attempted to invoke next when he hasn't entered any level");
      systemOut.println(
          "Вы ещё не заходили в какой-либо уровень!"); // переформулировать когда добавлю случайные
      // уровни
      return;
    }
    if (lastLevel < 0) {
      Log.getInstance()
          .write(
              "The player attempted to invoke next while playing the last time he was in a custom level");
      systemOut.println("Не существует предыдущего уровня!");
      return;
    }
    level(lastLevel - 1);
  }

  private void help() {
    Helper.clear(systemOut);
    systemOut.println("Список команд:");
    systemOut.println("menu          - Перейти в главное меню");
    systemOut.println("help          - Узнать список команд");
    systemOut.println("play          - Начать/продолжить игру");
    systemOut.println("level {lvl}   - Перейти к уровню {lvl}");
    systemOut.println("custom {lvl}  - Играть в пользовательский уровень {lvl}");
    systemOut.println("random {dfc}  - Играть в случайный уровень сложности {dfc} (НЕ РАБОТАЕТ)");
    systemOut.println("list {n}      - Открыть список обычных уровней на странице {n}");
    systemOut.println("clist {n}     - Открыть список пользовательских уровней на странице {n}");
    systemOut.println("last          - Перейти к последнему уровню");
    systemOut.println("prev          - Перейти к предыдущему уровню");
    systemOut.println("next          - Перейти к следующему уровню");
    systemOut.println("stats         - Узнать свою статистику");
    systemOut.println("make level    - Создать свой уровень");
    systemOut.println("make function - Создать свою функцию");
    systemOut.println("exit          - Выйти из игры");
  }

  private void menu() {
    Helper.clear(systemOut);
    int levels = Helper.filesCount("data\\levels");
    systemOut.println("Добро пожаловать, " + player.getName() + "!");
    systemOut.println("Вы сейчас играете в III прототип игры func(math).");
    systemOut.println("Он написан для реализации новых фич в игре и вашего оценивания.");
    systemOut.println();
    systemOut.println("Вы находитесь в главном меню.");
    systemOut.println("Игра начинается с 1-ого уровня. Всего в игре " + levels + " уровней.");
    systemOut.println(
        "Чтобы "
            + (player.getCompletedLevels().isEmpty()
                ? "начать игру"
                : "продолжить игру с первого непройденного уровня")
            + ", введите команду play.");
    systemOut.println("Чтобы играть в конкретный уровень, введите команду level {номер уровня}.");
    systemOut.println("Чтобы узнать список команд, введите команду help.");
    systemOut.println("Чтобы выйти из игры, введите команду exit.");
  }

  private void list(int page) {
    ArrayList<Integer> levels = LevelList.getLevelList(LevelPlayFlag.DEFAULT);
    if (page <= 0 || levels.size() < 20 * page - 20 + 1) {
      Log.getInstance().write("A player tried to invoke a list with a non-existent page");
      systemOut.println("Не бывает такой страницы!");
      return;
    }
    Helper.clear(systemOut);
    systemOut.println("Страница " + page + " из " + ((levels.size() - 1) / 20 + 1));
    for (int i = 20 * (page - 1); i < Math.min(20 * page, levels.size()); i++) {
      systemOut.println(Level.getLevelInstance(levels.get(i), LevelPlayFlag.DEFAULT));
    }
  }

  private void clist(int page) {
    ArrayList<Integer> levels = LevelList.getLevelList(LevelPlayFlag.DEFAULT);
    if (page <= 0 || levels.size() < 20 * page - 20 + 1) {
      Log.getInstance().write("A player tried to invoke a clist with a non-existent page");
      systemOut.println("Не бывает такой страницы!");
      return;
    }
    Helper.clear(systemOut);
    systemOut.println("Страница " + page + " из " + ((levels.size() - 1) / 20 + 1));
    for (int i = 20 * (page - 1); i < Math.min(20 * page, levels.size()); i++) {
      systemOut.println(Level.getLevelInstance(levels.get(i), LevelPlayFlag.CUSTOM));
    }
  }

  private void stats() {
    Helper.clear(systemOut);
    systemOut.println("Статистика игрока " + player.getName() + ":");
    systemOut.println("Пройденные уровни:");
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
      systemOut.print(level + "/" + fuses + "/" + time + "\t\t");
      if (i % 5 == 0) systemOut.println();
    }
    systemOut.println("\n");
    systemOut.println("(уровень/минимальное число использованных функций/минимальное время (с))");
    systemOut.println("(10000 - значит то, что эти данные не сохранились)");
  }

  private void random() {
    // to do...
  }

  public void game() {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    menu();
    boolean running = true;
    while (running) {
      player.writePlayerInfo();
      systemOut.println();
      systemOut.print("[Commander] Введите команду: ");
      String cmd = scanner.nextLine();
      ArrayList<String> command = Helper.wordsFromString(cmd);
      Log.getInstance().write("Introduced the command " + cmd);
      String first_command = command.get(0);
      switch (first_command) {
        case "menu", "clear" -> menu();
        case "help" -> help();
        case "play" -> play();
        case "level" -> {
          if (command.size() > 2) {
            Log.getInstance().write("The player tried to invoke level with excessive arguments");
            systemOut.println("Избыток аргументов! Введите команду ещё раз.");
            continue;
          } else if (command.size() == 1) {
            Log.getInstance().write("The player tried to invoke level without arguments");
            systemOut.println("Недостаточно аргументов! Введите команду ещё раз.");
            continue;
          }
          int level;
          try {
            level = Integer.parseInt(command.get(1));
          } catch (NumberFormatException e) {
            Log.getInstance().write("The player tried to invoke a level with NaN");
            systemOut.println("Неверный аргумент: должно быть число!");
            continue;
          }
          level(level);
        }
        case "custom" -> {
          if (command.size() > 2) {
            Log.getInstance().write("The player tried to invoke custom with excessive arguments");
            systemOut.println("Избыток аргументов! Введите команду ещё раз.");
            continue;
          } else if (command.size() == 1) {
            Log.getInstance().write("The player tried to invoke custom without arguments");
            systemOut.println("Недостаточно аргументов! Введите команду ещё раз.");
            continue;
          }
          int level;
          try {
            level = Integer.parseInt(command.get(1));
          } catch (NumberFormatException e) {
            Log.getInstance().write("The player tried to invoke a custom with NaN");
            systemOut.println("Неверный аргумент: должно быть число!");
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
            Log.getInstance().write("The player tried to invoke a list with NaN");
            systemOut.println("Неверный аргумент: должно быть число!");
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
            Log.getInstance().write("The player tried to invoke a clist with NaN");
            systemOut.println("Неверный аргумент: должно быть число!");
          } catch (IndexOutOfBoundsException e) {
            clist(1);
          }
        }
        case "random" -> systemOut.println("Скоро будет, но не щас.");
        case "last" -> last();
        case "prev" -> prev();
        case "make" -> {
          switch (command.get(1)) {
            case "function" ->
                this.functionMakerTutorial = FunctionMaker.make(functionMakerTutorial);
            case "level" ->
                this.levelMakerTutorial = DLevelMaker.make(levelMakerTutorial, tutorial);
            default ->
                systemOut.println(
                    "Неизвестная команда :( Введите help, чтобы узнать список команд.");
          }
        }
        case "next" -> next();
        case "stats" -> stats();
        case "exit", "quit", "stop" -> running = false;
        default ->
            systemOut.println("Неизвестная команда :( Введите help, чтобы узнать список команд.");
      }
    }
    Log.getInstance().write("The player is out of the game legally!");
  }

  public static Game login() {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    Player player;

    do {
      systemOut.print("\nВведите ваш логин: ");
      String name = scanner.nextLine();
      if (!Authorization.isPlayerExists(name)) {
        String password1, password2;
        systemOut.print("Придумайте пароль: ");
        password1 = scanner.nextLine();
        systemOut.print("Повторите пароль: ");
        password2 = scanner.nextLine();
        if (!password1.equals(password2)) {
          systemOut.println("Пароли отличаются!");
        } else {
          try {
            player = Authorization.register(name, password1);
            break;
          } catch (Exception e) {
            systemOut.println(e.getMessage());
          }
        }
      } else {
        String password;
        systemOut.print("Введите пароль: ");
        password = scanner.nextLine();
        try {
          player = Authorization.login(name, password);
          break;
        } catch (Exception e) {
          systemOut.println(e.getMessage());
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
