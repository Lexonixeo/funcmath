package funcmath.utility;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.bouncycastle.util.encoders.Hex;

// мб сделать extends String?
public class Hash implements Serializable {
  @Serial private static final long serialVersionUID = -8087992275412077181L;

  private final String hash;
  private final byte[] digest;

  private Hash(byte[] digest) {
    this.hash = Hex.toHexString(digest);
    this.digest = digest;
  }

  public static Hash encode(Object... o) {
    // https://stackoverflow.com/questions/30109958/calculating-sha-3-hash-in-java
    // https://stackoverflow.com/questions/332079/in-java-how-do-i-convert-a-byte-array-to-a-string-of-hex-digits-while-keeping-l
    // watch md5

    byte[] digest;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      digest = md.digest(Serializer.serialize(o));
    } catch (IOException | NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return new Hash(digest);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Hash hash1 = (Hash) o;
    return Objects.equals(hash, hash1.hash);
  }

  @Override
  public int hashCode() {
    return (new BigInteger(digest)).intValue();
  }

  public long toLong() {
    return (new BigInteger(digest)).longValue();
  }

  public BigInteger toBig() {
    return new BigInteger(digest);
  }

  @Override
  public String toString() {
    return hash;
  }
}
