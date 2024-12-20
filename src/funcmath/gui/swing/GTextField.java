package funcmath.gui.swing;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

public class GTextField extends JTextField {
  KeyboardFocusManager manager;

  int x, y;
  int width, height;
  String imagePath;
  TextFieldAction action;

  boolean firstFocus = false;

  public GTextField(
      int x,
      int y,
      int width,
      int height,
      String imagePath,
      TextFieldAction action,
      String defaultText) {
    manager =
        KeyboardFocusManager
            .getCurrentKeyboardFocusManager(); // менеджер по трудоустройству слушателей клавиатуры

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.imagePath = imagePath;
    this.setLocation(x, y);
    this.setSize(width, height);
    this.action = action;

    this.setText(defaultText);

    this.addFocusListener(
        new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {
            manager.addKeyEventDispatcher(action);
            if (!firstFocus) {
              setText("");
              firstFocus = true;
            }
          }

          @Override
          public void focusLost(FocusEvent e) {
            manager.removeKeyEventDispatcher(action);
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
