package funcmath.gui.utility;

import funcmath.exceptions.GuiException;
import funcmath.object.MathObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GMathObject extends JPanel {
    int x, y;
    int width, height;
    MathObject mathObject;

    public GMathObject(int x, int y, int width, int height, MathObject mathObject) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mathObject = mathObject;
        this.setLocation(x, y);
        this.setSize(width, height);
    }

    public void drag(int x, int y) {
        this.x = x;
        this.y = y;
        this.setLocation(x, y);
    }
}
