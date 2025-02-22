package funcmath.level;

import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;

public abstract class GConsoleLevelPanel extends GConsolePanel {
  JButton nextLevel;

    @Override
    protected void initOtherComponents() {
        nextLevel = new JButton();
        nextLevel.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
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

  @Override
  protected void run() {
    try {
      GConsoleLevelPanel.this.game();
    } catch (RuntimeException e) {
      out.println(e.getMessage());
    }
    inputField.setEnabled(false);
    if (GameFrame.getInstance().getCurrentLevel().isCompleted()) {
      southPanel.add(nextLevel, BorderLayout.EAST);
      repaint();
      validate();
    }
  }
}
