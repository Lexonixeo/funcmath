package funcmath.game;

import funcmath.Helper;
import funcmath.exceptions.InvalidPasswordException;
import funcmath.exceptions.NoRegisteredPlayerException;
import funcmath.exceptions.PlayerIsRegisteredException;
import funcmath.exceptions.WrongPasswordsException;
import funcmath.function.FunctionMaker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Game {
    String name;
    String hashedName;
    HashSet<Integer> completedLevels;
    int lastLevel;
    boolean tutorial = true;
    boolean levelMakerTutorial = true;
    boolean functionMakerTutorial = true;

    Game(String name) {
        this.name = name;
        this.hashedName = Integer.toHexString(name.hashCode());
        try {
            this.completedLevels = (HashSet<Integer>) Helper.read("data\\players\\" + hashedName + ".dat");
        } catch (Exception e) {
            this.completedLevels = new HashSet<>();
            Helper.write(completedLevels, "data\\players\\" + hashedName + ".dat");
        }
        try {
            this.lastLevel = (Integer) Helper.read("data\\players\\" + hashedName + "s.dat");
        } catch (Exception e) {
            this.lastLevel = 0;
            Helper.write(lastLevel, "data\\players\\" + hashedName + "s.dat");
        }
    }

    private void level(int level) {
        int levels = Helper.filesCount("data\\levels\\");
        if (level > levels || level < 1) {
            System.out.println("Такого уровня не существует!");
            return;
        }
        lastLevel = level;
        Helper.write(lastLevel, "data\\players\\" + hashedName + "s.dat");
        Level l = new Level(level, tutorial, 0);
        boolean[] result = l.game();
        tutorial = result[0];
        if (result[1]) {
            completedLevels.add(level);
        }
        Helper.write(completedLevels, "data\\players\\" + hashedName + ".dat");
    }

    private void customLevel(int level) {
        if (!Helper.isFileExists("data\\customLevels\\level" + level + ".dat")) {
            System.out.println("Такого уровня не существует!");
            return;
        }
        lastLevel = -level;
        Helper.write(lastLevel, "data\\players\\" + hashedName + "s.dat");
        Level l = new Level(level, tutorial, 1);
        boolean[] result = l.game();
        tutorial = result[0];
        if (result[1]) {
            completedLevels.add(level);
        }
        Helper.write(completedLevels, "data\\players\\" + hashedName + ".dat");
    }

    private void play() {
        int levels = Helper.filesCount("data\\levels");
        boolean isAnyNotCompletedLevel = false;
        for (int level = 1; level <= levels; level++) {
            if (!completedLevels.contains(level)) {
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
        if (lastLevel == 0) {
            System.out.println("Вы ещё не заходили в какой-либо уровень!");  // переформулировать когда добавлю случайные уровни
            return;
        }
        if (lastLevel < 0) {
            System.out.println("Не существует следующего уровня!");
            return;
        }
        level(lastLevel + 1);
    }

    private void prev() {
        if (lastLevel == 0) {
            System.out.println("Вы ещё не заходили в какой-либо уровень!");  // переформулировать когда добавлю случайные уровни
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
        System.out.println("stats         - Узнать свою статистику (НЕ РАБОТАЕТ)");
        System.out.println("make level    - Создать свой уровень");
        System.out.println("make function - Создать свою функцию");
        System.out.println("exit          - Выйти из игры");
    }

    private void menu() {
        Helper.clear();
        int levels = Helper.filesCount("data\\levels");
        System.out.println("Добро пожаловать, " + name + "!");
        System.out.println("Вы сейчас играете в III прототип игры func(math).");
        System.out.println("Он написан для реализации новых фич в игре и вашего оценивания.");
        System.out.println();
        System.out.println("Вы находитесь в главном меню.");
        System.out.println("Игра начинается с 1-ого уровня. Всего в игре " + levels + " уровней.");
        System.out.println("Чтобы " + (completedLevels.isEmpty() ? "начать игру" : "продолжить игру с первого непройденного уровня") + ", введите команду play.");
        System.out.println("Чтобы играть в конкретный уровень, введите команду level {номер уровня}.");
        System.out.println("Чтобы узнать список команд, введите команду help.");
        System.out.println("Чтобы выйти из игры, введите команду exit.");
    }

    private void list(int page) {
        ArrayList<Integer> levels = Helper.getLevelList(Helper.getFileNames("data\\levels\\"));
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
        ArrayList<Integer> levels = Helper.getLevelList(Helper.getFileNames("data\\customLevels\\"));
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
        // to do...
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
            Helper.write(completedLevels, "data\\players\\" + hashedName + ".dat");
            System.out.println();
            System.out.print("[Commander] Введите команду: ");
            ArrayList<String> command = Helper.wordsFromString(scanner.nextLine());
            String first_command = command.get(0);
            switch (first_command) {
                case "menu" -> menu();
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
                case "list" -> {
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
                case "clist" -> {
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
                        case "function" -> this.functionMakerTutorial = FunctionMaker.make(functionMakerTutorial);
                        case "level" -> this.levelMakerTutorial = LevelMaker.make(levelMakerTutorial, tutorial);
                        default -> System.out.println("Неизвестная команда :( Введите help, чтобы узнать список команд.");
                    }
                }
                case "next" -> next();
                case "stats" -> System.out.println("Скоро будет, но не щас.");
                case "exit" -> running = false;
                default -> System.out.println("Неизвестная команда :( Введите help, чтобы узнать список команд.");
            }
        }
    }

    public static Game login(String name, String password) {
        HashMap<Integer, String> players;
        if (!Helper.isFileExists("data\\players\\players.dat")) {
            players = new HashMap<>();
        } else {
            players = (HashMap<Integer, String>) Helper.read("data\\players\\players.dat");
        }

        if (!players.containsValue(name)) {
            throw new NoRegisteredPlayerException(name);
        } else if (!players.containsKey(password.hashCode()) || !players.get(password.hashCode()).equals(name)) {
            throw new InvalidPasswordException();
        }

        return new Game(name);
    }

    public static Game register(String name, String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new WrongPasswordsException();
        }

        HashMap<Integer, String> players;
        if (!Helper.isFileExists("data\\players\\players.dat")) {
            players = new HashMap<>();
        } else {
            players = (HashMap<Integer, String>) Helper.read("data\\players\\players.dat");
        }

        if (players.containsValue(name)) {
            throw new PlayerIsRegisteredException(name);
        }

        players.put(password1.hashCode(), name);
        Helper.write(players, "data\\players\\players.dat");
        return new Game(name);
    }

    public static Game login() {
        HashMap<Integer, String> players;
        if (!Helper.isFileExists("data\\players\\players.dat")) players = new HashMap<>();
        else players = (HashMap<Integer, String>) Helper.read("data\\players\\players.dat");
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.print("Введите ваш логин: ");
        String name = scanner.nextLine();
        if (!players.containsValue(name)) {
            String password1, password2;
            while (true) {
                System.out.print("Придумайте пароль: ");
                password1 = scanner.nextLine();
                System.out.print("Повторите пароль: ");
                password2 = scanner.nextLine();
                if (!password1.equals(password2)) {
                    System.out.println("Пароли отличаются!\n");
                } else {
                    break;
                }
            }
            players.put(password1.hashCode(), name);
        } else {
            String password;
            while (true) {
                System.out.print("Введите пароль: ");
                password = scanner.nextLine();
                if (!players.containsKey(password.hashCode()) || !players.get(password.hashCode()).equals(name)) {
                    System.out.println("Неверный пароль! Если вы перепутали логин, перезапустите игру.\n");
                } else {
                    break;
                }
            }
        }
        Helper.write(players, "data\\players\\players.dat");
        return new Game(name);
    }

    @Override
    public String toString() {
        return "Game. Player " + name;
    }
}
