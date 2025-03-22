package funcmath.packs.lexonixpack.objects;

import funcmath.object.MathObject;
import funcmath.utility.Hash;
import java.util.HashSet;

public class FColor implements MathObject {
  @Override
  public String getType() {
    return "color";
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public String getRawName() {
    return "";
  }

  @Override
  public void generateName(HashSet<String> usedNames) {}

  @Override
  public Hash getHash() {
    return null;
  }
}
