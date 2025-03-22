package funcmath.exceptions;

import funcmath.game.Logger;
import funcmath.utility.Helper;

public class GameException extends RuntimeException {
  public GameException(String message) {
    super(message);
    Logger.write(this);
  }

  public GameException(Exception e) {
    super(Helper.getLastMessage(e), e);
    Logger.write(this);
  }

  public GameException(String message, Exception e) {
    super(message, e);
    Logger.write(this);
  }
}
