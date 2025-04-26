package funcmath.gui.paint;

import funcmath.utility.Helper;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Figure implements Shape {
  Shape s;
  Color color;
  boolean fill;
  String imagePath;

  Figure(ArrayList<Integer> x, ArrayList<Integer> y, Color color, boolean fill, String imagePath) {
    this.color = color;
    this.fill = fill;
    this.s = new Polygon(Helper.toIntArray(x), Helper.toIntArray(y), Math.min(x.size(), y.size()));
    this.imagePath = imagePath;
  }

  Figure(Shape s, Color color, boolean fill, String imagePath) {
    this.color = color;
    this.fill = fill;
    this.s = s;
    this.imagePath = imagePath;
  }

  public void draw(Graphics gr) {
    Graphics2D g = (Graphics2D) gr;
    g.setColor(color);
    g.draw(s);
  }

  public void fill(Graphics gr) {
    Graphics2D g = (Graphics2D) gr;
    g.setColor(color);
    g.fill(s);

    if (imagePath != null) {

      Rectangle2D bounds = s.getBounds2D();
      try {
        Image img =
            ImageIO.read(new File(imagePath))
                .getScaledInstance(
                    (int) (bounds.getWidth()), (int) (bounds.getHeight()), Image.SCALE_SMOOTH);
        g.drawImage(img, (int) bounds.getX(), (int) bounds.getY(), null);
        // System.out.println(g.getClipBounds());
        // System.out.println((int) bounds.getWidth() + " " + (int) bounds.getHeight());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void paint(Graphics g) {
    if (fill) {
      this.fill(g);
    } else {
      this.draw(g);
    }
  }

  @Override
  public Rectangle getBounds() {
    return s.getBounds();
  }

  @Override
  public Rectangle2D getBounds2D() {
    return s.getBounds2D();
  }

  @Override
  public boolean contains(double x, double y) {
    return s.contains(x, y);
  }

  @Override
  public boolean contains(Point2D p) {
    return s.contains(p);
  }

  @Override
  public boolean intersects(double x, double y, double w, double h) {
    return s.intersects(x, y, w, h);
  }

  @Override
  public boolean intersects(Rectangle2D r) {
    return s.intersects(r);
  }

  @Override
  public boolean contains(double x, double y, double w, double h) {
    return s.contains(x, y, w, h);
  }

  @Override
  public boolean contains(Rectangle2D r) {
    return s.contains(r);
  }

  @Override
  public PathIterator getPathIterator(AffineTransform at) {
    return s.getPathIterator(at);
  }

  @Override
  public PathIterator getPathIterator(AffineTransform at, double flatness) {
    return s.getPathIterator(at, flatness);
  }

  public static Figure rect(
      int x, int y, int width, int height, Color color, boolean fill, String imagePath) {
    return new Figure(
        new ArrayList<>(List.of(x, x + width, x + width, x)),
        new ArrayList<>(List.of(y, y, y + height, y + height)),
        color,
        fill,
        imagePath);
  }

  public static Figure circle(
      int x, int y, int radius, Color color, boolean fill, String imagePath) {
    Shape circle = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
    return new Figure(circle, color, fill, imagePath);
  }

  public static Figure line(
      int x0, int y0, int x, int y, Color color, boolean fill, String imagePath) {
    Shape line = new Line2D.Double(x0, y0, x, y);
    return new Figure(line, color, fill, imagePath);
  }
}
