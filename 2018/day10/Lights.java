import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Lights {
    ArrayList<String> lines;
    ArrayList<Point> points;
    int maxX,maxY;
    int minX,minY;
    int minDiff;
    boolean found;
    int seconds;
    
    public Lights(String filename) {
	lines = IO.readFile(filename);
	maxX = maxY = -1*Integer.MAX_VALUE;
	minX = minY = Integer.MAX_VALUE;
	points = new ArrayList<Point>();
	
	for (String line : lines) {
	    int px = Integer.parseInt(line.split("<")[1].trim().split(",")[0].trim());
	    int py = Integer.parseInt(line.split(">")[0].trim().split(",")[1].trim());
	    int vx = Integer.parseInt(line.split("<")[2].trim().split(",")[0].trim());
	    int vy = Integer.parseInt(line.split(">")[1].trim().split(",")[1].trim());
	    Point p = new Point(px,py,vx,vy);
	    points.add(p);
	    if (px>maxX) maxX=px;
	    if (py>maxY) maxY=py;
	    if (px<minX) minX=px;	    
	    if (py<minY) minY=py;	    
	}
	//sort from top to bottom and left to right
	Collections.sort(points);
	minDiff = Integer.MAX_VALUE;
	found = false;
	seconds = 0;
    }

    public void displayPoints() {
	int xOffset,yOffset;
	xOffset = yOffset = 0;
	ArrayList<Point> toDo = new ArrayList<Point>();
	toDo.addAll(points);
	Collections.sort(toDo);
	if (minX<0) xOffset = -minX;
	if (minX>0) xOffset = minX;
	if (minY<0) yOffset = -minY;
	if (minY>0) yOffset = minY;
	for (int j=minY+yOffset;j<=maxY+yOffset;j++) {
	    for (int i=minX+xOffset;i<=maxX+xOffset;i++) {
		if (toDo.size()>0) {
		    Point p = toDo.get(0);
		    if ((p.posx==(i-xOffset)) && (p.posy==(j-yOffset))) {
			System.out.print("#");
			while ( (toDo.size()>0) && (toDo.get(0).posx == p.posx && (toDo.get(0).posy == p.posy))) {
			    p = toDo.get(0);
			    toDo.remove(0);
			}
		    } else {
			System.out.print(".");
		    }
		} else {
		    System.out.print(".");
		}
	    }
	    System.out.print("\n");
	}
    }

    public void update() {
	seconds++;
	maxX = maxY = -1*Integer.MAX_VALUE;
	minX = minY = Integer.MAX_VALUE;

	for (Point p : points) {
	    p.update();
	    if (p.posx>maxX) maxX=p.posx;
	    if (p.posy>maxY) maxY=p.posy;
	    if (p.posx<minX) minX=p.posx;	    
	    if (p.posy<minY) minY=p.posy;	    
	}
	Collections.sort(points);
	if (maxY-minY < minDiff) {
	    minDiff = maxY-minY;
	} else {
	    found = true;
	}
    }

    public void reverse() {
	maxX = maxY = -1*Integer.MAX_VALUE;
	minX = minY = Integer.MAX_VALUE;

	for (Point p : points) {
	    p.reverse();
	    if (p.posx>maxX) maxX=p.posx;
	    if (p.posy>maxY) maxY=p.posy;
	    if (p.posx<minX) minX=p.posx;	    
	    if (p.posy<minY) minY=p.posy;	    
	}
	Collections.sort(points);
    }
    
    public static void main(String[] args) {
	Lights l = new Lights(args[0]);
	int i=0;
	while (true) {
	    i++;
	    l.update();
	    if (l.found) {
		l.reverse();
		l.seconds -= 1;
		break;
	    }
	}
	IO.print("Part 1:");
	l.displayPoints();
	IO.print("Part 2: " + l.seconds);
    }
}
