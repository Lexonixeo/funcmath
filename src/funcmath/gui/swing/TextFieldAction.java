package funcmath.gui.swing;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class TextFieldAction implements KeyEventDispatcher {
  public abstract void onEnterPress();

  @Override
  public boolean dispatchKeyEvent(KeyEvent e) {
    if (e.getID() == KeyEvent.KEY_PRESSED) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        onEnterPress();
      }
    }
    return false;
  }
}
