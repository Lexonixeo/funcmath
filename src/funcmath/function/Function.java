package funcmath.function;

import funcmath.Helper;
import funcmath.object.*;

import java.io.Serializable;
import java.util.*;

public class Function implements Serializable {
    String name;
    String description;
    ArrayList<String> definition; // должен содержать x0, x1, x2...
    int numberOfArgs;
    String resultClassName;

    // хранить функции в hashmap?

    public Function(int hashCode) {
        HashMap<Integer, Function> functions;
        try {
            functions = (HashMap<Integer, Function>) Helper.read("data\\functions.dat");
        } catch (RuntimeException e) {
            functions = new HashMap<>();
        }
        if (!functions.containsKey(hashCode)) throw new NullPointerException("В functions.dat отсуствует функция: " + hashCode);
        Function f = functions.get(hashCode);
        this.name = f.name;
        this.description = f.description;
        this.definition = f.definition;
        this.numberOfArgs = f.numberOfArgs;
        this.resultClassName = f.resultClassName;
    }

    public Function(int hashCode, String name) {
        HashMap<Integer, Function> functions;
        try {
            functions = (HashMap<Integer, Function>) Helper.read("data\\functions.dat");
        } catch (RuntimeException e) {
            functions = new HashMap<>();
        }
        if (!functions.containsKey(hashCode)) throw new NullPointerException("В functions.dat отсуствует функция: " + hashCode);
        Function f = functions.get(hashCode);
        this.name = name;
        this.definition = f.definition;
        this.numberOfArgs = f.numberOfArgs;
        this.resultClassName = f.resultClassName;

        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("(");
        for (int i = 0; i < numberOfArgs; i++) {
            sb.append(values[i]);
            if (i != numberOfArgs - 1) {
                sb.append(", ");
            }
        }
        sb.append(") = неизвестная функция");
        this.description = sb.toString();
    }

    public Function(int hashCode, String name, String description) {
        HashMap<Integer, Function> functions;
        try {
            functions = (HashMap<Integer, Function>) Helper.read("data\\functions.dat");
        } catch (RuntimeException e) {
            functions = new HashMap<>();
        }
        if (!functions.containsKey(hashCode)) throw new NullPointerException("В functions.dat отсуствует функция: " + hashCode);
        Function f = functions.get(hashCode);
        this.name = name;
        this.description = description;
        this.definition = f.definition;
        this.numberOfArgs = f.numberOfArgs;
        this.resultClassName = f.resultClassName;
    }

    /*
    public Function(String name) {
        this.name = name;
        // this.description = ...
        // this.definition = ...

        this.numberOfArgs = 0;
        ArrayList<String> sfNames = SimpleFunctions.names;
        for (String word : definition) {
            if (!sfNames.contains(word)) {
                this.numberOfArgs = Math.max(this.numberOfArgs, Integer.parseInt(word.substring(1)) + 1);
            }
        }
    }
    */

    public Function(String name, ArrayList<String> definition, String resultClassName) {
        this.name = name;
        this.definition = definition;
        this.resultClassName = resultClassName;

        this.numberOfArgs = 0;
        ArrayList<String> sfNames = SimpleFunctions.names;
        for (String word : definition) {
            if (!sfNames.contains(word) && word.charAt(0) == 'x') {
                this.numberOfArgs = Math.max(this.numberOfArgs, Integer.parseInt(word.substring(1)) + 1);
            }
        }

        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("(");
        for (int i = 0; i < numberOfArgs; i++) {
            sb.append(values[i]);
            if (i != numberOfArgs - 1) {
                sb.append(", ");
            }
        }
        sb.append(") = неизвестная функция");
        this.description = sb.toString();
    }

    public Function(String name, ArrayList<String> definition, String resultClassName, String description) {
        this.name = name;
        this.description = description;
        this.definition = definition;
        this.resultClassName = resultClassName;

        this.numberOfArgs = 0;
        ArrayList<String> sfNames = SimpleFunctions.names;
        for (String word : definition) {
            if (!sfNames.contains(word) && word.charAt(0) == 'x') {
                this.numberOfArgs = Math.max(this.numberOfArgs, Integer.parseInt(word.substring(1)) + 1);
            }
        }
    }

    public int getNumberOfArgs() {
        return numberOfArgs;
    }

    public ArrayList<MathObject> use(MathObject... x) {
        if (x.length != numberOfArgs) throw new IllegalArgumentException("Не совпадает число аргументов функции: должно быть " + numberOfArgs + ", есть: " + x.length);

        MathObject resultClass = switch (resultClassName) {
            case "integer" -> new FInteger(0);
            case "natural" -> new FNatural(0);
            case "rational" -> new FRational(0, 1);
            case "real" -> new FReal(0);
            case "complex" -> new FComplex(0, 0);
            default -> throw new IllegalArgumentException();
        };
        ArrayList<String> sfNames = SimpleFunctions.names;
        Stack<SimpleFunction> sfStack = new Stack<>();
        Stack<ArrayList<MathObject>> nums = new Stack<>();
        nums.push(new ArrayList<>()); // конечный

        for (String word : definition) {
            // Считаем, что в определении переменные это x0, x1, ...
            if (sfNames.contains(word)) {
                sfStack.push(new SimpleFunction(word, resultClass));
                nums.push(new ArrayList<>());
            } else if (word.charAt(0) == 'x') {
                int j = Integer.parseInt(word.substring(1));
                nums.peek().add(x[j]);
                while (!sfStack.isEmpty() && nums.peek().size() == sfStack.peek().getNumberOfArgs()) {
                    MathObject[] args = new MathObject[nums.peek().size()];
                    Object ans = sfStack.peek().use(nums.peek().toArray(args));
                    sfStack.pop();
                    nums.pop();
                    try {
                        nums.peek().add((MathObject) ans);
                    } catch (RuntimeException e) {
                        nums.peek().addAll(List.of((MathObject[]) ans));
                    }
                }
            } else {
                nums.peek().add(MathObject.parseMathObject(word, resultClassName));
                while (!sfStack.isEmpty() && nums.peek().size() == sfStack.peek().getNumberOfArgs()) {
                    MathObject[] args = new MathObject[nums.peek().size()];
                    Object ans = sfStack.peek().use(nums.peek().toArray(args));
                    sfStack.pop();
                    nums.pop();
                    try {
                        nums.peek().add((MathObject) ans);
                    } catch (RuntimeException e) {
                        // Считаем, что функция вида list будет только в начале
                        nums.peek().addAll(List.of((MathObject[]) ans));
                    }
                }
            }
        }

        return nums.peek();
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return Objects.equals(name, function.name) && Objects.equals(description, function.description) && Objects.equals(definition, function.definition) && Objects.equals(resultClassName, function.resultClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, definition, resultClassName);
    }
}
