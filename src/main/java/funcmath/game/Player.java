package funcmath.game;

import funcmath.game.defaultlevel.DLevelState;
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
  // HashMap<Integer, DLevelState> savings;
  int lastLevel;
  HashMap<Integer, String> stats;

  public Player(String name, boolean registered) {
    Log.getInstance()
        .write("The data about the user " + name + ":" + registered + " is being read/wrote");
    this.name = name;
    this.ID = Integer.toHexString(name.hashCode());

    if (registered) {
      readPlayerInfo();
    } else {
      this.completedLevels = new HashSet<>();
      this.minFunctionUsesInLevel = new HashMap<>();
      this.minTimeInLevel = new HashMap<>();
      this.lastLevel = 0;
      this.stats = new HashMap<>();

      writePlayerInfo();
    }
    Log.getInstance().write("User data has loaded!");
  }

  public void writePlayerInfo() {
    Log.getInstance().write("The data about the user is writing...");
    ArrayList<Object> playerInfo = new ArrayList<>();

    playerInfo.add(this.completedLevels);
    playerInfo.add(this.minFunctionUsesInLevel);
    playerInfo.add(this.minTimeInLevel);
    playerInfo.add(lastLevel);
    playerInfo.add(stats);

    Helper.write(playerInfo, "data/players/" + ID + ".dat");
    Log.getInstance().write("The data is saved!");
  }

  public void readPlayerInfo() {
    Log.getInstance().write("The data about the user is reading...");
    try {
      ArrayList<Object> playerInfo =
          Helper.cast(Helper.read("data\\players\\" + ID + ".dat"), new ArrayList<>());
      this.completedLevels = Helper.cast(playerInfo.get(0), new HashSet<>());
      this.minFunctionUsesInLevel = Helper.cast(playerInfo.get(1), new HashMap<>());
      this.minTimeInLevel = Helper.cast(playerInfo.get(2), new HashMap<>());
      this.lastLevel = (int) playerInfo.get(3);
      this.stats = Helper.cast(playerInfo.get(4), new HashMap<>());
    } catch (Exception e) {
      try {
        this.completedLevels =
            Helper.cast(Helper.read("data\\players\\" + ID + ".dat"), new HashSet<>());
      } catch (Exception ee) {
        this.completedLevels = new HashSet<>();
      }
      minFunctionUsesInLevel = new HashMap<>();
      minTimeInLevel = new HashMap<>();
      stats = new HashMap<>();

      try {
        this.lastLevel = (Integer) Helper.read("data\\players\\" + ID + "s.dat");
      } catch (Exception ee) {
        this.lastLevel = 0;
      }

      writePlayerInfo();
    }
    Log.getInstance().write("The data has been read!");
  }

  public void addLevel(boolean completed, int level, int functionUses, int time) {
    if (completed) {
      if (completedLevels.contains(level)
          && minFunctionUsesInLevel.get(level) != null
          && minTimeInLevel.get(level) != null) {
        minFunctionUsesInLevel.put(
            level, Math.min(minFunctionUsesInLevel.get(level), functionUses));
        minTimeInLevel.put(level, Math.min(minTimeInLevel.get(level), time));
        stats.put(level, "FuncUses: " + Math.min(minFunctionUsesInLevel.get(level), functionUses) + " Время: " + Math.min(minTimeInLevel.get(level), time) + " с");
      } else {
        completedLevels.add(level);
        minFunctionUsesInLevel.put(level, functionUses);
        minTimeInLevel.put(level, time);
        stats.put(level, "FuncUses: " + functionUses + " Время: " + time + " с");
      }
    }
    lastLevel = level;

    writePlayerInfo();
    Log.getInstance().write("The player has added the results for the level to the statistics");
  }

  public void addLevel(Level level, int functionUses, int time) {
    if (level.isCompleted()) {
      if (completedLevels.contains(level.getLevel())
              && minFunctionUsesInLevel.get(level.getLevel()) != null
              && minTimeInLevel.get(level.getLevel()) != null) {
        minFunctionUsesInLevel.put(
                level.getLevel(), Math.min(minFunctionUsesInLevel.get(level.getLevel()), functionUses));
        minTimeInLevel.put(level.getLevel(), Math.min(minTimeInLevel.get(level.getLevel()), time));
      } else {
        completedLevels.add(level.getLevel());
        minFunctionUsesInLevel.put(level.getLevel(), functionUses);
        minTimeInLevel.put(level.getLevel(), time);
      }
      stats.put(level.getLevel(), level.getStats());
    }
    lastLevel = level.getLevel();

    writePlayerInfo();
    Log.getInstance().write("The player has added the results for the level to the statistics");
  }

  public HashMap<Integer, String> getStats() {
    return stats;
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
    return "Игрок " + ID;
  }
}
