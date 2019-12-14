public class Pair {
    public String name;
    public int amount;

    public Pair(String n, int a) {
	name = n;
	amount = a;
    }

    public Pair(String p) {
	amount = Integer.parseInt(p.split(" ")[0]);
	name = p.split(" ")[1];
    }

    public String toString() {
	return amount + " " + name;
    }
}
