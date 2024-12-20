package funcmath.gui.swing;

import funcmath.exceptions.GuiException;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GButton extends JButton {
  int x, y;
  int width, height;
  String imagePath;

  public GButton(int x, int y, int width, int height, String imagePath, ButtonAction action) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.imagePath = imagePath;
    this.setLocation(x, y);
    this.setSize(width, height);
    if (imagePath != null) {
      try {
        Image img = ImageIO.read(new File(imagePath));
        this.setIcon(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
      } catch (Exception ex) {
        throw new GuiException(ex);
      }
    }
    this.addActionListener(action);
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
