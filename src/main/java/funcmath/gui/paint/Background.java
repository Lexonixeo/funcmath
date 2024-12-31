package funcmath.gui.paint;

import java.awt.*;
import java.util.ArrayList;

public class Background {
  ArrayList<Figure> figures;

  public Background() {
    figures = new ArrayList<>();
    Figure bg = Figure.rect(0, 0, 1920, 1040, Color.black, true, "data/images/default.jpg");
    figures.add(bg);
  }

  public Background(ArrayList<Figure> figures) {
    this.figures = figures;
  }

  public void draw(Graphics g) {
    for (Figure f : figures) {
      f.paint(g);
    }
  }
}
