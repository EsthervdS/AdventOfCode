public class Portal {

    public Coord first,second;
    public String label;
    
    public Portal(String l, Coord f) {
	label = l;
	first = f;
    }

    public void setSecond(Coord c) {
        second = c;
    }

    public String toString() {
        String res = "Portal " + label + ": " + first.toString() + " - ";
	res += (second == null) ? "NULL" : second.toString();
	return res;
    }
}
