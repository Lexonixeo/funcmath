package funcmath.gui.swing;

import funcmath.exceptions.GuiException;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GImage extends JLabel {
  public GImage(String imagePath) {
    try {
      Image img = ImageIO.read(new File(imagePath));
      this.setIcon(new ImageIcon(img));
    } catch (Exception ex) {
      throw new GuiException(ex);
    }
  }
}
