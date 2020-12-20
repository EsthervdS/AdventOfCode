import util.*;

class Pair {
    public long latest, previous;

    public Pair(long l, long p) {
	latest = l;
	previous = p;
    }
    
    public Pair(long l) {
	latest = l;
	previous = -1;
    }
    
    public String toString() {
	return "("+latest+","+previous+")";
    }
}
