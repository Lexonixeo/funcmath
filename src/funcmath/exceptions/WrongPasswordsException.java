package funcmath.exceptions;

public class WrongPasswordsException extends RuntimeException {
  public WrongPasswordsException() {
    super("Пароли не совпадают!");
  }
}
