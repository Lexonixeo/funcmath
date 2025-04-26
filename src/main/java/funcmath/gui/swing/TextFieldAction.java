package funcmath.gui.swing;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

public abstract class TextFieldAction implements KeyEventDispatcher, FocusListener {
  boolean firstFocus = false;

  public abstract void onEnterPress();

  public abstract void onFirstFocus();

  @Override
  public boolean dispatchKeyEvent(KeyEvent e) {
    if (e.getID() == KeyEvent.KEY_PRESSED) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        onEnterPress();
      }
    }
    return false;
  }

  @Override
  public void focusGained(FocusEvent e) {
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    if (!firstFocus) {
      onFirstFocus();
      firstFocus = true;
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
  }
}
