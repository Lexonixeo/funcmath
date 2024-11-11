package funcmath.exceptions;

public class NoRegisteredPlayerException extends RuntimeException {
    public NoRegisteredPlayerException(String name) {
        super("Пользователь " + name + " не зарегистрирован");
    }
}
