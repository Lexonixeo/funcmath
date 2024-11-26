package funcmath.game;

import funcmath.exceptions.LevelException;
import funcmath.function.Function;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import funcmath.utility.Log;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LevelInfo implements Serializable {
  @Serial private static final long serialVersionUID = -1977159268179008434L;

  ArrayList<MathObject> originalNumbers;
  HashMap<String, Function> originalFunctions;
  ArrayList<String> hints;
  ArrayList<String> cutscene;
  ArrayList<MathObject> answers;
  String resultClassName;
  String name;
  boolean isCompleted;
  int level;
  int mode = 0;

  private LevelInfo() {}

  public LevelInfo(int level, int customFlag) {
    Log.getInstance().write("Получается информация об уровне №" + level + "...");
    this.level = level;
    String levelSwitch =
        switch (customFlag) {
          case 0 -> "l";
          case 1 -> "customL";
          case 2 -> "preL";
          default -> throw new LevelException("Несуществующий уровневый флаг: " + customFlag);
        };
    try {
      LevelInfo levelInfo =
          Helper.cast(
              Helper.read("data\\" + levelSwitch + "evels\\level" + level + ".dat"),
              new LevelInfo());
      this.originalNumbers = levelInfo.getOriginalNumbers();
      this.originalFunctions = levelInfo.getOriginalFunctions();
      this.hints = levelInfo.getHints();
      this.cutscene = levelInfo.getCutscene();
      this.answers = levelInfo.getAnswers();
      this.resultClassName = levelInfo.getResultClassName();
      this.name = levelInfo.getName();
      this.isCompleted = levelInfo.getCompleted();
      this.mode = levelInfo.getMode();
    } catch (ClassCastException e) {
      Log.getInstance().write("Доисторический уровень №" + level);
      int add = 0;
      if (customFlag == 2) {
        add = 1;
      }
      isCompleted = true;
      ArrayList<Object> generated =
          Helper.cast(
              Helper.read("data\\" + levelSwitch + "evels\\level" + level + ".dat"),
              new ArrayList<>());
      if (generated.get(0).equals("nothing :)") && add == 0) {
        Log.getInstance().write("Игрок попытался войти в непройденный уровень!");
        isCompleted = false;
      }
      originalNumbers = Helper.cast(generated.get(add), new ArrayList<>());
      originalFunctions = Helper.cast(generated.get(1 + add), new HashMap<>());
      try {
        answers = Helper.cast(generated.get(2 + add), new ArrayList<>());
      } catch (RuntimeException ej) {
        answers = new ArrayList<>();
        answers.add((MathObject) generated.get(2 + add));
      }
      hints = Helper.cast(generated.get(3 + add), new ArrayList<>());
      resultClassName = (String) generated.get(4 + add);
      cutscene = Helper.cast(generated.get(5 + add), new ArrayList<>());
      name = (String) generated.get(6 + add);
      mode = 0;
      Helper.write(this, "data\\" + levelSwitch + "evels\\level" + level + ".dat");
    }
    Log.getInstance().write("Уровень загружен!");
  }

  public LevelInfo(
      ArrayList<MathObject> originalNumbers,
      HashMap<String, Function> originalFunctions,
      ArrayList<String> hints,
      ArrayList<String> cutscene,
      ArrayList<MathObject> answers,
      String resultClassName,
      String name,
      boolean isCompleted,
      int level,
      int customFlag,
      int mode) {
    Log.getInstance().write("Пытается создаться уровень с такими данными:");
    Log.getInstance()
        .write(name + " " + resultClassName + " " + level + " " + customFlag + " " + mode);
    Log.getInstance().write(Helper.collectionToString(originalNumbers));
    Log.getInstance().write(Helper.collectionToString(answers));
    Log.getInstance().write(Helper.collectionToString(originalFunctions.values()));
    this.level = level;
    String levelSwitch =
        switch (customFlag) {
          case 0 -> "l";
          case 1 -> "customL";
          case 2 -> "preL";
          default -> throw new LevelException("Несуществующий уровневый флаг: " + customFlag);
        };

    this.originalNumbers = originalNumbers;
    this.originalFunctions = originalFunctions;
    this.hints = hints;
    this.cutscene = cutscene;
    this.answers = answers;
    this.resultClassName = resultClassName;
    this.name = name;
    this.isCompleted = isCompleted;
    this.mode = mode;
    Helper.write(this, "data\\" + levelSwitch + "evels\\level" + level + ".dat");
    Log.getInstance().write("Уровень создан!");
  }

  public String getName() {
    return name;
  }

  public ArrayList<MathObject> getAnswers() {
    return answers;
  }

  public ArrayList<MathObject> getOriginalNumbers() {
    return originalNumbers;
  }

  public ArrayList<String> getCutscene() {
    return cutscene;
  }

  public ArrayList<String> getHints() {
    return hints;
  }

  public HashMap<String, Function> getOriginalFunctions() {
    return originalFunctions;
  }

  public String getResultClassName() {
    return resultClassName;
  }

  public boolean getCompleted() {
    return isCompleted;
  }

  public int getMode() {
    return mode;
  }

  public void setCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
    Helper.write(this, "data\\customLevels\\level" + level + ".dat");
  }
}
