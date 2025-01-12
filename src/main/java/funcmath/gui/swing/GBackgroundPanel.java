package funcmath.gui.swing;

import funcmath.gui.BackgroundManager;
import java.awt.*;

public abstract class GBackgroundPanel extends GPanel {
  @Override
  protected void paintComponent(Graphics g) {
    BackgroundManager.getInstance().getCurrentBackground().draw(g);
  }
}
