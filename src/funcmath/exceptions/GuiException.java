package funcmath.exceptions;

import funcmath.game.Log;

public class GuiException extends RuntimeException {
  public GuiException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public GuiException(Exception e) {
    super(e);
    Log.getInstance().write(this);
  }
}
