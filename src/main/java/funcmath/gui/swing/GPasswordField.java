package funcmath.gui.swing;

import javax.swing.*;

public class GPasswordField extends JPasswordField {
  boolean show = true;

  public void setShow(boolean show) {
    this.show = show;
    if (show) {
      this.setEchoChar((char) 0);
    } else {
      setEchoChar('*');
    }
  }
}
