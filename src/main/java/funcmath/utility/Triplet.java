package funcmath.utility;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Triplet<T1, T2, T3> implements Serializable {
  @Serial private static final long serialVersionUID = 8181641874160647780L;

  private final T1 x;
  private final T2 y;
  private final T3 z;

  public Triplet(T1 first, T2 second, T3 third) {
    this.x = first;
    this.y = second;
    this.z = third;
  }

  public T1 first() {
    return x;
  }

  public T2 second() {
    return y;
  }

  public T3 third() {
    return z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
    return Objects.equals(x, triplet.x)
        && Objects.equals(y, triplet.y)
        && Objects.equals(z, triplet.z);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }
}
