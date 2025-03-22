package funcmath.exceptions;

public class LevelException extends GameException {
  public LevelException(String message) {
    super(message);
  }

  public LevelException(Exception e) {
    super(e);
  }
}
