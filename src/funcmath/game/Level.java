package funcmath.game;

import funcmath.function.Function;
import funcmath.object.MathObject;
import funcmath.utility.Helper;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Level implements Serializable {
    @Serial
    private static final long serialVersionUID = -815013050840486071L;

    LevelInfo levelInfo;
    LevelState currentLevelState;
    Stack<LevelState> history;

    boolean tutorial;
    int hint;
    boolean isCompleted = false;
    HashSet<String> usingNames = new HashSet<>();

    public boolean numsCheck(ArrayList<MathObject> args) {
        ArrayList<MathObject> nums = Helper.deepClone(currentLevelState.getNumbers());
        for (MathObject arg : args) {
            if (nums.contains(arg)) nums.remove(arg);
            else return false;
        }
        return true;
    }

    public void restart() {
        currentLevelState = levelInfo.getLevelState();
        history.clear();
        history.add(Helper.deepClone(currentLevelState));
    }

    // public void computation(...) {...}

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public LevelState getCurrentLevelState() {
        return currentLevelState;
    }

    public void setLevelState(LevelState levelState) {
        this.currentLevelState = levelState;
    }

    public Stack<LevelState> getHistory() {
        return history;
    }
}
