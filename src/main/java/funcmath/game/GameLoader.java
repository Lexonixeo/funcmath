package funcmath.game;

import java.io.File;

public class GameLoader {
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
    generateDirectories();
  }
}
