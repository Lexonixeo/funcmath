package funcmath.packs.defaultpack.objects;

import funcmath.utility.Hash;
import java.util.HashSet;

public class FUnknown extends FInteger {
  @Override
  public String getType() {
    return "unknown";
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
    return Hash.encode(super.getHash(), "unknown");
  }
}
