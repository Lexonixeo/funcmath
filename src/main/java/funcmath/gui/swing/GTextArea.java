package funcmath.gui.swing;

import javax.swing.*;

public class GTextArea extends JTextArea {
  GOutputStream outputStream;

  public GTextArea(GOutputStream outputStream) {
    this.outputStream = outputStream;
  }

  public void textUpdate() {
    if (outputStream != null) {
      SwingUtilities.invokeLater(
              () -> {
                setText("");
                for (String a : outputStream.getText()) {
                  append(a);
                }
              });
    }
  }
}
