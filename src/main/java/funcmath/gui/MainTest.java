package funcmath.gui;

import funcmath.game.Level;
import funcmath.game.defaultlevel.DefaultLevel;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import funcmath.utility.Log;

public class MainTest {
  public static void main(String[] args) {
    Helper.generateDirectories();
    Log.getInstance().write("Directories is generated! Player has entered the game.");
    MathObject.loadMathObjects();
    Level.loadLevelType(new DefaultLevel());
    GameFrame.getInstance();
  }
}
