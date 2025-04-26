package funcmath.level;

import funcmath.exceptions.LevelException;
import funcmath.game.Logger;
import funcmath.gui.swing.GPanel;
import funcmath.utility.Helper;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class LevelRegister {
  private static final HashMap<String, Level> LEVEL_TYPE_HASH_MAP =
      new HashMap<>(); // <Type, Level>
  private static ArrayList<String> DEFAULT_LEVEL_FILENAMES = new ArrayList<>();
  private static final ArrayList<Long> DEFAULT_LEVEL_LIST = new ArrayList<>();
  private static ArrayList<String> CUSTOM_LEVEL_FILENAMES = new ArrayList<>();
  private static final ArrayList<Long> CUSTOM_LEVEL_LIST = new ArrayList<>();
  private static ArrayList<String> PRE_LEVEL_FILENAMES = new ArrayList<>();
  private static final ArrayList<Long> PRE_LEVEL_LIST = new ArrayList<>();
  private static final HashMap<LevelPrimaryKey, String> LEVEL_NAMES = new HashMap<>();
  private static final HashMap<String, GPanel> LEVEL_MAKER_HASH_MAP = new HashMap<>();

  public static void registerType(Level l) {
    Logger.write("Регистрация уровневого типа " + l.getType());
    try {
      LEVEL_TYPE_HASH_MAP.put(l.getType(), l.getClass().getConstructor().newInstance());
    } catch (NoSuchMethodException e) {
      throw new LevelException("Отсутствует пустой конструктор уровня типа " + l.getType());
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static void registerLevelMaker(Level l, GPanel p) {
    Logger.write("Регистрация создателя уровня типа " + l.getType());
    LEVEL_MAKER_HASH_MAP.put(l.getType(), p);
  }

  public static GPanel getLevelMaker(String type) {
    return LEVEL_MAKER_HASH_MAP.get(type);
  }

  public static GPanel getLevelMaker(Level l) {
    return getLevelMaker(l.getType());
  }

  private static LevelInfo getLevelInfo(Long levelID, PlayFlag playFlag) {
    String levelSwitch =
        switch (playFlag) {
          case DEFAULT -> "levels";
          case CUSTOM -> "customLevels";
          case PRELEVELS -> "preLevels";
        };
    LevelInfo li = (LevelInfo) Helper.read("data\\" + levelSwitch + "\\level" + levelID + ".dat");
    li.setID(levelID);
    return li;
  }

  public static LevelInfo getLevelInfo(LevelPrimaryKey key) {
    return getLevelInfo(key.ID(), key.playFlag());
  }

  public static Level getLevel(LevelPrimaryKey key) {
    return getLevel(getLevelInfo(key));
  }

  public static Level getLevel(LevelInfo li) {
    Level l;
    try {
      l =
          LEVEL_TYPE_HASH_MAP
              .get(li.getType())
              .getClass()
              .getConstructor(LevelInfo.class)
              .newInstance(li);
    } catch (NoSuchMethodException e) {
      throw new LevelException(
          "Отсутствует конструктор уровня типа " + li.getType() + ", принимающий LevelInfo!");
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    l.setLevelState(li.getDefaultLevelState());
    return l;
  }

  public static Level getLevel(LevelState ls) {
    if (ls == null) return null;
    Level l = getLevel(ls.getPrimaryKey());
    l.setLevelState(ls);
    return l;
  }

  public static Level getNextLevel(LevelPrimaryKey lk) throws IndexOutOfBoundsException {
    switch (lk.playFlag()) {
      case DEFAULT -> {
        int i = DEFAULT_LEVEL_LIST.indexOf(lk.ID());
        return getLevel(
            new LevelPrimaryKey(DEFAULT_LEVEL_LIST.get(i + 1), PlayFlag.DEFAULT));
      }
      case CUSTOM -> {
        int i = CUSTOM_LEVEL_LIST.indexOf(lk.ID());
        return getLevel(
            new LevelPrimaryKey(CUSTOM_LEVEL_LIST.get(i + 1), PlayFlag.CUSTOM));
      }
      case PRELEVELS -> {
        int i = PRE_LEVEL_LIST.indexOf(lk.ID());
        return getLevel(
            new LevelPrimaryKey(PRE_LEVEL_LIST.get(i + 1), PlayFlag.PRELEVELS));
      }
    }
    return null;
  }

  public static void updateLevels() {
    if (!DEFAULT_LEVEL_FILENAMES.equals( Helper.getFileNames("data/levels/"))) {
      DEFAULT_LEVEL_FILENAMES = Helper.getFileNames("data/levels/");

      DEFAULT_LEVEL_LIST.clear();
      for (String fileName : DEFAULT_LEVEL_FILENAMES) {
        Long ID = Long.parseLong(fileName.substring(5, fileName.length() - 4));
        LevelPrimaryKey key = new LevelPrimaryKey(ID, PlayFlag.DEFAULT);
        LEVEL_NAMES.put(key, LevelRegister.getLevelInfo(key).getName());
        DEFAULT_LEVEL_LIST.add(ID);
      }
      DEFAULT_LEVEL_LIST.sort(Comparator.naturalOrder());
    }

    if (!CUSTOM_LEVEL_FILENAMES.equals( Helper.getFileNames("data/customLevels/"))) {
      CUSTOM_LEVEL_FILENAMES = Helper.getFileNames("data/customLevels/");

      CUSTOM_LEVEL_LIST.clear();
      for (String fileName : Helper.getFileNames("data\\customLevels\\")) {
        Long ID = Long.parseLong(fileName.substring(5, fileName.length() - 4));
        LevelPrimaryKey key = new LevelPrimaryKey(ID, PlayFlag.CUSTOM);
        LEVEL_NAMES.put(key, LevelRegister.getLevelInfo(key).getName());
        CUSTOM_LEVEL_LIST.add(ID);
      }
      CUSTOM_LEVEL_LIST.sort(Comparator.naturalOrder());
    }

    if (!PRE_LEVEL_FILENAMES.equals(Helper.getFileNames("data/preLevels/"))) {
      PRE_LEVEL_FILENAMES = Helper.getFileNames("data/preLevels/");

      PRE_LEVEL_LIST.clear();
      for (String fileName : Helper.getFileNames("data\\preLevels\\")) {
        Long ID = Long.parseLong(fileName.substring(5, fileName.length() - 4));
        LevelPrimaryKey key = new LevelPrimaryKey(ID, PlayFlag.PRELEVELS);
        LEVEL_NAMES.put(key, LevelRegister.getLevelInfo(key).getName());
        PRE_LEVEL_LIST.add(ID);
      }
      PRE_LEVEL_LIST.sort(Comparator.naturalOrder());
    }
  }

  private static void writeLevelInfo(LevelInfo li) {
    PlayFlag pf = li.getPlayFlag();
    String levelSwitch =
        switch (pf) {
          case DEFAULT -> "levels";
          case CUSTOM -> "customLevels";
          case PRELEVELS -> "preLevels";
        };
    Helper.write(li, "data\\" + levelSwitch + "\\level" + li.getID() + ".dat");
  }

  public static void addLevelInfo(LevelInfo li, boolean rewrite) {
    PlayFlag pf = li.getPlayFlag();

    // TODO: ссылочное присваивание или нет? - проверьте, мододелы
    ArrayList<Long> list =
        switch (pf) {
          case DEFAULT -> DEFAULT_LEVEL_LIST;
          case CUSTOM -> CUSTOM_LEVEL_LIST;
          case PRELEVELS -> PRE_LEVEL_LIST;
        };
    if (list.contains(li.getID()) && !rewrite) {
      li.setID(li.getID() + 1);
      addLevelInfo(
          li,
          false); // рекурсия когда-нибудь закончится, если уровней не столько же, сколько чисел в
      // Long
    } else {
      if (!list.contains(li.getID())) {
        list.add(li.getID());
      }
      writeLevelInfo(li);
    }
  }

  public static ArrayList<Long> getLevelList(PlayFlag flag) {
    return switch(flag) {
      case DEFAULT -> DEFAULT_LEVEL_LIST;
      case CUSTOM -> CUSTOM_LEVEL_LIST;
      case PRELEVELS -> PRE_LEVEL_LIST;
    };
  }

  public static String getName(LevelPrimaryKey key) {
    return LEVEL_NAMES.get(key);
  }

  // TO DO: добавить получение уровня через LevelState, там как раз появился LevelInfo.
  // может сохранять уровни в виде levelState, а не levelInfo?
}
