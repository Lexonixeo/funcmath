package funcmath.game;

import funcmath.utility.Helper;
import funcmath.utility.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Player {
  String name;
  String ID; // hashedName
  HashSet<Integer> completedLevels;
  HashMap<Integer, Integer> minFunctionUsesInLevel;
  HashMap<Integer, Integer> minTimeInLevel;
  int lastLevel;

  public Player(String name, boolean registered) {
    this.name = name;
    this.ID = Integer.toHexString(name.hashCode());

    if (registered) {
      readPlayerInfo();
    } else {
      this.completedLevels = new HashSet<>();
      this.minFunctionUsesInLevel = new HashMap<>();
      this.minTimeInLevel = new HashMap<>();
      this.lastLevel = 0;

      writePlayerInfo();
    }
  }

  public void writePlayerInfo() {
    Log.getInstance().write("Сохраняются данные о пользователе...");
    ArrayList<Object> playerInfo = new ArrayList<>();

    playerInfo.add(this.completedLevels);
    playerInfo.add(this.minFunctionUsesInLevel);
    playerInfo.add(this.minTimeInLevel);
    playerInfo.add(lastLevel);

    Helper.write(playerInfo, "data/players/" + ID + ".dat");
    Log.getInstance().write("Данные сохранены!");
  }

  public void readPlayerInfo() {
    Log.getInstance().write("Читаются данные о пользователе...");
    try {
      ArrayList<Object> playerInfo =
          Helper.cast(Helper.read("data\\players\\" + ID + ".dat"), new ArrayList<>());
      this.completedLevels = Helper.cast(playerInfo.get(0), new HashSet<>());
      this.minFunctionUsesInLevel = Helper.cast(playerInfo.get(1), new HashMap<>());
      this.minTimeInLevel = Helper.cast(playerInfo.get(2), new HashMap<>());
      this.lastLevel = (int) playerInfo.get(3);
    } catch (Exception e) {
      try {
        this.completedLevels =
            Helper.cast(Helper.read("data\\players\\" + ID + ".dat"), new HashSet<>());
      } catch (Exception ee) {
        this.completedLevels = new HashSet<>();
      }
      minFunctionUsesInLevel = new HashMap<>();
      minTimeInLevel = new HashMap<>();

      try {
        this.lastLevel = (Integer) Helper.read("data\\players\\" + ID + "s.dat");
      } catch (Exception ee) {
        this.lastLevel = 0;
      }

      writePlayerInfo();
    }
    Log.getInstance().write("Данные прочитаны!");
  }

  public void addLevel(boolean completed, int level, int functionUses, int time) {
    if (completed) {
      if (completedLevels.contains(level)
          && minFunctionUsesInLevel.get(level) != null
          && minTimeInLevel.get(level) != null) {
        minFunctionUsesInLevel.put(
            level, Math.min(minFunctionUsesInLevel.get(level), functionUses));
        minTimeInLevel.put(level, Math.min(minTimeInLevel.get(level), time));
      } else {
        completedLevels.add(level);
        minFunctionUsesInLevel.put(level, functionUses);
        minTimeInLevel.put(level, time);
      }
    }
    lastLevel = level;

    writePlayerInfo();
    Log.getInstance().write("Игроку в статистику добавлены результаты за уровень");
  }

  public String getName() {
    return name;
  }

  public int getLastLevel() {
    return lastLevel;
  }

  public HashMap<Integer, Integer> getMinFunctionUsesInLevel() {
    return minFunctionUsesInLevel;
  }

  public HashMap<Integer, Integer> getMinTimeInLevel() {
    return minTimeInLevel;
  }

  public HashSet<Integer> getCompletedLevels() {
    return completedLevels;
  }

  @Override
  public String toString() {
    return "Player " + ID;
  }
}
