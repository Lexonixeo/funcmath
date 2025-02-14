package funcmath.exceptions;


public class ServerException extends GameException {
  public ServerException(String message) {
    super(message);
  }

  public ServerException(Exception e) {
    super(e);
  }
}
