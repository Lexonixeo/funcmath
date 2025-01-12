package funcmath.gui.panel;

import funcmath.game.FMPrintStream;
import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LevelPanel extends GBackgroundPanel {
  GInputStream in;
  GOutputStream outputStream;
  final FMPrintStream out;

  JLabel nameLabel;
  JButton returnButton;
  GTextArea outputField;
  JScrollPane outputScrollPane;
  JLabel enterLabel;
  JTextField inputField;
  JScrollPane inputScrollPane;

  public LevelPanel() {
    in = new GInputStream();
    outputStream = new GOutputStream();
    out =
        new FMPrintStream(outputStream, true, StandardCharsets.UTF_8) {
          @Override
          public void clear() {
            outputStream.clear();
          }
        };
    outputStream.addOutputStreamAction(
        new OutputStreamAction() {
          @Override
          public void afterWrite() {
            synchronized (out) {
              SwingUtilities.invokeLater(
                      () -> {
                        outputField.setText("");
                        for (String a : outputStream.getText()) {
                          outputField.append(a);
                        }
                      });
            }
          }
        });

    initComponents();

    this.setLayout(new BorderLayout(30, 30));
    this.setBorder(new EmptyBorder(30, 30, 30, 30));
    this.setBackground(Color.black);

    GOpaquePanel topPanel = new GOpaquePanel();
    topPanel.setLayout(new BorderLayout());
    this.add(topPanel, BorderLayout.NORTH);
    topPanel.add(nameLabel, BorderLayout.WEST);
    topPanel.add(returnButton, BorderLayout.EAST);

    this.add(outputScrollPane, BorderLayout.CENTER);

    GOpaquePanel southPanel = new GOpaquePanel();
    southPanel.setLayout(new BorderLayout(30, 30));
    this.add(southPanel, BorderLayout.SOUTH);
    southPanel.add(enterLabel, BorderLayout.WEST);
    southPanel.add(inputScrollPane);

    this.validate();
    this.repaint();

    // Создание потока - поток игры - обязательно должен идти параллельно с графическим интерфейсом
      // Этот метод будет выполняться в побочном потоке
      Thread gameThread =
        new Thread(
                () -> {
              GameFrame.getInstance().getCurrentLevel().consoleRun(in, out);
              inputField.setEnabled(false);
            });
    gameThread.start(); // Запуск потока
  }

  @Override
  protected void initComponents() {
    nameLabel = new JLabel();
    nameLabel.setText(GameFrame.getInstance().getCurrentLevel().toString());
    nameLabel.setFont(Fonts.COMIC_SANS_MS_30);
    nameLabel.setForeground(Color.white);

    returnButton = new JButton();
    returnButton.addActionListener(
            new ButtonAction() {
              @Override
              public void onClick() {
                GameFrame.getInstance().changePanel("menu");
              }
            });
    returnButton.setFont(Fonts.COMIC_SANS_MS_30);
    returnButton.setForeground(Color.white);
    returnButton.setBackground(new Color(56, 109, 80));
    returnButton.setText("Вернуться в меню");
    returnButton.setFocusable(false);

    outputField = new GTextArea(outputStream);
    outputField.setText("");
    outputField.setFont(Fonts.COMIC_SANS_MS_16);
    outputField.setBackground(new Color(56, 109, 80));
    outputField.setForeground(Color.white);
    outputField.setEditable(false);

    outputScrollPane = new JScrollPane(outputField);
    outputScrollPane.setBackground(new Color(56, 109, 80));
    outputScrollPane.setForeground(Color.white);

    enterLabel = new JLabel();
    enterLabel.setText("Введите команду:");
    enterLabel.setFont(Fonts.COMIC_SANS_MS_30);
    enterLabel.setForeground(Color.white);

    inputField = new JTextField();
    inputField.addFocusListener(
        new TextFieldAction() {
          @Override
          public void onEnterPress() {
            in.send(inputField.getText());
            out.println(inputField.getText());
            inputField.setText("");
          }

          @Override
          public void onFirstFocus() {}
        });
    inputField.setText("");
    inputField.setFont(Fonts.COMIC_SANS_MS_40);
    inputField.setBackground(new Color(56, 109, 80));
    inputField.setForeground(Color.white);
    inputField.setCaretColor(Color.white);

    inputScrollPane =
        new JScrollPane(
            inputField,
            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    inputScrollPane.setBackground(new Color(56, 109, 80));
    inputScrollPane.setForeground(Color.white);
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
