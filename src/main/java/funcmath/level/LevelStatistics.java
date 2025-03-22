package funcmath.level;

import funcmath.utility.Hash;
import java.io.Serializable;

// то, что храниться у каждого пользователя после прохождения уровня
public interface LevelStatistics extends Serializable {
  String getType();

  boolean isCompleted();

  Hash getLevelHash();

  LevelPrimaryKey getPrimaryKey();

  LevelStatistics add(LevelStatistics x);
}
