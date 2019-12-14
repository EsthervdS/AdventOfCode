import java.math.*;

public class Pair {
    public String name;
    public BigInteger amount;

    public Pair(String n, BigInteger a) {
	name = n;
	amount = a;
    }

    public Pair(String p) {
	amount = new BigInteger(p.split(" ")[0]);
	name = p.split(" ")[1];
    }

    public String toString() {
	return amount + " " + name;
    }
}
