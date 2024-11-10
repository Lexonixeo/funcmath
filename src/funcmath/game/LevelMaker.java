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
        System.out.println("Так как я плохо умею объяснять, буду пояснять на примере.");
        System.out.println("Будем вести пример на уровне №29.");
        System.out.println("Нажмите Enter, чтобы продолжить... ");
        scanner.nextLine();

        System.out.println("Сначала у вас попросят ввести что-нибудь.");
        System.out.println("Просто введите что-нибудь, это может влиять лишь на номер уровня.");
        System.out.println("Введите что-нибудь: gjifnswdfjew");
        scanner.nextLine();

        System.out.println("Потом вам нужно ввести название уровня.");
        System.out.println("Оно будет отображаться в самом уровне рядом с его номером и в списке уровней.");
        System.out.println("Введите название уровня: Что это?");
        scanner.nextLine();

        System.out.println("Потом вас попросят ввести катсцену, которая будет перед уровнем.");
        System.out.println("Если вы играли в первые уровни, вы, возможно, знаете что это такое.");
        System.out.println("Катсцена состоит из скольких-то фраз.");
        System.out.println("Если вы захотите закончить катсцену, просто нажмите Enter. (как в нашем случае)");
        System.out.println("Введите катсцену, которая будет перед уровнем:");
        System.out.println("(введите пустую строку, чтобы закончить катсцену)");
        System.out.println("(1) ");
        scanner.nextLine();

        System.out.println("Затем у вас попросят ввести область определения уровня.");
        System.out.println("Область определения уровня - это то, в каких числах будет уровень считаться.");
        System.out.println("Области определения уровня задаётся одним английским словом.");
        System.out.println("Виды областей определения уровней:");
        System.out.println("natural - натуральные числа");
        System.out.println("integer - целые числа");
        System.out.println("rational - рациональные (дробные) числа");
        System.out.println("real - действительные (с плавающей запятой) числа");
        System.out.println("complex - комплексные числа");
        System.out.println("Введите область определения уровня: natural");
        scanner.nextLine();

        System.out.println("Потом вас попросят ввести числа, которые будут в наборе.");
        System.out.println("Каждое число из набора может использоваться только 1 раз.");
        System.out.println("Введите числа в наборе: 60 120 2 14 62");
        scanner.nextLine();

        System.out.println("После вас попросят ввести ID функций, которые будут в уровне.");
        System.out.println("ID функции вы можете получить из названия файла с функцией внутри папки data/functions/(область определения)/");
        System.out.println("Либо же из мастерской создания функций - там сразу же, когда создастся функция, выдастся её ID.");
        System.out.println("Самое главное - чтобы у функций уровня не совпадали названия.");
        System.out.println("Введите ID функций через пробел: 12345abc 3e1bd21 2f983c0");
        scanner.nextLine();

        System.out.println("Затем вам нужно написать ответ - требуемое(ые) число(а) для вашего уровня.");
        System.out.println("Здесь вы можете ввести не только одно число, а несколько.");
        System.out.println("Уровень будет пройден тогда, когда все требуемые числа будут в наборе.");
        System.out.println("Введите ответ (необходимое(ые) число(а)): 194");
        scanner.nextLine();

        System.out.println("Затем по вашему желанию вы можете ввести подсказки.");
        System.out.println("Сначала спросят количество подсказок, и затем по каждой подсказке.");
        System.out.println("Порядок отображения подсказок в уровне будет такой же, в каком порядке вы их вводите.");
        System.out.println("Введите кол-во подсказок: 0");
        scanner.nextLine();

        System.out.println("Потом вас попросят пройти данный уровень, чтобы он сохранился в пользовательских.");
        System.out.println("После прохождения уровень будет сохранен под случайным номером, который вы узнаете в процессе прохождения.");
        System.out.println("Чтобы в следующий раз пройти его, вам нужно будет ввести команду custom (номер уровня)");
        System.out.println("Также вы сможете найти свой уровень через команду clist.");
        scanner.nextLine();

        System.out.println("Спасибо за прочтение!");
        scanner.nextLine();
    }

    public static boolean make(boolean levelMakerTutorial, boolean tutorial) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        Helper.clear();

        System.out.println("Добро пожаловать в мастерскую создания уровней!");
        if (levelMakerTutorial) {
            System.out.print("Хотите ли вы прочитать туториал? y/n ");
            String input = scanner.nextLine();
            if (input.equals("y") || input.equals("у")) {
                tutorial();
            }
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

        System.out.print("Введите область определения уровня: ");
        String resultClassName = scanner.nextLine();

        System.out.print("Введите числа в наборе: ");
        ArrayList<String> numbers = Helper.wordsFromString(scanner.nextLine());
        ArrayList<MathObject> originalNumbers = new ArrayList<>();
        for (String number : numbers) {
            originalNumbers.add(MathObject.parseMathObject(number, resultClassName));
        }

        System.out.print("Введите ID функций через пробел: ");
        ArrayList<String> ids = Helper.wordsFromString(scanner.nextLine());

        for (String id : ids) {
            Function f = new Function(resultClassName, id);
            if (originalFunctions.containsKey(f.getName())) {
                System.out.println("Названия функций не должны повторяться: " + f.getName());
                return true;
            }
            originalFunctions.put(f.getName(), f);
        }

        System.out.print("Введите ответ (необходимое(ые) число(а)): ");
        ArrayList<String> ansnumbers = Helper.wordsFromString(scanner.nextLine());
        ArrayList<MathObject> answers = new ArrayList<>();
        for (String ansnum : ansnumbers) {
            answers.add(MathObject.parseMathObject(ansnum, resultClassName));
        }

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
        generated.add(answers);
        generated.add(hints);
        generated.add(resultClassName);
        generated.add(cutscene);
        generated.add(name);

        int levelHash = Objects.hash(originalNumbers, originalFunctions, hints, answers, resultClassName);
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
