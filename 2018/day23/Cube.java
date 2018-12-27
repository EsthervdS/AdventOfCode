import java.util.*;
import util.*;

public class Cube implements Comparable<Cube> {
    public int nBots;
    public Point3D corner;
    public long width;
    public long xmin,xmax,ymin,ymax,zmin,zmax;

    public Cube(Point3D c, long w, ArrayList<Nanobot> bots) {
	corner = c;
	width = w;
	xmin = c.x;
	ymin = c.y;
	zmin = c.z;
	xmax = c.x + width;
	ymax = c.y + width;
	zmax = c.z + width;
        countBots(bots);
    }

    public void countBots(ArrayList<Nanobot> bots) {
	//count bots in list that are in range of some point in this cube
	int count = 0;
	for (Nanobot b : bots) {
	    Point3D p1 = corner;
	    Point3D p2 = new Point3D(corner.x+width,corner.y,corner.z);
	    Point3D p3 = new Point3D(corner.x,corner.y+width,corner.z);
	    Point3D p4 = new Point3D(corner.x,corner.y,corner.z+width);
	    Point3D p5 = new Point3D(corner.x+width,corner.y+width,corner.z);
	    Point3D p6 = new Point3D(corner.x,corner.y,corner.z+width);
	    Point3D p7 = new Point3D(corner.x,corner.y+width,corner.z+width);
	    Point3D p8 = new Point3D(corner.x+width,corner.y+width,corner.z+width);
	    if (b.inRange(p1) || b.inRange(p2) || b.inRange(p3) || b.inRange(p4) || b.inRange(p5) || b.inRange(p6) || b.inRange(p7) || b.inRange(p8)) count++;
	}
	nBots = count;
    }

    @Override
    public int compareTo(Cube c) {
	if (nBots > c.nBots) {
	    return -1;
	} else if ((nBots == c.nBots) && (corner.distOrigin() < c.corner.distOrigin())) {
	    return -1;
	} else if ((nBots == c.nBots) && (corner.distOrigin() == c.corner.distOrigin()) && (width < c.width)) {
	    return -1;
	} else if ((nBots == c.nBots) && (corner.distOrigin() == c.corner.distOrigin()) && (width == c.width)) {
	    return 0;
	} else {
	    return 1;
	}
    }

    public String toString() {
	return ("[n=" + nBots + "; w=" + width + "; p=" + corner.toString() + "]");
    }
}

