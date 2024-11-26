package funcmath.exceptions;

import funcmath.game.Log;

public class MathObjectException extends RuntimeException {
  public MathObjectException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public MathObjectException(Exception e) {
    super(e);
    Log.getInstance().write(this);
  }
}
