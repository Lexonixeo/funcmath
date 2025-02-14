package funcmath.utility;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

// мб сделать extends String?
public class Hash implements Serializable {
  @Serial private static final long serialVersionUID = -8087992275412077181L;

  private final String hash;
  private final byte[] digest;

  private Hash(String hash, byte[] digest) {
    this.hash = hash;
    this.digest = digest;
  }

  public static Hash encode(Object... o) {
    // https://stackoverflow.com/questions/30109958/calculating-sha-3-hash-in-java

    SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
    byte[] digest;
    try {
      digest = digestSHA3.digest(Serializer.serialize(o));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new Hash(Hex.toHexString(digest), digest);
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
    return Objects.hashCode(hash);
  }

  public long toLong() {
    return (new BigInteger(digest)).longValue();
  }

  @Override
  public String toString() {
    return hash;
  }
}
