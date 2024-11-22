package funcmath.game;

import funcmath.Helper;
import funcmath.exceptions.FunctionException;
import funcmath.exceptions.LevelException;
import funcmath.exceptions.MathObjectException;
import funcmath.function.Function;
import funcmath.object.MathObject;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
  HashSet<String> usingNames;

  private LevelInfo() {}

  public LevelInfo(int level, int customFlag) {
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
      this.usingNames = levelInfo.getUsingNames();
    } catch (ClassCastException e) {
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
      usingNames = new HashSet<>();
      Helper.write(this, "data\\" + levelSwitch + "evels\\level" + level + ".dat");
    }

    updateUsingNames(levelSwitch);
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
      HashSet<String> usingNames) {
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
    this.usingNames = usingNames;
    Helper.write(this, "data\\" + levelSwitch + "evels\\level" + level + ".dat");
  }

  private void updateUsingNames(String levelSwitch) {
    if (usingNames.isEmpty()) {
      for (Function f : originalFunctions.values()) {
        if (usingNames.contains(f.getName())) {
          throw new FunctionException("Названия функций не должны повторяться: " + f.getName());
        }
        usingNames.add(f.getName());
      }
      for (MathObject n : originalNumbers) {
        if (n.getName() != null && usingNames.contains(n.getName())) {
          throw new MathObjectException(
              "Названия объектов не должны повторяться с функциями или другими объектами: "
                  + n.getName());
        }
        if (n.getName() != null) {
          usingNames.add(n.getName());
        }
      }
      Helper.write(this, "data\\" + levelSwitch + "evels\\level" + level + ".dat");
    }
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

  public HashSet<String> getUsingNames() {
    return usingNames;
  }

  public void setCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
    Helper.write(this, "data\\customLevels\\level" + level + ".dat");
  }
}
