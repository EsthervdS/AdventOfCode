public class Location implements Comparable<Location> {
    
    public int x,y;
    public Location parent;
    public int dist;
    public int level;
    public boolean justWarped;

    public Location(int i, int j, int d, int l, Location p) {
        x = i;
	y = j;
	dist = d;
	level = l;
	parent = p;
	justWarped = false;
    }

    public int compareTo(Location l) {
	if (dist == l.dist) {
	    return 0;
	} else {
	    return (dist < l.dist) ? -1 : 1;
	}
    }

    public String toString() {
	return "Loc: ("+x+","+y+") | dist: " + dist + " | level: " + level;
    }

    public boolean isSame(Location l) {
	return (x==l.x) && (y==l.y) && (level==l.level);
    }
}
