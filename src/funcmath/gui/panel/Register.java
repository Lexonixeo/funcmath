package funcmath.gui.panel;

import funcmath.gui.swing.GPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Register extends GPanel {
  public Register() {
    this.setLayout(null);
    this.setBackground(Color.black);

    this.validate();
    this.repaint();
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent e) {
    return false;
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mouseDragged(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {}
}
