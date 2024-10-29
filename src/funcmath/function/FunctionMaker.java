package funcmath.function;

import funcmath.Helper;
import funcmath.object.*;

import java.util.ArrayList;
import java.util.HashMap;
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
        MathObject resultClass = switch (resultClassName) {
            case "integer" -> FInteger.getInstance();
            case "natural" -> FNatural.getInstance();
            case "rational" -> FRational.getInstance();
            default -> throw new IllegalArgumentException();
        };

        Function function = new Function(name, definition, resultClass, description);
        HashMap<Integer, Function> functions;
        try {
            functions = (HashMap<Integer, Function>) Helper.read("data\\functions.dat");
        } catch (RuntimeException e) {
            functions = new HashMap<>();
        }
        functions.put(function.hashCode(), function);
        Helper.write(function, "data\\functions.dat");
        System.out.println("Ваша функция теперь имеет ID: " + function.hashCode());
    }
}
