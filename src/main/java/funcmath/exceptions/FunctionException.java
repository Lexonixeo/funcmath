package funcmath.exceptions;

import funcmath.utility.Log;

public class FunctionException extends RuntimeException {
  public FunctionException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public FunctionException(Exception e) {
    super(e);
    Log.getInstance().write(this);
  }
}
