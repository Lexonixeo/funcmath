package funcmath.gui.swing;

import funcmath.exceptions.JavaException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GInputStream extends InputStream {
  byte[] contents;
  boolean check = false;
  int pointer = 0;

  boolean stopper = false;

  public void send(String text) {
    contents = text.getBytes(StandardCharsets.UTF_8);
    pointer = 0;
  }

  public void stop() {
    this.stopper = true;
  }

  @Override
  public int read() throws IOException {
    while (contents == null && !stopper) {
      // wait, Thread.yield() throws NoSuchElementException
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new JavaException(e);
      }
    }
    if (stopper) {
      throw new JavaException("Stopping at reading");
    }
    if (pointer >= contents.length) {
      contents = null;
      return -1;
    }
    return this.contents[pointer++] & 0xFF;
  }
}
