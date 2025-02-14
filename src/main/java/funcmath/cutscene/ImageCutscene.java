package funcmath.cutscene;

import javax.swing.*;

public class ImageCutscene extends JPanel implements Cutscene {
  @Override
  public void playCutscene() {}

  @Override
  public JPanel getPanel() {
    return this;
  }
}
