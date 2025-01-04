package funcmath.gui.panel;

import funcmath.exceptions.AuthorizationException;
import funcmath.game.Authorization;
import funcmath.game.Player;
import funcmath.gui.Fonts;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Register extends GPanel {
  boolean show = false;

  public Register() {
    this.setLayout(new GridLayout(3, 3, 50, 50));
    this.setBorder(new EmptyBorder(50, 50, 50, 50));
    this.setBackground(Color.black);

    JLabel nameLabel = new JLabel();
    nameLabel.setText("func(math)");
    nameLabel.setFont(Fonts.COMIC_SANS_MS_40);
    nameLabel.setForeground(Color.white);
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(nameLabel);

    JButton logInButton = new JButton();
    logInButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {
            GameFrame.getInstance().changePanel("login");
          }
        });
    logInButton.setFont(Fonts.COMIC_SANS_MS_30);
    logInButton.setForeground(Color.white);
    logInButton.setBackground(new Color(56 * 5 / 4, 109 * 5 / 4, 80 * 5 / 4));
    logInButton.setText("Log in");
    this.add(logInButton);

    JButton signUpButton = new JButton();
    signUpButton.setFont(Fonts.COMIC_SANS_MS_30);
    signUpButton.setForeground(Color.white);
    signUpButton.setBackground(new Color(56 * 5 / 4, 109 * 5 / 4, 80 * 5 / 4));
    signUpButton.setEnabled(false);
    signUpButton.setText("Sign up");
    this.add(signUpButton);

    JLabel warnLabel = new JLabel();
    warnLabel.setText(
        "<html>ВНИМАНИЕ: Согласно стандартам ISO 80000-2:2019 и ГОСТ Р 54521-2011 мы условно принимаем число 0 как натуральное.<br\\>Данное решение было принято в связи с возникающими проблемами у некоторых функций при отсутствии нуля.<html\\>");
    warnLabel.setFont(Fonts.COMIC_SANS_MS_20);
    warnLabel.setForeground(Color.white);
    this.add(warnLabel);

    GTextField nameField = new GTextField();
    nameField.setTextFieldAction(
        new TextFieldAction() {
          @Override
          public void onEnterPress() {}
        });
    nameField.setText("username");
    nameField.setFont(Fonts.COMIC_SANS_MS_40);
    nameField.setBackground(new Color(56, 109, 80));
    nameField.setForeground(Color.white);
    this.add(nameField);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1, 50, 50));
    panel.setBackground(this.getBackground());
    this.add(panel);

    GPasswordField passField = new GPasswordField();
    passField.setTextFieldAction(
        new TextFieldAction() {
          @Override
          public void onEnterPress() {}
        });
    passField.setText("password");
    passField.setFont(Fonts.COMIC_SANS_MS_40);
    passField.setBackground(new Color(56, 109, 80));
    passField.setForeground(Color.white);
    panel.add(passField);

    GPasswordField pass2Field = new GPasswordField();
    pass2Field.setTextFieldAction(
        new TextFieldAction() {
          @Override
          public void onEnterPress() {}
        });
    pass2Field.setText("confirm");
    pass2Field.setFont(Fonts.COMIC_SANS_MS_40);
    pass2Field.setBackground(new Color(56, 109, 80));
    pass2Field.setForeground(Color.white);
    panel.add(pass2Field);

    JLabel errorInfo = new JLabel();
    errorInfo.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
    errorInfo.setForeground(Color.white);
    errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(errorInfo);

    JButton trySignUpButton = new JButton();
    trySignUpButton.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {
            String username = nameField.getText();
            String password = String.valueOf(pass2Field.getPassword());
            String password2 = String.valueOf(pass2Field.getPassword());
            try {
              errorInfo.setText("");
              if (!password.equals(password2)) {
                throw new AuthorizationException("Passwords are unequal!");
              }
              Player p = Authorization.register(username, password);
              GameFrame.getInstance().setPlayer(p);
              GameFrame.getInstance().changePanel("menu");
            } catch (AuthorizationException e) {
              errorInfo.setText(e.getMessage());
            }
          }
        });
    trySignUpButton.setFont(Fonts.COMIC_SANS_MS_30);
    trySignUpButton.setForeground(Color.white);
    trySignUpButton.setBackground(new Color(56, 109, 80));
    trySignUpButton.setText("Sign up!");
    this.add(trySignUpButton);

    JButton showPassword = new JButton();
    showPassword.addActionListener(
        new ButtonAction() {
          @Override
          public void onClick() {
            show = !show;
            passField.setShow(show);
          }
        });
    showPassword.setText("Show password");
    showPassword.setFont(Fonts.COMIC_SANS_MS_30);
    showPassword.setBackground(new Color(56, 109, 80));
    showPassword.setForeground(Color.white);
    this.add(showPassword);

    this.validate();
    this.repaint();
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
