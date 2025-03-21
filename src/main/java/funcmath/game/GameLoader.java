package funcmath.game;

import funcmath.auth.Player;
import funcmath.gui.GameFrame;
import funcmath.level.Level;
import funcmath.level.LevelRegister;
import funcmath.packs.defaultpack.DefaultPack;
import java.io.File;

public class GameLoader {
  private static Thread levelUpdater;

  private static Level currentLevel;
  private static Player player;

  public static void generateDirectories() {
    Logger.write("Генерируются директории...");
    new File("data").mkdirs(); // бессмысленно, т.к. в Logger это уже сгенерировалось
    new File("data/logs").mkdirs(); // бессмысленно, т.к. в Logger это уже сгенерировалось
    new File("data/customLevels").mkdirs();
    new File("data/functions").mkdirs();
    new File("data/levels").mkdirs();
    new File("data/players").mkdirs();
    new File("data/preLevels").mkdirs();
    new File("data/locales").mkdirs();
    new File("data/objects").mkdirs();
    new File("data/images").mkdirs();
  }

  public static void main(String[] args) {
    GameFrame.getInstance().changePanel("loading");
    GameLoader.generateDirectories();
    new DefaultPack().init();
    startLevelUpdaterThread();
    GameFrame.getInstance().changePanel("login");
  }

  public static void interruptLevelUpdater() {
    levelUpdater.interrupt();
  }

  private static void startLevelUpdaterThread() {
    levelUpdater =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                while (!levelUpdater.isInterrupted()) {
                  // wait 1000 ms
                  try {
                    Thread.sleep(1000);
                  } catch (InterruptedException ignored) {
                  }
                  LevelRegister.updateLevels();
                }
              }
            });
    levelUpdater.setName("FM-LL-Update");
    levelUpdater.setDaemon(true);
    levelUpdater.start();
  }

  public static Level getCurrentLevel() {
    return currentLevel;
  }

  public static void setCurrentLevel(Level currentLevel) {
    GameLoader.currentLevel = currentLevel;
  }

  public static void exitCurrentLevel() {
    switch (currentLevel.getLevelInfo().getPlayFlag()) {
      case DEFAULT ->
          player.addDefaultLevel(currentLevel.getCurrentLevelState(), currentLevel.getStatistics());
      case CUSTOM ->
          player.addCustomLevel(currentLevel.getCurrentLevelState(), currentLevel.getStatistics());
    }
    currentLevel = null;
  }

  public static Level getLastLevel() {
    return LevelRegister.getLevel(player.getLastLevel());
  }

  public static void setPlayer(Player player) {
    GameLoader.player = player;
  }

  public static Player getPlayer() {
    return player;
  }
}
