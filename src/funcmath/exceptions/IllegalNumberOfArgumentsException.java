package funcmath.exceptions;

public class IllegalNumberOfArgumentsException extends RuntimeException {
    public IllegalNumberOfArgumentsException(int functionArgs, int args) {
        super("Не совпадает число аргументов функции: должно быть " + functionArgs + ", есть: " + args);
    }
}
