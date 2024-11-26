package funcmath.gui;

import funcmath.exceptions.GuiException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Button {
  int x, y;
  int width, height;
  String imagePath;

  public Button(int x, int y, int width, int height, String imagePath) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.imagePath = imagePath;
  }

  public void paint(Graphics g) {
    BufferedImage image;
    try {
      image = ImageIO.read(new File(this.imagePath));
    } catch (IOException e) {
      throw new GuiException(e);
    }
    g.drawImage(image, x, y, width, height, null);
  }

  private boolean isHit(int mouseX, int mouseY) {
    return (x <= mouseX && mouseX <= x + width && y <= mouseY && mouseY <= y + height);
  }

  public void onMouseHit(int mouseX, int mouseY) {
    if (isHit(mouseX, mouseY)) {
      System.out.println("Button hit!");
    }
  }
}
