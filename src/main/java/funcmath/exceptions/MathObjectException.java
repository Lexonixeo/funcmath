package funcmath.exceptions;

public class MathObjectException extends GameException {
  public MathObjectException(String message) {
    super(message);
  }

  public MathObjectException(Exception e) {
    super(e);
  }
}
