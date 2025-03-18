package funcmath.functions;

import funcmath.exceptions.FunctionException;
import funcmath.exceptions.MakerException;
import funcmath.game.Logger;
import funcmath.gui.swing.GConsolePanel;
import funcmath.object.MathObject;
import funcmath.object.TypeRegister;
import funcmath.utility.Helper;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FunctionMakerPanel extends GConsolePanel {
  Function f;

  boolean interrupted = false;

  @Override
  protected void initBeforeComponents() {
    super.initBeforeComponents();
    f = new Function("", new String[0], new String[0], "", -1);
  }

  @Override
  protected void initComponents() {
    super.initComponents();

    nameLabel.setText("Создатель функций");
  }

  @Override
  protected void run() {
    int res = 0;
    out.clear();
    out.println("Добро пожаловать в мастерскую создания функций!");
    do {
      if (res == 0) {
        funcInfo();
      }
      res = turn();
    } while (res != -1 && !interrupted);
  }

  @Override
  protected void runInterrupt() {
    interrupted = true;
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
          case "help" -> {
            out.clear();
            help();
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

  private void help() {
    out.println("Список команд:");
    out.println("set name {name} - установить название функции");
    out.println("set description {...} - установить описание функции");
    out.println(
        "set types {Type} {Type} {...} - установить типы аргументов функции (кол-во типов = кол-во аргументов)");
    out.println("set definition {Type/Type=___} {...} - установить определение функции");
    out.println("set uses {n} - установить кол-во использований функции");
    out.println("help - открыть список команд");
    out.println("list - открыть список простейших функций");
    out.println("exit - выключить создатель функции");
    out.println(
        "use {name} {type=...} {type=...} {...} - использовать функцию для аргументов type=...");
    out.println("validate - пройти автоматическую проверку");
    out.println("save - сохранить функцию");
    out.println("tutorial - открыть туториал");
    out.println();
  }

  private void sfList() {
    out.println("Список простейших функций: ");
    HashMap<String, SimpleFunction> SF_HASH_MAP = SimpleFunctionRegister.getSfHashMap();
    HashMap<SimpleFunction, String> HASH_SF_MAP = new HashMap<>();
    for (String key : SF_HASH_MAP.keySet()) {
      HASH_SF_MAP.put(SF_HASH_MAP.get(key), key);
      // out.println(key + ": " + SF_HASH_MAP.get(key));
    }
    ArrayList<SimpleFunction> sfList = new ArrayList<>(HASH_SF_MAP.keySet());
    sfList.sort(
        new Comparator<>() {
          @Override
          public int compare(SimpleFunction o1, SimpleFunction o2) {
            return o1.getName().compareTo(o2.getName());
          }
        });
    for (SimpleFunction sf : sfList) {
      out.println(HASH_SF_MAP.get(sf));
    }
    out.println();
  }

  private void tutorial() {
    // TODO
  }

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
    String functionHash = String.valueOf(Long.valueOf(f.getHash().toLong()).byteValue());
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
    /*
    if (tutorial) {
      out.print("Хотите ли вы прочитать туториал? y/n ");
      String input = scanner.nextLine();
      if (input.equals("y") || input.equals("у")) {
        tutorial();
      }
    }
     */

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
}
