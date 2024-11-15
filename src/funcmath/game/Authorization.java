package funcmath.game;

import funcmath.Helper;
import funcmath.exceptions.AuthorizationException;
import java.util.HashMap;

public class Authorization {
  public static Player login(String name, String password) {
    HashMap<Integer, String> players;
    if (!Helper.isFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = (HashMap<Integer, String>) Helper.read("data\\players\\players.dat");
    }

    if (!players.containsValue(name)) {
      throw new AuthorizationException("Пользователь не найден!");
    } else if (!players.containsKey(password.hashCode())
        || !players.get(password.hashCode()).equals(name)) {
      throw new AuthorizationException("Неверный пароль!");
    }

    return new Player(name, true);
  }

  public static Player register(String name, String password) {
    HashMap<Integer, String> players;
    if (!Helper.isFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = (HashMap<Integer, String>) Helper.read("data\\players\\players.dat");
    }

    if (players.containsValue(name)) {
      throw new AuthorizationException("Пользователь уже существует!");
    }

    players.put(password.hashCode(), name);
    Helper.write(players, "data\\players\\players.dat");

    return new Player(name, false);
  }

  public static boolean isPlayerExists(String name) {
    HashMap<Integer, String> players;
    if (!Helper.isFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = (HashMap<Integer, String>) Helper.read("data\\players\\players.dat");
    }
    return players.containsValue(name);
  }
}
