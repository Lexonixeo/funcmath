package funcmath.gui.panel;

import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.ButtonAction;
import funcmath.gui.swing.GPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Menu extends GPanel {
  public Menu() {
    this.setLayout(new GridLayout(3, 1, 50, 50));
    this.setBorder(new EmptyBorder(50, 50, 50, 50));
    this.setBackground(Color.black);

    JLabel nameLabel = new JLabel();
    nameLabel.setText("func(math)");
    nameLabel.setFont(Fonts.COMIC_SANS_MS_40);
    nameLabel.setForeground(Color.white);
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(nameLabel);

    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridLayout(4, 1));
    centerPanel.setBackground(this.getBackground());
    this.add(centerPanel);

    JLabel welcomeLabel = new JLabel();
    welcomeLabel.setText(
        "Добро пожаловать, " + GameFrame.getInstance().getPlayer().getName() + "!");
    welcomeLabel.setFont(Fonts.COMIC_SANS_MS_30);
    welcomeLabel.setForeground(Color.white);
    centerPanel.add(welcomeLabel);

    JLabel prototypeLabel = new JLabel();
    prototypeLabel.setText("Вы сейчас играете в IV прототип func(math).");
    prototypeLabel.setFont(Fonts.COMIC_SANS_MS_30);
    prototypeLabel.setForeground(Color.white);
    centerPanel.add(prototypeLabel);

    JLabel whyLabel = new JLabel();
    whyLabel.setText("Он написан для реализации графического интерфейса игры.");
    whyLabel.setFont(Fonts.COMIC_SANS_MS_30);
    whyLabel.setForeground(Color.white);
    centerPanel.add(whyLabel);

    JLabel menuLabel = new JLabel();
    menuLabel.setText("Вы находитесь в главном меню.");
    menuLabel.setFont(Fonts.COMIC_SANS_MS_30);
    menuLabel.setForeground(Color.white);
    centerPanel.add(menuLabel);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(2, 5, 50, 50));
    bottomPanel.setBackground(this.getBackground());
    this.add(bottomPanel);

    JButton playButton = new JButton();
    playButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    playButton.setFont(Fonts.COMIC_SANS_MS_30);
    playButton.setText("Играть!");
    playButton.setForeground(Color.white);
    playButton.setBackground(new Color(56, 109, 80));
    bottomPanel.add(playButton);

    JButton levelListButton = new JButton();
    levelListButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    levelListButton.setFont(Fonts.COMIC_SANS_MS_30);
    levelListButton.setText("Кампания!");
    levelListButton.setForeground(Color.white);
    levelListButton.setBackground(new Color(56, 109, 80));
    bottomPanel.add(levelListButton);

    JButton customLevelListButton = new JButton();
    customLevelListButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    customLevelListButton.setFont(Fonts.COMIC_SANS_MS_30);
    customLevelListButton.setText("Уровни");
    customLevelListButton.setForeground(Color.white);
    customLevelListButton.setBackground(new Color(56, 109, 80));
    bottomPanel.add(customLevelListButton);

    JButton statsButton = new JButton();
    statsButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    statsButton.setFont(Fonts.COMIC_SANS_MS_30);
    statsButton.setText("Статистика");
    statsButton.setForeground(Color.white);
    statsButton.setBackground(new Color(56, 109, 80));
    bottomPanel.add(statsButton);

    JButton settingsButton = new JButton();
    settingsButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    settingsButton.setFont(Fonts.COMIC_SANS_MS_30);
    settingsButton.setText("Настройки");
    settingsButton.setForeground(Color.white);
    settingsButton.setBackground(new Color(56, 109, 80));
    bottomPanel.add(settingsButton);

    JButton guideButton = new JButton();
    guideButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    guideButton.setFont(Fonts.COMIC_SANS_MS_30);
    guideButton.setText("Туториал");
    guideButton.setForeground(Color.white);
    guideButton.setBackground(new Color(56, 109, 80));
    bottomPanel.add(guideButton);

    JButton functionMakerButton = new JButton();
    functionMakerButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    functionMakerButton.setFont(Fonts.COMIC_SANS_MS_30);
    functionMakerButton.setText("Создать функцию");
    functionMakerButton.setForeground(Color.white);
    functionMakerButton.setBackground(new Color(56, 109, 80));
    bottomPanel.add(functionMakerButton);

    JButton levelMakerButton = new JButton();
    levelMakerButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    levelMakerButton.setFont(Fonts.COMIC_SANS_MS_30);
    levelMakerButton.setText("Создать уровень");
    levelMakerButton.setForeground(Color.white);
    levelMakerButton.setBackground(new Color(56, 109, 80));
    bottomPanel.add(levelMakerButton);

    JButton button1 = new JButton();
    button1.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    button1.setFont(Fonts.COMIC_SANS_MS_30);
    button1.setText("");
    button1.setEnabled(false);
    button1.setForeground(Color.white);
    button1.setBackground(new Color(56, 109, 80));
    bottomPanel.add(button1);

    JButton button2 = new JButton();
    button2.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {}
        });
    button2.setFont(Fonts.COMIC_SANS_MS_30);
    button2.setText("");
    button2.setEnabled(false);
    button2.setForeground(Color.white);
    button2.setBackground(new Color(56, 109, 80));
    bottomPanel.add(button2);

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
