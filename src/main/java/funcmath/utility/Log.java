package funcmath.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
  private static final Log instance = new Log(); // singleton

  String pathname;

  private Log() {
    this.pathname =
        "data/logs/"
            + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
                .format(new Date(System.currentTimeMillis()))
            + ".txt";
  }

  public void write(String s) {
    Helper.writeLine("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .format(new Date(System.currentTimeMillis())) + "]: " + s, pathname);
  }

  public void write(Exception e) {
    PrintStream stream;
    try {
      stream = new PrintStream(new FileOutputStream(pathname, true));
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    }
    stream.print(
        "["
            + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new Date(System.currentTimeMillis()))
            + "]: ");
    e.printStackTrace(stream);
  }

  public String getPathname() {
    return pathname;
  }

  public static Log getInstance() {
    return instance;
  }
}
