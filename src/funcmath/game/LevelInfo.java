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
  ArrayList<String> functionNames;
  boolean isCompleted;
  int level;
  int mode = 0;

  private LevelInfo() {}

  public LevelInfo(int level, int customFlag) {
    Log.getInstance().write("Receiving information about level №" + level + "...");
    this.level = level;
    String levelSwitch =
        switch (customFlag) {
          case 0 -> "l";
          case 1 -> "customL";
          case 2 -> "preL";
          default -> throw new LevelException("A non-existent level flag: " + customFlag);
        };
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
    this.functionNames = new ArrayList<>(originalFunctions.keySet());
    Log.getInstance().write("The level is loaded!");
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
    Log.getInstance().write("Trying to create a level with such data:");
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
          default -> throw new LevelException("A non-existent level flag: " + customFlag);
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
    Log.getInstance().write("The level has been created!");
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

  public LevelState getLevelState() {
    return new LevelState(level, originalNumbers, originalFunctions);
  }

  public ArrayList<String> getFunctionNames() {
    return functionNames;
  }

  public void setCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
    Helper.write(this, "data\\customLevels\\level" + level + ".dat");
  }
}
