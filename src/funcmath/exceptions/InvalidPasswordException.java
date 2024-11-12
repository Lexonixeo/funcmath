package funcmath.exceptions;

public class InvalidPasswordException extends RuntimeException {
  public InvalidPasswordException() {
    super("Неверный пароль!");
  }
}
