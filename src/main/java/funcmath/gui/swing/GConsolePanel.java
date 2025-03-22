package funcmath.gui.swing;

import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.utility.FMInputStream;
import funcmath.utility.FMOutputStream;
import funcmath.utility.FMPrintStream;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public abstract class GConsolePanel extends GBackgroundPanel {
  protected final FMInputStream in;
  private final FMOutputStream outputStream;
  protected final FMPrintStream out;

  protected JLabel nameLabel;
  protected JButton returnButton;
  protected JTextArea outputField;
  protected JScrollPane outputScrollPane;
  protected JLabel enterLabel;
  protected JTextField inputField;
  protected JScrollPane inputScrollPane;
  protected GOpaquePanel southPanel;

  private Thread textUpdateThread;
  private Thread runThread;

  public GConsolePanel() {
    in = new FMInputStream();
    FMOutputStream outputStream1 = new FMOutputStream();
    outputStream = outputStream1;
    out =
        new FMPrintStream(outputStream1, true, StandardCharsets.UTF_8) {
          @Override
          public void clear() {
            outputStream1.clear();
          }
        };
    super();
  }

  @Override
  protected void initBeforeComponents() {}

  @Override
  protected void initComponents() {
    nameLabel = new JLabel();
    nameLabel.setFont(Fonts.COMIC_SANS_MS_30);
    nameLabel.setForeground(Color.white);

    returnButton = new JButton();
    returnButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            runThread.interrupt();
            textUpdateThread.interrupt();
            GameFrame.getInstance().changePanel("menu");
          }
        });
    returnButton.setFont(Fonts.COMIC_SANS_MS_30);
    returnButton.setForeground(Color.white);
    returnButton.setBackground(new Color(56, 109, 80));
    returnButton.setText("Вернуться в меню");
    returnButton.setFocusable(false);

    outputField = new JTextArea();
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
            out.print(text); // println?
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
    resetTextUpdateThread();
    resetRunThread();
  }

  @Override
  public void start() {
    textUpdateThread.start();
    runThread.start();
  }

  @Override
  public void reset() {
    runThread.interrupt();
    textUpdateThread.interrupt();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    out.clear();
    super.reset(); // TODO: preview
  }

  private void resetTextUpdateThread() {
    // Создание потока - поток игры - обязательно должен идти параллельно с графическим интерфейсом
    // Этот метод будет выполняться в побочном потоке
    textUpdateThread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                while (!textUpdateThread.isInterrupted()) {
                  // wait 10 ms
                  try {
                    Thread.sleep(10);
                  } catch (InterruptedException e) {
                    break;
                  }
                  if (System.currentTimeMillis() - outputStream.getTimer() > 10
                      && !outputStream.isChecked()) {
                    SwingUtilities.invokeLater(
                        new Runnable() {
                          @Override
                          public void run() {
                            synchronized (out) {
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
    textUpdateThread.setName("FM-CP-Update");
    textUpdateThread.setDaemon(true);
  }

  // Проверять на Interrupt!!!
  protected abstract void run();

  private void resetRunThread() {
    // Создание потока - поток игры - обязательно должен идти параллельно с графическим интерфейсом
    // Этот метод будет выполняться в побочном потоке
    runThread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  GConsolePanel.this.run();
                } catch (Exception e) {
                  out.println(e.getMessage());
                }
                inputField.setEnabled(false);
                try {
                  Thread.sleep(100);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                textUpdateThread.interrupt();
                // GameFrame.getInstance().exitCurrentLevel(gameData);
              }
            });
    runThread.setName("FM-CP-Run");
    runThread.setDaemon(true);
  }

  public FMInputStream getIn() {
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
