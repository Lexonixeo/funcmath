package funcmath.cutscene;

import java.util.ArrayList;
import javax.swing.*;

public class StringCutscene extends JPanel implements Cutscene {
  private ArrayList<String> strings;

  @Override
  public void playCutscene() {}

  @Override
  public JPanel getPanel() {
    return this;
  }
}
