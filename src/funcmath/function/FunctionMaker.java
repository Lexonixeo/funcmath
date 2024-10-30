package funcmath.function;

import funcmath.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FunctionMaker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название функции: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание функции: ");
        String description = scanner.nextLine();
        System.out.print("Введите определение функции, используя простейшие: ");
        ArrayList<String> definition = Helper.wordsFromString(scanner.nextLine());
        System.out.print("Введите область опредения функции: ");
        String resultClassName = scanner.nextLine();

        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
        ArrayList<String> aValues = new ArrayList<>(List.of(values));
        for (int i = 0; i < definition.size(); i++) {
            if (aValues.contains(definition.get(i))) {
                definition.set(i, "x" + (definition.get(i).charAt(0) - 'a'));
            }
        }

        Function function = new Function(name, definition, resultClassName, description);
        HashMap<Integer, Function> functions;
        try {
            functions = (HashMap<Integer, Function>) Helper.read("data\\functions.dat");
        } catch (RuntimeException e) {
            functions = new HashMap<>();
        }
        functions.put(function.hashCode(), function);
        Helper.write(functions, "data\\functions.dat");
        System.out.println("Ваша функция теперь имеет ID: " + function.hashCode());
    }
}
