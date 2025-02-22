package funcmath.defaultpack.level;

import funcmath.exceptions.LevelException;
import funcmath.level.LevelStatistics;
import funcmath.utility.Hash;

public class DLStats implements LevelStatistics {
  private long startTime = Long.MIN_VALUE; // millis
  private long endTime = Long.MAX_VALUE; // millis
  private long functionUses = Long.MAX_VALUE;
  private boolean completed = false;
  private final long ID;
  private final Hash levelHash;
  private final DefaultLevel level;

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  DLStats(DefaultLevel level) {
    this.level = level;
    this.ID = level.getLevelInfo().getID();
    this.levelHash = level.getHash();
  }

  public void startTime() {
    this.startTime = System.currentTimeMillis();
  }

  public void endTime() {
    this.endTime = System.currentTimeMillis();
  }

  public void setFunctionUses(long uses) {
    functionUses = uses;
  }

  @Override
  public String getType() {
    return "default";
  }

  @Override
  public boolean isCompleted() {
    return completed;
  }

  @Override
  public Long getLevelID() {
    return ID;
  }

  @Override
  public Hash getLevelHash() {
    return levelHash;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public long getID() {
    return ID;
  }

  public long getFunctionUses() {
    return functionUses;
  }

  public DefaultLevel getLevel() {
    return level;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public static DLStats best(DLStats first, DLStats second) {
    if (!first.getLevel().equals(second.getLevel())) {
      throw new LevelException("Не совпадают уровни для сложения статистик!");
    }
    DLStats s = new DLStats(first.getLevel());
    s.setCompleted(first.isCompleted() || second.isCompleted());
    if (first.isCompleted() && second.isCompleted()) {
      s.setFunctionUses(Math.min(first.getFunctionUses(), second.getFunctionUses()));
      if (first.getEndTime() - first.getStartTime() < second.getEndTime() - second.getStartTime()) {
        s.setStartTime(first.getStartTime());
        s.setEndTime(first.getEndTime());
      } else {
        s.setStartTime(second.getStartTime());
        s.setEndTime(second.getEndTime());
      }
    } else if (first.isCompleted()) {
      return first;
    } else if (second.isCompleted()) {
      return second;
    }
    return s;
  }
}
