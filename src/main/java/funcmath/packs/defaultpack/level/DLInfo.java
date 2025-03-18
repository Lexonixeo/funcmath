package funcmath.packs.defaultpack.level;

import funcmath.cutscene.Cutscene;
import funcmath.level.LevelInfo;
import funcmath.level.LevelPrimaryKey;
import funcmath.level.LevelState;
import funcmath.level.PlayFlag;
import funcmath.object.MathObject;
import funcmath.utility.Hash;
import java.io.Serial;
import java.util.ArrayList;

public class DLInfo implements LevelInfo {
  @Serial private static final long serialVersionUID = -304014995324334864L;

  private DLState defaultLevelState;
  private final DLRules rules;
  private PlayFlag playFlag;
  private final String name;
  private final Cutscene beforeCutscene;
  private final Cutscene afterCutscene;
  private Long ID;
  private final ArrayList<String> hints;
  private final ArrayList<MathObject> ans;

  public DLInfo() {
    this(
        new DLState(),
        new DLRules(true, true, false, false),
        PlayFlag.PRELEVELS,
        "",
        null,
        null,
        0L,
        new ArrayList<>(),
        new ArrayList<>());
  }

  public DLInfo(
      DLState defaultLevelState,
      DLRules rules,
      PlayFlag playFlag,
      String name,
      Cutscene beforeCutscene,
      Cutscene afterCutscene,
      Long ID,
      ArrayList<String> hints,
      ArrayList<MathObject> ans) {
    this.defaultLevelState = defaultLevelState;
    this.rules = rules;
    this.playFlag = playFlag;
    this.name = name;
    this.beforeCutscene = beforeCutscene;
    this.afterCutscene = afterCutscene;
    this.ID = ID;
    this.hints = hints;
    this.ans = ans;
  }

  @Override
  public String getType() {
    return "default";
  }

  @Override
  public LevelState getDefaultLevelState() {
    return defaultLevelState;
  }

  @Override
  public PlayFlag getPlayFlag() {
    return playFlag;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Cutscene getBeforeCutscene() {
    return beforeCutscene;
  }

  @Override
  public Cutscene getAfterCutscene() {
    return afterCutscene;
  }

  public ArrayList<MathObject> getAns() {
    return ans;
  }

  @Override
  public Hash getHash() {
    return Hash.encode(name, defaultLevelState, rules, beforeCutscene, afterCutscene);
  }

  @Override
  public Long getID() {
    return ID;
  }

  @Override
  public void setID(Long ID) {
    this.ID = ID;
  }

  @Override
  public LevelPrimaryKey getPrimaryKey() {
    return new LevelPrimaryKey(ID, playFlag);
  }

  @Override
  public void setPrimaryKey(LevelPrimaryKey key) {
    this.ID = key.ID();
    this.playFlag = key.playFlag();
  }

  public void setDefaultLevelState(DLState defaultLevelState) {
    this.defaultLevelState = defaultLevelState;
  }

  public ArrayList<String> getHints() {
    return hints;
  }

  public DLRules getRules() {
    return rules;
  }
}
