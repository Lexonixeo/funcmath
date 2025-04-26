package funcmath.exceptions;

public class InterfaceException extends GameException {
  public InterfaceException(String message) {
    super(message);
  }

  public InterfaceException(Exception e) {
    super(e);
  }
}
