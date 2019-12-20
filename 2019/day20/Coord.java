public class Coord implements Comparable<Coord> {

    int x,y;

    public Coord(int i, int j) {
	x=i;
	y=j;
    }

    public String toString() {
	return "("+x+","+y+")";
    }

    public int compareTo(Coord c) {
	if (x<c.x || (x == c.x && y<c.y)) {
	    return -1;
	} else if (x == c.x && y == c.y) {
	    return 0;
	} else {
	    return 1;
	}
    }
}
