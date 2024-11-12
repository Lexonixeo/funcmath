package funcmath.exceptions;

public class IllegalLevelFlagException extends RuntimeException {
  public IllegalLevelFlagException(int flag) {
    super("Такого флага нет: " + flag);
  }
}
