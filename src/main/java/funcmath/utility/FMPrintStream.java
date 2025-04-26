package funcmath.utility;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

public abstract class FMPrintStream extends PrintStream {
  public FMPrintStream(OutputStream out) {
    super(out);
  }

  public FMPrintStream(OutputStream out, boolean autoFlush, Charset charset) {
    super(out, autoFlush, charset);
  }

  public abstract void clear();
}
