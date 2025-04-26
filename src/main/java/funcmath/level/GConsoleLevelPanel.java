package funcmath.level;

import funcmath.game.GameLoader;
import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public abstract class GConsoleLevelPanel extends GConsolePanel {
  JButton nextLevel;

  @Override
  protected void initComponents() {
    super.initComponents();

    nextLevel = new JButton();
    nextLevel.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameLoader.setCurrentLevel(
                LevelRegister.getNextLevel(GameLoader.getPlayer().getLastLevel().getPrimaryKey()));
            GameFrame.getInstance().changePanel("level");
            /*
            GameFrame.getInstance()
                    .setCurrentLevel(
                            Level.getLevelInstance(
                                    LevelList.getNextLevel(
                                            GameFrame.getInstance().getLastLevel().getLevel(),
                                            LevelPlayFlag.DEFAULT),
                                    LevelPlayFlag.DEFAULT));
            reset();
             */
          }
        });
    nextLevel.setFont(Fonts.COMIC_SANS_MS_30);
    nextLevel.setForeground(Color.white);
    nextLevel.setBackground(new Color(56, 109, 80));
    nextLevel.setText("Следующий уровень");
  }

  protected abstract void game();

  protected void afterGame() {
    if (GameLoader.getCurrentLevel().isCompleted()) {
      southPanel.add(nextLevel, BorderLayout.EAST);
      repaint();
      validate();
    }
  }

  protected void setNameLabelText() {
    Level level = GameLoader.getCurrentLevel();
    nameLabel.setText("Уровень №" + level.getLevelInfo().getID() + ": " + level.getName());
    repaint();
    validate();
  }

  @Override
  protected void run() {
    setNameLabelText();
    try {
      GConsoleLevelPanel.this.game();
      GConsoleLevelPanel.this.afterGame();
      GameLoader.exitCurrentLevel();
    } catch (RuntimeException e) {
      out.println(e.getMessage());
    }
  }
}
