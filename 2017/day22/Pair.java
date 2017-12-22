public class Pair<L,R> extends Object{

  private final L x;
  private final R y;

  public Pair(L x, R y) {
    this.x = x;
    this.y = y;
  }

  public L getX() { return x; }
  public R getY() { return y; }

  @Override
  public int hashCode() { return x.hashCode() ^ y.hashCode(); }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair)) return false;
    Pair pairo = (Pair) o;
    return this.x.equals(pairo.getX()) &&
           this.y.equals(pairo.getY());
  }

}
