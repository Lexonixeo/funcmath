package funcmath.exceptions;

public class NoDomainOfDefinitionException extends RuntimeException {
  public NoDomainOfDefinitionException(String domain) {
    super("Не существует такой области определения: " + domain);
  }
}
