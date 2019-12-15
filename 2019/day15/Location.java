public class Location implements Comparable<Location> {

    public final static int UNK = -1;
    public final static int WALL = 0;
    public final static int OPEN = 1;
    public final static int TARGET = 2;
    
    //0: unknown (white), 1: visited (black), 2: to be visited (gray)
    public int x,y;
    public int color;
    public Location parent;
    public int dist;
    public int element;

    public Location(int i, int j, int d, Location p) {
        x = i;
	y = j;
	color = 0;
	parent = p;
	dist = d;
	element = UNK;
    }

    public int compareTo(Location l) {
	if (dist == l.dist) {
	    return 0;
	} else {
	    return (dist < l.dist) ? -1 : 1;
	}
    }

    public String toString() {
	return "Loc: ("+x+","+y+") | color: " + color + " dist: " + dist;
    }

    public boolean isSame(Location l) {
	return (x==l.x) && (y==l.y);
    }
}
