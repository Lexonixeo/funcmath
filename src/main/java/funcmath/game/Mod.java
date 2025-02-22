package funcmath.game;

import funcmath.gui.swing.GPanel;
import funcmath.utility.Hash;

public abstract class Mod {
  // LevelRegister.registerType(Level l); - регистрация уровневого типа
  // LevelRegister.addLevelInfo(LevelInfo li, PlayFlag pf, boolean rewrite); - регистрация уровня
  // для LevelMaker

  // SimpleFunctionRegister.registerSimpleFunction(SimpleFunction f); - регистрация простейшей
  // функции
  // TypeRegister.registerType(MathObject m); - регистрация типа математического объекта
  // TypeRegister.registerConvert(MathObject input, MathObject output, Method convert); -
  // регистрация перевода из одного математического объекта в другой

  public abstract void init();

  public abstract GPanel
      getModPanel(); // внутри него делайте свою систему изменения панели, независимо от GameFrame

  public abstract String getName();

  public abstract Hash getHash();
}
