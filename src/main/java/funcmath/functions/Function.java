package funcmath.functions;

import funcmath.exceptions.FunctionException;
import funcmath.object.MathObject;
import funcmath.object.TypeRegister;
import funcmath.utility.Hash;
import funcmath.utility.Helper;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Function implements Serializable {
  @Serial private static final long serialVersionUID = 1779220397367729803L;

  private String name;
  private final String rawName;
  private final String[] types;
  // а зачем я добавил resultType если возвращается MathObject[]???
  // String resultType; - при чтении выражения будет тяжело угадывать необходимый тип, так что
  // приходится комментировать

  // блин, а как я при чтении выражения буду угадывать какая simple function используется???
  // перебираем все возможные варианты конвертаций в типы и пытаемся найти sf с этими типами?
  // перебираем все существующие sf с этим именем и пытаемся подогнать?
  // кажется лучше по хешам, нежели чем по info-tripletам
  // в гуи можно сделать выглядящим ок, в консоли нет
  // ладно, будем по хешам

  private final String description; // latex должен поддерживаться

  private final String[] definition;
  private int remainingUses;

  private boolean validate = false;

  public Function(
      String name, String[] types, String[] definition, String description, int remainingUses) {
    this.name = name;
    this.rawName = name;
    this.types = types;
    this.definition = definition;
    this.description = description;
    this.remainingUses = remainingUses;

    // так стоп, а как я буду из definition понимать, какого типа mathobject???
    // можно сделать типа integer=42
    // т.е. (type)=(string that going to constructor)

    // при создании функции надо проводить validate()
  }

  public Function(SimpleFunction f) {
    this.name = f.getName();
    this.rawName = name;
    this.types = f.getTypes();
    this.definition = new String[1 + types.length];
    definition[0] = f.getHash().toString();
    for (int i = 1; i < 1 + types.length; i++) {
      definition[i] = "x" + (i - 1);
    }
    this.description = ""; // TO DO: определить описание
    this.remainingUses = -1; // default

    validate();
  }

  private boolean validateArgsNumber() {
    int maxArgNumber = -1;
    for (String word : definition) {
      if (word.charAt(0) == 'x' && Character.isDigit(word.charAt(1))) {
        maxArgNumber = Math.max(maxArgNumber, Integer.parseInt(word.substring(1)));
      }
    }
    return maxArgNumber + 1 <= getArgsNumber();
  }

  private boolean validateDefinition() {
    // аналогичная ситуация с validateTypes
    return true;
  }

  private boolean validateTypes() {
    HashSet<String> sfNames = SimpleFunctionRegister.getSfStringKeys();

    Stack<SimpleFunction> sfStack = new Stack<>();
    Stack<ArrayList<String>> numsTypes = new Stack<>();
    numsTypes.push(new ArrayList<>()); // конечный ответ

    LinkedList<String> temp = new LinkedList<>();
    for (String word : definition) {
      if (sfNames.contains(word)) {
        sfStack.push(SimpleFunctionRegister.getSimpleFunction(word));
        numsTypes.push(new ArrayList<>());
      } else {
        if (word.charAt(0) == 'x'
            && Character.isDigit(word.charAt(1))) { // имена начинающиеся на x{цифра} запрещены
          int j = Integer.parseInt(word.substring(1));
          temp.add(types[j]);
        } else {
          temp.add(TypeRegister.parseMathObject(word).getType());
        }
      }
      // чистим temp
      while (!temp.isEmpty()) {
        numsTypes.peek().add(temp.pop());
        while (!sfStack.isEmpty() && numsTypes.peek().size() == sfStack.peek().getArgsNumber()) {
          if (!TypeRegister.isConvertibleTypes(
              numsTypes.peek().toArray(new String[0]), sfStack.peek().getTypes())) {
            return false;
          }
          sfStack.pop();
          numsTypes.pop();
          // temp.addAll(List.of(ans)); // TO DO: что добавлять? нету returnTypes[] у
          // simplefunction, да и на вряд ли будет :(
        }
      }
    }
    return true; // сейчас сделана проверка не на все конверты, а лишь на самые первые в плане
    // исполнения
  }

  public void validate() {
    // проверка корректности функции, а именно:
    // проверка на то, что в типах столько же аргументов, сколько и в аргументах внутри определения
    // проверка на то, что определение функции верно
    // проверка на то, верно ли расставлены типы (не возникнет ли ошибок с конвертацией)
    if (!validateArgsNumber()) {
      throw new FunctionException(
          "Кол-во аргументов в определении функции не совпадает с числом типов для аргументов");
    }
    /*
    if (!validateDefinition()) {
      throw new FunctionException("Определение функции построено неверно.");
    }
    if (!validateTypes()) {
      throw new FunctionException("Типы аргументов не конвертируются при выполнении простейших функций из определения.");
    }
     */
    validate = true;
  }

  public int getArgsNumber() {
    return types.length;
  }

  public ArrayList<MathObject> use(boolean isInfinityFunctions, MathObject... x) {
    if (!isInfinityFunctions && this.remainingUses == 0) {
      throw new FunctionException("У функции " + name + " закончилось число использований.");
    }
    ArrayList<MathObject> ans = compute(x);
    if (!isInfinityFunctions) {
      this.remainingUses--;
    }
    return ans;
  }

  public ArrayList<MathObject> compute(MathObject... xx) {
    MathObject[] x = retype(xx);

    HashSet<String> sfNames = SimpleFunctionRegister.getSfStringKeys();

    Stack<SimpleFunction> sfStack = new Stack<>();
    Stack<ArrayList<MathObject>> nums = new Stack<>();
    nums.push(new ArrayList<>()); // конечный ответ

    LinkedList<MathObject> temp = new LinkedList<>();
    for (String word : definition) {
      if (sfNames.contains(word)) {
        sfStack.push(SimpleFunctionRegister.getSimpleFunction(word));
        nums.push(new ArrayList<>());
      } else {
        if (word.charAt(0) == 'x'
            && Character.isDigit(word.charAt(1))) { // имена начинающиеся на x{цифра} запрещены
          int j = Integer.parseInt(word.substring(1));
          temp.add(x[j]);
        } else {
          temp.add(TypeRegister.parseMathObject(word));
        }
      }
      // чистим temp
      while (!temp.isEmpty()) {
        nums.peek().add(temp.pop());
        while (!sfStack.isEmpty() && nums.peek().size() == sfStack.peek().getArgsNumber()) {
          MathObject[] ans = sfStack.peek().use(nums.peek().toArray(new MathObject[0]));
          sfStack.pop();
          nums.pop();
          temp.addAll(List.of(ans));
        }
      }
    }
    return nums.peek();
  }

  private MathObject[] retype(MathObject... args) {
    if (args.length != getArgsNumber()) {
      throw new FunctionException(
          "Число аргументов функции не совпадает: должно быть "
              + getArgsNumber()
              + ", но есть: "
              + args.length);
    }

    MathObject[] newArgs = new MathObject[args.length];
    for (int i = 0; i < args.length; i++) {
      MathObject arg = args[i];
      if (!arg.getType().equals(types[i])) {
        arg = TypeRegister.convert(arg, types[i]);
      }
      newArgs[i] = arg;
    }
    return newArgs;
  }

  public void save() {
    if (!validate) {
      throw new FunctionException("Нельзя сохранять непроверенную функцию!");
    }
    String functionHash = String.valueOf(Long.valueOf(this.getHash().toLong()).byteValue());
    String functionFileName = name + "_" + functionHash + ".dat";
    Helper.write(this, "data/functions/" + functionFileName);
  }

  public void generateName(HashSet<String> usingNames) {
    int i = 0;
    while (usingNames.contains(getName())) {
      this.name = rawName + i;
      i++;
    }
  }

  public void setRemainingUses(int remainingUses) {
    this.remainingUses = remainingUses;
  }

  public String getName() {
    return name;
  }

  public int getRemainingUses() {
    return remainingUses;
  }

  public String[] getTypes() {
    return types;
  }

  public String getDescription() {
    return description;
  }

  public String[] getDefinition() {
    return definition;
  }

  public boolean isValidate() {
    return validate;
  }

  public Hash getHash() {
    return Hash.encode(name, types, definition);
  }

  @Override
  public String toString() {
    String[] values = {
      "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
      "t", "u", "v", "w", "x", "y", "z"
    };
    ArrayList<String> aValues = new ArrayList<>(List.of(values).subList(0, getArgsNumber()));
    for (int i = 0; i < aValues.size(); i++) {
      aValues.set(i, types[i] + " " + aValues.get(i));
    }
    return "("
        + (remainingUses < 0 ? "inf" : remainingUses)
        + ") "
        + name
        + "("
        + String.join(", ", aValues)
        + ") = "
        + description;
  }
}
