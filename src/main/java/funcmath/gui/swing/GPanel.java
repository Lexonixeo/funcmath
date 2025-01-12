package funcmath.gui.swing;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public abstract class GPanel extends JPanel
    implements KeyEventDispatcher, MouseListener, MouseMotionListener {
  // public void reset() {}

  protected abstract void initComponents();
}
