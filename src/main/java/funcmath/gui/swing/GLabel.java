package funcmath.gui.swing;

import javax.swing.*;

public class GLabel extends JLabel {
  int x, y;
  int width, height;

  public GLabel(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.setLocation(x, y);
    this.setSize(width, height);
  }

  @Override
  public void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
    super.setLocation(x, y);
  }

  @Override
  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
    super.setSize(width, height);
  }
}
