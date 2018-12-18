public class Position implements Comparable<Position> {
    public int x,y;
    
    public Position(int i, int j) {
	x = i;
	y = j;
    }

    public Position(Unit u) {
	x = u.x;
	y = u.y;
    }

    public Position left() {
	return (new Position(x-1,y));
    }
    public Position right() {
	return (new Position(x+1,y));
    }
    public Position top() {
	return (new Position(x,y-1));
    }
    public Position bottom() {
	return (new Position(x,y+1));
    }
    
    @Override
    public int compareTo(Position p2) {
	if ( (y < p2.y) || ((y == p2.y) && (x < p2.x)) ) {
	    return -1;
	} else if (x==p2.x && y==p2.y) {
	    return 0;
	} else {
	    return 1;
	}
    }

    
    
    public int distanceTo(Position p2) {
	return (Math.abs(x-p2.x) + Math.abs(y-p2.y));
    }
    
    public String toString() {
	return ("("+x+","+y+")");
    }
}
