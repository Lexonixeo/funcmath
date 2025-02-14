package funcmath.cutscene;

import java.io.Serializable;
import javax.swing.*;

public interface Cutscene extends Serializable {
  void playCutscene(); // надо ли? можно ли перенести функционал в getPanel?

  JPanel getPanel();
}
