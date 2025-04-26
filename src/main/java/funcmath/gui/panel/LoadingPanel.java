package funcmath.gui.panel;

import funcmath.gui.Fonts;
import funcmath.gui.swing.GPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class LoadingPanel extends GPanel {
  private static final LoadingPanel instance = new LoadingPanel();

  JLabel wait;

  @Override
  protected void initBeforeComponents() {}

  @Override
  protected void initComponents() {
    wait = new JLabel();
    wait.setText("Пожалуйста, подождите...");
    wait.setFont(Fonts.COMIC_SANS_MS_50);
    wait.setForeground(Color.white);
    wait.setHorizontalAlignment(SwingConstants.CENTER);
  }

  @Override
  protected void initBeforeConstruct() {}

  @Override
  protected void constructPanel() {
    this.setBackground(Color.black);
    this.setLayout(new BorderLayout());

    this.add(wait, BorderLayout.CENTER);
  }

  @Override
  protected void initAfterConstruct() {}

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

  public static LoadingPanel getInstance() {
    return instance;
  }
}
