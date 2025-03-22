package funcmath.exceptions;

public class MathException extends GameException {
  public MathException(String message) {
    super(message);
  }

  public MathException(Exception e) {
    super(e);
  }
}
