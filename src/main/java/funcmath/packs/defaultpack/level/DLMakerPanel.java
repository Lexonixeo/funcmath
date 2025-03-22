package funcmath.packs.defaultpack.level;

import funcmath.cutscene.Cutscene;
import funcmath.functions.Function;
import funcmath.game.GameLoader;
import funcmath.game.Logger;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.GConsolePanel;
import funcmath.level.PlayFlag;
import funcmath.object.MathObject;
import funcmath.object.TypeRegister;
import funcmath.utility.Helper;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DLMakerPanel extends GConsolePanel {
  DLInfo li;
  DLState ls;
  DefaultLevel level;
  PlayFlag futurePlayFlag = PlayFlag.PRELEVELS;
  boolean isValidatedCompleted = false;

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
      lValidate();
      if (res == 0) {
        lvlInfo();
      }
      res = turn();
    } while (res != -1 && !Thread.currentThread().isInterrupted());
  }

  private void updateLevelInfo(DLInfo li) {
    this.li = li;
    this.ls =
        new DLState(
            this.ls.getHistory(),
            this.ls.getNumbers(),
            this.ls.getFunctions(),
            li.getPlayFlag(),
            li.getID());
    this.li.setDefaultLevelState(this.ls);
    this.level = new DefaultLevel(li);
  }

  private void updateLevelState(DLState ls) {
    this.ls =
        new DLState(
            ls.getHistory(), ls.getNumbers(), ls.getFunctions(), li.getPlayFlag(), li.getID());
    this.li.setDefaultLevelState(this.ls);
    this.level = new DefaultLevel(li);
  }

  private void setLNumbers(ArrayList<MathObject> numbers) {
    isValidatedCompleted = false;
    updateLevelState(
        new DLState(ls.getHistory(), numbers, ls.getFunctions(), PlayFlag.PRELEVELS, li.getID()));
  }

  private void setLFunctions(HashMap<String, Function> functions) {
    isValidatedCompleted = false;
    updateLevelState(
        new DLState(ls.getHistory(), ls.getNumbers(), functions, PlayFlag.PRELEVELS, li.getID()));
  }

  private void setLRules(DLRules rules) {
    isValidatedCompleted = false;
    updateLevelInfo(
        new DLInfo(
            ls,
            rules,
            PlayFlag.PRELEVELS,
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

  private void safeSetLPlayFlag(PlayFlag pf) {
    futurePlayFlag = pf;
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
    isValidatedCompleted = false;
    updateLevelInfo(
        new DLInfo(
            ls,
            li.getRules(),
            PlayFlag.PRELEVELS,
            li.getName(),
            li.getBeforeCutscene(),
            li.getAfterCutscene(),
            li.getID(),
            li.getHints(),
            ans));
  }

  private void addFunction(String functionID) {
    HashMap<String, Function> functions = ls.getFunctions();
    Function f = (Function) Helper.read("data/functions/" + functionID + ".dat");
    f.generateName(ls.getUsingNames());
    functions.put(f.getName(), f);
    setLFunctions(functions);
  }

  private void addHint(String hint) {
    ArrayList<String> hints = li.getHints();
    hints.add(hint);
    setLHints(hints);
  }

  private void resetFunctions() {
    setLFunctions(new HashMap<>());
  }

  private void resetHints() {
    setLHints(new ArrayList<>());
  }

  private ArrayList<MathObject> add(ArrayList<MathObject> first, ArrayList<MathObject> second) {
    HashSet<String> usingNames = new HashSet<>(ls.getFunctions().keySet());
    for (MathObject n : first) {
      usingNames.add(n.getName());
    }

    ArrayList<MathObject> ans = Helper.deepClone(first);
    for (MathObject n : second) {
      for (MathObject m : ans) {
        if (n.getName().equals(m.getName()) && !n.equals(m)) {
          n.generateName(usingNames);
          break;
        }
      }
      usingNames.add(n.getName());
      ans.add(n);
    }
    return ans;
  }

  private void addNumber(String number) {
    ArrayList<MathObject> numbers = ls.getNumbers();
    MathObject mnumber = TypeRegister.parseMathObject(number);
    ArrayList<MathObject> newNumbers =
        add(numbers, new ArrayList<>(List.of(new MathObject[] {mnumber})));
    setLNumbers(newNumbers);
  }

  private void resetNumbers() {
    setLNumbers(new ArrayList<>());
  }

  private void addAnsNumber(String number) {
    ArrayList<MathObject> ansNumbers = li.getAns();
    MathObject mnumber = TypeRegister.parseMathObject(number);
    ArrayList<MathObject> newAnsNumbers =
        add(ansNumbers, new ArrayList<>(List.of(new MathObject[] {mnumber})));
    setLAnswer(newAnsNumbers);
  }

  private void resetAnswers() {
    setLAnswer(new ArrayList<>());
  }

  private void lvlInfo() {
    out.println("Ваш уровень:");
    out.println();
    out.println("Видимая информация для игрока:");
    out.println("Уровень №" + this.level.getLevelInfo().getID() + ": " + this.level.getName());

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

    out.println("Подсказки:");
    for (int i = 0; i < li.getHints().size(); i++) {
      out.println("Подсказка №" + (i + 1) + ": " + li.getHints().get(i));
    }

    out.println();
    out.println("Остальная информация:");
    out.println("ID: " + li.getID());
    out.println("PlayFlag: " + li.getPlayFlag().toString());
    out.println("FuturePlayFlag: " + futurePlayFlag.toString());
    out.println("DLRules: " + li.getRules());
    if (li.getBeforeCutscene() != null) {
      out.println("BeforeCutscene: " + li.getBeforeCutscene().toString());
    } else {
      out.println("BeforeCutscene: ");
    }
    if (li.getAfterCutscene() != null) {
      out.println("AfterCutscene: " + li.getAfterCutscene().toString());
    } else {
      out.println("AfterCutscene: ");
    }
  }

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
        ArrayList<String> command = Helper.wordsFromStringOnlySpaces(cmd);
        Logger.write("Ввели выражение: " + cmd);
        String first_command = command.getFirst();
        switch (first_command) {
          case "exit", "menu", "stop" -> {
            return -1;
          }
          case "help" -> {
            out.clear();
            help();
          }
          case "set" -> {
            out.clear();
            String second_command = command.get(1);
            switch (second_command) {
              case "name" -> setLName(String.join(" ", command.subList(2, command.size())));
              case "playflag" -> {
                switch (command.get(2)) {
                  case "DEFAULT" -> safeSetLPlayFlag(PlayFlag.DEFAULT);
                  case "CUSTOM" -> safeSetLPlayFlag(PlayFlag.CUSTOM);
                  case "PRELEVELS" -> safeSetLPlayFlag(PlayFlag.PRELEVELS);
                  default -> {
                    out.println("Неизвестная команда!");
                    out.println();
                    return 0;
                  }
                }
              }
              case "ID" -> setLID(Long.parseLong(command.get(2)));
              case "rule" -> {
                boolean flag = Boolean.parseBoolean(command.get(3));
                switch (command.get(2)) {
                  case "allowBack" ->
                      setLRules(
                          new DLRules(
                              flag,
                              li.getRules().allowCalc(),
                              li.getRules().infinityNumbers(),
                              li.getRules().infinityFunctions()));
                  case "allowCalc" ->
                      setLRules(
                          new DLRules(
                              li.getRules().allowBack(),
                              flag,
                              li.getRules().infinityNumbers(),
                              li.getRules().infinityFunctions()));
                  case "infinityNumbers" ->
                      setLRules(
                          new DLRules(
                              li.getRules().allowBack(),
                              li.getRules().allowCalc(),
                              flag,
                              li.getRules().infinityFunctions()));
                  case "infinityFunctions" ->
                      setLRules(
                          new DLRules(
                              li.getRules().allowBack(),
                              li.getRules().allowCalc(),
                              li.getRules().infinityNumbers(),
                              flag));
                }
              }
              default -> {
                out.println("Неизвестная команда!");
                out.println();
                return 0;
              }
            }
          }
          case "add" -> {
            out.clear();
            String second_command = command.get(1);
            switch (second_command) {
              case "number" -> addNumber(command.get(2));
              case "function" -> addFunction(command.get(2));
              case "answer" -> addAnsNumber(command.get(2));
              case "hint" -> addHint(String.join(" ", command.subList(2, command.size())));
              default -> {
                out.println("Неизвестная команда!");
                out.println();
                return 0;
              }
            }
          }
          case "reset" -> {
            out.clear();
            String second_command = command.get(1);
            switch (second_command) {
              case "numbers" -> resetNumbers();
              case "functions" -> resetFunctions();
              case "answers" -> resetAnswers();
              case "hints" -> resetHints();
              default -> {
                out.println("Неизвестная команда!");
                out.println();
                return 0;
              }
            }
          }
          case "tutorial" -> {
            out.clear();
            tutorial();
          }
          case "try" -> {
            tryLevel();
            return 0;
          }
          case "save" -> {
            lSave();
            return 1;
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

  // TODO: Есть баг, что при попытке пройти уровень он не загружается, и нельзя выйти в меню.
  private void tryLevel() {
    PlayFlag last = this.level.getPlayFlag();
    setLPlayFlag(PlayFlag.PRELEVELS);
    final boolean[] sleep = {true};
    this.level.setAfterGameAction(
        new AfterGameAction() {
          @Override
          public void afterGame() {
            DLMakerPanel.this.level.resetAfterGameAction();
            if (DLMakerPanel.this.level.isCompleted() || last != PlayFlag.PRELEVELS) {
              isValidatedCompleted = true;
            }
            try {
              Thread.sleep(2000);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
            GameFrame.getInstance().changePanel(DLMakerPanel.this);
            lValidate();
            sleep[0] = false;
          }
        });
    GameLoader.setCurrentLevel(this.level);
    GameFrame.getInstance().changePanel("level");
    out.clear();
    while (sleep[0]) {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void lSave() {
    String chooser =
        switch (li.getPlayFlag()) {
          case DEFAULT -> "levels";
          case CUSTOM -> "customLevels";
          case PRELEVELS -> "preLevels";
        };
    Helper.write(li, "data/" + chooser + "/level" + li.getID() + ".dat");
    out.println("Ваш уровень сохранён в data/" + chooser + "/level" + li.getID() + ".dat");
    out.println();
  }

  private void help() {
    out.println("Список команд:");
    out.println("set name {name} - установить название уровня");
    out.println("set playflag {DEFAULT/CUSTOM/PRELEVELS} - установить тип уровня");
    out.println("set ID {id} - установить номер уровня");
    out.println("set rule {rule} {bool} - установить правило {rule} в значение {bool}");
    out.println("add number {number} - добавить число {number} в стартовый набор");
    out.println("add function {function} - добавить функцию в стартовый набор");
    out.println("add answer {answerNumber} - добавить число {answerNumber} в ответы");
    out.println("add hint {hint} - добавить подсказку");
    out.println("reset (numbers/answers/functions/hints) - обнулить все (...)");
    out.println("help - открыть список команд");
    out.println("try - попробовать уровень");
    out.println("exit - выключить создатель уровня");
    out.println("save - сохранить уровень");
    out.println("tutorial - открыть туториал");
    out.println();
  }

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
    // TODO: tutorial
  }

  private void lValidate() {
    if (isValidatedCompleted) {
      setLPlayFlag(futurePlayFlag);
    } else {
      setLPlayFlag(PlayFlag.PRELEVELS);
    }
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
