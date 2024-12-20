package funcmath.gui.swing;

import java.awt.*;
import javax.swing.*;

public class GLabel extends JLabel {
  int x, y;
  int width, height;
  String text;
  Font font;
  Color color;

  public GLabel(int x, int y, int width, int height, String text, Font font, Color color) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.text = text;
    this.font = font;
    this.color = color;
    this.setText(text);
    this.setFont(font);
    this.setLocation(x, y);
    this.setSize(width, height);
    this.setForeground(color);
  }

  @Override
  public void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
    super.setLocation(x, y);
  }

  @Override
  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
    super.setSize(width, height);
  }

  @Override
  public void setText(String text) {
    this.text = text;
    super.setText(text);
  }

  @Override
  public void setFont(Font font) {
    this.font = font;
    super.setFont(font);
  }

  @Override
  public void setForeground(Color fg) {
    this.color = fg;
    super.setForeground(fg);
  }
}
