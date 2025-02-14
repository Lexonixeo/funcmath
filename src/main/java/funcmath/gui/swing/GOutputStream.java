package funcmath.gui.swing;

import funcmath.utility.Helper;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GOutputStream extends OutputStream {
  private final ArrayList<ArrayList<Byte>> textList;

  private long timer;
  private boolean checked = false;

  public GOutputStream() {
    this.textList = new ArrayList<>();
    this.textList.add(new ArrayList<>());
    timer = System.currentTimeMillis();
  }

  public void clear() {
    this.textList.clear();
    this.textList.add(new ArrayList<>());
  }

  public ArrayList<String> getText() {
    ArrayList<String> text = new ArrayList<>();
    for (ArrayList<Byte> a : textList) {
      text.add(new String(Helper.toByteArray(a), StandardCharsets.UTF_8));
    }
    this.checked = true;
    return text;
  }

  public long getTimer() {
    return timer;
  }

  public boolean isChecked() {
    return checked;
  }

  @Override
  public void write(int b) throws IOException {
    timer = System.currentTimeMillis();
    checked = false;
    if ((byte) b == '\r') {
      return;
    } else if ((byte) b == '\n') {
      textList.getLast().add((byte) b);
      textList.add(new ArrayList<>());
    } else {
      textList.getLast().add((byte) b);
    }
  }
}
