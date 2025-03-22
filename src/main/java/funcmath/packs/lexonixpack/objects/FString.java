package funcmath.packs.lexonixpack.objects;

import funcmath.object.MathObject;
import funcmath.utility.Hash;
import java.util.HashSet;

public class FString implements MathObject {
  @Override
  public String getType() {
    return "string";
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
