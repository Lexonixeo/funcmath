package funcmath.gui.swing;

import funcmath.utility.Helper;
import javax.swing.*;

public class GLabel extends JLabel {
  public GLabel() {
    super();
  }

  @Override
  public void setText(String text) {
    super.setText(Helper.toHtmlString(text));
  }
}
