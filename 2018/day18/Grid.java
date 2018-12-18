import java.util.*;
import util.*;

public class Grid {
    public static char[][] grid;
    public static int xmax, ymax, wood, lumber;
    public static int minutesPassed,loop;
    public static HashMap<Integer,Integer> prevValues;
    public static HashMap<Integer,Integer> prevValuesRev;
    public static boolean sameResourceValue;

    public static char[][] buildGrid(ArrayList<String> lines) {
	prevValues = new HashMap<Integer,Integer>();
	prevValuesRev = new HashMap<Integer,Integer>();
	sameResourceValue = false;
	loop = 0;
	minutesPassed = 0;
	xmax = lines.get(0).length();
	ymax = lines.size();
	grid = new char[xmax][ymax];
	int j=0;
	for (String line : lines) {
	    for (int i=0; i<line.length(); i++) {
		grid[i][j] = line.charAt(i);
	    }
	    j++;
	}
	return grid;
    }

    public static void display() {
	IO.print("\nAfter " + minutesPassed + " minutes:");
	for (int j=0; j<ymax; j++) {
	    for (int i=0; i<xmax; i++) {
		System.out.print(grid[i][j]+"");
	    }
	    System.out.println("");
	}
	IO.print("");
    }

    public static char[][] minute() {
	char[][] newgrid = new char[xmax][ymax];
	for (int i=0; i<xmax; i++) {
	    for (int j=0; j<ymax; j++) {
		int wood = 0;
		int open = 0;
		int lumber = 0;
		if (i-1>=0) {
		    //left 
		    if (grid[i-1][j] == '|') wood++;
		    if (grid[i-1][j] == '.') open++;
		    if (grid[i-1][j] == '#') lumber++;
		    //left top
		    if (j-1>=0) {
			if (grid[i-1][j-1] == '|') wood++;
			if (grid[i-1][j-1] == '.') open++;
			if (grid[i-1][j-1] == '#') lumber++;
		    }
		    //left bottom
		    if (j+1<ymax) {
			if (grid[i-1][j+1] == '|') wood++;
			if (grid[i-1][j+1] == '.') open++;
			if (grid[i-1][j+1] == '#') lumber++;
		    }
		}
		//top
		if (j-1>=0) {
		    if (grid[i][j-1] == '|') wood++;
		    if (grid[i][j-1] == '.') open++;
		    if (grid[i][j-1] == '#') lumber++;
		}
		//bottom
		if (j+1<ymax) {
		    if (grid[i][j+1] == '|') wood++;
		    if (grid[i][j+1] == '.') open++;
		    if (grid[i][j+1] == '#') lumber++;
		}
		if (i+1<xmax) {
		    //right 
		    if (grid[i+1][j] == '|') wood++;
		    if (grid[i+1][j] == '.') open++;
		    if (grid[i+1][j] == '#') lumber++;
		    //right top
		    if (j-1>=0) {
			if (grid[i+1][j-1] == '|') wood++;
			if (grid[i+1][j-1] == '.') open++;
			if (grid[i+1][j-1] == '#') lumber++;
		    }
		    //rightt bottom
		    if (j+1<ymax) {
			if (grid[i+1][j+1] == '|') wood++;
			if (grid[i+1][j+1] == '.') open++;
			if (grid[i+1][j+1] == '#') lumber++;
		    }
		}
		if (grid[i][j] == '.') {
		    newgrid[i][j] = (wood >= 3) ? '|' : '.';
		}
		if (grid[i][j] == '|') {
		    newgrid[i][j] = (lumber >= 3) ? '#' : '|';
		}
		if (grid[i][j] == '#') {
		    newgrid[i][j] = ((lumber >= 1) && (wood >= 1)) ? '#' : '.';
		}
	    }
	}
	part1();
	if (minutesPassed > 750) {
	    if (prevValues.containsKey((wood*lumber))) {
		sameResourceValue = true;
		loop = Math.abs( prevValues.get(wood*lumber) - minutesPassed );
	    } else {
		prevValues.put(wood*lumber,minutesPassed);
		prevValuesRev.put(minutesPassed,wood*lumber);
	    }
	} else {
	    if (minutesPassed > 500) {
		prevValues.put(wood*lumber,minutesPassed);
		prevValuesRev.put(minutesPassed,wood*lumber);
	    }
	}	    
	minutesPassed++;
	    
	grid = newgrid;
	return newgrid;
    }

    public static int part1() {
	wood = 0;
	lumber = 0;
	for (int i=0; i<xmax; i++) {
	    for (int j=0; j<ymax; j++) {
		if (grid[i][j] == '|') wood++;
		if (grid[i][j] == '#') lumber++;
	    }
	}
	return (wood*lumber);
    }
    
}
