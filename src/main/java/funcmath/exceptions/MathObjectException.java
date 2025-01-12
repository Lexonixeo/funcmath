package funcmath.exceptions;

import funcmath.utility.Helper;
import funcmath.utility.Log;

public class MathObjectException extends RuntimeException {
  public MathObjectException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public MathObjectException(Exception e) {
    super(Helper.getLastMessage(e), e);
    Log.getInstance().write(this);
  }
}
