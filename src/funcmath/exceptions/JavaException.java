package funcmath.exceptions;

import funcmath.utility.Log;

public class JavaException extends RuntimeException {
  public JavaException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public JavaException(Exception e) {
    super(e);
    Log.getInstance().write(this);
  }
}
