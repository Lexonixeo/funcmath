package funcmath.game;

import funcmath.functions.SimpleFunction;
import funcmath.functions.SimpleFunctionRegister;
import funcmath.gui.swing.GPanel;
import funcmath.level.Level;
import funcmath.level.LevelInfo;
import funcmath.level.LevelRegister;
import funcmath.object.MathObject;
import funcmath.object.TypeRegister;
import funcmath.utility.Hash;

import java.lang.reflect.Method;

public abstract class Mod {
  // LevelRegister.registerType(Level l); - регистрация уровневого типа
  // LevelRegister.addLevelInfo(LevelInfo li, boolean rewrite); - регистрация уровня
  // для LevelMaker

  // SimpleFunctionRegister.registerSimpleFunction(SimpleFunction f); - регистрация простейшей
  // функции
  // TypeRegister.registerType(MathObject m); - регистрация типа математического объекта
  // TypeRegister.registerConvert(MathObject input, MathObject output, Method convert); -
  // регистрация перевода из одного математического объекта в другой

  protected void registerLevelType(Level level) {
    LevelRegister.registerType(level);
  }

  protected void registerLevelMaker(Level level, GPanel panel) {
    LevelRegister.registerLevelMaker(level, panel);
  }

  protected void addLevelInfo(LevelInfo li, boolean rewrite) {
    LevelRegister.addLevelInfo(li, rewrite);
  }

  protected void registerSimpleFunction(SimpleFunction func) {
    SimpleFunctionRegister.registerSimpleFunction(func);
  }

  protected void registerType(MathObject m) {
    TypeRegister.registerType(m);
  }

  protected void registerTypeConvert(MathObject input, MathObject output, Method convert) {
    TypeRegister.registerConvert(input, output, convert);
  }

  public abstract void init();

  public abstract GPanel
      getModPanel(); // внутри него делайте свою систему изменения панели, независимо от GameFrame

  public abstract String getName();

  public abstract Hash getHash();

  public abstract Hash getVersion();
}
