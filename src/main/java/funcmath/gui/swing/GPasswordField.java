package funcmath.gui.swing;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

public class GPasswordField extends JPasswordField {
  KeyboardFocusManager manager;

  int x, y;
  int width, height;
  TextFieldAction action;
  boolean firstFocus = false;
  boolean show = true;

  public GPasswordField() {
    manager =
        KeyboardFocusManager
            .getCurrentKeyboardFocusManager(); // менеджер по трудоустройству слушателей клавиатуры

    this.setShow(true);
  }

  public void setTextFieldAction(TextFieldAction action) {
    this.action = action;
    this.addFocusListener(
        new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {
            manager.addKeyEventDispatcher(action);
            if (!firstFocus) {
              setText("");
              firstFocus = true;
              setShow(false);
            }
          }

          @Override
          public void focusLost(FocusEvent e) {
            manager.removeKeyEventDispatcher(action);
          }
        });
  }

  public void setShow(boolean show) {
    this.show = show;
    if (show) {
      this.setEchoChar((char) 0);
    } else {
      setEchoChar('*');
    }
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
