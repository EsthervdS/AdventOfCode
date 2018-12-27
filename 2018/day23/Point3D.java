import java.util.*;
import java.math.*;
import util.*;

public class Point3D implements Comparable<Point3D>{

    long x,y,z;
    
    public Point3D(long i,long j, long k) {
	x=i;
	y=j;
	z=k;
    }

    public long distOrigin() {
	return x+y+z;
    }
    
    @Override
    public int compareTo(Point3D q) {
	long distP = x+y+z;
	long distQ = q.x+q.y+q.z;
	if (distP < distQ) {
	    return -1;
	} else if (distP == distQ) {
	    return 0;
	} else {
	    return 1;
	}
    }

    public String toString() {
	return ("("+x+","+y+","+z+")");
    }
}
