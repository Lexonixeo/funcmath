package funcmath.gui.swing;

import funcmath.game.GameLoader;
import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.level.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GLevelNamePanel extends GConstructorPanel {
  LevelPrimaryKey levelKey;
  LevelState levelState;
  JLabel nameLabel;
  JLabel aboutLabel;
  JLabel statsLabel;
  JButton continueButton;
  JButton playButton;

  public GLevelNamePanel(LevelPrimaryKey key) {
    this.levelKey = key;
    levelState = GameLoader.getPlayer().getLevelState(key);
    super(); // TODO: как-то исправить preview-функцию
  }

  protected void initBeforeComponents() {}

  protected void initComponents() {
    nameLabel = new JLabel();
    nameLabel.setText("Уровень №" + levelKey.ID() + ": " + LevelRegister.getName(levelKey));
    nameLabel.setFont(Fonts.COMIC_SANS_MS_30);
    nameLabel.setForeground(Color.white);

    // TODO: а что тут вообще должно быть?
    aboutLabel = new JLabel();
    aboutLabel.setFont(Fonts.COMIC_SANS_MS_30);
    aboutLabel.setForeground(Color.white);
    aboutLabel.setText("Уровень №" + levelKey.ID() + ": " + LevelRegister.getName(levelKey));
    aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

    statsLabel = new JLabel();
    statsLabel.setFont(Fonts.COMIC_SANS_MS_30);
    statsLabel.setForeground(Color.white);
    LevelStatistics stats = GameLoader.getPlayer().getLevelStatistics(levelKey);
    if (stats != null) {
      statsLabel.setText(stats.toString());
    }
    statsLabel.setHorizontalAlignment(SwingConstants.CENTER);

    continueButton = new JButton();
    continueButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameLoader.setCurrentLevel(LevelRegister.getLevel(levelState));
            GameFrame.getInstance().changePanel("level");
          }
        });
    continueButton.setFont(Fonts.COMIC_SANS_MS_30);
    continueButton.setText("Продолжить!");
    continueButton.setForeground(Color.white);
    continueButton.setBackground(new Color(56, 109, 80));

    playButton = new JButton();
    playButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameLoader.setCurrentLevel(LevelRegister.getLevel(levelKey));
            GameFrame.getInstance().changePanel("level");
          }
        });
    playButton.setFont(Fonts.COMIC_SANS_MS_30);
    playButton.setText("Играть!");
    playButton.setForeground(Color.white);
    playButton.setBackground(new Color(56, 109, 80));
  }

  protected void initBeforeConstruct() {}

  protected void constructPanel() {
    this.setLayout(new BorderLayout());
    this.setBorder(new EmptyBorder(10, 10, 10, 10));
    this.setBackground(new Color(56 * 5 / 4, 109 * 5 / 4, 80 * 5 / 4));
    this.add(nameLabel, BorderLayout.WEST);
    // this.add(aboutLabel, BorderLayout.WEST);
    this.add(statsLabel, BorderLayout.CENTER);

    GOpaquePanel eastPanel = new GOpaquePanel();
    eastPanel.setLayout(new BorderLayout());
    if (levelState != null) {
      eastPanel.add(continueButton, BorderLayout.WEST);
    }
    eastPanel.add(playButton, BorderLayout.EAST);
    this.add(eastPanel, BorderLayout.EAST);

    this.setForeground(Color.white);
  }

  protected void initAfterConstruct() {}
}
