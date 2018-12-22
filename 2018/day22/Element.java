public class Element implements Comparable<Element>{

    int x,y,t,d;
    
    public Element(int i, int j, int tool, int dist) {
	x = i;
	y = j;
	t = tool;
	d = dist;
    }

    @Override
    public int compareTo(Element e2) {
	if (d < e2.d) {
	    return -1;
	} else if (d == e2.d && ( (x<e2.x) || (x==e2.x && (y<e2.y) ) ) ) {
	    return -1;
	} else if ( (x == e2.x) && (y == e2.y) && (t == e2.t) && (d == e2.d) ) {
	    return 0;
	} else {
	    return 1;
	}
    }

    public boolean equals(Element e2) {
	return ( (x==e2.x) && (y==e2.y) && (t==e2.t) && (d == e2.d) );
    }
    public String toString() {
	return ("(" + x + "," + y + "," + t + "," + d + ")");
    }
}
