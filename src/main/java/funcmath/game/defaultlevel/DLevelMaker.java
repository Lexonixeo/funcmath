package funcmath.game.defaultlevel;

import funcmath.function.Function;
import funcmath.game.Cutscene;
import funcmath.game.FMPrintStream;
import funcmath.game.Level;
import funcmath.game.LevelPlayFlag;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import funcmath.utility.Log;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DLevelMaker {
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
    Log.getInstance().write("The player reads a tutorial on the levels");
    systemOut.println("Добро пожаловать в мастерскую создания уровней!");
    systemOut.println("Так как я плохо умею объяснять, буду пояснять на примере.");
    systemOut.println("Будем вести пример на уровне №29.");
    systemOut.println("Нажмите Enter, чтобы продолжить... ");
    scanner.nextLine();

    systemOut.println("Сначала у вас попросят ввести что-нибудь.");
    systemOut.println("Просто введите что-нибудь, это может влиять лишь на номер уровня.");
    systemOut.println("Введите что-нибудь: gjifnswdfjew");
    scanner.nextLine();

    systemOut.println("Потом вам нужно ввести название уровня.");
    systemOut.println(
        "Оно будет отображаться в самом уровне рядом с его номером и в списке уровней.");
    systemOut.println("Введите название уровня: Что это?");
    scanner.nextLine();

    systemOut.println("Потом вас попросят ввести катсцену, которая будет перед уровнем.");
    systemOut.println("Если вы играли в первые уровни, вы, возможно, знаете что это такое.");
    systemOut.println("Катсцена состоит из скольких-то фраз.");
    systemOut.println(
        "Если вы захотите закончить катсцену, просто нажмите Enter. (как в нашем случае)");
    systemOut.println("Введите катсцену, которая будет перед уровнем:");
    systemOut.println("(введите пустую строку, чтобы закончить катсцену)");
    systemOut.println("(1) ");
    scanner.nextLine();

    systemOut.println("Затем у вас попросят ввести область определения уровня.");
    systemOut.println(
        "Область определения уровня - это то, в каких числах будет уровень считаться.");
    systemOut.println("Области определения уровня задаётся одним английским словом.");
    systemOut.println("Виды областей определения уровней:");
    systemOut.println("natural - натуральные числа");
    systemOut.println("integer - целые числа");
    systemOut.println("rational - рациональные (дробные) числа");
    systemOut.println("real - действительные (с плавающей запятой) числа");
    systemOut.println("complex - комплексные числа");
    systemOut.println("Введите область определения уровня: natural");
    scanner.nextLine();

    systemOut.println("Потом вас попросят ввести числа, которые будут в наборе.");
    systemOut.println("Каждое число из набора может использоваться только 1 раз.");
    systemOut.println("Введите числа в наборе: 60 120 2 14 62");
    scanner.nextLine();

    systemOut.println("После вас попросят ввести ID функций, которые будут в уровне.");
    systemOut.println(
        "ID функции вы можете получить из названия файла с функцией внутри папки data/functions/(область определения)/");
    systemOut.println(
        "Либо же из мастерской создания функций - там сразу же, когда создастся функция, выдастся её ID.");
    systemOut.println("Самое главное - чтобы у функций уровня не совпадали названия.");
    systemOut.println("Введите ID функций через пробел: 12345abc 3e1bd21 2f983c0");
    scanner.nextLine();

    systemOut.println("Затем вам нужно написать ответ - требуемое(ые) число(а) для вашего уровня.");
    systemOut.println("Здесь вы можете ввести не только одно число, а несколько.");
    systemOut.println("Уровень будет пройден тогда, когда все требуемые числа будут в наборе.");
    systemOut.println("Введите ответ (необходимое(ые) число(а)): 194");
    scanner.nextLine();

    systemOut.println("Затем по вашему желанию вы можете ввести подсказки.");
    systemOut.println("Сначала спросят количество подсказок, и затем по каждой подсказке.");
    systemOut.println(
        "Порядок отображения подсказок в уровне будет такой же, в каком порядке вы их вводите.");
    systemOut.println("Введите кол-во подсказок: 0");
    scanner.nextLine();

    systemOut.println(
        "Потом вас попросят пройти данный уровень, чтобы он сохранился в пользовательских.");
    systemOut.println(
        "После прохождения уровень будет сохранен под случайным номером, который вы узнаете в процессе прохождения.");
    systemOut.println(
        "Чтобы в следующий раз пройти его, вам нужно будет ввести команду custom (номер уровня)");
    systemOut.println("Также вы сможете найти свой уровень через команду clist.");
    scanner.nextLine();

    systemOut.println("Спасибо за прочтение!");
    scanner.nextLine();
    Log.getInstance().write("The player has read the tutorial on the levels");
  }

  public static boolean make(boolean levelMakerTutorial, boolean tutorial) {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    Helper.clear(systemOut);
    Log.getInstance().write("The player entered the level creation workshop");

    systemOut.println("Добро пожаловать в мастерскую создания уровней!");
    if (levelMakerTutorial) {
      systemOut.print("Хотите ли вы прочитать туториал? y/n ");
      String input = scanner.nextLine();
      if (input.equals("y") || input.equals("у")) {
        tutorial();
      }
    }

    LevelPlayFlag customFlag = LevelPlayFlag.PRE;
    String normalPath = "data\\customLevels\\";
    systemOut.print("Введите что-нибудь: ");
    String mode = scanner.nextLine();
    boolean universalMode = Integer.toHexString(mode.hashCode()).equals("586034f");
    if (universalMode) {
      customFlag = LevelPlayFlag.DEFAULT;
    }

    SecureRandom random = new SecureRandom();
    int levelID = random.nextInt();
    String level = "level" + levelID + ".dat";
    while (!universalMode && Helper.getFileNames(normalPath).contains(level)) {
      levelID += mode.hashCode();
      level = "level" + levelID + ".dat";
    }
    if (universalMode) levelID = Helper.filesCount("data/levels/") + 1;

    systemOut.print("Введите название уровня: ");
    String name = scanner.nextLine();

    ArrayList<String> cutscene = new ArrayList<>();
    systemOut.println("Введите катсцену, которая будет перед уровнем:");
    systemOut.println("(введите пустую строку, чтобы закончить катсцену)");
    String input = "";
    int j = 1;
    do {
      cutscene.add(input);
      systemOut.print("(" + j + ") ");
      input = scanner.nextLine();
      j++;
    } while (!input.isEmpty());
    cutscene.remove(0);

    HashMap<String, Function> originalFunctions = new HashMap<>();

    systemOut.print("Введите область определения уровня: ");
    String resultClassName = scanner.nextLine();

    systemOut.print("Введите числа в наборе: ");
    ArrayList<String> numbers = Helper.wordsFromString(scanner.nextLine());
    ArrayList<MathObject> originalNumbers = new ArrayList<>();
    for (String number : numbers) {
      originalNumbers.add(MathObject.parseMathObject(number, resultClassName));
    }

    systemOut.print("Введите ID функций через пробел: ");
    ArrayList<String> ids = Helper.wordsFromString(scanner.nextLine());

    for (String id : ids) {
      Function f = new Function(resultClassName, id);
      if (originalFunctions.containsKey(f.getName())) {
        systemOut.println("Названия функций не должны повторяться: " + f.getName());
        return true;
      }
      originalFunctions.put(f.getName(), f);
    }

    systemOut.print("Введите ответ (необходимое(ые) число(а)): ");
    ArrayList<String> ansnumbers = Helper.wordsFromString(scanner.nextLine());
    ArrayList<MathObject> answers = new ArrayList<>();
    for (String ansnum : ansnumbers) {
      answers.add(MathObject.parseMathObject(ansnum, resultClassName));
    }

    ArrayList<String> hints = new ArrayList<>();
    systemOut.print("Введите кол-во подсказок: ");
    int n = Integer.parseInt(scanner.nextLine());
    for (int i = 1; i <= n; i++) {
      systemOut.print("Введите подсказку №" + i + ": ");
      hints.add(scanner.nextLine());
    }

    DLevelRules rules = new DLevelRules();
    rules.setInfinityNumbers(false);
    rules.setInfinityFunctions(false);
    rules.setAllowBack(true);
    rules.setAllowCalc(true);
    if (resultClassName.equals("unknown")) {
      rules.setAllowBack(false);
      rules.setAllowCalc(false);
      rules.setInfinityNumbers(true);
    }

    DLevelInfo levelInfo =
        new DLevelInfo(
            originalNumbers,
            originalFunctions,
            hints,
            new Cutscene(cutscene),
            answers,
            resultClassName,
            name,
            universalMode,
            levelID,
            customFlag,
            rules);

    if (!universalMode) {
      systemOut.println(
          "А теперь вам придётся проходить уровень, чтобы он попал в список пользовательских.");
      systemOut.print("Будете проходить? y/n ");
      String answer = scanner.nextLine();
      if (answer.equals("y") || answer.equals("у")) {
        Level l = Level.getLevelInstance(levelID, LevelPlayFlag.PRE);
        int[] result = l.consoleRun(System.in, systemOut);
        if (result[1] == 1) {
          levelInfo.setCompleted(true);
          Log.getInstance().write("The level is confirmed!");
        }
      }
    }

    return false;
  }

  public static void main(String[] args) {
    Log.getInstance().write("Someone has launched a program from LevelMaker");
    make(false, false);
  }

  // в будущем реализовать LevelGenerator
}
