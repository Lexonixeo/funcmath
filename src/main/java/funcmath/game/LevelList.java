package funcmath.game;


import funcmath.exceptions.LevelException;
import funcmath.utility.Helper;

import java.util.ArrayList;
import java.util.Comparator;

public class LevelList {
    public static ArrayList<Integer> getLevelList(LevelPlayFlag flag) {
        ArrayList<String> fileNames = switch(flag) {
            case DEFAULT -> Helper.getFileNames("data\\levels\\");
            case CUSTOM -> Helper.getFileNames("data\\customLevels\\");
            case PRE -> Helper.getFileNames("data\\preLevels\\");
        };
        ArrayList<Integer> answer = new ArrayList<>();
        for (String fileName : fileNames) {
            answer.add(Integer.parseInt(fileName.substring(5, fileName.length() - 4)));
        }
        answer.sort(Comparator.naturalOrder());
        return answer;
    }

    public static int getNextLevel(int level, LevelPlayFlag flag) {
        ArrayList<Integer> levelList = getLevelList(flag);
        int lastLevelIndex = levelList.lastIndexOf(level);
        if (levelList.size() - 1 == lastLevelIndex) {
            throw new LevelException("Нет следующего уровня!");
        } else {
            return levelList.get(lastLevelIndex + 1);
        }
    }

    public static int getBackLevel(int level, LevelPlayFlag flag) {
        ArrayList<Integer> levelList = getLevelList(flag);
        int lastLevelIndex = levelList.lastIndexOf(level);
        if (0 == lastLevelIndex) {
            throw new LevelException("Нет предыдущего уровня!");
        } else {
            return levelList.get(lastLevelIndex - 1);
        }
    }
}
