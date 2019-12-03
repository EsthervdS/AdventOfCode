import java.util.*;
import util.*;
import java.awt.geom.*;

public class Wires {

    public ArrayList<String> lines;
    public ArrayList<Line2D.Float> wires,wires2;
    public ArrayList<Point2D.Float> intersections;
    public Point2D.Float startP;
    
    public Wires(String fileName) {
	// start timing
	long startTime = System.currentTimeMillis();

	//initialize variables
	lines = new ArrayList<String>();
	wires = new ArrayList<Line2D.Float>();
	wires2 = new ArrayList<Line2D.Float>();
	intersections = new ArrayList<Point2D.Float>();	
	lines = IO.readFile(fileName);
	startP = new Point2D.Float(0,0);

	//parse the two wires and detect intersections
	parseWire(lines.get(0), true);
	parseWire(lines.get(1), false);

	//compute Manhattan distances of intersections to starting point
	//find lowest one
	int minD = Integer.MAX_VALUE;
	for (Point2D.Float p : intersections) {
	    int d = Math.abs((int) (p.getX() - startP.getX())) + Math.abs((int) (p.getY() - startP.getY()));
	    if (d < minD && d > 0) minD = d;
	}
	IO.print("Part 1: " + minD);

	//compute timing part 1
	long endTime = System.currentTimeMillis();
	long duration = (endTime - startTime);	
	IO.print("Exec part 1: " + duration + "ms");

	//start timing part 2
	startTime = System.currentTimeMillis();

	//compute wire distances / steps of intersections to starting point
	//detect lowest one
	minD = Integer.MAX_VALUE;
	for (Point2D.Float p : intersections) {

	    int d1 = 0;
	    int d2 = 0;

	    for (Line2D.Float l : wires) {
		if (lineContains(l,p)) {
		    int diff = (int) (Math.abs(l.getX1() - p.getX()) + Math.abs(l.getY1() - p.getY()));
		    d1 += diff;
		    break;
		} else {
		    d1 += Math.abs(l.getX1() - l.getX2()) + Math.abs(l.getY1() - l.getY2());
		}
	    }

	    for (Line2D.Float l : wires2) {

		if (lineContains(l,p)) {
		    int diff = (int) (Math.abs(l.getX1() - p.getX()) + Math.abs(l.getY1() - p.getY()));
		    d2 += diff;

		    break;
		} else {
		    d2 += Math.abs(l.getX1() - l.getX2()) + Math.abs(l.getY1() - l.getY2());
		}
	    }
	    if ((d1+d2) < minD && (d1+d2) > 0) minD = (d1+d2);
	}
	IO.print("Part 2: " + minD);

	//compute timing part 2
	endTime = System.currentTimeMillis();
	duration = (endTime - startTime);	
	IO.print("Exec part 2: " + duration + "ms");
	
    }

    /* HELPER FUNCTIONS */
    public String lineToString(Line2D.Float l) {
	//to print line segment to output
	return ("{("+l.getX1()+","+l.getY1()+"),("+l.getX2()+","+l.getY2()+")}");
    }

    public boolean lineContains(Line2D.Float l, Point2D.Float p) {
	//detect if point p lies on line segment l (which is either horizontal or vertical
	if (l.getX1() == l.getX2()) {
	    //vertical
	    return ( (p.getX() == l.getX1()) && (p.getY() >= Math.min(l.getY1(),l.getY2())) && (p.getY() <= Math.max(l.getY1(),l.getY2())) );
	} else {
	    return ( (p.getY() == l.getY1()) && (p.getX() >= Math.min(l.getX1(),l.getX2())) && (p.getX() <= Math.max(l.getX1(),l.getX2())) );
	}
    }
    
    public Point2D.Float getIntersectionPoint(Line2D.Float line1, Line2D.Float line2) {
	//compute the intersection point of line1 and line2
	if (! line1.intersectsLine(line2) ) return null;
	double px = line1.getX1(),
            py = line1.getY1(),
            rx = line1.getX2()-px,
            ry = line1.getY2()-py;
	double qx = line2.getX1(),
            qy = line2.getY1(),
            sx = line2.getX2()-qx,
            sy = line2.getY2()-qy;
	
	double det = sx*ry - sy*rx;
	if (det == 0) {
	    return null;
	} else {
	    double z = (sx*(qy-py)+sy*(px-qx))/det;
	    return new Point2D.Float((float)(px+z*rx), (float)(py+z*ry));
	}
    } 
    
    public void parseWire(String line, boolean first) {
	//parse a wire into an arraylist of line segments
	//if this is the second wire, compute and save intersections
	String[] moves = line.split(",");
	float px = startP.x;
	float py = startP.y;
	
	for (int i=0; i<moves.length; i++) {
	    
	    char dir = moves[i].charAt(0);
	    int len = Integer.parseInt(moves[i].substring(1));

	    //init l to prevent uninitialize error
	    Line2D.Float l = new Line2D.Float(new Point2D.Float(Integer.MAX_VALUE,Integer.MAX_VALUE),new Point2D.Float(-1*Integer.MAX_VALUE,-1*Integer.MAX_VALUE));

	    switch(dir) {
	    case 'R' :
		l = new Line2D.Float(new Point2D.Float(px,py),new Point2D.Float(px+len,py));
		px += len;
		break;
	    case 'L' :
		l = new Line2D.Float(new Point2D.Float(px,py),new Point2D.Float(px-len,py));
		px -= len;
		break;
	    case 'U' :
		l = new Line2D.Float(new Point2D.Float(px,py),new Point2D.Float(px,py-len));
		py -= len;
		break;
	    case 'D' :
		l = new Line2D.Float(new Point2D.Float(px,py),new Point2D.Float(px,py+len));
		py += len;
		break;
	    default : break;
	    }
	    if (first) {
		wires.add(l);
	    } else {
		wires2.add(l);
		//detect interection
		for (Line2D.Float l2 : wires) {
		    if (l2.intersectsLine(l)) {
			Point2D.Float p = getIntersectionPoint(l,l2);
			if (p != null && !p.equals(startP)) intersections.add(p);
		    } 
		}
	    }
	}
    }


    public static void main(String[] args) {
	Wires w = new Wires(args[0]);
    }
}
