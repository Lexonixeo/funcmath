package funcmath.exceptions;

public class TypeRegisterException extends GameException {
  public TypeRegisterException(String message) {
    super(message);
  }

  public TypeRegisterException(Exception e) {
    super(e);
  }

  public TypeRegisterException(String message, Exception e) {super(message, e);}
}
