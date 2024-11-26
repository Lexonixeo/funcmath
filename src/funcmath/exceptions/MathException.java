package funcmath.exceptions;

import funcmath.game.Log;

public class MathException extends RuntimeException {
  public MathException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public MathException(Exception e) {
    super(e);
    Log.getInstance().write(this);
  }
}
