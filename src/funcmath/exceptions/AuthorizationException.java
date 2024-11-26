package funcmath.exceptions;

import funcmath.utility.Log;

public class AuthorizationException extends RuntimeException {
  public AuthorizationException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public AuthorizationException(Exception e) {
    super(e);
    Log.getInstance().write(this);
  }
}
