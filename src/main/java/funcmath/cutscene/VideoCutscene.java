package funcmath.cutscene;

import javax.swing.*;

public class VideoCutscene extends JPanel implements Cutscene {
  // TODO
  // см.
  // https://stackoverflow.com/questions/6525493/a-simple-way-of-embedding-a-video-in-my-swing-gui ?
  @Override
  public void playCutscene() {}

  @Override
  public JPanel getPanel() {
    return this;
  }
}
