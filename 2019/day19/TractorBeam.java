import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class TractorBeam {

    public ArrayList<String> lines;
    public ArrayList<Long> program;
    public Intcode drone;
    public int[][] grid;
    public int part1,p2;
    public boolean part2;
    
    public TractorBeam(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
        part1 = 0;
	part2 = false;
	program = new ArrayList<Long>();
	String[] ss = lines.get(0).split(",");
	for (String s : ss) {
	    program.add(Long.parseLong(s));
	}

	grid = new int[2000][2000];
	int search = 100;
	int x,y;
	x = y = p2 = 0;
	int curBox = 0;
	while (!part2) {
	    //   IO.print("Searching box of size: " + curBox);
	    //process horizontal edge of box
	    y = curBox;
	    x = 0;
	    while (x<=y) {
		process(x,y);
		x++;
	    }
	    //process vertical edge of box
	    x = curBox;
	    y = 0;
	    while (y<x) {
		process(x,y);
		if (gridFitsBox(search,x,y)) {
		    int ax = x-search+1;
		    int ay = y-search+1;
		    p2 = 10000*ax + ay;
		    part2 = true;
		}
		y++;
	    }
	    curBox++;
	}
	IO.print("Part 1: " + part1);
	IO.print("Part 2: " + p2);
    }

    public boolean gridFitsBox(int size, int curX, int curY) {
	if (grid[curX][curY] == 0) {
	    return false;
	} else if (curX < size) {
	    return false;
	} else {
	    return (grid[curX-size+1][curY] == 1) && (grid[curX-size+1][curY-size+1] == 1) && (grid[curX][curY-size+1] == 1);
	}
    }
    
    public void process(int i, int j) {
	int cur = getStatus(i,j);
	grid[i][j] = cur;
	if (i<50 && j<50) {
	    part1 += cur;
	}
    }
    public int getStatus(int i, int j) {
	drone = new Intcode(program);
	drone.input.add((long) i);
	drone.input.add((long) j);
	drone.runUntilHalted();
	return (int) drone.output;
    }
    
    public static void main(String[] args) {
	TractorBeam tb = new TractorBeam(args[0]);

     }
}
