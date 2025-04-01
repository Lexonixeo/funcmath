package funcmath.auth;

import funcmath.exceptions.LevelException;
import funcmath.level.*;
import funcmath.utility.Hash;
import funcmath.utility.Helper;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Player implements Serializable {
  @Serial private static final long serialVersionUID = -7778290545460356656L;

  Hash playerVersion;

  String name;
  Long ID;
  LevelState lastLevel;

  HashSet<LevelPrimaryKey> completedLevels;
  HashMap<LevelPrimaryKey, LevelStatistics> levelStatistics;
  HashMap<LevelPrimaryKey, LevelState> levelStates;

  HashSet<Long> completedDefaultLevels;
  HashMap<Long, LevelStatistics> defaultLevelStats;
  HashMap<Long, LevelState> defaultLevelStates;

  HashSet<Long> completedCustomLevels;
  HashMap<Long, LevelStatistics> customLevelStats;
  HashMap<Long, LevelState> customLevelStates;

  Hash passHash;
  Hash salt;

  public Player(String name, String password) {
    this.name = name;
    this.passHash = Hash.encode(password);
    this.salt = Hash.encode(password, System.currentTimeMillis());
    this.ID = this.getHash().toLong();
    this.lastLevel = null;

    this.completedLevels = new HashSet<>();
    this.levelStatistics = new HashMap<>();
    this.levelStates = new HashMap<>();

    this.playerVersion = Hash.encode(0);

    this.save();
  }

  public void addLevel(LevelState state, LevelStatistics stats) {
    if (stats.isCompleted()) {
      completedLevels.add(stats.getPrimaryKey());
      levelStates.remove(stats.getPrimaryKey());
    } else {
      levelStates.put(stats.getPrimaryKey(), state);
    }
    levelStatistics.put(stats.getPrimaryKey(), stats.add(getLevelStatistics(stats.getPrimaryKey())));
    this.lastLevel = state;
  }

  public LevelPrimaryKey getFirstUncompletedLevel() {
    for (Long ID : LevelRegister.getLevelList(PlayFlag.DEFAULT)) {
      LevelPrimaryKey tempLevel = new LevelPrimaryKey(ID, PlayFlag.DEFAULT);
      if (!completedLevels.contains(tempLevel)) {
        return tempLevel;
      }
    }
    for (Long ID : LevelRegister.getLevelList(PlayFlag.CUSTOM)) {
      LevelPrimaryKey tempLevel = new LevelPrimaryKey(ID, PlayFlag.CUSTOM);
      if (!completedLevels.contains(tempLevel)) {
        return tempLevel;
      }
    }
    throw new LevelException("Все уровни пройдены!");
  }

  public void save() {
    // TODO: можно сделать историю игрока в виде блокчейна!
    Helper.write(this, "data/players/" + getHash() + ".dat");
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

  public LevelStatistics getLevelStatistics(LevelPrimaryKey key) {
    return levelStatistics.get(key);
  }

  public LevelState getLevelState(LevelPrimaryKey key) {
    return levelStates.get(key);
  }

  public Hash getHash() {
    return Hash.encode(name, salt);
  }

  public Hash getPlayerVersion() {
    return playerVersion;
  }
}
