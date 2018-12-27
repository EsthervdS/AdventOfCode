import java.util.*;
import java.math.*;
import util.*;

public class Nanobots {
    ArrayList<String> lines;
    ArrayList<Nanobot> bots;
    long xmin,ymin,zmin,xmax,ymax,zmax;
    
    public Nanobots(String filename) {
	lines = IO.readFile(filename);
	bots = new ArrayList<Nanobot>();
	long maxR = -1;
	Nanobot maxN = null;
	xmin = ymin = zmin = Long.MAX_VALUE;
	xmax = ymax = zmax = -1*Long.MAX_VALUE;
	
	for (String line : lines) {
	    Nanobot n = new Nanobot(line);
	    bots.add(n);
	    if (n.r > maxR) {
		maxR = n.r;
		maxN = n;
	    }
	    if (n.x > xmax) xmax = n.x;
	    if (n.y > ymax) ymax = n.y;
	    if (n.z > zmax) zmax = n.z;
	    if (n.x < xmin) xmin = n.x;
	    if (n.y < ymin) ymin = n.y;
	    if (n.z < zmin) zmin = n.z;
	}
	int c = 0;
	for (Nanobot n : bots) {
	    if (maxN.inRange(n)) c++;
	}
	IO.print("Part 1: " + c);
	part2();
    }

    public void part2() {
	int maxCount = -1;
        Point3D densest = null;
	ArrayList<Cube> prioQ = new ArrayList<Cube>();
	//create cube over all nanobots
	//add to Q
	long w = -1;
	if ( ( (xmax-xmin) < (ymax-ymin) ) && ( (xmax - xmin < zmax-zmin) ) ) {
	    w = xmax-xmin;
	} else if ( ( (ymax-ymin) < (xmax-xmin)) && ( (ymax - ymin) < (zmax - zmin) ) ) {
	    w = ymax - ymin;
	} else {
	    w = zmax - zmin;
	}

	w= (long) Math.pow(2, Math.ceil(Math.log(w)/Math.log(2)));
	Point3D p = new Point3D(-w/2,-w/2,-w/2);
	Cube c = new Cube(p, w, bots);
	prioQ.add(c);

	Cube cur = prioQ.remove(0);
	while(cur.width > 1) {
	    //add 8 subcubes to Q
	    long newW = cur.width / 2;
	    
	    Point3D p1 = cur.corner;
	    Point3D p2 = new Point3D(cur.corner.x+newW,cur.corner.y,cur.corner.z);
	    Point3D p3 = new Point3D(cur.corner.x,cur.corner.y+newW,cur.corner.z);
	    Point3D p4 = new Point3D(cur.corner.x,cur.corner.y,cur.corner.z+newW);
	    Point3D p5 = new Point3D(cur.corner.x+newW,cur.corner.y+newW,cur.corner.z);
	    Point3D p6 = new Point3D(cur.corner.x+newW,cur.corner.y,cur.corner.z+newW);
	    Point3D p7 = new Point3D(cur.corner.x,cur.corner.y+newW,cur.corner.z+newW);
	    Point3D p8 = new Point3D(cur.corner.x+newW,cur.corner.y+newW,cur.corner.z+newW);
	    
	    Cube c1 = new Cube(p1,newW,bots);
	    Cube c2 = new Cube(p2,newW,bots);
	    Cube c3 = new Cube(p3,newW,bots);
	    Cube c4 = new Cube(p4,newW,bots);
	    Cube c5 = new Cube(p5,newW,bots);
	    Cube c6 = new Cube(p6,newW,bots);
	    Cube c7 = new Cube(p7,newW,bots);
	    Cube c8 = new Cube(p8,newW,bots);

	    if (c1.nBots > 0) prioQ.add(c1);
	    if (c2.nBots > 0) prioQ.add(c2);
	    if (c3.nBots > 0) prioQ.add(c3);
	    if (c4.nBots > 0) prioQ.add(c4);
	    if (c5.nBots > 0) prioQ.add(c5);
	    if (c6.nBots > 0) prioQ.add(c6);
	    if (c7.nBots > 0) prioQ.add(c7);
	    if (c8.nBots > 0) prioQ.add(c8);
	    Collections.sort(prioQ);

	    //IO.print("prioQ is now: " + prioQ.toString()+"\n");
	    cur = prioQ.remove(0);
	}
	//local search
	Integer c1,c2,c3,c4,c5,c6,c7,c8;
	c1 = c2 = c3 = c4 = c5 = c6 = c7 = c8 = 0;
	Point3D p1 = cur.corner;
	Point3D p2 = new Point3D(cur.corner.x+1,cur.corner.y,cur.corner.z);
	Point3D p3 = new Point3D(cur.corner.x,cur.corner.y+1,cur.corner.z);
	Point3D p4 = new Point3D(cur.corner.x,cur.corner.y,cur.corner.z+1);
	Point3D p5 = new Point3D(cur.corner.x+1,cur.corner.y+1,cur.corner.z);
	Point3D p6 = new Point3D(cur.corner.x+1,cur.corner.y,cur.corner.z+1);
	Point3D p7 = new Point3D(cur.corner.x,cur.corner.y+1,cur.corner.z+1);
	Point3D p8 = new Point3D(cur.corner.x+1,cur.corner.y+1,cur.corner.z+1);
	for (Nanobot b : bots) {
	    if (b.inRange(p1)) c1++;
	    if (b.inRange(p2)) c2++;
	    if (b.inRange(p3)) c3++;
	    if (b.inRange(p4)) c4++;
	    if (b.inRange(p5)) c5++;
	    if (b.inRange(p6)) c6++;
	    if (b.inRange(p7)) c7++;
	    if (b.inRange(p8)) c8++;
	}

        java.util.List<Integer> max = Arrays.asList(new Integer[] {c1,c2,c3,c4,c5,c6,c7,c8});
	if (c1 == Collections.max(max)) {
	    IO.print("Part 2: "  + p1.toString() + " with distance " + p1.distOrigin());
	} else if (c2 == Collections.max(max)) {
	    IO.print("Part 2: "  + p2.toString() + " with distance " + p2.distOrigin());
	} else if (c3 == Collections.max(max)) {
	    IO.print("Part 2: "  + p3.toString() + " with distance " + p3.distOrigin());
	} else if (c4 == Collections.max(max)) {
	    IO.print("Part 2: "  + p4.toString() + " with distance " + p4.distOrigin());
	} else if (c5 == Collections.max(max)) {
	    IO.print("Part 2: "  + p5.toString() + " with distance " + p5.distOrigin());
	} else if (c6 == Collections.max(max)) {
	    IO.print("Part 2: "  + p6.toString() + " with distance " + p6.distOrigin());
	} else if (c7 == Collections.max(max)) {
	    IO.print("Part 2: "  + p7.toString() + " with distance " + p7.distOrigin());
	} else if (c8 == Collections.max(max)) {
	    IO.print("Part 2: "  + p8.toString() + " with distance " + p8.distOrigin());
	}
	
    }

    public static void main(String[] args) {
	Nanobots c = new Nanobots(args[0]);
    }
}
