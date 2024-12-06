package funcmath.gui.utility;

import funcmath.exceptions.GuiException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

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
        try {
            Image img = ImageIO.read(new File(imagePath));
            this.setIcon(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        } catch (Exception ex) {
            throw new GuiException(ex);
        }
        this.addActionListener(action);
    }
}
