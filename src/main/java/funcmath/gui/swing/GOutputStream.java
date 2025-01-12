package funcmath.gui.swing;

import funcmath.exceptions.JavaException;
import funcmath.utility.Helper;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GOutputStream extends OutputStream {
  private final ArrayList<ArrayList<Byte>> textList;
  private final ArrayList<OutputStreamAction> actions;

  public GOutputStream() {
    this.textList = new ArrayList<>();
    this.textList.add(new ArrayList<>());
    actions = new ArrayList<>();
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
    return text;
  }

  public void addOutputStreamAction(OutputStreamAction action) {
    this.actions.add(action);
  }

  private void afterWrite() {
    for (OutputStreamAction a : actions) {
      a.afterWrite(); // BEDA: VLOZHENYY SYNC
    }
  }

  @Override
  public void flush() {
    try {
      super.flush();
    } catch (IOException e) {
      throw new JavaException(e);
    }
  }

  @Override
  public void close() {
    try {
      super.close();
    } catch (IOException e) {
      throw new JavaException(e);
    }
  }

  @Override
  public void write(int b) throws IOException {
    if ((byte) b == '\r') {
      return;
    } else if ((byte) b == '\n') {
      textList.get(textList.size() - 1).add((byte) b);
      textList.add(new ArrayList<>());
    } else {
      textList.get(textList.size() - 1).add((byte) b);
    }
    this.afterWrite();
  }
}
