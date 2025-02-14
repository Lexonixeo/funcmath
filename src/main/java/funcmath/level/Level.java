package funcmath.level;

import funcmath.utility.Hash;
import javax.swing.*;

// играние в уровень
// Самое главное: чтобы структура интерфейсов Level не зависела от MathObject/Function и т.д.
public interface Level {
  // в каждом Level должен быть пустой конструктор и конструктор, который принимает в качестве
  // параметра LevelInfo;
  // при вызове конструктора с LevelInfo мы делаем setLevelState(LevelInfo.getDefaultLevelState())
  String getType();

  LevelInfo getLevelInfo();

  LevelState
      getCurrentLevelState(); // мы можем и будем хранить последний LevelState, а вот историю
                              // LevelState в уровне делаете вы сами

  LevelStatistics getStatistics();

  PlayFlag getPlayFlag();

  String getName();

  JPanel getLevelPanel();

  // getLevelMakerPanel?

  boolean isCompleted();

  Hash getHash(); // const для каждого уровня

  void setLevelState(
      LevelState
          state); // мы будем передавать LevelState только такого типа, какой у данного уровня,
  // и только такого LevelInfo, какой у данного уровня
}
