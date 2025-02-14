package funcmath.gui.swing;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GImage extends JLabel {
  public GImage(String imagePath) {
    try {
      Image img = ImageIO.read(new File(imagePath));
      this.setIcon(new ImageIcon(img));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
