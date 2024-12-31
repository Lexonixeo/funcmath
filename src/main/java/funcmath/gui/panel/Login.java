package funcmath.gui.panel;

import funcmath.exceptions.AuthorizationException;
import funcmath.game.Authorization;
import funcmath.game.Player;
import funcmath.gui.GameFrame;
import funcmath.gui.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Login extends GPanel {
  boolean show = false;

  public Login() {
    this.setLayout(null);
    this.setBackground(Color.black);

    GLabel nameLabel = new GLabel((1920 - 250) / 2, 50, 250, 50);
    nameLabel.setText("func(math)");
    nameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    nameLabel.setForeground(Color.white);
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(nameLabel);

    GButton logInButton =
        new GButton(
            (1920 - 300) / 2,
            150,
            140,
            60,
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
            150,
            140,
            60,
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

    GTextField focuses = new GFocuses();
    this.add(focuses);

    GTextField nameField =
        new GTextField(
            (1920 - 300) / 2,
            250,
            300,
            50,
            new TextFieldAction() {
              @Override
              public void onEnterPress() {}
            });
    nameField.setText("username");
    nameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    nameField.setBackground(new Color(56, 109, 80));
    nameField.setForeground(Color.white);
    this.add(nameField);

    GPasswordField passField =
        new GPasswordField(
            (1920 - 300) / 2,
            350,
            300,
            50,
            new TextFieldAction() {
              @Override
              public void onEnterPress() {}
            });
    passField.setText("password");
    passField.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    passField.setBackground(new Color(56, 109, 80));
    passField.setForeground(Color.white);
    this.add(passField);

    GLabel errorInfo = new GLabel((1920 - 300) / 2, 550, 300, 50);
    errorInfo.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
    errorInfo.setForeground(Color.white);
    this.add(errorInfo);

    GButton tryLogInButton =
        new GButton(
            (1920 - 300) / 2,
            450,
            300,
            50,
            new ButtonAction() {
              @Override
              public void onClick() {
                String username = nameField.getText();
                String password = String.valueOf(passField.getPassword());
                try {
                  errorInfo.setText("");
                  Player p = Authorization.login(username, password);
                  GameFrame.getInstance().setPlayer(p);
                  GameFrame.getInstance().changePanel("menu");
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

      GButton showPassword =
              new GButton(
                      (1920 - 300) / 2 + 350,
                      350,
                      50,
                      50,
                      new ButtonAction() {
                          @Override
                          public void onClick() {
                              show = !show;
                              passField.setShow(show);
                          }
                      });
      showPassword.setBackground(new Color(56, 109, 80));
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
