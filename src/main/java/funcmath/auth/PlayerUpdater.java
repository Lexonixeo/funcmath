package funcmath.auth;

import funcmath.exceptions.AuthorizationException;
import funcmath.exceptions.GameException;
import funcmath.game.Logger;
import funcmath.level.LevelPrimaryKey;
import funcmath.level.LevelState;
import funcmath.level.LevelStatistics;
import funcmath.level.PlayFlag;
import funcmath.utility.Hash;
import funcmath.utility.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class PlayerUpdater {
  private static final HashMap<Hash, ArrayList<Hash>> CONVERT_GRAPH = new HashMap<>();
  private static final HashMap<Pair<Hash, Hash>, Method> CONVERT_HASH_MAP = new HashMap<>();

  public static Player update(Player player, Hash newVersion) {
      Player ans = player;
      ArrayList<Hash> way = findShortestWay(player.getPlayerVersion(), newVersion);
      if (way == null) {
      throw new AuthorizationException(
          "Не существует прямого способа конвертации игрока из " + player.getPlayerVersion() + " в " + newVersion);
      }
      ArrayList<Hash> ourWay = new ArrayList<>(way.subList(1, way.size()));
      for (Hash node : ourWay) {
          ans = directUpdate(ans, node);
      }
      return ans;
  }

    // bfs
    private static ArrayList<Hash> findShortestWay(Hash start, Hash end) {
        ArrayList<Hash> queue = new ArrayList<>();
        queue.add(start);
        HashMap<Hash, ArrayList<Hash>> ways = new HashMap<>();
        ways.put(start, new ArrayList<>(Collections.singletonList(start)));
        for (int i = 0; i < queue.size(); i++) {
            Hash node = queue.get(i);
            for (Hash neighbour : CONVERT_GRAPH.get(node)) {
                if (ways.get(neighbour) == null) {
                    queue.add(neighbour);
                    ArrayList<Hash> wayToNeighbour = new ArrayList<>(ways.get(node));
                    wayToNeighbour.add(neighbour);
                    ways.put(neighbour, wayToNeighbour);
                }
            }
        }
        return ways.get(end);
    }

  private static Player directUpdate(Player player, Hash newVersion) {
    Logger.write("Прямое обновление игрока из " + player.getPlayerVersion() + " в " + newVersion);
      try {
          return (Player) CONVERT_HASH_MAP.get(new Pair<>(player.getPlayerVersion(), newVersion)).invoke(null, player);
      } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
          throw new AuthorizationException("Не удалось обновить игрока с " + player.getPlayerVersion() + " на " + newVersion);
      }
  }

  private static void registerConvert(Hash oldVersion, Hash newVersion, Method method) {
    if (!CONVERT_GRAPH.containsKey(oldVersion)) {
      CONVERT_GRAPH.put(oldVersion, new ArrayList<>());
    }
    if (!CONVERT_GRAPH.containsKey(newVersion)) {
        CONVERT_GRAPH.put(newVersion, new ArrayList<>());
    }
    CONVERT_GRAPH.get(oldVersion).add(newVersion);
    CONVERT_HASH_MAP.put(new Pair<>(oldVersion, newVersion), method);
  }

    public static void init() {
        try {
            registerConvert(null, Hash.encode(0), PlayerUpdater.class.getMethod("firstUpdate", Player.class));
        } catch (NoSuchMethodException e) {
            throw new GameException(e);
        }
    }

  public static Player firstUpdate(Player x) {
      x.completedLevels = new HashSet<>();
      x.levelStatistics = new HashMap<>();
      x.levelStates = new HashMap<>();

      for (Long ID : x.completedDefaultLevels) {
          x.completedLevels.add(new LevelPrimaryKey(ID, PlayFlag.DEFAULT));
      }
      for (Long ID : x.completedCustomLevels) {
          x.completedLevels.add(new LevelPrimaryKey(ID, PlayFlag.CUSTOM));
      }
      for (LevelStatistics stats : x.defaultLevelStats.values()) {
          x.levelStatistics.put(stats.getPrimaryKey(), stats);
      }
      for (LevelStatistics stats : x.customLevelStats.values()) {
          x.levelStatistics.put(stats.getPrimaryKey(), stats);
      }
      for (LevelState state : x.defaultLevelStates.values()) {
          x.levelStates.put(state.getPrimaryKey(), state);
      }
      for (LevelState state : x.customLevelStates.values()) {
          x.levelStates.put(state.getPrimaryKey(), state);
      }
      x.playerVersion = Hash.encode(0);
      return x;
  }
}
