package funcmath.level;

import java.io.Serializable;

// текущее состояние уровня (чтобы после выхода можно было вернуться назад)
public interface LevelState extends Serializable {
  String getType();

  LevelInfo
      getLevelInfo(); // должен быть levelInfo, чтобы можно было вспомнить, что за уровень ты
                      // проходил - рекурсия?
  // TO DO: может LevelState убрать в Level? - тут более согл... а история действий? хотя... историю
  // действий делает каждый тип уровня сам.
}
