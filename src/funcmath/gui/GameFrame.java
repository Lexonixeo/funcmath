package funcmath.gui;

import funcmath.gui.panels.LoginPanel;
import funcmath.gui.panels.MenuPanel;
import funcmath.gui.utility.GamePanel;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class GameFrame extends JFrame {
  GamePanel panel;
  KeyboardFocusManager manager;

  public GameFrame() {
    this.setTitle("func(math)");
    this.setSize(1920, 1040);
    // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // this.setUndecorated(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    manager =
        KeyboardFocusManager
            .getCurrentKeyboardFocusManager(); // менеджер по трудоустройству слушателей клавиатуры

    panel = new LoginPanel();

    this.add(panel);

    this.addMouseListener(panel);
    this.addMouseMotionListener(panel);
    manager.addKeyEventDispatcher(panel);

    this.setVisible(true);

    while (true) {
      this.repaint();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void paint(Graphics g) {
    BufferStrategy bufferStrategy = getBufferStrategy(); // Обращаемся к стратегии буферизации
    if (bufferStrategy == null) { // Если она еще не создана
      createBufferStrategy(2); // то создаем ее
      bufferStrategy =
          getBufferStrategy(); // и опять обращаемся к уже наверняка созданной стратегии
    }
    g = bufferStrategy.getDrawGraphics(); // Достаем текущую графику (текущий буфер)
    g.clearRect(
        0, 0, getWidth(), getHeight()); // Очищаем наш холст (ведь там остался предыдущий кадр)

    panel.paint(g);

    g.dispose(); // Освободить все временные ресурсы графики (после этого в нее уже нельзя рисовать)
    bufferStrategy
        .show(); // Сказать буферизирующей стратегии отрисовать новый буфер (т.е. Поменять
                 // показываемый и обновляемый буферы местами)
  }

  public void changePanel(String newPanel) {
    this.removeMouseListener(panel);
    this.removeMouseMotionListener(panel);
    manager.removeKeyEventDispatcher(panel);
    this.remove(panel);

    panel = new MenuPanel();

    this.add(panel);
    this.addMouseListener(panel);
    this.addMouseMotionListener(panel);
    manager.addKeyEventDispatcher(panel);
  }
}
