package funcmath.gui.swing;

import javax.swing.*;

public abstract class GConstructorPanel extends JPanel {
  public GConstructorPanel() {
    initBeforeComponents();
    initComponents();
    initBeforeConstruct();
    constructPanel();
    this.validate();
    this.repaint();
    initAfterConstruct();
  }

  public void reset() {
    this.removeAll();
    initBeforeComponents();
    initComponents();
    initBeforeConstruct();
    constructPanel();
    this.validate();
    this.repaint();
    initAfterConstruct();
  }

  protected abstract void initBeforeComponents();

  protected abstract void initComponents();

  protected abstract void initBeforeConstruct();

  protected abstract void constructPanel();

  protected abstract void initAfterConstruct();

  public void start() {}
}
