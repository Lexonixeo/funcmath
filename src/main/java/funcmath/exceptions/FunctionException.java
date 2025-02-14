package funcmath.exceptions;


public class FunctionException extends GameException {
  public FunctionException(String message) {
    super(message);
  }

  public FunctionException(Exception e) {
    super(e);
  }

  public FunctionException(String message, Exception e) {
    super(message, e);
  }
}
