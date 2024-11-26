package funcmath.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Log {
  private static final Log instance = new Log(); // попробовал реализовать singleton

  String pathname;

  private Log() {
    this.pathname = "data/logs/" + System.currentTimeMillis() + ".txt";
  }

  public void write(String s) {
    Helper.writeLine("[" + System.currentTimeMillis() + "]: " + s, pathname);
  }

  public void write(Exception e) {
    PrintStream stream;
    try {
      stream = new PrintStream(new FileOutputStream(pathname, true));
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    }
    stream.print("[" + System.currentTimeMillis() + "]: ");
    e.printStackTrace(stream);
  }

  public static Log getInstance() {
    return instance;
  }
}
