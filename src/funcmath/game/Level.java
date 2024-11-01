package funcmath.game;

import funcmath.Helper;
import funcmath.function.Function;
import funcmath.object.*;

import java.util.*;

public class Level {
    ArrayList<MathObject> numbers;
    ArrayList<MathObject> originalNumbers;
    HashMap<String, Function> originalFunctions;
    HashMap<String, Function> functions;
    Stack<ArrayList<MathObject>> numbersStack = new Stack<>();
    Stack<HashMap<String, Function>> functionsStack = new Stack<>();
    ArrayList<String> hints;
    ArrayList<String> cutscene;
    MathObject ans;
    int level;
    int hint = 0;
    // int difficulty;
    boolean tutorial;
    String resultClassName;
    boolean completed = false;

    public Level(int level, boolean tutorial, int customFlag) {
        String levelSwitch = switch (customFlag) {
            case 0 -> "l";
            case 1 -> "customL";
            case 2 -> "preL";
            default -> throw new IllegalArgumentException("Такого флага нет: " + customFlag);
        };

        this.tutorial = tutorial;
        this.level = level;
        ArrayList<Object> generated = (ArrayList<Object>) Helper.read("data\\" + levelSwitch + "evels\\level" + level + ".dat");

        originalNumbers = (ArrayList<MathObject>) generated.get(0);
        numbers = Helper.deepClone(originalNumbers);
        numbersStack.push(Helper.deepClone(numbers));

        originalFunctions = (HashMap<String, Function>) generated.get(1);
        functions = Helper.deepClone(originalFunctions);
        functionsStack.push(Helper.deepClone(functions));

        ans = (MathObject) generated.get(2);
        hints = (ArrayList<String>) generated.get(3);
        resultClassName = (String) generated.get(4);
        cutscene = (ArrayList<String>) generated.get(5);
    }

    private void playCutscene() {
        Scanner scanner = new Scanner(System.in);
        Helper.clear();
        for (String phrase : cutscene) {
            System.out.print(phrase + " ");
            scanner.nextLine();
        }
        System.out.println("\n\nНажмите Enter, чтобы продолжить...");
        scanner.nextLine();
    }

    private void winCheck(MathObject newNumber) {
        completed |= ans.equals(newNumber);
    }

    private boolean numsCheck(ArrayList<MathObject> args) {
        ArrayList<MathObject> nums = Helper.deepClone(numbers);
        for (MathObject arg : args) {
            if (nums.contains(arg)) nums.remove(arg);
            else return false;
        }
        return true;
    }

    private void tutorial() {
        System.out.println("Используя функцию над двумя числами, вы забираете эти два числа из набора и в набор добавляется результат функции.");
        System.out.println("Запись выражения происходит так:   func a b    / это выполнение функции func над параметрами a и b.");
        System.out.println("Пример одного из выражений:        sum 1 3     / удалит из набора числа 1 и 3 и добавит в него 4.");
        System.out.println("Обратите внимание: скобки и запятые в выражении должны отсутствовать.\n");

        System.out.println("Введите help, чтобы узнать полный список команд.");
        System.out.println("Введите clear, чтобы очистить экран от лишних выражений.");
        System.out.println("Введите nums, чтобы узнать текущий набор чисел.");
        System.out.println("Введите reset, чтобы перезагрузить уровень.");
        System.out.println("Введите exit, чтобы выйти из уровня.\n");

        tutorial = false;
    }

    private void help() {
        System.out.println("Используя функцию над двумя числами, вы забираете эти два числа из набора и в набор добавляется результат функции.");
        System.out.println("Запись выражения происходит так:   func a b    / это выполнение функции func над параметрами a и b.");
        System.out.println("Пример одного из выражений:        sum 1 3     / удалит из набора числа 1 и 3 и добавит в него 4.");
        System.out.println("Обратите внимание: скобки и запятые в выражении должны отсутствовать.\n");

        System.out.println("Введите help, чтобы узнать полный список команд.");
        System.out.println("Введите clear, чтобы очистить экран от лишних выражений.");
        System.out.println("Введите nums, чтобы узнать текущий набор чисел.");
        System.out.println("Введите hint, чтобы получить подсказку.");
        System.out.println("Введите calc {выражение}, чтобы посчитать выражение.");
        System.out.println("Введите back, чтобы откатить предыдущее выражение.");
        System.out.println("Введите reset, чтобы перезагрузить уровень.");
        System.out.println("Введите exit, чтобы выйти из уровня.\n");

        tutorial = true;
    }

    private void start() {
        Helper.clear();
        System.out.println("Уровень " + level + ".");

        String mathObjectType = switch (resultClassName) {
            case "integer" -> "целых";
            case "natural" -> "натуральных";
            case "rational" -> "рациональных";
            case "real" -> "действительных";
            case "complex" -> "комплексных";
            default -> throw new IllegalArgumentException("Не существует такого числа");
        };
        System.out.println("В данном уровне все вычисления происходят в " + mathObjectType + " числах.\n");

        System.out.println("У вас есть ограниченный набор чисел:");
        for (MathObject number : numbers) {
            System.out.print(number + " ");
        }

        System.out.println("\n\nУ вас есть функции:");
        for (Function function : functions.values()) {
            System.out.println(function);
        }

        System.out.println("\nВам нужно из данных чисел, используя данные функции, получить число " + ans + ".\n");
        if (tutorial) this.tutorial();
    }

    private void restart() {
        numbers = Helper.deepClone(originalNumbers);
        numbersStack.clear();
        numbersStack.push(Helper.deepClone(numbers));

        functions = Helper.deepClone(originalFunctions);
        functionsStack.clear();
        functionsStack.push(Helper.deepClone(functions));

        Helper.clear();
        this.start();
    }

    private void nums() {
        System.out.print("=");
        for (MathObject number : numbers) {
            System.out.print(" " + number);
        }
        System.out.println();
    }

    private void computation(int mode, ArrayList<String> expression) {
        ArrayList<String> fNames = new ArrayList<>(functions.keySet());
        Stack<Function> fStack = new Stack<>();
        Stack<ArrayList<MathObject>> nums = new Stack<>();
        nums.push(new ArrayList<>()); // конечный

        // в будущем: при mode = 1 добавить simple functions

        ArrayList<MathObject> globalArgs = new ArrayList<>();
        for (String word : expression) {
            if (!fNames.contains(word)) {
                try {
                    globalArgs.add(MathObject.parseMathObject(word, resultClassName));
                } catch (RuntimeException e) {
                    System.out.println("= Неверные аргументы: не существует какой-то функции или числа! Введите выражение заново.");
                    return;
                }
            }
        }
        if (!numsCheck(globalArgs) && mode == 0) {
            System.out.println("= Неверные аргументы: какие-то числа не существуют в наборе! Введите выражение заново.");
            return;
        }
        // избыток аргументов - ну и ладно, они всё равно вернутся

        int i = 0;
        LinkedList<MathObject> timed = new LinkedList<>();
        for (String word : expression) {
            if (fNames.contains(word)) {
                fStack.push(functions.get(word));
                nums.push(new ArrayList<>());
            } else {
                timed.add(globalArgs.get(i));
                i++;
                while (!timed.isEmpty()) {
                    nums.peek().add(timed.pop());
                    while (!fStack.isEmpty() && nums.peek().size() >= fStack.peek().getNumberOfArgs()) {
                        MathObject[] args = new MathObject[nums.peek().size()];
                        ArrayList<MathObject> ans;
                        try {
                            ans = fStack.peek().use(mode, nums.peek().toArray(args));
                        } catch (RuntimeException e) {
                            System.out.println("= У функции " + fStack.peek().getName() + " закончилось число использований");
                            return;
                        }
                        fStack.pop();
                        nums.pop();

                        timed.addAll(ans);
                    }
                }
            }
        }
        if (!fStack.isEmpty()) {
            System.out.println("= Недостаточно аргументов! Введите выражение заново.");
            return;
        }

        if (mode == 0) {
            for (MathObject arg : globalArgs) {
                numbers.remove(arg);
            }
        }
        System.out.print("=");
        for (MathObject answerNum : nums.peek()) {
            if (mode == 0) {
                numbers.add(answerNum);
                winCheck(answerNum);
            }
            System.out.print(" " + answerNum);
        }
        System.out.println();

        if (mode == 0) {
            numbersStack.push(Helper.deepClone(numbers));
            functionsStack.push(Helper.deepClone(functions));
        }
    }

    private void hint() {
        hint++;
        if (hint >= hints.size()) {
            hint = hints.size();
            for (int i = 0; i < hint; i++) {
                System.out.println("Подсказка №"+ (i + 1) + ": " + hints.get(i));
            }
        } else {
            System.out.println("Подсказка №" + hint + ": " + hints.get(hint));
        }
    }

    private void back() {
        numbersStack.pop();
        functionsStack.pop();
        if (numbersStack.empty()) {
            System.out.println("= Нет хода назад!");
            numbersStack.push(Helper.deepClone(numbers));
            functionsStack.push(Helper.deepClone(functions));
            return;
        }
        numbers = Helper.deepClone(numbersStack.peek());
        functions = Helper.deepClone(functionsStack.peek());
        nums();
    }

    private int turn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("[Level] Введите выражение: ");
        ArrayList<String> command = Helper.wordsFromString(scanner.nextLine());
        String first_command = command.get(0);
        switch (first_command) {
            case "exit" -> {
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
            case "calc" -> computation(1, new ArrayList<>(command.subList(1, command.size())));
            case "back" -> back();
            default -> computation(0, command);
        }
        return 0;
    }

    public boolean[] game() {
        playCutscene();
        start();
        while (!completed) {
            int check = turn();
            if (check == -1) break;
        }
        if (completed) {
            System.out.println("Поздравляем! Вы получили число " + ans + " и прошли уровень!");
        }
        return new boolean[]{tutorial, completed};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return Objects.equals(originalNumbers, level.originalNumbers) && Objects.equals(originalFunctions, level.originalFunctions) && Objects.equals(hints, level.hints) && Objects.equals(ans, level.ans) && Objects.equals(resultClassName, level.resultClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalNumbers, originalFunctions, hints, ans, resultClassName);
    }

    public static void main(String[] args) {
        Level l = new Level(1, true, 0);
        boolean[] result = l.game();
    }
}
