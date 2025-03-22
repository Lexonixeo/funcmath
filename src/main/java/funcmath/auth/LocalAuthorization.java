package funcmath.auth;

import funcmath.exceptions.AuthorizationException;
import funcmath.game.Logger;
import funcmath.utility.Hash;
import funcmath.utility.Helper;
import funcmath.utility.Pair;
import java.util.HashMap;

public class LocalAuthorization {
  // регистрируем локально только тех, кто регистрировался на этом пк и кто входил на этом пк (и при
  // этом на какой-то период)
  public static Player login(String name, String password) {
    Logger.write("Попытка локальной авторизации...");
    HashMap<String, Pair<String, String>> players;
    // кажется, хотелось бы, чтобы каждый игрок хранился в своем файле
    if (Helper.isNotFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = (HashMap<String, Pair<String, String>>) Helper.read("data\\players\\players.dat");
    }

    if (!players.containsKey(name)) {
      throw new AuthorizationException("Пользователь с таким именем не найден!");
    }
    if (!players.get(name).first().equals(Hash.encode(password).toString())) {
      throw new AuthorizationException("Неверный пароль!");
    }

    Logger.write("Игрок локально авторизован: " + name);

    return getLocalPlayer(players.get(name).second(), password);
  }

  public static Player register(String name, String password) {
    Logger.write("Попытка локальной регистрации...");
    HashMap<String, Pair<String, String>> players;
    if (Helper.isNotFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = (HashMap<String, Pair<String, String>>) Helper.read("data\\players\\players.dat");
    }

    if (players.containsKey(name)) {
      throw new AuthorizationException("Пользователь с таким именем уже существует!");
    }

    Player p = new Player(name, password);

    players.put(name, new Pair<>(Hash.encode(password).toString(), p.getHash().toString()));
    Helper.write(players, "data\\players\\players.dat");

    Logger.write("Игрок локально зарегистрирован: " + name);
    return p;
  }

  private static Player getLocalPlayer(String hash, String password) {
    if (Helper.isNotFileExists("data\\players\\" + hash + ".dat")) {
      throw new AuthorizationException("Не существует локального игрока с таким хэшем");
    }
    Player p = (Player) Helper.read("data/players/" + hash + ".dat");
    if (!p.passHash.equals(Hash.encode(password))) {
      throw new AuthorizationException("Неверный пароль!");
    } else {
      return p;
    }
  }
}
