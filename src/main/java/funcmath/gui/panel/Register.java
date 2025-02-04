package funcmath.gui.panel;

import funcmath.exceptions.AuthorizationException;
import funcmath.game.Authorization;
import funcmath.game.Player;
import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Register extends GBackgroundPanel {
  boolean passShow;

  JLabel nameLabel;
  JButton logInButton;
  JButton signUpButton;
  JLabel warnLabel;
  JTextField nameField;
  GPasswordField passField;
  GPasswordField pass2Field;
  JLabel errorInfo;
  JButton trySignUpButton;
  JButton showPassword;

  @Override
  protected void initBeforeComponents() {}

  @Override
  protected void initComponents() {
    nameLabel = new JLabel();
    nameLabel.setText("func(math)");
    nameLabel.setFont(Fonts.COMIC_SANS_MS_40);
    nameLabel.setForeground(Color.white);
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

    // nameLabel = new GImage("data/images/logo.png");
    // nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

    logInButton = new JButton();
    logInButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GameFrame.getInstance().changePanel("login");
          }
        });
    logInButton.setFont(Fonts.COMIC_SANS_MS_30);
    logInButton.setForeground(Color.white);
    logInButton.setBackground(new Color(56 * 5 / 4, 109 * 5 / 4, 80 * 5 / 4));
    logInButton.setText("Вход");
    logInButton.setFocusable(false);

    signUpButton = new JButton();
    signUpButton.setFont(Fonts.COMIC_SANS_MS_30);
    signUpButton.setForeground(Color.white);
    signUpButton.setBackground(new Color(56 * 5 / 4, 109 * 5 / 4, 80 * 5 / 4));
    signUpButton.setEnabled(false);
    signUpButton.setText("Регистрация");
    signUpButton.setFocusable(false);

    warnLabel = new JLabel();
    warnLabel.setText(
        "<html>ВНИМАНИЕ: Согласно стандартам ISO 80000-2:2019 и ГОСТ Р 54521-2011 мы условно принимаем число 0 как натуральное.<br\\>Данное решение было принято в связи с возникающими проблемами у некоторых функций при отсутствии нуля.<html\\>");
    warnLabel.setFont(Fonts.COMIC_SANS_MS_20);
    warnLabel.setForeground(Color.white);

    nameField = new JTextField();
    nameField.addFocusListener(
        new TextFieldAction() {
          @Override
          public void onEnterPress() {}

          @Override
          public void onFirstFocus() {
            nameField.setText("");
          }
        });
    nameField.setText("логин");
    nameField.setFont(Fonts.COMIC_SANS_MS_30);
    nameField.setBackground(new Color(56, 109, 80));
    nameField.setForeground(Color.white);
    nameField.setCaretColor(Color.white);

    passField = new GPasswordField();
    passShow = true;
    passField.addFocusListener(
        new TextFieldAction() {
          @Override
          public void onEnterPress() {}

          @Override
          public void onFirstFocus() {
            passField.setText("");
            passField.setShow(false);
            passShow = false;
          }
        });
    passField.setShow(true);
    passField.setText("пароль");
    passField.setFont(Fonts.COMIC_SANS_MS_30);
    passField.setBackground(new Color(56, 109, 80));
    passField.setForeground(Color.white);
    passField.setCaretColor(Color.white);

    pass2Field = new GPasswordField();
    pass2Field.addFocusListener(
        new TextFieldAction() {
          @Override
          public void onEnterPress() {}

          @Override
          public void onFirstFocus() {
            pass2Field.setText("");
            pass2Field.setShow(false);
          }
        });
    pass2Field.setShow(true);
    pass2Field.setText("подтвердите");
    pass2Field.setFont(Fonts.COMIC_SANS_MS_30);
    pass2Field.setBackground(new Color(56, 109, 80));
    pass2Field.setForeground(Color.white);
    pass2Field.setCaretColor(Color.white);

    errorInfo = new JLabel();
    errorInfo.setFont(Fonts.COMIC_SANS_MS_25);
    errorInfo.setForeground(Color.white);
    errorInfo.setHorizontalAlignment(SwingConstants.CENTER);

    trySignUpButton = new JButton();
    trySignUpButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String username = nameField.getText();
            String password = String.valueOf(passField.getPassword());
            String password2 = String.valueOf(pass2Field.getPassword());
            errorInfo.setText("");
            try {
              if (!password.equals(password2)) {
                throw new AuthorizationException("Пароли не совпадают!");
              }
              Player p = Authorization.register(username, password);
              GameFrame.getInstance().setPlayer(p);
              GameFrame.getInstance().changePanel("menu");
            } catch (AuthorizationException ex) {
              errorInfo.setText(ex.getMessage());
            }
            validate();
            repaint();
          }
        });
    trySignUpButton.setFont(Fonts.COMIC_SANS_MS_30);
    trySignUpButton.setForeground(Color.white);
    trySignUpButton.setBackground(new Color(56, 109, 80));
    trySignUpButton.setText("Зарегистрироваться!");

    showPassword = new JButton();
    showPassword.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            passShow = !passShow;
            passField.setShow(passShow);
          }
        });
    showPassword.setText("<html>&#x1F441;</html>");
    showPassword.setFont(Fonts.COMIC_SANS_MS_32);
    showPassword.setMaximumSize(
        new Dimension(0, 10000)); // setting minimum width and maximum height
    showPassword.setBackground(new Color(56, 109, 80));
    showPassword.setForeground(Color.white);
    showPassword.setFocusable(false);
  }

  @Override
  protected void initBeforeConstruct() {}

  @Override
  protected void constructPanel() {
    this.setLayout(new GridLayout(3, 3, 30, 30));
    this.setBorder(new EmptyBorder(50, 50, 30, 30));

    this.add(new GOpaquePanel());
    GOpaquePanel upperPanel = new GOpaquePanel();
    upperPanel.setLayout(new GridLayout(2, 1, 30, 30));
    this.add(upperPanel);
    upperPanel.add(nameLabel);
    GOpaquePanel upperBottomPanel = new GOpaquePanel();
    upperBottomPanel.setLayout(new GridLayout(1, 2, 30, 30));
    upperPanel.add(upperBottomPanel);
    upperBottomPanel.add(logInButton);
    upperBottomPanel.add(signUpButton);
    this.add(new GOpaquePanel());

    this.add(warnLabel);
    GOpaquePanel centerPanel = new GOpaquePanel();
    centerPanel.setLayout(new GridLayout(3, 1, 30, 30));
    this.add(centerPanel);
    centerPanel.add(nameField);
    GOpaquePanel passPanel = new GOpaquePanel();
    passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.X_AXIS));
    passPanel.add(passField);
    passPanel.add(Box.createHorizontalStrut(30));
    passPanel.add(showPassword);
    centerPanel.add(passPanel);
    centerPanel.add(pass2Field);
    this.add(new GOpaquePanel());

    // нижние три клетки
    this.add(new GOpaquePanel());
    GOpaquePanel bottommPanel = new GOpaquePanel();
    bottommPanel.setLayout(new GridLayout(3, 1, 30, 30));
    this.add(bottommPanel);
    bottommPanel.add(trySignUpButton);
    bottommPanel.add(errorInfo);
    bottommPanel.add(new GOpaquePanel());
    this.add(new GOpaquePanel());
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
