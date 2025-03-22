package funcmath.level;

import java.io.Serial;
import java.io.Serializable;

public record LevelPrimaryKey(Long ID, PlayFlag playFlag) implements Serializable {
  @Serial private static final long serialVersionUID = -8868922036572598794L;
}
