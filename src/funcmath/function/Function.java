package funcmath.function;

import funcmath.Helper;
import funcmath.object.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Function implements Serializable {
    @Serial
    private static final long serialVersionUID = -5266414674402354315L;

    String name;
    String description;
    ArrayList<String> definition;
    int numberOfArgs;
    int uses;
    String resultClassName;

    public Function(int hashCode) {
        HashMap<Integer, Function> functions;
        try {
            functions = (HashMap<Integer, Function>) Helper.read("data\\functions.dat");
        } catch (RuntimeException e) {
            functions = new HashMap<>();
        }
        if (!functions.containsKey(hashCode)) {
            throw new NullPointerException("В functions.dat отсуствует функция: " + hashCode);
        }
        Function f = functions.get(hashCode);
        this.name = f.name;
        this.description = f.description;
        this.definition = f.definition;
        this.numberOfArgs = f.numberOfArgs;
        this.resultClassName = f.resultClassName;
        this.uses = f.uses;
    }

    /*
    здесь будет простейшая функция
    public Function(String name) {
        this.name = name;
        this.description = ...
        this.definition = ...
        this.uses = -1;

        this.numberOfArgs = 0;
        ArrayList<String> sfNames = SimpleFunctions.names;
        for (String word : definition) {
            if (!sfNames.contains(word)) {
                this.numberOfArgs = Math.max(this.numberOfArgs, Integer.parseInt(word.substring(1)) + 1);
            }
        }
    }
    */

    public Function(String name, ArrayList<String> definition, String resultClassName, String description, int uses) {
        this.name = name;
        this.definition = definition;
        this.resultClassName = resultClassName;
        this.description = description;
        this.uses = uses;

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

    public String getName() {
        return name;
    }

    public String getResultClassName() {
        return resultClassName;
    }

    public ArrayList<MathObject> use(int mode, MathObject... x) {
        if (mode == 0 && this.uses == 0) {
            throw new RuntimeException("У функции закончилось число использований.");
        }
        if (x.length != numberOfArgs) {
            throw new IllegalArgumentException(
                    "Не совпадает число аргументов функции: должно быть " + numberOfArgs + ", есть: " + x.length
            );
        }

        MathObject resultClass = switch (resultClassName) {
            case "integer" -> new FInteger(0);
            case "natural" -> new FNatural(0);
            case "rational" -> new FRational(0, 1);
            case "real" -> new FReal(0);
            case "complex" -> new FComplex(0, 0);
            default -> throw new IllegalArgumentException("Не существует такой области определения");
        };
        ArrayList<String> sfNames = SimpleFunctions.names;
        Stack<SimpleFunction> sfStack = new Stack<>();
        Stack<ArrayList<MathObject>> nums = new Stack<>();
        nums.push(new ArrayList<>()); // конечный

        for (String word : definition) {
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
                        nums.peek().addAll(List.of((MathObject[]) ans));
                    }
                }
            }
        }

        if (mode == 0) this.uses--;
        return nums.peek();
    }

    @Override
    public String toString() {
        return "(" + (this.uses < 0 ? "inf" : this.uses) + ") " + this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return numberOfArgs == function.numberOfArgs
                && uses == function.uses
                && Objects.equals(name, function.name)
                && Objects.equals(description, function.description)
                && Objects.equals(definition, function.definition)
                && Objects.equals(resultClassName, function.resultClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, definition, numberOfArgs, uses, resultClassName);
    }
}
