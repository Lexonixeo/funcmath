package funcmath.gui.swing;

import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.level.FMPrintStream;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public abstract class GConsolePanel extends GBackgroundPanel {
  protected GInputStream in;
  private GOutputStream outputStream;
  protected FMPrintStream out;

  protected JLabel nameLabel;
  protected JButton returnButton;
  protected GTextArea outputField;
  protected JScrollPane outputScrollPane;
  protected JLabel enterLabel;
  protected JTextField inputField;
  protected JScrollPane inputScrollPane;
  protected GOpaquePanel southPanel;

  boolean turnedOnUpdateThread;

  protected abstract void initOtherComponents();

  @Override
  protected void initBeforeComponents() {
    in = new GInputStream();
    outputStream = new GOutputStream();
    out =
        new FMPrintStream(outputStream, true, StandardCharsets.UTF_8) {
          @Override
          public void clear() {
            outputStream.clear();
          }
        };
  }

  @Override
  protected void initComponents() {
    nameLabel = new JLabel();
    nameLabel.setText(GameFrame.getInstance().getCurrentLevel().toString());
    nameLabel.setFont(Fonts.COMIC_SANS_MS_30);
    nameLabel.setForeground(Color.white);

    returnButton = new JButton();
    returnButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            in.stop();
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
            String text = inputField.getText();
            text += (char) 10;
            in.send(text);
            out.print(text);
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

    initOtherComponents();
  }

  @Override
  protected void initBeforeConstruct() {}

  @Override
  protected void constructPanel() {
    this.setLayout(new BorderLayout(30, 30));
    this.setBorder(new EmptyBorder(30, 30, 30, 30));

    GOpaquePanel topPanel = new GOpaquePanel();
    topPanel.setLayout(new BorderLayout());
    this.add(topPanel, BorderLayout.NORTH);
    topPanel.add(nameLabel, BorderLayout.WEST);
    topPanel.add(returnButton, BorderLayout.EAST);

    this.add(outputScrollPane, BorderLayout.CENTER);

    southPanel = new GOpaquePanel();
    southPanel.setLayout(new BorderLayout(30, 30));
    this.add(southPanel, BorderLayout.SOUTH);
    southPanel.add(enterLabel, BorderLayout.WEST);
    southPanel.add(inputScrollPane, BorderLayout.CENTER);
  }

  @Override
  protected void initAfterConstruct() {
    Thread updateThread = getUpdateThread();
    updateThread.start(); // Запуск потока

    Thread gameThread = getRunThread();
    gameThread.start(); // Запуск потока
  }

  private Thread getUpdateThread() {
    turnedOnUpdateThread = true;
    // Создание потока - поток игры - обязательно должен идти параллельно с графическим интерфейсом
    // Этот метод будет выполняться в побочном потоке
    Thread updateThread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                while (turnedOnUpdateThread) {
                  // wait 10 ms
                  try {
                    Thread.sleep(10);
                  } catch (InterruptedException ignored) {
                  }
                  if (System.currentTimeMillis() - outputStream.getTimer() > 10
                      && !outputStream.isChecked()) {
                    SwingUtilities.invokeLater(
                        new Runnable() {
                          @Override
                          public void run() {
                            final FMPrintStream out2 = out;
                            synchronized (out2) {
                              outputField.setText("");
                              for (String a : outputStream.getText()) {
                                outputField.append(a);
                              }
                            }
                          }
                        });
                  }
                }
              }
            });
    updateThread.setName("FM Text update");
    updateThread.setDaemon(true);
    return updateThread;
  }

  protected abstract void run();

  private Thread getRunThread() {
    // Создание потока - поток игры - обязательно должен идти параллельно с графическим интерфейсом
    // Этот метод будет выполняться в побочном потоке
    Thread gameThread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  GConsolePanel.this.run();
                } catch (Exception e) {
                  out.println(e.getMessage());
                }
                try {
                  Thread.sleep(1000);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                turnedOnUpdateThread = false;
                // GameFrame.getInstance().exitCurrentLevel(gameData);
              }
            });
    gameThread.setName("FM Level");
    gameThread.setDaemon(true);
    return gameThread;
  }

  public GInputStream getIn() {
    return in;
  }

  public FMPrintStream getOut() {
    return out;
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
