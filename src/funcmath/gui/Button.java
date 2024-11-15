package funcmath.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Button {
  int x, y;
  int width, height;
  BufferedImage image;

  public Button(int x, int y, int width, int height, String imagePath) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    try {
      this.image = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
