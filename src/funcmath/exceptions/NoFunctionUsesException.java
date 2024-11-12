package funcmath.exceptions;

public class NoFunctionUsesException extends RuntimeException {
  public NoFunctionUsesException() {
    super("У функции закончилось число использований.");
  }
}
