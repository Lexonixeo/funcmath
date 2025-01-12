package funcmath.gui.swing;

import funcmath.exceptions.JavaException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GInputStream extends InputStream {
  byte[] contents;
  boolean check = false;
  int pointer = 0;

  public GInputStream() {}

  public void send(String text) {
    contents = text.getBytes(StandardCharsets.UTF_8);
    pointer = 0;
  }

  @Override
  public int read() throws IOException {
    while (contents == null && !check) {
      // wait, Thread.yield() throws NoSuchElementException
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new JavaException(e);
      }
    }
    if (check) {
      check = false;
      return -1;
    }
    if (pointer >= contents.length) {
      contents = null;
      check = true;
      return -1;
    }
    return this.contents[pointer++] & 0xFF;
  }
}
