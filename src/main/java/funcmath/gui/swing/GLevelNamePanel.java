package funcmath.gui.swing;

import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.level.Level;
import funcmath.level.PlayFlag;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GLevelNamePanel extends GConstructorPanel {
  Level level;
  JLabel nameLabel;
  JLabel aboutLabel;
  JButton playButton;
  JButton continueButton;
  PlayFlag playFlag;

  public GLevelNamePanel(Level level, PlayFlag playFlag) {
    this.level = level;
    this.playFlag = playFlag;
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
    if (playFlag == PlayFlag.DEFAULT) {
      aboutLabel.setText(
          GameFrame.getInstance()
              .getPlayer()
              .getDefaultLevelStats()
              .get(this.level.getLevelInfo().getID())
              .toString());
    } else if (playFlag == PlayFlag.CUSTOM) {
      aboutLabel.setText(
          GameFrame.getInstance()
              .getPlayer()
              .getCustomLevelStats()
              .get(this.level.getLevelInfo().getID())
              .toString());
    }
    aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

    playButton = new JButton();
    playButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameFrame.getInstance().setCurrentLevel(level);
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
    this.add(aboutLabel, BorderLayout.CENTER);
    this.add(playButton, BorderLayout.EAST);
    this.setForeground(Color.white);
  }

  protected void initAfterConstruct() {}
}
