package funcmath.gui;

import funcmath.functions.FunctionMakerPanel;
import funcmath.game.GameLoader;
import funcmath.gui.panel.*;
import funcmath.gui.panel.Menu;
import funcmath.gui.swing.GPanel;
import funcmath.level.LevelRegister;
import funcmath.level.PlayFlag;
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
  private static final GameFrame instance = new GameFrame(); // singleton

  GPanel currentPanel;
  KeyboardFocusManager manager;

  private GameFrame() {
    this.setTitle("func(math)");
    this.setSize(1920, 825);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // this.setUndecorated(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setContentPane(LoadingPanel.getInstance());
    this.setVisible(true);

    manager =
        KeyboardFocusManager
            .getCurrentKeyboardFocusManager(); // менеджер по трудоустройству слушателей клавиатуры
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

  public void registerPanel(String panelKey, GPanel panel) {}

  // // public static ArrayList<Pair<String, Foo>> mods;
    // это смена панели на определенную без ресета
  public void changePanel(GPanel panel) {
    this.removeMouseListener(currentPanel);
    this.removeMouseMotionListener(currentPanel);
    manager.removeKeyEventDispatcher(currentPanel);

    currentPanel = panel;

    setContentPane(currentPanel);
    addMouseListener(currentPanel);
    addMouseMotionListener(currentPanel);
    manager.addKeyEventDispatcher(currentPanel);

    invalidate();
    validate();
    repaint();
  }

  // это смена панели на заданную с ресетом
  public void changePanel(String panelKey) {
    this.removeMouseListener(currentPanel);
    this.removeMouseMotionListener(currentPanel);
    manager.removeKeyEventDispatcher(currentPanel);

    currentPanel =
        switch (panelKey) {
          case "login" -> new Login();
          case "register" -> new Register();
          case "menu" -> new Menu();
          case "stats" -> new Statistics();
          case "settings" -> new Settings();
          case "mod list" -> new ModListPanel();
          case "level" -> GameLoader.getCurrentLevel().getLevelEngine().getLevelPanel();
          case "level maker" -> LevelRegister.getLevelMaker("default");
          case "function maker" -> new FunctionMakerPanel();
          case "level list" -> new LevelListPanel(1, PlayFlag.DEFAULT);
          case "custom level list" -> new LevelListPanel(1, PlayFlag.CUSTOM);
          case "loading" -> LoadingPanel.getInstance();
          default -> /*
                     for (int i = 0 ...) {
                         if (mods.get(i).first == panelKey) {
                             ..second.exec();
                         }
                     }
                     */
              throw new IllegalStateException("Unexpected value: " + panelKey);
        };
    currentPanel.reset();
    currentPanel.start();

    setContentPane(currentPanel);
    addMouseListener(currentPanel);
    addMouseMotionListener(currentPanel);
    manager.addKeyEventDispatcher(currentPanel);

    invalidate();
    validate();
    repaint();
  }

  public GPanel getCurrentPanel() {
    return currentPanel;
  }

  public static GameFrame getInstance() {
    return instance;
  }
}
