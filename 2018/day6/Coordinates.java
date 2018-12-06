import java.io.*;
import java.util.*;
import util.*;

public class Coordinates {

    public ArrayList<String> lines;
    public ArrayList<Location> locs;
    public int[][] labels;
    public int maxX,maxY;
    public int maxDistance = 10000;
    public int[][] distances;

    
    public Coordinates(String fileName) {
	lines = new ArrayList<String>();	
	lines = IO.readFile(fileName);
	locs = new ArrayList<Location>();

	maxX = -1;
	maxY = -1;
	int label = 1;
	//read coordinates
	for (String line : lines) {
	    int x = Integer.parseInt(line.split(",")[0]);
	    int y = Integer.parseInt(line.split(", ")[1]);
	    locs.add(new Location(x,y,label));
	    if (x>maxX) maxX = x;
	    if (y>maxY) maxY = y;
	    label++;
	}
	//init
	labels = new int[maxX+1][maxY+1];
	distances = new int[maxX+1][maxY+1];
	for (int i=1; i<maxX+1; i++) {
	    for (int j=1; j<maxY+1; j++) {
		labels[i][j] = -1;
		distances[i][j] = 0;
	    }
	}
	// -1 is init value
	// -10 is distance tie
	for (int i=1; i<maxX+1; i++) {
	    for (int j=1; j<maxY+1; j++) {
		Location closestLoc = new Location(-1000,-1000,-1);
		int minD = 1000;
		// find closest location
		for (Location l : locs) {
		    int curD = Math.abs(i-l.x) + Math.abs(j-l.y);
		    //add distance for part 2
		    distances[i][j] += curD;
		    if (curD<minD) {
			closestLoc = l;
			minD = curD;
		    }
		}
		if (labels[i][j] == -1) {
		    labels[i][j] = closestLoc.label;
		} else {
		    //was already set, so tie in distance
		    labels[i][j] = -10;
		}
	    }
	}
    }

    public void computeFinite(Location l) {
	for (int i=1; i<maxX+1; i++) {
	    for (int j=1; j<maxY+1; j++) {
		if (labels[i][j] == l.label) {
		    if ((i==1) || (j==1) || (i==maxX) || (j==maxY)) {
			l.finite = false;
		    }
		}
	    }
	}	
    }
    
    public int numberOfCells(Location l) {
	int res = 0;
	for (int i=1; i<maxX+1; i++) {
	    for (int j=1; j<maxY+1; j++) {
		if (labels[i][j] == l.label) {
		    res++;
		}
	    }
	}
	return res;
    }

    public int part2() {
	int count = 0;
	for (int i=1; i<maxX+1; i++) {
	    for (int j=1; j<maxY+1; j++) {
		if (distances[i][j] < maxDistance) count++;
	    }
	}
	return count;
    }
    
    public static void main(String[] args) {
	Coordinates cc = new Coordinates(args[0]);
	int maxCells = -1;
	
	for (Location l : cc.locs) {
	    cc.computeFinite(l);
	    if (l.finite) {
		int curCells = cc.numberOfCells(l);
		if (curCells > maxCells) {
		    maxCells = curCells;
		}
	    }
	}
	IO.print("Part 1: " + maxCells);
	IO.print("Part 2: " + cc.part2());
    }

}
