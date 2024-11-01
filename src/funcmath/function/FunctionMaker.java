package funcmath.function;

import funcmath.Helper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FunctionMaker {
    private static void tutorial() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        Helper.clear();
        System.out.println("Добро пожаловать в мастерскую создания функций!");
        System.out.println("Так как я плохо умею объяснять, буду пояснять на примере.");
        System.out.println("Будем вести пример на функции в натуральных числах f(a, b, c) = (a + (b - c)) + 2");
        System.out.println("Нажмите Enter, чтобы продолжить... ");
        scanner.nextLine();

        System.out.println("Сначала у вас попросят ввести название вашей функции:");
        System.out.println("Введите название функции: f");
        scanner.nextLine();

        System.out.println("Затем попросят ввести описание - это то, что будет показываться в самом уровне");
        System.out.println("Вы можете ввести любое описание. Главное, чтобы игроки поняли, как называется функция и сколько у неё аргументов.");
        System.out.println("Введите описание функции: f(a, b, c) = (a + (b - c)) + 2");
        scanner.nextLine();

        System.out.println("Либо же, вы можете просто написать:");
        System.out.println("Введите описание функции: f(a, b, c) - неизвестная функция");
        scanner.nextLine();

        System.out.println("После описания у вас запросят область определения функции.");
        System.out.println("Области определения функции задаётся одним английским словом.");
        System.out.println("Виды областей определения функций:");
        System.out.println("natural - натуральные числа");
        System.out.println("integer - целые числа");
        System.out.println("rational - рациональные (дробные) числа");
        System.out.println("real - действительные (с плавающей запятой) числа");
        System.out.println("complex - комплексные числа");
        System.out.println("Введите область определения функции: natural");
        scanner.nextLine();

        System.out.println("Потом вас попросят ввести то, как функция будет задаваться для компьютера.");
        System.out.println("Функция задаётся каким-либо выражением, записанным в префиксной (польской) нотации.");
        System.out.println("Пример такой записи: div sum 5 2 sub 3 2        = (5 + 2) / (3 - 2)");
        scanner.nextLine();

        System.out.println("В выражении могут присутствовать: простейшие функции (sum, sub, div и т.д.), аргументы (x0, x1, x2, ...) и числа (1, -2, 1/5, 4.23, -7-5i, ...)");
        System.out.println("Все виды простейших функций (название и аргументы):");
        System.out.println("sum x0 x1       sub x0 x1       mul x0 x1       div x0 x1       mod x0 x1       pow x0 x1");
        System.out.println("root x0 x1      log x0 x1       gcd x0 x1       rand x0 x1      abs x0          xor x0 x1");
        System.out.println("not x0          and x0 x1       or x0 x1        conj x0         arg x0          ignore x0");
        System.out.println("(Функция ignore предназначена для игнорирования какого-либо аргумента и ничего не возвращает.)");
        scanner.nextLine();

        System.out.println("Аргументы могут записываться либо через 'x' и номер аргумента (например x0), либо же через a, b, c и другие буквы латинского алфавита");
        System.out.println("Большая просьба, если у вас в функции присутствуют константы (как в нашем случае),");
        System.out.println("записывайте эти числа, чтобы они входили в область определения функции. (иначе произойдёт ошибка)");
        System.out.println("То есть для функции в натуральных/целых числах надо писать не какое-то действительное 2.63, а именно 2");
        System.out.println("Введите определение функции, используя простейшие: sum sum x0 sub x1 x2 2");
        scanner.nextLine();

        System.out.println("Также вы можете сделать так, чтобы ваша функция возвращала несколько параметров.");
        System.out.println("Для этого нужно просто сделать составное выражение");
        System.out.println("Например, функция doub(a), которая клонирует число a, задаётся вот так:");
        System.out.println("x0 x0");
        scanner.nextLine();

        System.out.println("И в завершении у вас спросят, сколько раз будет использоваться функция.");
        System.out.println("Если вы хотите, чтобы она использовалась бесконечно много раз, пишите любое отрицательное число. (желательно -1)");
        System.out.println("Если ограниченное число раз - пишите определенное количество.");
        System.out.println("Введите кол-во использований функции (-1 если бесконечно): -1");
        scanner.nextLine();

        System.out.println("После данных действий вам выдадут ID вашей функции и вы сможете добавлять вашу функцию в любой ваш уровень.");
        System.out.println("Обратите внимание: данный ID функции действителен только на вашем компьютере.");
        System.out.println("Ваша функция теперь имеет ID: 71395630152482        (он недействительный, написал в качестве примера)");
        scanner.nextLine();

        System.out.println("Спасибо за прочтение!");
        scanner.nextLine();
    }

    public static boolean make(boolean functionMakerTutorial) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        Helper.clear();

        System.out.println("Добро пожаловать в мастерскую создания функций!");
        if (functionMakerTutorial) {
            System.out.print("Хотите ли вы прочитать туториал? y/n ");
            String input = scanner.nextLine();
            if (input.equals("y") || input.equals("у")) tutorial();
        }

        System.out.print("Введите название функции: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание функции: ");
        String description = scanner.nextLine();
        System.out.print("Введите область опредения функции: ");
        String resultClassName = scanner.nextLine();
        System.out.print("Введите определение функции, используя простейшие: ");
        ArrayList<String> definition = Helper.wordsFromString(scanner.nextLine());
        System.out.print("Введите кол-во использований функции (-1 если бесконечно): ");
        int uses = scanner.nextInt();

        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
        ArrayList<String> aValues = new ArrayList<>(List.of(values));
        for (int i = 0; i < definition.size(); i++) {
            if (aValues.contains(definition.get(i))) {
                definition.set(i, "x" + (definition.get(i).charAt(0) - 'a'));
            }
        }

        Function function = new Function(name, definition, resultClassName, description, uses);
        HashMap<Integer, Function> functions;
        if (!Helper.isFileExists("data\\functions.dat")) functions = new HashMap<>();
        else functions = (HashMap<Integer, Function>) Helper.read("data\\functions.dat");
        functions.put(function.hashCode(), function);
        Helper.write(functions, "data\\functions.dat");
        System.out.println("Ваша функция теперь имеет ID: " + function.hashCode());

        System.out.println(function);

        return false;
    }

    public static void main(String[] args) {
        make(false);
    }
}
