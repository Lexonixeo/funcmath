package funcmath.exceptions;

public class PlayerIsRegisteredException extends RuntimeException {
    public PlayerIsRegisteredException(String name) {
        super("Пользователь " + name + " уже зарегистрирован!");
    }
}
