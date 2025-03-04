package funcmath.defaultpack.level;

import funcmath.cutscene.Cutscene;
import funcmath.functions.Function;
import funcmath.gui.swing.GConsolePanel;
import funcmath.level.PlayFlag;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import java.util.*;

public class DLMakerPanel extends GConsolePanel {
  DLInfo li;
  DLState ls;
  DefaultLevel level;

  boolean interrupted = false;

  @Override
  protected void initBeforeComponents() {
    super.initBeforeComponents();
    ls = new DLState();
    li = new DLInfo();
    li.setDefaultLevelState(ls);
    level = new DefaultLevel(li);
  }

  @Override
  protected void initComponents() {
    super.initComponents();

    nameLabel.setText("Создатель уровней");
  }

  @Override
  protected void run() {
    int res = 0;
    out.clear();
    out.println("Добро пожаловать в мастерскую создания уровней!");
    do {
      if (res == 0) {
        // funcInfo();
      }
      // res = turn();
    } while (res != -1 && !interrupted);
  }

  @Override
  protected void runInterrupt() {
    interrupted = true;
  }

  private void updateLevelInfo(DLInfo li) {
    this.li = li;
    this.ls = new DLState(this.ls.getHistory(), this.ls.getNumbers(), this.ls.getFunctions(), li.getPlayFlag(), li.getID());
    this.li.setDefaultLevelState(this.ls);
    this.level = new DefaultLevel(li);
  }

  private void updateLevelState(DLState ls) {
    this.ls = new DLState(ls.getHistory(), ls.getNumbers(), ls.getFunctions(), li.getPlayFlag(), li.getID());
    this.li.setDefaultLevelState(this.ls);
    this.level = new DefaultLevel(li);
  }

  private void setLNumbers(ArrayList<MathObject> numbers) {
    updateLevelState(new DLState(ls.getHistory(), numbers, ls.getFunctions(), li.getPlayFlag(), li.getID()));
  }

  private void setLFunctions(HashMap<String, Function> functions) {
    updateLevelState(new DLState(ls.getHistory(), ls.getNumbers(), functions, li.getPlayFlag(), li.getID()));
  }

  private void setLRules(DLRules rules) {
    updateLevelInfo(
        new DLInfo(
            ls,
            rules,
            li.getPlayFlag(),
            li.getName(),
            li.getBeforeCutscene(),
            li.getAfterCutscene(),
            li.getID(),
            li.getHints(),
            li.getAns()));
  }

  private void setLPlayFlag(PlayFlag pf) {
    updateLevelInfo(
        new DLInfo(
            ls,
            li.getRules(),
            pf,
            li.getName(),
            li.getBeforeCutscene(),
            li.getAfterCutscene(),
            li.getID(),
            li.getHints(),
            li.getAns()));
  }

  private void setLName(String name) {
    updateLevelInfo(
        new DLInfo(
            ls,
            li.getRules(),
            li.getPlayFlag(),
            name,
            li.getBeforeCutscene(),
            li.getAfterCutscene(),
            li.getID(),
            li.getHints(),
            li.getAns()));
  }

  private void setLBeforeCutscene(Cutscene cutscene) {
    updateLevelInfo(
        new DLInfo(
            ls,
            li.getRules(),
            li.getPlayFlag(),
            li.getName(),
            cutscene,
            li.getAfterCutscene(),
            li.getID(),
            li.getHints(),
            li.getAns()));
  }

  private void setLAfterCutscene(Cutscene cutscene) {
    updateLevelInfo(
        new DLInfo(
            ls,
            li.getRules(),
            li.getPlayFlag(),
            li.getName(),
            li.getBeforeCutscene(),
            cutscene,
            li.getID(),
            li.getHints(),
            li.getAns()));
  }

  private void setLID(Long ID) {
    updateLevelInfo(
        new DLInfo(
            ls,
            li.getRules(),
            li.getPlayFlag(),
            li.getName(),
            li.getBeforeCutscene(),
            li.getAfterCutscene(),
            ID,
            li.getHints(),
            li.getAns()));
  }

  private void setLHints(ArrayList<String> hints) {
    updateLevelInfo(
        new DLInfo(
            ls,
            li.getRules(),
            li.getPlayFlag(),
            li.getName(),
            li.getBeforeCutscene(),
            li.getAfterCutscene(),
            li.getID(),
            hints,
            li.getAns()));
  }

  private void setLAnswer(ArrayList<MathObject> ans) {
    updateLevelInfo(
        new DLInfo(
            ls,
            li.getRules(),
            li.getPlayFlag(),
            li.getName(),
            li.getBeforeCutscene(),
            li.getAfterCutscene(),
            li.getID(),
            li.getHints(),
            ans));
  }

  private void visibleLevelInfo() {
    out.println(
        "У вас есть "
            + (((DLInfo) this.level.getLevelInfo()).getRules().infinityNumbers() ? "не" : "")
            + "ограниченный набор чисел:");
    for (MathObject number : ((DLState) this.level.getCurrentLevelState()).getNumbers()) {
      out.print(number + " ");
    }

    out.println("\n\nУ вас есть функции:");
    for (Function function :
        ((DLState) this.level.getCurrentLevelState()).getFunctions().values()) {
      out.println(function);
    }

    out.println(
        "\nВам нужно из данных чисел, используя данные функции, получить число(а) "
            + Helper.collectionToString(((DLInfo) this.level.getLevelInfo()).getAns())
            + ".\n");
  }

  /*

  private int turn() {
    synchronized (out) {
      // this.level.getCurrentLevelState().save();
      out.println();
      out.print("[Maker] Введите выражение: ");
    }
    Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
    String cmd = "";
    try {
      cmd = scanner.nextLine();
    } catch (RuntimeException e) {
      return -1;
    } catch (Exception ignored) {
    }
    synchronized (out) {
      try {
        ArrayList<String> command = Helper.wordsFromString(cmd);
        Logger.write("Ввели выражение: " + cmd);
        String first_command = command.getFirst();
        switch (first_command) {
          case "exit", "menu", "stop" -> {
            return -1;
          }
          case "set" -> {
            out.clear();
            String second_command = command.get(1);
            switch (second_command) {
              case "name" -> setFName(command.get(2));
              case "types" -> setFTypes(new ArrayList<>(command.subList(2, command.size())));
              case "def", "definition" ->
                  setFDefinition(new ArrayList<>(command.subList(2, command.size())));
              case "desc", "description" ->
                  setFDescription(String.join(" ", command.subList(2, command.size())));
              case "uses" -> setFUses(Integer.parseInt(command.get(2)));
              default -> {
                out.println("Неизвестная команда!");
                out.println();
                return 0;
              }
            }
          }
          case "list" -> {
            out.clear();
            sfList();
          }
          case "use" -> {
            use(new ArrayList<>(command.subList(1, command.size())));
            return 1; // funcInfo не надо
          }
          case "validate" -> {
            out.clear();
            fValidate();
          }
          case "save" -> {
            fSave();
            return 1;
          }
          case "tutorial" -> {
            out.clear();
            tutorial();
          }
          default -> {
            out.println("Неизвестная команда!");
            return 1;
          }
        }
      } catch (RuntimeException e) {
        out.clear();
        out.println(Helper.getLastGameExceptionMessage(e));
        out.println();
      }
    }
    return 0; // funcInfo надо
  }

  */
  /*
  private void sfList() {
    out.println("Список простейших функций: ");
    HashMap<String, SimpleFunction> SF_HASH_MAP = SimpleFunctionRegister.getSfHashMap();
    HashMap<SimpleFunction, String> HASH_SF_MAP = new HashMap<>();
    for (String key : SF_HASH_MAP.keySet()) {
      HASH_SF_MAP.put(SF_HASH_MAP.get(key), key);
      // out.println(key + ": " + SF_HASH_MAP.get(key));
    }
    ArrayList<SimpleFunction> sfList = new ArrayList<>(HASH_SF_MAP.keySet());
    sfList.sort(new Comparator<SimpleFunction>() {
      @Override
      public int compare(SimpleFunction o1, SimpleFunction o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    for (SimpleFunction sf : sfList) {
      out.println(HASH_SF_MAP.get(sf) + ": " + sf);
    }
    out.println();
  }

   */

  private void tutorial() {
    // TODO
  }

  /*
  private void setFName(String name) {
    f =
        new Function(
            name, f.getTypes(), f.getDefinition(), f.getDescription(), f.getRemainingUses());
  }

  private void setFTypes(ArrayList<String> typesList) {
    for (String type : typesList) {
      if (!TypeRegister.isTypeExists(type)) {
        throw new FunctionException("Не существует типа " + type);
      }
    }
    f =
        new Function(
            f.getName(),
            typesList.toArray(new String[0]),
            f.getDefinition(),
            f.getDescription(),
            f.getRemainingUses());
  }

  private void setFDefinition(ArrayList<String> definitionList) {
    String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
    ArrayList<String> aValues = new ArrayList<>(List.of(values));
    for (int i = 0; i < definitionList.size(); i++) {
      if (aValues.contains(definitionList.get(i))) {
        definitionList.set(i, "x" + (definitionList.get(i).charAt(0) - 'a'));
      }
    }
    f =
        new Function(
            f.getName(),
            f.getTypes(),
            definitionList.toArray(new String[0]),
            f.getDescription(),
            f.getRemainingUses());
  }

  private void setFDescription(String description) {
    f =
        new Function(
            f.getName(), f.getTypes(), f.getDefinition(), description, f.getRemainingUses());
  }

  private void setFUses(int uses) {
    f = new Function(f.getName(), f.getTypes(), f.getDefinition(), f.getDescription(), uses);
  }

  private void fValidate() {
    f.validate();
  }

  private void fSave() {
    f.save();
    String functionHash = f.getHash().toString();
    out.println("Ваша функция теперь имеет ID: " + f.getName() + "_" + functionHash);
  }

  // expression = func a b c ...
  private void use(ArrayList<String> expression) {
    if (!expression.getFirst().equals(f.getName())) {
      throw new MakerException(
          "В рамках создателя функций мы можем использовать только данную функцию и только один раз - в начале.");
    }
    ArrayList<MathObject> globalArgs = new ArrayList<>();
    for (String word : expression.subList(1, expression.size())) {
      globalArgs.add(TypeRegister.parseMathObject(word));
    }
    try {
      ArrayList<MathObject> ans = f.use(true, globalArgs.toArray(new MathObject[0]));
      out.print("=");
      for (MathObject number : ans) {
        out.print(" " + number);
      }
    } catch (RuntimeException e) {
      out.print("= ");
      out.println(Helper.getLastGameExceptionMessage(e));
    }
  }

  private void funcInfo() {
    out.println("Ваша функция:");
    out.println("Название: " + f.getName());
    out.println("Описание: " + f.getDescription());
    out.println("Типы аргументов: " + String.join(", ", f.getTypes()));
    out.println("Определение: " + String.join(", ", f.getDefinition()));
    out.println("Количество использований: " + f.getRemainingUses());
    out.println("Прошла автоматическую проверку: " + f.isValidate());
    out.println();
    out.println("Выглядит для пользователя как");
    out.println(f.toString());
    out.println();
  }

  private void make() {
    Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
    out.clear();

    sfList();

    out.println("Добро пожаловать в мастерскую создания функций!");
    */
  /*
  if (tutorial) {
    out.print("Хотите ли вы прочитать туториал? y/n ");
    String input = scanner.nextLine();
    if (input.equals("y") || input.equals("у")) {
      tutorial();
    }
  }
   */
  /*

    out.print("Введите название функции: ");
    String name = scanner.nextLine();
    out.print("Введите описание функции: ");
    String description = scanner.nextLine();
    out.print("Введите типы аргументов (через пробел), принимаемые функцией: ");
    ArrayList<String> typesList = Helper.wordsFromString(scanner.nextLine());
    for (String type : typesList) {
      if (!TypeRegister.isTypeExists(type)) {
        throw new FunctionException("Не существует типа " + type);
      }
    }
    String[] types = typesList.toArray(new String[0]);

    // вводить простейшие функции по их хэшам!
    // математические объекты как type=___, например natural=42
    out.print("Введите определение функции, используя простейшие: ");
    ArrayList<String> definitionList = Helper.wordsFromString(scanner.nextLine());

    out.print("Введите кол-во использований функции (-1 если бесконечно): ");
    int uses = scanner.nextInt();

    String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
    ArrayList<String> aValues = new ArrayList<>(List.of(values));
    for (int i = 0; i < definitionList.size(); i++) {
      if (aValues.contains(definitionList.get(i))) {
        definitionList.set(i, "x" + (definitionList.get(i).charAt(0) - 'a'));
      }
    }

    String[] definition = definitionList.toArray(new String[0]);

    Function function = new Function(name, types, definition, description, uses);

    String functionHash = function.getHash().toString();
    String functionFileName = name + functionHash + ".dat";
    Helper.write(function, "data/functions/" + functionFileName);

    out.println("Ваша функция теперь имеет ID: " + name + functionHash);
  }
  */
}
