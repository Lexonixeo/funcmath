package funcmath.game;

import funcmath.Helper;
import funcmath.function.Function;
import funcmath.object.MathObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class LevelMaker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = "data\\preLevels\\";

        System.out.print("Введите режим создания уровня: ");
        String mode = scanner.nextLine();
        if (mode.equals("admin")) {
            path = "data\\levels\\";
        }

        HashMap<String, Function> originalFunctions = new HashMap<>();

        System.out.println("Обратите внимание: названия функций не должны повторяться!");
        System.out.println("Обратите внимание: область определения функций должна быть одинакова!");
        System.out.print("Введите ID функций: ");
        ArrayList<Integer> ids = Helper.numbersFromWords(Helper.wordsFromString(scanner.nextLine()));
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
        for (int i = 0; i < n; i++) {
            System.out.print("Введите подсказку №" + (i + 1) + ": ");
            hints.add(scanner.nextLine());
        }

        ArrayList<String> cutscene = new ArrayList<>();
        System.out.print("Введите кол-во фраз в катсцене: ");
        int m = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < m; i++) {
            System.out.println("(" + (i + 1) + ") ");
            cutscene.add(scanner.nextLine());
        }

        ArrayList<Object> generated = new ArrayList<>();
        generated.add(originalNumbers);
        generated.add(originalFunctions);
        generated.add(ans);
        generated.add(hints);
        generated.add(resultClassName);
        generated.add(cutscene);

        int levelHash = Objects.hash(originalNumbers, originalFunctions, hints, ans, resultClassName);
        String level = "level" + levelHash + ".dat";
        if (mode.equals("admin")) level = "level" + (Helper.filesCount(path) + 1) + ".dat";

        Helper.write(generated, path + level);
    }
}
