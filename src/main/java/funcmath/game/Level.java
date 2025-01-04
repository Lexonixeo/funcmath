package funcmath.game;

import funcmath.exceptions.JavaException;
import funcmath.exceptions.LevelException;
import funcmath.gui.swing.GPanel;
import funcmath.utility.Helper;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public interface Level extends Serializable {
  HashMap<String, Level> LEVEL_HASH_MAP = new HashMap<>();

  String getType();

  String getName();

  int getLevel();

  Cutscene getCutscene();

  int[] consoleRun(InputStream in, PrintStream out);

  int[] guiRun(GPanel panel);

  static ArrayList<Integer> getLevelList(ArrayList<String> fileNames) {
    ArrayList<Integer> answer = new ArrayList<>();
    for (String fileName : fileNames) {
      answer.add(Integer.parseInt(fileName.substring(5, fileName.length() - 4)));
    }
    answer.sort(Comparator.naturalOrder());
    return answer;
  }

  static void loadLevelType(Level x) {
    String type = x.getType();
    Level typeInstance;
    try {
      typeInstance = x.getClass().getConstructor(new Class[] {}).newInstance();
    } catch (NoSuchMethodException e) {
      throw new LevelException(e);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new JavaException(e);
    }
    LEVEL_HASH_MAP.put(type, typeInstance);
  }

  static Level getLevelInstance(int level, LevelPlayFlag playFlag) {
    String levelSwitch =
            switch (playFlag) {
              case DEFAULT -> "l";
              case CUSTOM -> "customL";
              case PRE -> "preL";
            };
    LevelInfo li = (LevelInfo) Helper.read("data\\" + levelSwitch + "evels\\level" + level + ".dat");
    String type = li.getType();
    try {
      return LEVEL_HASH_MAP
              .get(type)
              .getClass()
              .getConstructor(new Class[] {Integer.class, LevelPlayFlag.class})
              .newInstance(level, playFlag);
    } catch (NoSuchMethodException e) {
      throw new LevelException(e);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new JavaException(e);
    }
  }
}
