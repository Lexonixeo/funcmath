package funcmath.auth;

import funcmath.level.LevelState;
import funcmath.level.LevelStatistics;
import funcmath.utility.Hash;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Player implements Serializable {
  @Serial private static final long serialVersionUID = -7778290545460356656L;

  String name;
  Long ID;
  LevelState lastLevel;

  HashSet<Long> completedDefaultLevels;
  HashMap<Long, LevelStatistics> defaultLevelStats;
  HashMap<Long, LevelState> defaultLevelStates;

  HashSet<Long> completedCustomLevels;
  HashMap<Long, LevelStatistics> customLevelStats;
  HashMap<Long, LevelState> customLevelStates;

  Hash salt;

  public Player(String name, String password) {
    this.name = name;
    this.salt = Hash.encode(password, System.currentTimeMillis());
    this.ID = this.getHash().toLong();
    this.lastLevel = null;

    this.completedDefaultLevels = new HashSet<>();
    this.defaultLevelStats = new HashMap<>();
    this.defaultLevelStates = new HashMap<>();

    this.completedCustomLevels = new HashSet<>();
    this.customLevelStats = new HashMap<>();
    this.customLevelStates = new HashMap<>();
  }

  public void addDefaultLevel(LevelState state, LevelStatistics stats) {
    if (stats.isCompleted()) {
      completedDefaultLevels.add(stats.getLevelID());
      defaultLevelStates.remove(stats.getLevelID());
    } else {
      defaultLevelStates.put(stats.getLevelID(), state);
    }
    defaultLevelStats.put(stats.getLevelID(), stats);
  }

  public void addCustomLevel(LevelState state, LevelStatistics stats) {
    if (stats.isCompleted()) {
      completedCustomLevels.add(stats.getLevelID());
      customLevelStates.remove(stats.getLevelID());
    } else {
      customLevelStates.put(stats.getLevelID(), state);
    }
    customLevelStats.put(stats.getLevelID(), stats);
  }

  public String getName() {
    return name;
  }

  public LevelState getLastLevel() {
    return lastLevel;
  }

  public Long getID() {
    return ID;
  }

  public HashMap<Long, LevelStatistics> getDefaultLevelStats() {
    return defaultLevelStats;
  }

  public HashMap<Long, LevelStatistics> getCustomLevelStats() {
    return customLevelStats;
  }

  public HashMap<Long, LevelState> getDefaultLevelStates() {
    return defaultLevelStates;
  }

  public HashMap<Long, LevelState> getCustomLevelStates() {
    return customLevelStates;
  }

  public Hash getHash() {
    return Hash.encode(name, salt);
  }
}
