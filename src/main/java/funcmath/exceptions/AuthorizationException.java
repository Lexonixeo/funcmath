package funcmath.exceptions;

public class AuthorizationException extends GameException {
  public AuthorizationException(String message) {
    super(message);
  }

  public AuthorizationException(Exception e) {
    super(e);
  }
}
