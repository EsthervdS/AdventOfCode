import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
import java.math.*;

public class Vents {

    public ArrayList<String> lines;
    public int[][] grid;
    public int w,h;
    
    public Vents(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	w = h = 1000;
	grid = new int[w][h];
	
    }

    public int part1() {
	resetGrid();
	computeGrid(false);
	int res = 0;
	for (int i=0; i<w; i++) {
	    for (int j=0; j<h; j++) {
		if (grid[i][j] >= 2) res++;
	    }
	}
	return res;
    }
   
    public int part2() {
	resetGrid();
	computeGrid(true);
	int res = 0;
	for (int i=0; i<w; i++) {
	    for (int j=0; j<h; j++) {
		if (grid[i][j] >= 2) res++;
	    }
	}
	return res;
    }

    public void resetGrid() {
	for (int i=0; i<w; i++) {
	    for (int j=0; j<h; j++) {
		grid[i][j] = 0;
	    }
	}
    }

    public void computeGrid(boolean diags) {
	for (String line : lines) {
	    //IO.print("Processing: " + line);
	    String[] coords = line.split(" -> ");
	    String[] first = coords[0].split(",");
	    String[] second = coords[1].split(",");
	    int x1 = Integer.parseInt(first[0]);
	    int y1 = Integer.parseInt(first[1]);
	    int x2 = Integer.parseInt(second[0]);
	    int y2 = Integer.parseInt(second[1]);

	    if (y1 == y2) {
		//horizontal
		grid[x1][y1]++;
		int dx = (x1 < x2) ? 1 : -1;
		int i=0;
		while ( (x1 + i*dx) != x2 ) {
		    i++;
		    grid[x1+i*dx][y1]++;
		}
	    } else if (x1 == x2) {
		//vertical
		grid[x1][y1]++;
		int i=0;
		int dy = (y1 < y2) ? 1 : -1;
		while ( (y1 + i*dy) != y2) {
		    i++;
		    grid[x1][y1+i*dy]++;
		}
	    } else {
		if (diags) {
		    int dx = (x1 < x2) ? 1 : -1;
		    int dy = (y1 < y2) ? 1 : -1;
		    int i=0;
		    grid[x1][y1]++;
		    while ( (y1 + i*dy) != y2) {
			i++;
			grid[x1+i*dx][y1+i*dy]++;			
		    }
		}
	    }
	}
    }

    public void printGrid() {
	for (int j=0; j<h; j++) {
	    String l = "";
	    for (int i=0; i<w; i++) {
		l = l + grid[i][j];
	    }
	IO.print(l);
	}
	IO.print("");
    }
    
    public static void main(String[] args) {
	     Vents v= new Vents(args[0]);
	     IO.print("Part 1: " + v.part1());
	     IO.print("Part 2: " + v.part2());
     }
}
