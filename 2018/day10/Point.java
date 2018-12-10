public class Point implements Comparable<Point> {
    public int posx,posy,velx,vely;

    public Point(int px, int py, int vx, int vy) {
	posx = px;
	posy = py;
	velx = vx;
	vely = vy;
    }

    public void update() {
	posx += velx;
	posy += vely;
    }

    public void reverse() {
	posx -= velx;
	posy -= vely;
    }    

    @Override
    public int compareTo(Point q) {
	//this point is smaller than q if y is smaller or if equal, if x is smaller
	if ( (posy < q.posy) || ((posy == q.posy) && (posx < q.posx)) ) {
	    return -1;
	} else if (posx==q.posx && posy==q.posy) {
	    return 0;
	} else {
	    return 1;
	}
    }

    public String toString() {
	return "("+posx+","+posy+")";

    }
}
