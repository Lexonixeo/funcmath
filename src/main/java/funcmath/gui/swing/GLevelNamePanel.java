package funcmath.gui.swing;

import funcmath.game.GameLoader;
import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.level.Level;
import funcmath.level.LevelState;
import funcmath.level.LevelStatistics;
import funcmath.level.PlayFlag;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GLevelNamePanel extends GConstructorPanel {
  Level level;
  LevelState levelState;
  JLabel nameLabel;
  JLabel aboutLabel;
  JLabel statsLabel;
  JButton continueButton;
  JButton playButton;
  PlayFlag playFlag;

  public GLevelNamePanel(Level level, PlayFlag playFlag) {
    this.level = level;
    this.playFlag = playFlag;
    if (playFlag == PlayFlag.DEFAULT) {
      levelState = GameLoader.getPlayer().getDefaultLevelStates().get(level.getLevelInfo().getID());
    } else if (playFlag == PlayFlag.CUSTOM) {
      levelState = GameLoader.getPlayer().getCustomLevelStates().get(level.getLevelInfo().getID());
    }
    super(); // TODO: как-то исправить preview-функцию
  }

  protected void initBeforeComponents() {}

  protected void initComponents() {
    nameLabel = new JLabel();
    nameLabel.setText(level.toString());
    nameLabel.setFont(Fonts.COMIC_SANS_MS_30);
    nameLabel.setForeground(Color.white);

    aboutLabel = new JLabel();
    aboutLabel.setFont(Fonts.COMIC_SANS_MS_30);
    aboutLabel.setForeground(Color.white);
    aboutLabel.setText("Уровень №" + level.getLevelInfo().getID() + ": " + level.getName());
    aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

    statsLabel = new JLabel();
    statsLabel.setFont(Fonts.COMIC_SANS_MS_30);
    statsLabel.setForeground(Color.white);
    if (playFlag == PlayFlag.DEFAULT) {
      LevelStatistics stats =
          GameLoader.getPlayer().getDefaultLevelStats().get(this.level.getLevelInfo().getID());
      if (stats != null) {
        statsLabel.setText(stats.toString());
      }
    } else if (playFlag == PlayFlag.CUSTOM) {
      LevelStatistics stats =
          GameLoader.getPlayer().getCustomLevelStats().get(this.level.getLevelInfo().getID());
      if (stats != null) {
        statsLabel.setText(stats.toString());
      }
    }
    statsLabel.setHorizontalAlignment(SwingConstants.CENTER);

    continueButton = new JButton();
    continueButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            level.setLevelState(levelState);
            GameLoader.setCurrentLevel(level);
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
            GameLoader.setCurrentLevel(level);
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
    this.add(aboutLabel, BorderLayout.WEST);
    this.add(statsLabel, BorderLayout.CENTER);
    if (levelState != null) {
      this.add(continueButton, BorderLayout.EAST);
    }
    this.add(playButton, BorderLayout.EAST);
    this.setForeground(Color.white);
  }

  protected void initAfterConstruct() {}
}
