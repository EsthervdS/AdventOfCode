import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Donut {

    public ArrayList<String> lines;
    public int xmax,ymax,xmin,ymin,rows,cols;
    public int[][] grid;
    public HashMap<String,Portal> portals;
    public HashMap<Integer,Location> administration;    
    public ArrayList<Coord> portalCoords;
    public Coord start,target;
    public PriorityQueue<Location> toProcess;
    public int maxDist;
    
    public Donut(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	portals = new HashMap<String,Portal>();
        portalCoords = new ArrayList<Coord>();
	administration = new HashMap<Integer,Location>();	
	toProcess = new PriorityQueue<Location>();
	initGrid();
	readGrid();
	//printGrid();

	part1();
	

    }

    public void part1() {
	while (toProcess.size() > 0) {
	    ///check current location
	    Location cur = toProcess.remove();
	    if (cur.dist > maxDist) maxDist = cur.dist;
	    if (cur.x == target.x && cur.y == target.y) {
		IO.print("Part 1: " + maxDist);
		break;
	    }

	    //check neighbours & portal
	    if (isPortal(cur.x,cur.y)) {
		Coord out = findOtherSide(new Coord(cur.x,cur.y));
		Location otherSide = new Location(out.x,out.y,-1,null);
		processNeighbour(otherSide,cur);		
	    }
	    
	    if (!administration.containsKey(index(cur.x-1,cur.y))) {
		Location left = new Location(cur.x-1,cur.y,-1,null);
		processNeighbour(left,cur);
	    }
	    if (!administration.containsKey(index(cur.x+1,cur.y))) {		
		Location right = new Location(cur.x+1,cur.y,-1,null);
		processNeighbour(right,cur);
	    }
	    if (!administration.containsKey(index(cur.x,cur.y-1))) {		
		Location top = new Location(cur.x,cur.y-1,-1,null);
		processNeighbour(top,cur);
	    }
	    if (!administration.containsKey(index(cur.x,cur.y+1))) {		
		Location bottom = new Location(cur.x,cur.y+1,-1,null);
		processNeighbour(bottom,cur);
	    }
	}
    }
    
    public void processNeighbour(Location loc, Location cur) {	
	if (notIn(toProcess,loc)) {
	    if ((loc.x<0 || loc.x>=cols) || (loc.y<0 || loc.y>=rows)) {
		//skip
	    } else {
		int canMove = grid[loc.x][loc.y];
		if (canMove >= 1) {
		    loc.parent = cur;
		    loc.dist = cur.dist+1;
		    toProcess.add(loc);
		} 
		administration.put(index(loc.x,loc.y),loc);
	    }
	}
    }

    public boolean notIn(PriorityQueue<Location> q, Location l) {
	boolean res = true;
	for (Location loc : q) {
	    if (loc.isSame(l)) {
		res = false;
		break;
	    }
	}
	return res;
    }

    public Coord findOtherSide(Coord p) {
	Coord res =  null;
	//p is portal, find other side of portal to go there
	for (String s : portals.keySet()) {
	    Coord first = portals.get(s).first;
	    Coord second = portals.get(s).second;
	    if (first.x == p.x && first.y == p.y) {
	        res = second;
		break;
	    } else if (second.x == p.x && second.y == p.y) {
		res = first;
		break;
	    }
	}
	return res;
    }
    
    public void initGrid() {
	maxDist = -1;
	xmax = lines.get(0).length();
	ymax = lines.size();
	cols = xmax - 4;
	rows = ymax - 4;
	
	grid = new int[cols][rows];
	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		grid[i][j] = -1;
	    }
	}

    }

    public void readGrid() {
	for (int j=0; j<ymax; j++) {
	    for (int i=0; i<xmax; i++) {
		char c = lines.get(j).charAt(i);
		if (c == '#' || c == '.') {
		    grid[i-2][j-2] = (c=='#') ? 0 : 1;
		} else if (c != ' ') {
		    char below = (j<ymax-1) ? lines.get(j+1).charAt(i) : ' ';
		    char above = (j > 0) ? lines.get(j-1).charAt(i) : ' ';
		    char left = (i > 0) ? lines.get(j).charAt(i-1) : ' ';
		    char right = (i < xmax-1) ? lines.get(j).charAt(i+1) : ' ';
		    if (letter(above) || letter(left)) {
			//skip, we already processed this one
		    } else {
			String label = "";
			Coord port = new Coord(-1,-1);
			if (letter(below)) {
			    //vertical portal
			    label = ""+c+below;
			    if (j<ymax-3) {
				if (lines.get(j+2).charAt(i) == '.') {
				//portal is down
				    port = new Coord(i-2,j);
				}
			    }
			    if (j>0) {
				if (lines.get(j-1).charAt(i) == '.') {
				//portal is up
				    port = new Coord(i-2,j-3);
				}
			    }
			} else {
			    //horizontal portal
			    label = ""+c+right;
			    if (i<xmax-3) {
				if (lines.get(j).charAt(i+2) == '.') {
				    //portal is to the right
				    port = new Coord(i,j-2);
				} 
			    }
			    if (i>0) {
				if (lines.get(j).charAt(i-1) == '.') {
				    //portal is to the left
				    port = new Coord(i-3,j-2);
				}
			    }
			}
			if (portals.containsKey(label)) {
			    portals.get(label).setSecond(port);
			    portalCoords.add(port);
			} else {
			    if (label.equals("AA")) {
				start = port;
				Location root = new Location(port.x,port.y,0,null);
				toProcess.add(root);
				administration.put(index(port.x,port.y),root);				
			    } else if (label.equals("ZZ")) {
				target = port;
			    } else {
				portalCoords.add(port);
				portals.put(label,new Portal(label,port));
			    }
			} 
		    }
		}
	    }
	}
    }

    public boolean letter(char c) {
	return Character.isLetter(c);
    }

    public int index(int i, int j) {
	return 10000*i + j;
    }

    public boolean isPortal(int i, int j) {
	boolean res = false;
	for (Coord c : portalCoords) {
	    if (c.x == i && c.y == j) {
		res = true;
		break;
	    }
	}
	return res;
    }
    
    public void printGrid() {
	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		if (grid[i][j] == -1) {
		    System.out.print(" ");
		} else {
		    
		    if (isPortal(i,j)) {
			System.out.print("@");
		    } else {
			char c = (grid[i][j] == 1) ? '.' : 'X';
			System.out.print(c+"");
		    }
		}
	    }
	    IO.print("");
	}
    }

    public static void main(String[] args) {
	Donut d = new Donut(args[0]);

     }
}
