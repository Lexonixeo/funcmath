package funcmath.console;

import funcmath.function.Function;
import funcmath.game.FMPrintStream;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import funcmath.utility.Log;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FunctionMaker {
  static final FMPrintStream systemOut =
      new FMPrintStream(System.out) {
        @Override
        public void clear() {
          Helper.clear(this);
        }
      };

  private static void tutorial() {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    Helper.clear(systemOut);
    Log.getInstance().write("Игрок читает туториал по функциям");
    systemOut.println("Добро пожаловать в мастерскую создания функций!");
    systemOut.println("Так как я плохо умею объяснять, буду пояснять на примере.");
    systemOut.println(
        "Будем вести пример на функции в натуральных числах f(a, b, c) = (a + (b - c)) + 2");
    systemOut.println("Нажмите Enter, чтобы продолжить... ");
    scanner.nextLine();

    systemOut.println("Сначала у вас попросят ввести название вашей функции:");
    systemOut.println("Введите название функции: f");
    scanner.nextLine();

    systemOut.println(
        "Затем попросят ввести описание - это то, что будет показываться в самом уровне");
    systemOut.println(
        "Вы можете ввести любое описание. Главное, чтобы игроки поняли, как называется функция и сколько у неё аргументов.");
    systemOut.println("Введите описание функции: f(a, b, c) = (a + (b - c)) + 2");
    scanner.nextLine();

    systemOut.println("Либо же, вы можете просто написать:");
    systemOut.println("Введите описание функции: f(a, b, c) - неизвестная функция");
    scanner.nextLine();

    systemOut.println("После описания у вас запросят область определения функции.");
    systemOut.println("Области определения функции задаётся одним английским словом.");
    systemOut.println("Виды областей определения функций:");
    systemOut.println("natural - натуральные числа");
    systemOut.println("integer - целые числа");
    systemOut.println("rational - рациональные (дробные) числа");
    systemOut.println("real - действительные (с плавающей запятой) числа");
    systemOut.println("complex - комплексные числа");
    systemOut.println("Введите область определения функции: natural");
    scanner.nextLine();

    systemOut.println("Потом вас попросят ввести то, как функция будет задаваться для компьютера.");
    systemOut.println(
        "Функция задаётся каким-либо выражением, записанным в префиксной (польской) нотации.");
    systemOut.println("Пример такой записи: div sum 5 2 sub 3 2        = (5 + 2) / (3 - 2)");
    scanner.nextLine();

    systemOut.println(
        "В выражении могут присутствовать: простейшие функции (sum, sub, div и т.д.), аргументы (x0, x1, x2, ...) и числа (1, -2, 1/5, 4.23, -7-5i, ...)");
    systemOut.println("Все виды простейших функций (название и аргументы):");
    systemOut.println(
        "sum x0 x1       sub x0 x1       mul x0 x1       div x0 x1       mod x0 x1       pow x0 x1");
    systemOut.println(
        "root x0 x1      log x0 x1       gcd x0 x1       lcm x0 x1       fact x0         concat x0 x1");
    systemOut.println(
        "rand x0 x1      abs x0          and x0 x1       or x0 x1        xor x0 x1       min x0 x1");
    systemOut.println(
        "max x0 x1       sign x0         primes x0       not x0          med x0 x1       sin x0");
    systemOut.println(
        "cos x0          tan x0          arcsin x0       arccos x0       arctan x0       conj x0");
    systemOut.println("arg x0          ignore x0");
    systemOut.println(
        "(Функция ignore предназначена для игнорирования какого-либо аргумента и ничего не возвращает.)");
    scanner.nextLine();

    systemOut.println(
        "Аргументы могут записываться либо через 'x' и номер аргумента (например x0), либо же через a, b, c и другие буквы латинского алфавита");
    systemOut.println(
        "Большая просьба, если у вас в функции присутствуют константы (как в нашем случае),");
    systemOut.println(
        "записывайте эти числа, чтобы они входили в область определения функции. (иначе произойдёт ошибка)");
    systemOut.println(
        "То есть для функции в натуральных/целых числах надо писать не какое-то действительное 2.63, а именно 2");
    systemOut.println("Введите определение функции, используя простейшие: sum sum x0 sub x1 x2 2");
    scanner.nextLine();

    systemOut.println(
        "Также вы можете сделать так, чтобы ваша функция возвращала несколько параметров.");
    systemOut.println("Для этого нужно просто сделать составное выражение");
    systemOut.println("Например, функция doub(a), которая клонирует число a, задаётся вот так:");
    systemOut.println("x0 x0");
    scanner.nextLine();

    systemOut.println("И в завершении у вас спросят, сколько раз будет использоваться функция.");
    systemOut.println(
        "Если вы хотите, чтобы она использовалась бесконечно много раз, пишите любое отрицательное число. (желательно -1)");
    systemOut.println("Если ограниченное число раз - пишите определенное количество.");
    systemOut.println("Введите кол-во использований функции (-1 если бесконечно): -1");
    scanner.nextLine();

    systemOut.println(
        "После данных действий вам выдадут ID вашей функции и вы сможете добавлять вашу функцию в любой ваш уровень.");
    systemOut.println(
        "Функция будет сохранена в файле, который находится в папке data/functions/(область определения)/ и будет иметь название в виде ID.");
    systemOut.println(
        "Ваша функция теперь имеет ID: 12345abc        (он недействительный, написал в качестве примера)");
    scanner.nextLine();

    systemOut.println("Спасибо за прочтение!");
    scanner.nextLine();
    Log.getInstance().write("Игрок прочитал туториал по функциям");
  }

  public static boolean make(boolean functionMakerTutorial) {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    Helper.clear(systemOut);
    Log.getInstance().write("The player went into the function maker");

    systemOut.println("Добро пожаловать в мастерскую создания функций!");
    if (functionMakerTutorial) {
      systemOut.print("Хотите ли вы прочитать туториал? y/n ");
      String input = scanner.nextLine();
      if (input.equals("y") || input.equals("у")) {
        tutorial();
      }
    }

    systemOut.print("Введите название функции: ");
    String name = scanner.nextLine();
    systemOut.print("Введите описание функции: ");
    String description = scanner.nextLine();
    systemOut.print("Введите область определения функции: ");
    String resultClassName = scanner.nextLine();
    if (!MathObject.checkResultClassName(resultClassName)) {
      systemOut.println("Неверная область определения!");
      return true;
    }
    systemOut.print("Введите определение функции, используя простейшие: ");
    ArrayList<String> definition = Helper.wordsFromString(scanner.nextLine());
    systemOut.print("Введите кол-во использований функции (-1 если бесконечно): ");
    int uses = scanner.nextInt();

    String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
    ArrayList<String> aValues = new ArrayList<>(List.of(values));
    for (int i = 0; i < definition.size(); i++) {
      if (aValues.contains(definition.get(i))) {
        definition.set(i, "x" + (definition.get(i).charAt(0) - 'a'));
      }
    }

    Function function = new Function(name, definition, resultClassName, description, uses);
    int functionHash = function.hashCode();
    String functionFileName = name + Integer.toHexString((functionHash % 256 + 256) % 256) + ".dat";
    while (Helper.getFileNames("data/functions/" + resultClassName + "/")
        .contains(functionFileName)) {
      functionHash += functionFileName.hashCode();
      functionFileName = name + Integer.toHexString((functionHash % 256 + 256) % 256) + ".dat";
    }
    Helper.write(function, "data/functions/" + resultClassName + "/" + functionFileName);

    systemOut.println(
        "Ваша функция теперь имеет ID: "
            + name
            + Integer.toHexString((functionHash % 256 + 256) % 256));
    Log.getInstance()
        .write("Created a new function in " + resultClassName + "/" + functionFileName);

    return false;
  }

  public static void main(String[] args) {
    Log.getInstance().write("Someone ran a program from class FunctionMaker");
    make(false);
  }
}
