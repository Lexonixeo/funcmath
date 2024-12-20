package funcmath.gui.panel;

import funcmath.exceptions.AuthorizationException;
import funcmath.game.Authorization;
import funcmath.game.Player;
import funcmath.gui.GameFrame;
import funcmath.gui.paint.Background;
import funcmath.gui.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Login extends GPanel {
  Background bg = new Background();
  boolean show = false;

  public Login() {
    this.setLayout(null);
    this.setBackground(Color.black);

    GLabel nameLabel =
        new GLabel(
            (1920 - 250) / 2,
            100,
            250,
            50,
            "func(math)",
            new Font("Comic Sans MS", Font.PLAIN, 40),
            Color.white);
    this.add(nameLabel);

    GButton logInButton =
        new GButton(
            (1920 - 300) / 2,
            200,
            140,
            60,
            null,
            new ButtonAction() {
              @Override
              public void onClick() {}
            });
    logInButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    logInButton.setForeground(Color.white);
    logInButton.setBackground(new Color(56 * 5 / 4, 109 * 5 / 4, 80 * 5 / 4));
    logInButton.setEnabled(false);
    logInButton.setText("Log in");
    this.add(logInButton);

    GButton signUpButton =
        new GButton(
            (1920 - 300) / 2 + 160,
            200,
            140,
            60,
            null,
            new ButtonAction() {
              @Override
              public void onClick() {
                GameFrame.getInstance().changePanel("register");
              }
            });
    signUpButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    signUpButton.setForeground(Color.white);
    signUpButton.setBackground(new Color(56, 109, 80));
    signUpButton.setText("Sign up");
    this.add(signUpButton);

    GTextField focuser =
        new GTextField(
            0,
            0,
            1,
            1,
            null,
            new TextFieldAction() {
              @Override
              public void onEnterPress() {}
            },
            "");
    this.add(focuser);

    GTextField nameField =
        new GTextField(
            (1920 - 300) / 2,
            300,
            300,
            50,
            null,
            new TextFieldAction() {
              @Override
              public void onEnterPress() {}
            },
            "username");
    nameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    nameField.setBackground(new Color(56, 109, 80));
    nameField.setForeground(Color.white);
    this.add(nameField);

    GPasswordField passField =
        new GPasswordField(
            (1920 - 300) / 2,
            400,
            300,
            50,
            null,
            new TextFieldAction() {
              @Override
              public void onEnterPress() {}
            },
            "password");
    passField.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    passField.setBackground(new Color(56, 109, 80));
    passField.setForeground(Color.white);
    this.add(passField);

    GButton showPassword =
        new GButton(
            (1920 - 300) / 2 + 350,
            400,
            50,
            50,
            null,
            new ButtonAction() {
              @Override
              public void onClick() {
                show = !show;
                if (show) {
                  passField.setEchoChar((char) 0);
                } else {
                  passField.setEchoChar('*');
                }
              }
            });
    showPassword.setBackground(new Color(56, 109, 80));
    this.add(showPassword);

    GLabel errorInfo =
        new GLabel(
            (1920 - 300) / 2,
            600,
            300,
            50,
            "",
            new Font("Comic Sans MS", Font.PLAIN, 25),
            Color.white);
    this.add(errorInfo);

    GButton tryLogInButton =
        new GButton(
            (1920 - 300) / 2,
            500,
            300,
            50,
            null,
            new ButtonAction() {
              @Override
              public void onClick() {
                String username = nameField.getText();
                String password = String.valueOf(passField.getPassword());
                try {
                  errorInfo.setText("");
                  Player p = Authorization.login(username, password);
                  GameFrame.getInstance().setPlayer(p);
                } catch (AuthorizationException e) {
                  errorInfo.setText(e.getMessage());
                }
              }
            });
    tryLogInButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    tryLogInButton.setForeground(Color.white);
    tryLogInButton.setBackground(new Color(56, 109, 80));
    tryLogInButton.setText("Log in!");
    this.add(tryLogInButton);

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
