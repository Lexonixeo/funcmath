package funcmath.gui.panel;

import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.GBackgroundPanel;
import funcmath.gui.swing.GLevelNamePanel;
import funcmath.gui.swing.GOpaquePanel;
import funcmath.level.LevelPrimaryKey;
import funcmath.level.LevelRegister;
import funcmath.level.PlayFlag;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LevelListPanel extends GBackgroundPanel {
  ArrayList<Long> levels;

  JLabel nameLabel;
  JButton returnButton;
  JButton backButton;
  JButton nextButton;

  int page;
  PlayFlag flag;
  int levelsPerPage;

  public LevelListPanel(int page, PlayFlag flag) {
    this.page = page;
    this.flag = flag;
    super();
  }

  @Override
  protected void initBeforeComponents() {
    this.levelsPerPage = 7;
    this.levels = LevelRegister.getLevelList(flag);
  }

  @Override
  protected void initComponents() {
    nameLabel = new JLabel();
    nameLabel.setText("Список уровней, страница " + page);
    nameLabel.setFont(Fonts.COMIC_SANS_MS_30);
    nameLabel.setForeground(Color.white);

    returnButton = new JButton();
    returnButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameFrame.getInstance().changePanel("menu");
          }
        });
    returnButton.setFont(Fonts.COMIC_SANS_MS_30);
    returnButton.setForeground(Color.white);
    returnButton.setBackground(new Color(56, 109, 80));
    returnButton.setText("Вернуться в меню");
    returnButton.setFocusable(false);

    nextButton = new JButton();
    nextButton.setEnabled(!(page + 1 <= 0 || levels.size() < levelsPerPage * (page + 1) - levelsPerPage + 1));
    nextButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            page++;
            backButton.setEnabled(true);
            if (page + 1 <= 0 || levels.size() < levelsPerPage * (page + 1) - levelsPerPage + 1) {
              nextButton.setEnabled(false);
            }
            reset();
          }
        });
    nextButton.setFont(Fonts.COMIC_SANS_MS_30);
    nextButton.setForeground(Color.white);
    nextButton.setBackground(new Color(56, 109, 80));
    nextButton.setText("Вперед");

    backButton = new JButton();
    backButton.setEnabled(!(page - 1 <= 0 || levels.size() < levelsPerPage * (page - 1) - levelsPerPage + 1));
    backButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            page--;
            nextButton.setEnabled(true);
            if (page - 1 <= 0 || levels.size() < levelsPerPage * (page - 1) - levelsPerPage + 1) {
              backButton.setEnabled(false);
            }
            reset();
          }
        });
    backButton.setFont(Fonts.COMIC_SANS_MS_30);
    backButton.setForeground(Color.white);
    backButton.setBackground(new Color(56, 109, 80));
    backButton.setText("Назад");
  }

  @Override
  protected void initBeforeConstruct() {}

  @Override
  protected void constructPanel() {
    this.setLayout(new GridLayout(1 + levelsPerPage, 1, 30, 30));
    this.setBorder(new EmptyBorder(30, 30, 30, 30));

    GOpaquePanel topPanel = new GOpaquePanel();
    topPanel.setLayout(new BorderLayout());
    this.add(topPanel);
    nameLabel.setText("Список уровней, страница " + page);
    topPanel.add(nameLabel, BorderLayout.WEST);
    GOpaquePanel topEastPanel = new GOpaquePanel();
    topPanel.add(topEastPanel, BorderLayout.EAST);
    topEastPanel.add(backButton);
    topEastPanel.add(nextButton);
    topEastPanel.add(returnButton);

    for (int i = levelsPerPage * (page - 1);
        i < Math.min(levelsPerPage * page, levels.size());
        i++) {
      this.add(
          new GLevelNamePanel(new LevelPrimaryKey(levels.get(i), flag)));
      // this.add(new GLevelNamePanel(Level.getLevelInstance(levels.get(i), flag)));
    }
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
