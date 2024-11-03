package funcmath.game;

import funcmath.Helper;
import funcmath.function.Function;
import funcmath.object.MathObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class LevelMaker {
    private static void tutorial() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        Helper.clear();
        System.out.println("Добро пожаловать в мастерскую создания уровней!");
        System.out.println("Временно здесь нет туториала :(");
        System.out.println("Разбирайтесь! :)");
        System.out.println("Нажмите Enter, чтобы продолжить... ");
        scanner.nextLine();
    }

    public static boolean make(boolean levelMakerTutorial, boolean tutorial) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        Helper.clear();

        System.out.println("Добро пожаловать в мастерскую создания уровней!");
        if (levelMakerTutorial) {
            System.out.print("Хотите ли вы прочитать туториал? y/n ");
            String input = scanner.nextLine();
            if (input.equals("y") || input.equals("у")) tutorial();
        }

        String path = "data\\preLevels\\";
        String normalPath = "data\\customLevels\\";
        System.out.print("Введите что-нибудь: ");
        String mode = scanner.nextLine();
        boolean universalMode = Integer.toHexString(mode.hashCode()).equals("586034f");
        if (universalMode) {
            path = "data\\levels\\";
        }

        System.out.print("Введите название уровня: ");
        String name = scanner.nextLine();

        ArrayList<String> cutscene = new ArrayList<>();
        System.out.println("Введите катсцену, которая будет перед уровнем:");
        System.out.println("(введите пустую строку, чтобы закончить катсцену)");
        String input = "";
        int j = 1;
        do {
            cutscene.add(input);
            System.out.print("(" + j + ") ");
            input = scanner.nextLine();
            j++;
        } while(!input.isEmpty());
        cutscene.remove(0);

        HashMap<String, Function> originalFunctions = new HashMap<>();

        System.out.print("Введите ID функций через пробел: ");
        ArrayList<Integer> ids = Helper.integersFromWords(Helper.wordsFromString(scanner.nextLine()));
        String resultClassName = "";

        for (int id : ids) {
            Function f = new Function(id);
            if (!resultClassName.isEmpty() && !f.getResultClassName().equals(resultClassName)) {
                throw new IllegalArgumentException(
                        "Функции должны иметь одинаковую область определения. " +
                                "Должно: " + resultClassName + ", есть: " + f.getResultClassName()
                );
            }
            if (originalFunctions.containsKey(f.getName())) {
                throw new IllegalArgumentException("Названия функций не должны повторяться: " + f.getName());
            }
            resultClassName = f.getResultClassName();
            originalFunctions.put(f.getName(), f);
        }

        System.out.print("Введите числа в наборе: ");
        ArrayList<String> numbers = Helper.wordsFromString(scanner.nextLine());
        ArrayList<MathObject> originalNumbers = new ArrayList<>();
        for (String number : numbers) {
            originalNumbers.add(MathObject.parseMathObject(number, resultClassName));
        }

        System.out.print("Введите ответ: ");
        MathObject ans = MathObject.parseMathObject(scanner.nextLine(), resultClassName);

        ArrayList<String> hints = new ArrayList<>();
        System.out.print("Введите кол-во подсказок: ");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 1; i <= n; i++) {
            System.out.print("Введите подсказку №" + i + ": ");
            hints.add(scanner.nextLine());
        }

        ArrayList<Object> generated = new ArrayList<>();
        generated.add(originalNumbers);
        generated.add(originalFunctions);
        generated.add(ans);
        generated.add(hints);
        generated.add(resultClassName);
        generated.add(cutscene);
        generated.add(name);

        int levelHash = Objects.hash(originalNumbers, originalFunctions, hints, ans, resultClassName);
        String level = "level" + levelHash + ".dat";
        while (!universalMode && Helper.getFileNames(normalPath).contains(level)) {
            levelHash += mode.hashCode();
            level = "level" + levelHash + ".dat";
        }
        if (universalMode) level = "level" + (Helper.filesCount(path) + 1) + ".dat";

        Helper.write(generated, path + level);

        if (!universalMode) {
            System.out.println("А теперь вам придётся проходить уровень, чтобы он попал в список пользовательских.");
            System.out.print("Будете проходить? y/n ");
            String answer = scanner.nextLine();
            if (answer.equals("y") || answer.equals("у")) {
                Level l = new Level(levelHash, tutorial, 2);
                boolean[] result = l.game();
                if (result[1]) {
                    Helper.write(generated, normalPath + level);
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        make(false, false);
    }

    // в будущем реализовать LevelGenerator
}
