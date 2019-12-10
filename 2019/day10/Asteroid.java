import util.*;

public class Asteroid implements Comparable<Asteroid> {
    public int x,y,id;
    public double angle, radius;

    public Asteroid(int i, int j, int it) {
	x = i;
	y = j;
	id = it;
	angle = -1.0*Integer.MAX_VALUE;
	radius = -1.0*Integer.MAX_VALUE;
    }

    public String toString() {
        String res = id + ": (" + x + "," + y + ")";
	if ( !(angle < -10) ) res += " | angle = " + angle + " | radius = " + radius;
	return res;
    }

    public int compareTo(Asteroid a){  
	if ((angle == a.angle) && (radius == a.radius)) {
	    return 0;
	} else if ( (angle > a.angle) || (angle == a.angle && radius > a.radius) ) {
	    return 1;
	} else  
	    return -1;  
    }

    public void setRadius(Asteroid o) {
	radius = Math.sqrt((o.x - x) * (o.x - x) + (o.y - y) * (o.y - y));
    }

    public void setAngle(Asteroid o) {

	if((x > o.x)) {//above 0 to 180 degrees

	    angle = (Math.atan2((x - o.x), (o.y - y)) * 180 / Math.PI);

	} else if ((x < o.x)) {//above 180 degrees to 360/0

	    angle =  360 - (Math.atan2((o.x - x), (o.y - y)) * 180 / Math.PI);

	} else {

	    angle =  Math.atan2(0 ,0);
	}
    }
}

