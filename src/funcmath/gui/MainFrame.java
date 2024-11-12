package funcmath.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class MainFrame extends JFrame implements MouseListener {

  public MainFrame() {
    this.setTitle("func(math)");
    this.setSize(1920, 1040);
    // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // this.setUndecorated(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  public void changePanel(String newPanel) {
    switch (newPanel) {
    }
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
}
