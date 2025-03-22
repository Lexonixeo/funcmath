package funcmath.utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FMInputStream extends InputStream {
  byte[] contents;
  int pointer = 0;

  public void send(String text) {
    contents = text.getBytes(StandardCharsets.UTF_8);
    pointer = 0;
  }

  @Override
  public int read() throws IOException {
    while (contents == null) {
      // wait, Thread.yield() throws NoSuchElementException
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new IOException("Поток, который читает GInputStream, закрывается");
      }
    }
    if (pointer >= contents.length) {
      contents = null;
      return -1;
    }
    return this.contents[pointer++] & 0xFF;
  }
}
