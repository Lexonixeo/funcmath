package funcmath.packs.defaultpack.level;

import funcmath.exceptions.LevelException;
import funcmath.level.LevelInfo;
import funcmath.level.LevelPrimaryKey;
import funcmath.level.LevelRegister;
import funcmath.level.LevelStatistics;
import funcmath.utility.Hash;
import java.io.Serial;

public class DLStats implements LevelStatistics {
  @Serial private static final long serialVersionUID = 6815267273286583937L;

  private long startTime = Long.MIN_VALUE; // millis
  private long endTime = Long.MAX_VALUE; // millis
  private long functionUses = Long.MAX_VALUE;
  private boolean completed = false;
  private final LevelInfo li;

  // private final DefaultLevel level;

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  DLStats(DefaultLevel level) {
    this.li = level.getLevelInfo();
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
  public Hash getLevelHash() {
    return li.getHash();
  }

  @Override
  public LevelPrimaryKey getPrimaryKey() {
    return li.getPrimaryKey();
  }

  @Override
  public LevelStatistics add(LevelStatistics x) {
    if (x == null) {
      return this;
    }
    return best(this, (DLStats) x);
  }

  public long getStartTime() {
    return startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public long getFunctionUses() {
    return functionUses;
  }

  public DefaultLevel getLevel() {
    return (DefaultLevel) LevelRegister.getLevel(li);
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public static DLStats best(DLStats first, DLStats second) {
    if (!first.getPrimaryKey().equals(second.getPrimaryKey())) {
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

  @Override
  public String toString() {
    return (!completed
        ? ""
        : "Дето: " + functionUses + " функц; Время: " + ((endTime - startTime) / 1000) + " с");
  }
}
