package funcmath.game.defaultlevel;

import funcmath.function.Function;
import funcmath.game.Cutscene;
import funcmath.game.LevelInfo;
import funcmath.game.LevelPlayFlag;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import funcmath.utility.Log;
import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;

public class DLevelInfo implements LevelInfo {
  @Serial private static final long serialVersionUID = 365506896889958399L;

  ArrayList<MathObject> originalNumbers;
  HashMap<String, Function> originalFunctions;
  DLevelRules rules;
  ArrayList<String> hints;
  Cutscene cutscene;
  ArrayList<MathObject> answers;
  String resultClassName;
  String name;
  ArrayList<String> functionNames;
  boolean isCompleted;
  int level;

  private DLevelInfo() {}

  public DLevelInfo(int level, LevelPlayFlag customFlag) {
    Log.getInstance().write("Receiving information about level №" + level + "...");
    this.level = level;
    String levelSwitch =
        switch (customFlag) {
          case DEFAULT -> "l";
          case CUSTOM -> "customL";
          case PRE -> "preL";
        };
    DLevelInfo levelInfo =
        Helper.cast(
            Helper.read("data\\" + levelSwitch + "evels\\level" + level + ".dat"),
            new DLevelInfo());
    this.originalNumbers = levelInfo.getOriginalNumbers();
    this.originalFunctions = levelInfo.getOriginalFunctions();
    this.hints = levelInfo.getHints();
    this.cutscene = levelInfo.getCutscene();
    this.answers = levelInfo.getAnswers();
    this.resultClassName = levelInfo.getResultClassName();
    this.name = levelInfo.getName();
    this.isCompleted = levelInfo.getCompleted();
    this.rules = levelInfo.getRules();
    this.functionNames = new ArrayList<>(originalFunctions.keySet());
    Log.getInstance().write("The level is loaded!");
  }

  public DLevelInfo(
      ArrayList<MathObject> originalNumbers,
      HashMap<String, Function> originalFunctions,
      ArrayList<String> hints,
      Cutscene cutscene,
      ArrayList<MathObject> answers,
      String resultClassName,
      String name,
      boolean isCompleted,
      int level,
      LevelPlayFlag customFlag,
      DLevelRules rules) {
    Log.getInstance().write("Trying to create a level with such data:");
    Log.getInstance()
        .write(name + " " + resultClassName + " " + level + " " + customFlag);
    Log.getInstance().write(Helper.collectionToString(originalNumbers));
    Log.getInstance().write(Helper.collectionToString(answers));
    Log.getInstance().write(Helper.collectionToString(originalFunctions.values()));
    this.level = level;
    String levelSwitch =
        switch (customFlag) {
          case DEFAULT -> "l";
          case CUSTOM -> "customL";
          case PRE -> "preL";
        };

    this.originalNumbers = originalNumbers;
    this.originalFunctions = originalFunctions;
    this.hints = hints;
    this.cutscene = cutscene;
    this.answers = answers;
    this.resultClassName = resultClassName;
    this.name = name;
    this.isCompleted = isCompleted;
    this.rules = rules;
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

  public Cutscene getCutscene() {
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

  public DLevelRules getRules() {
    return rules;
  }

  public DLevelState getLevelState() {
    return new DLevelState(level, originalNumbers, originalFunctions);
  }

  public ArrayList<String> getFunctionNames() {
    return functionNames;
  }

  public int getLevel() {
    return level;
  }

  public void setCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
    Helper.write(this, "data\\customLevels\\level" + level + ".dat");
  }

  @Override
  public String getType() {
    return "default";
  }
}
