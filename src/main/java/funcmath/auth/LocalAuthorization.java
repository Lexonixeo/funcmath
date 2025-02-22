package funcmath.auth;

import funcmath.exceptions.AuthorizationException;
import funcmath.game.Logger;
import funcmath.utility.Hash;
import funcmath.utility.Helper;
import funcmath.utility.Pair;
import java.util.HashMap;

public class LocalAuthorization {
  // регистрируем локально только тех, кто регистрировался на этом пк и кто входил на этом пк (и при этом на какой-то период)
  // TODO: можно сделать историю игрока в виде блокчейна!
  public static Player login(String name, String password) {
    Logger.write("Попытка локальной авторизации...");
    HashMap<String, Pair<String, Player>> players;
    // TODO: кажется, хотелось бы, чтобы каждый игрок хранился в своем файле
    if (Helper.isNotFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players =
              (HashMap<String, Pair<String, Player>>) Helper.read("data\\players\\players.dat");
    }

    if (!players.containsKey(name)) {
      throw new AuthorizationException("Пользователь с таким именем не найден!");
    }
    if (!players.get(name).first().equals(Hash.encode(password).toString())) {
      throw new AuthorizationException("Неверный пароль!");
    }

    Logger.write("Игрок локально авторизован: " + name);

    return players.get(name).second();
  }

  public static Player register(String name, String password) {
    Logger.write("Попытка локальной регистрации...");
    HashMap<String, Pair<String, Player>> players;
    if (Helper.isNotFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = (HashMap<String, Pair<String, Player>>) Helper.read("data\\players\\players.dat");
    }

    if (players.containsKey(name)) {
      throw new AuthorizationException("Пользователь с таким именем уже существует!");
    }

    Player p = new Player(name, password);

    players.put(name, new Pair<>(Hash.encode(password).toString(), p));
    Helper.write(players, "data\\players\\players.dat");

    Logger.write("Игрок локально зарегистрирован: " + name);
    return p;
  }
}
