package funcmath.gui.swing;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GTextArea extends JTextArea {
    int x, y;
    int width, height;

    boolean firstFocus = false;

    public GTextArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setLocation(x, y);
        this.setSize(width, height);

        this.addFocusListener(
                new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (!firstFocus) {
                            setText("");
                            firstFocus = true;
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                    }
                });
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
