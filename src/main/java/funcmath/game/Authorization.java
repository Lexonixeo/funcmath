package funcmath.game;

import funcmath.exceptions.AuthorizationException;
import funcmath.utility.Helper;
import funcmath.utility.Log;
import java.util.HashMap;

public class Authorization {
  public static Player login(String name, String password) {
    Log.getInstance().write("Authorization is in progress...");
    HashMap<Integer, String> players;
    if (Helper.isNotFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = Helper.cast(Helper.read("data\\players\\players.dat"), new HashMap<>());
    }

    if (!players.containsValue(name)) {
      throw new AuthorizationException("User not found!");
    } else if (!players.containsKey(password.hashCode())
        || !players.get(password.hashCode()).equals(name)) {
      throw new AuthorizationException("Incorrect password!");
    }

    Log.getInstance().write("Player authorized: " + name);
    return new Player(name, true);
  }

  public static Player register(String name, String password) {
    Log.getInstance().write("Registration is taking place...");
    HashMap<Integer, String> players;
    if (Helper.isNotFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = Helper.cast(Helper.read("data\\players\\players.dat"), new HashMap<>());
    }

    if (players.containsValue(name)) {
      throw new AuthorizationException("The user already exists!");
    }

    players.put(password.hashCode(), name);
    Helper.write(players, "data\\players\\players.dat");

    Log.getInstance().write("Player registered: " + name);
    return new Player(name, false);
  }

  public static boolean isPlayerExists(String name) {
    HashMap<Integer, String> players;
    if (Helper.isNotFileExists("data\\players\\players.dat")) {
      players = new HashMap<>();
    } else {
      players = Helper.cast(Helper.read("data\\players\\players.dat"), new HashMap<>());
    }
    return players.containsValue(name);
  }
}
