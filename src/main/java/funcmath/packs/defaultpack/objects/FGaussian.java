package funcmath.packs.defaultpack.objects;

import funcmath.object.MathObject;
import funcmath.utility.Hash;
import funcmath.utility.Helper;
import java.io.Serial;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

public class FGaussian implements MathObject {
  @Serial private static final long serialVersionUID = 8598593842030000611L;

  public static final FGaussian NEGATIVE_ONE = new FGaussian(-1, 0);
  public static final FGaussian ZERO = new FGaussian(0, 0);
  public static final FGaussian ONE = new FGaussian(1, 0);
  public static final FGaussian TWO = new FGaussian(2, 0);
  public static final FGaussian IMAGINARY_UNIT = new FGaussian(0, 1);

  private final String rawName;
  private String name;

  FInteger real, imaginary;

  public FGaussian() {
    this(FInteger.ZERO, FInteger.ZERO);
  }

  public FGaussian(long re, long im) {
    this(new FInteger(re), new FInteger(im));
  }

  public FGaussian(BigInteger re, BigInteger im) {
    this(new FInteger(re), new FInteger(im));
  }

  public FGaussian(FInteger re, FInteger im) {
    this.real = re;
    this.imaginary = im;
    this.rawName =
        this.real.toString()
            + (this.imaginary.compareTo(FInteger.ZERO) >= 0 ? "+" : "") // знак мнимой компоненты
            + this.imaginary
            + "i";
    this.name = this.rawName;
  }

  public FGaussian(String s) {
    ArrayList<String> snumbers =
        Helper.wordsFromString(s.replace('i', ' ').replace('+', ' ').replaceAll("-", " -"));
    this(new FInteger(snumbers.get(0)), new FInteger(snumbers.get(1)));
  }

  @Override
  public String getType() {
    return "gaussian";
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getRawName() {
    return rawName;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public void generateName(HashSet<String> usedNames) {
    String newName = rawName + "[gauss]";
    int i = 0;
    while (usedNames.contains(newName)) {
      i++;
      newName = rawName + "[gauss" + i + "]";
    }
    this.name = newName;
  }

  @Override
  public Hash getHash() {
    return Hash.encode(real, imaginary);
  }

  public FInteger getRe() {
    return real;
  }

  public FInteger getIm() {
    return imaginary;
  }

  public static FInteger re(FGaussian number) {
    return number.getRe();
  }

  public static FInteger im(FGaussian number) {
    return number.getIm();
  }
}
