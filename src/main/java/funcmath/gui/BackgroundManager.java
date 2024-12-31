package funcmath.gui;

import funcmath.gui.paint.Background;
import java.util.HashMap;

public class BackgroundManager {
  private static final BackgroundManager instance = new BackgroundManager(); // singleton

  Background currentBackground;
  HashMap<String, Background> backgrounds;

  BackgroundManager() {
    backgrounds = new HashMap<>();
    initBackgrounds();

    currentBackground = backgrounds.get("school desk");
  }

  void initBackgrounds() {
    backgrounds.put("school desk", new Background());
  }

  public Background getCurrentBackground() {
    return currentBackground;
  }

  public static BackgroundManager getInstance() {
    return instance;
  }
}
