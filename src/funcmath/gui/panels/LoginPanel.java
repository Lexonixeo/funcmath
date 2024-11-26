package funcmath.gui.panels;

import funcmath.gui.utility.Button;
import funcmath.gui.utility.GamePanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LoginPanel extends GamePanel {
  funcmath.gui.utility.Button button;

  public LoginPanel() {
    button = new Button(100, 100, 200, 50, "data/images/test.png", () -> {});
  }

  @Override
  protected void paintComponent(Graphics g) {
    button.paint(g);
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    button.onMouseHit(e.getX(), e.getY());
  }

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public boolean dispatchKeyEvent(KeyEvent e) {
    return false;
  }

  @Override
  public void mouseDragged(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {}
}
