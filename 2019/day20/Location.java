public class Location implements Comparable<Location> {
    
    public int x,y;
    public Location parent;
    public int dist;

    public Location(int i, int j, int d, Location p) {
        x = i;
	y = j;
	parent = p;
	dist = d;
    }

    public int compareTo(Location l) {
	if (dist == l.dist) {
	    return 0;
	} else {
	    return (dist < l.dist) ? -1 : 1;
	}
    }

    public String toString() {
	return "Loc: ("+x+","+y+") | dist: " + dist;
    }

    public boolean isSame(Location l) {
	return (x==l.x) && (y==l.y);
    }
}
