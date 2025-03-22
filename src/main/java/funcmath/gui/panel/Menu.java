package funcmath.gui.panel;

import funcmath.game.GameLoader;
import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.GBackgroundPanel;
import funcmath.gui.swing.GOpaquePanel;
import funcmath.level.LevelRegister;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Menu extends GBackgroundPanel {
  JLabel nameLabel;
  JLabel welcomeLabel;
  JLabel prototypeLabel;
  JLabel whyLabel;
  JLabel menuLabel;
  JLabel errorLabel;
  JButton playButton;
  JButton levelListButton;
  JButton customLevelListButton;
  JButton statsButton;
  JButton settingsButton;
  JButton modListButton;
  JButton functionMakerButton;
  JButton levelMakerButton;
  JButton companyButton;
  JButton exitButton;

  @Override
  protected void initBeforeComponents() {
    GameLoader.getPlayer().save();
  }

  @Override
  protected void initComponents() {
    nameLabel = new JLabel();
    nameLabel.setText("func(math)");
    nameLabel.setFont(Fonts.COMIC_SANS_MS_40);
    nameLabel.setForeground(Color.white);
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

    welcomeLabel = new JLabel();
    welcomeLabel.setText("Добро пожаловать, " + GameLoader.getPlayer().getName() + "!");
    welcomeLabel.setFont(Fonts.COMIC_SANS_MS_30);
    welcomeLabel.setForeground(Color.white);

    prototypeLabel = new JLabel();
    prototypeLabel.setText("Вы сейчас играете в IV прототип func(math).");
    prototypeLabel.setFont(Fonts.COMIC_SANS_MS_30);
    prototypeLabel.setForeground(Color.white);

    whyLabel = new JLabel();
    whyLabel.setText("Он написан для реализации графического интерфейса игры.");
    whyLabel.setFont(Fonts.COMIC_SANS_MS_30);
    whyLabel.setForeground(Color.white);

    menuLabel = new JLabel();
    menuLabel.setText("Вы находитесь в главном меню.");
    menuLabel.setFont(Fonts.COMIC_SANS_MS_30);
    menuLabel.setForeground(Color.white);

    errorLabel = new JLabel();
    errorLabel.setText("");
    errorLabel.setFont(Fonts.COMIC_SANS_MS_30);
    errorLabel.setForeground(Color.white);

    playButton = new JButton();
    playButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (GameLoader.getLastLevel() == null || GameLoader.getLastLevel().isCompleted()) {
              GameLoader.setCurrentLevel(
                  LevelRegister.getLevel(GameLoader.getPlayer().getFirstUncompletedLevel()));
              GameFrame.getInstance().changePanel("level");
            } else {
              GameLoader.setCurrentLevel(
                  LevelRegister.getLevel(GameLoader.getPlayer().getLastLevel()));
              GameFrame.getInstance().changePanel("level");
            }
          }
        });
    playButton.setFont(Fonts.COMIC_SANS_MS_30);
    if (GameLoader.getLastLevel() == null || GameLoader.getLastLevel().isCompleted()) {
      playButton.setText("Играть!");
    } else {
      playButton.setText("Продолжить!");
    }
    playButton.setForeground(Color.white);
    playButton.setBackground(new Color(56, 109, 80));

    levelListButton = new JButton();
    levelListButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameFrame.getInstance().changePanel("level list");
          }
        });
    levelListButton.setFont(Fonts.COMIC_SANS_MS_30);
    levelListButton.setText("Уровни");
    levelListButton.setForeground(Color.white);
    levelListButton.setBackground(new Color(56, 109, 80));

    customLevelListButton = new JButton();
    customLevelListButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameFrame.getInstance().changePanel("custom level list");
          }
        });
    customLevelListButton.setFont(Fonts.COMIC_SANS_MS_30);
    customLevelListButton.setText("Пользовательские уровни");
    customLevelListButton.setForeground(Color.white);
    customLevelListButton.setBackground(new Color(56, 109, 80));

    statsButton = new JButton();
    statsButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {}
        });
    statsButton.setFont(Fonts.COMIC_SANS_MS_30);
    statsButton.setText("Статистика");
    statsButton.setForeground(Color.white);
    statsButton.setBackground(new Color(56, 109, 80));
    statsButton.setEnabled(false);

    settingsButton = new JButton();
    settingsButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {}
        });
    settingsButton.setFont(Fonts.COMIC_SANS_MS_30);
    settingsButton.setText("Настройки");
    settingsButton.setForeground(Color.white);
    settingsButton.setBackground(new Color(56, 109, 80));
    settingsButton.setEnabled(false);

    modListButton = new JButton();
    modListButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {}
        });
    modListButton.setFont(Fonts.COMIC_SANS_MS_30);
    modListButton.setText("Моды");
    modListButton.setForeground(Color.white);
    modListButton.setBackground(new Color(56, 109, 80));
    modListButton.setEnabled(false);

    functionMakerButton = new JButton();
    functionMakerButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameFrame.getInstance().changePanel("function maker");
          }
        });
    functionMakerButton.setFont(Fonts.COMIC_SANS_MS_30);
    functionMakerButton.setText("Создать функцию");
    functionMakerButton.setForeground(Color.white);
    functionMakerButton.setBackground(new Color(56, 109, 80));

    levelMakerButton = new JButton();
    levelMakerButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameFrame.getInstance().changePanel("level maker");
          }
        });
    levelMakerButton.setFont(Fonts.COMIC_SANS_MS_30);
    levelMakerButton.setText("Создать уровень");
    levelMakerButton.setForeground(Color.white);
    levelMakerButton.setBackground(new Color(56, 109, 80));

    companyButton = new JButton();
    companyButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {}
        });
    companyButton.setFont(Fonts.COMIC_SANS_MS_30);
    companyButton.setText("Кампания");
    companyButton.setForeground(Color.white);
    companyButton.setBackground(new Color(56, 109, 80));
    companyButton.setEnabled(false);

    exitButton = new JButton();
    exitButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameLoader.stop();
          }
        });
    exitButton.setFont(Fonts.COMIC_SANS_MS_30);
    exitButton.setText("Выйти");
    exitButton.setForeground(Color.white);
    exitButton.setBackground(new Color(56, 109, 80));
  }

  @Override
  protected void initBeforeConstruct() {}

  @Override
  protected void constructPanel() {
    this.setLayout(new BorderLayout());
    this.setBorder(new EmptyBorder(50, 50, 50, 50));

    this.add(nameLabel, BorderLayout.NORTH);

    GOpaquePanel centerPanel = new GOpaquePanel();
    centerPanel.setLayout(new GridLayout(10, 1));
    this.add(centerPanel, BorderLayout.CENTER);
    centerPanel.add(welcomeLabel);
    centerPanel.add(prototypeLabel);
    centerPanel.add(whyLabel);
    centerPanel.add(new GOpaquePanel());
    centerPanel.add(menuLabel);
    centerPanel.add(new GOpaquePanel());
    centerPanel.add(new GOpaquePanel());
    centerPanel.add(errorLabel);
    centerPanel.add(new GOpaquePanel());
    centerPanel.add(new GOpaquePanel());

    GOpaquePanel bottomPanel = new GOpaquePanel();
    bottomPanel.setLayout(new GridLayout(2, 5, 50, 50));
    this.add(bottomPanel, BorderLayout.SOUTH);
    bottomPanel.add(playButton);
    bottomPanel.add(companyButton);
    bottomPanel.add(levelListButton);
    bottomPanel.add(customLevelListButton);
    bottomPanel.add(statsButton);
    bottomPanel.add(settingsButton);
    bottomPanel.add(modListButton);
    bottomPanel.add(functionMakerButton);
    bottomPanel.add(levelMakerButton);
    bottomPanel.add(exitButton);
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
}
