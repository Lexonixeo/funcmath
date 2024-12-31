package funcmath.gui.panel;

import funcmath.gui.GameFrame;
import funcmath.gui.swing.ButtonAction;
import funcmath.gui.swing.GButton;
import funcmath.gui.swing.GLabel;
import funcmath.gui.swing.GPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu extends GPanel {
  public Menu() {
    this.setLayout(null);
    this.setBackground(Color.black);

    GLabel nameLabel = new GLabel((1920 - 250) / 2, 50, 250, 50);
    nameLabel.setText("func(math)");
    nameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    nameLabel.setForeground(Color.white);
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(nameLabel);

    GLabel welcomeLabel = new GLabel(100, 150, 1000, 50);
    welcomeLabel.setText("Добро пожаловать, " + GameFrame.getInstance().getPlayer().getName() + "!");
    welcomeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    welcomeLabel.setForeground(Color.white);
    this.add(welcomeLabel);

    GLabel prototypeLabel = new GLabel(100, 200, 1000, 50);
    prototypeLabel.setText("Вы сейчас играете в IV прототип func(math).");
    prototypeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    prototypeLabel.setForeground(Color.white);
    this.add(prototypeLabel);

    GLabel whyLabel = new GLabel(100, 250, 1000, 50);
    whyLabel.setText("Он написан для реализации графического интерфейса игры.");
    whyLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    whyLabel.setForeground(Color.white);
    this.add(whyLabel);

    GLabel menuLabel = new GLabel(100, 350, 1000, 50);
    menuLabel.setText("Вы находитесь в главном меню.");
    menuLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    menuLabel.setForeground(Color.white);
    this.add(menuLabel);

    GButton playButton = new GButton(100, 600, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    playButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    playButton.setText("Играть!");
    playButton.setForeground(Color.white);
    playButton.setBackground(new Color(56, 109, 80));
    this.add(playButton);

    GButton levelListButton = new GButton(464, 600, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    levelListButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    levelListButton.setText("Кампания");
    levelListButton.setForeground(Color.white);
    levelListButton.setBackground(new Color(56, 109, 80));
    this.add(levelListButton);

    GButton customLevelListButton = new GButton(828, 600, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    customLevelListButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    customLevelListButton.setText("Уровни");
    customLevelListButton.setForeground(Color.white);
    customLevelListButton.setBackground(new Color(56, 109, 80));
    this.add(customLevelListButton);

    GButton statsButton = new GButton(1192, 600, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    statsButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    statsButton.setText("Статистика");
    statsButton.setForeground(Color.white);
    statsButton.setBackground(new Color(56, 109, 80));
    this.add(statsButton);

    GButton settingsButton = new GButton(1556, 600, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    settingsButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    settingsButton.setText("Настройки");
    settingsButton.setForeground(Color.white);
    settingsButton.setBackground(new Color(56, 109, 80));
    this.add(settingsButton);

    GButton guideButton = new GButton(100, 750, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    guideButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    guideButton.setText("Туториал");
    guideButton.setForeground(Color.white);
    guideButton.setBackground(new Color(56, 109, 80));
    this.add(guideButton);

    GButton functionMakerButton = new GButton(464, 750, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    functionMakerButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    functionMakerButton.setText("Создать функцию");
    functionMakerButton.setForeground(Color.white);
    functionMakerButton.setBackground(new Color(56, 109, 80));
    this.add(functionMakerButton);

    GButton levelMakerButton = new GButton(828, 750, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    levelMakerButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    levelMakerButton.setText("Создать уровень");
    levelMakerButton.setForeground(Color.white);
    levelMakerButton.setBackground(new Color(56, 109, 80));
    this.add(levelMakerButton);

    GButton button1 = new GButton(1192, 750, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    button1.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    button1.setText("");
    button1.setEnabled(false);
    button1.setForeground(Color.white);
    button1.setBackground(new Color(56, 109, 80));
    this.add(button1);

    GButton button2 = new GButton(1556, 750, 264, 100, new ButtonAction() {
      @Override
      public void onClick() {

      }
    });
    button2.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    button2.setText("");
    button2.setEnabled(false);
    button2.setForeground(Color.white);
    button2.setBackground(new Color(56, 109, 80));
    this.add(button2);

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
