package funcmath.exceptions;

import funcmath.utility.Log;

public class LevelException extends RuntimeException {
  public LevelException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public LevelException(Exception e) {
    super(e);
    Log.getInstance().write(this);
  }
}
