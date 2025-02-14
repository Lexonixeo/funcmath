package funcmath.level;

import funcmath.cutscene.Cutscene;
import funcmath.utility.Hash;
import java.io.Serializable;

// информация об уровне с нулевой точки
public interface LevelInfo extends Serializable {
  String getType();

  LevelState getDefaultLevelState();

  PlayFlag getPlayFlag();

  String getName();

  Cutscene getBeforeCutscene();

  Cutscene getAfterCutscene();

  Hash getHash();

  Long getID(); // const для каждого уровня, зависит на порядок уровня в списке

  void setID(Long ID);
}
