package funcmath.gui;

import funcmath.game.Player;
import funcmath.gui.panel.*;
import funcmath.gui.panel.Menu;
import funcmath.gui.swing.GPanel;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class GameFrame extends JFrame {
  private static final GameFrame instance = new GameFrame(); // singleton

  GPanel currentPanel;
  KeyboardFocusManager manager;
  HashMap<String, GPanel> panels;
  Player player;

  private GameFrame() {
    this.setTitle("func(math)");
    this.setSize(1920, 1040);
    // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // this.setUndecorated(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    manager =
        KeyboardFocusManager
            .getCurrentKeyboardFocusManager(); // менеджер по трудоустройству слушателей клавиатуры

    initPanels();

    currentPanel = panels.get("login");
    this.add(currentPanel);
    this.addMouseListener(currentPanel);
    this.addMouseMotionListener(currentPanel);
    manager.addKeyEventDispatcher(currentPanel);

    this.setVisible(true);
  }

  /*
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

    currentPanel.paint(g);

    g.dispose(); // Освободить все временные ресурсы графики (после этого в нее уже нельзя рисовать)
    bufferStrategy.show(); // Сказать буферизирующей стратегии отрисовать новый буфер
    // (т.е. Поменять показываемый и обновляемый буферы местами)
  }
   */

  private void initPanels() {
    panels = new HashMap<>();
    panels.put("login", new Login());
    panels.put("register", new Register());
    panels.put("menu", new Menu());
    panels.put("stats", new Statistics());
    panels.put("settings", new Settings());
    panels.put("tutorial", new Tutorial());
    panels.put("level", new LevelPanel());
    panels.put("level maker", new LevelMakerPanel());
    panels.put("level maker tutorial", new LevelMakerTutorial());
    panels.put("function maker", new FunctionMakerPanel());
    panels.put("function maker tutorial", new FunctionMakerTutorial());
  }

  public void changePanel(String panelKey) {
    this.removeMouseListener(currentPanel);
    this.removeMouseMotionListener(currentPanel);
    manager.removeKeyEventDispatcher(currentPanel);
    this.remove(currentPanel);

    currentPanel = panels.get(panelKey);

    this.add(currentPanel);
    this.addMouseListener(currentPanel);
    this.addMouseMotionListener(currentPanel);
    manager.addKeyEventDispatcher(currentPanel);

    this.invalidate();
    this.validate();
    this.repaint();
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public static GameFrame getInstance() {
    return instance;
  }
}
