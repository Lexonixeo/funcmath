package funcmath.packs.defaultpack;

import funcmath.game.Mod;
import funcmath.gui.swing.GPanel;
import funcmath.level.LevelRegister;
import funcmath.packs.defaultpack.functions.*;
import funcmath.packs.defaultpack.level.DLMakerPanel;
import funcmath.packs.defaultpack.level.DefaultLevel;
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
      new FComplexInitializer().init();
      new FGaussianInitializer().init();
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

  @Override
  public Hash getVersion() {
    return Hash.encode(0);
  }
}
