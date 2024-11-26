package funcmath.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class GameFrame extends JFrame implements MouseListener {
  Button button;

  public GameFrame() {
    this.setTitle("func(math)");
    this.setSize(1920, 1040);
    // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // this.setUndecorated(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.addMouseListener(this);

    button = new Button(100, 100, 200, 50, "data/images/test.png");

    this.setVisible(true);
  }

  public void draw(Graphics g) {
    button.paint(g);
  }

  @Override
  public void paint(Graphics g) {
    BufferStrategy bufferStrategy = getBufferStrategy();        // Обращаемся к стратегии буферизации
    if (bufferStrategy == null) {                               // Если она еще не создана
      createBufferStrategy(2);                                // то создаем ее
      bufferStrategy = getBufferStrategy();                   // и опять обращаемся к уже наверняка созданной стратегии
    }
    g = bufferStrategy.getDrawGraphics();                       // Достаем текущую графику (текущий буфер)
    g.clearRect(0, 0, getWidth(), getHeight());                 // Очищаем наш холст (ведь там остался предыдущий кадр)

    draw(g);

    g.dispose();                // Освободить все временные ресурсы графики (после этого в нее уже нельзя рисовать)
    bufferStrategy.show();      // Сказать буферизирующей стратегии отрисовать новый буфер (т.е. поменять показываемый и обновляемый буферы местами)
  }

  public void changePanel(String newPanel) {}

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
}
