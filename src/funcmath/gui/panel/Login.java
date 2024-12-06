package funcmath.gui.panel;

import funcmath.gui.utility.ButtonAction;
import funcmath.gui.utility.GButton;
import funcmath.gui.utility.GPanel;
import funcmath.gui.utility.GTextField;
import funcmath.utility.Log;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Login extends GPanel {
  public Login() {
    this.setLayout(null);
    this.setBackground(Color.black);

    GTextField x = new GTextField(300, 300, 100, 50, null);
    this.add(x);

    GButton y = new GButton(100, 100, 200, 50, "data/images/test.png", new ButtonAction() {
      @Override
      public void onClick() {}
    });
    this.add(y);

    this.validate();
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0,0, 1920, 1080);
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
