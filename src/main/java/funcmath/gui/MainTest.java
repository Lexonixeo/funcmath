package funcmath.gui;

import funcmath.level.LevelRegister;

public class MainTest {
  public static void main(String[] args) {
    LevelRegister.updateLevels();
    GameFrame.getInstance();
  }
}
