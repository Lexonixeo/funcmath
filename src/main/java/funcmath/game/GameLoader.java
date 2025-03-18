package funcmath.game;

import funcmath.gui.GameFrame;
import funcmath.level.LevelRegister;
import funcmath.packs.defaultpack.DefaultPack;
import java.io.File;

public class GameLoader {
  private static Thread levelUpdater;

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
    levelUpdater.setName("Level list updater");
    levelUpdater.setDaemon(true);
    levelUpdater.start();
  }
}
