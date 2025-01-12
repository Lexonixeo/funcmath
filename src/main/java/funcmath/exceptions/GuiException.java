package funcmath.exceptions;

import funcmath.utility.Helper;
import funcmath.utility.Log;

public class GuiException extends RuntimeException {
  public GuiException(String message) {
    super(message);
    Log.getInstance().write(this);
  }

  public GuiException(Exception e) {
    super(Helper.getLastMessage(e), e);
    Log.getInstance().write(this);
  }
}
