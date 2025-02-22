package funcmath.defaultpack;

import funcmath.defaultpack.functions.FIntegerInitializer;
import funcmath.defaultpack.functions.FNaturalInitializer;
import funcmath.game.Mod;
import funcmath.gui.swing.GPanel;
import funcmath.utility.Hash;

public class DefaultPack extends Mod {
  @Override
  public void init() {
    try {
      new FNaturalInitializer().init();
      new FIntegerInitializer().init();
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
