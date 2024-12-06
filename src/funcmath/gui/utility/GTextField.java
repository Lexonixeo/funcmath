package funcmath.gui.utility;

import javax.swing.*;

public class GTextField extends JTextField {
  int x, y;
  int width, height;
  String imagePath;

  public GTextField(int x, int y, int width, int height, String imagePath) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.imagePath = imagePath;
    this.setLocation(x, y);
    this.setSize(width, height);
  }
}
