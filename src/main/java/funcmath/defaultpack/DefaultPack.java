package funcmath.defaultpack;

import funcmath.defaultpack.functions.FIntegerInitializer;
import funcmath.defaultpack.functions.FNaturalInitializer;
import funcmath.defaultpack.functions.FRationalInitializer;
import funcmath.defaultpack.functions.FRealInitializer;
import funcmath.defaultpack.level.DLMakerPanel;
import funcmath.defaultpack.level.DefaultLevel;
import funcmath.game.Mod;
import funcmath.gui.swing.GPanel;
import funcmath.level.LevelRegister;
import funcmath.utility.Hash;

public class DefaultPack extends Mod {
  @Override
  public void init() {
    LevelRegister.registerType(new DefaultLevel());
    LevelRegister.registerLevelMaker(new DefaultLevel(), new DLMakerPanel());
    try {
      new FNaturalInitializer().init();
      new FIntegerInitializer().init();
      new FRationalInitializer().init();
      new FRealInitializer().init();
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public GPanel getModPanel() {
    return null;
  }

  @Override
  public String getName() {
    return "DefaultPack";
  }

  @Override
  public Hash getHash() {
    return Hash.encode("DefaultPack");
  }
}
