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
  protected FMInputStream in;
  private FMOutputStream outputStream;
  protected FMPrintStream out;

  protected JLabel nameLabel;
  protected JButton returnButton;
  protected JTextArea outputField;
  protected JScrollPane outputScrollPane;
  protected JLabel enterLabel;
  protected JTextField inputField;
  protected JScrollPane inputScrollPane;
  protected GOpaquePanel southPanel;

  private Thread updateThread;
  private Thread gameThread;

  @Override
  protected void initBeforeComponents() {
    in = new FMInputStream();
    outputStream = new FMOutputStream();
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
    setUpdateThread();
    updateThread.start(); // Запуск потока

    setRunThread();
    gameThread.start(); // Запуск потока
  }

  // TODO: проблема с reset, возникает у FunctionMakerPanel
  @Override
  public void reset() {
    in.stop();
    out.clear();
    runInterrupt(); // gameThread.interrupt();
    updateThread.interrupt();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    super.reset(); // TODO: preview
  }

  private void setUpdateThread() {
    // Создание потока - поток игры - обязательно должен идти параллельно с графическим интерфейсом
    // Этот метод будет выполняться в побочном потоке
    updateThread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                while (!updateThread.isInterrupted()) {
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
    updateThread.setName("Console Panel Text update");
    updateThread.setDaemon(true);
  }

  protected abstract void run();

  protected abstract void runInterrupt();

  private void setRunThread() {
    // Создание потока - поток игры - обязательно должен идти параллельно с графическим интерфейсом
    // Этот метод будет выполняться в побочном потоке
    gameThread =
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
                  Thread.sleep(1000);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                updateThread.interrupt();
                // GameFrame.getInstance().exitCurrentLevel(gameData);
              }
            });
    gameThread.setName("Console Panel Game Thread");
    gameThread.setDaemon(true);
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
