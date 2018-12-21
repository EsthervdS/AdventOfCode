import java.util.*;
import util.*;

public class Rooms {
    ArrayList<String> lines;
    String line;
    int[][] grid,distances;
    String[] draw;
    int offset;
    Position start;
    Position curPos;
    int xmin,xmax,ymin,ymax;
    Tree tree;
    int part1,part2;
    
    public Rooms(String filename) {
	lines = IO.readFile(filename);
	offset = 200;
	curPos = start = new Position(offset,offset,0);
	part1 = -1;
	part2 = 0;
	
	// ? 0 . 1 | 2 - 3 # 4
	draw = new String[5];
	draw[0] = "?";
	draw[1] = ".";
	draw[2] = "|";
	draw[3] = "-";
	draw[4] = "#";
	
	initGrid();

	String line = lines.get(0).substring(1,lines.get(0).length()-1);
	curPos = start;
	tree = new Tree(line);
	buildGridFromTree();
	fillUpWalls();
    }

    public void initGrid() {
	xmin = ymin = xmax = ymax = offset;
	grid = new int[2*offset][2*offset];
	for (int i=xmin; i<=xmax; i++) {
	    for (int j=ymin; j<=ymax; j++) {
		grid[i][j] = 0;
	    }
	}
    }

    public void fillUpWalls() {
	for (int i=xmin; i<=xmax; i++) {
	    for (int j=ymin; j<=ymax; j++) {
		if (grid[i][j] == 0) grid[i][j] = 4;
	    }
	}
    }

    public void buildGridFromTree() {
	ArrayList<String> paths = tree.allPaths();
	//IO.print(paths.toString());
	for (String path : paths) {
	    buildGridDo(start,path);
	}
    }

    private void buildGridDo(Position p, String line) {
	int i=0;
	// line is a path withouth branches 
	while (i<line.length() ) {
	    char c = line.charAt(i);
	    switch(c) {
	    case 'N' : {
		build(3,p.north()); // door
		build(4,p.north().east()); // wall
		build(4,p.north().west()); // wall
		build(1,p.north().north()); // room
		p = p.north().north();
		break;
	    }
	    case 'E' : {
		build(2,p.east());
		build(4, p.east().north());
		build(4, p.east().south());
		build(1,p.east().east());
		p = p.east().east();
		break;
	    }
	    case 'W' : {
		build(2,p.west());
		build(4, p.west().north());
		build(4, p.west().south());		    
		build(1,p.west().west());
		p = p.west().west();
		break;
	    }
	    case 'S' : {
		build(3,p.south());
		build(4, p.south().east());
		build(4, p.south().west());		    
		build(1,p.south().south());
		p = p.south().south();
		break;
	    }
	    default : break;
	    }
	    i++;
	}
    }

    public void build(int element, Position p) {
	if ( (p.x < 0) || (p.y < 0) || (p.x > 2*offset-1) || (p.y > 2*offset-1) ) {
	    IO.print("Outside of grid: " + p.toString());
	} else {
	    if (grid[p.x][p.y] == 0) {
		grid[p.x][p.y] = element;
	    }
	}
	if (p.x < xmin) xmin = p.x;
	if (p.y < ymin) ymin = p.y;
	if (p.x > xmax) xmax = p.x;
	if (p.y > ymax) ymax = p.y;
    } 
    
    public void displayGrid() {
	for (int j=ymin; j<=ymax; j++) {
	    for (int i=xmin; i<=xmax; i++) {
		if (i==start.x && j == start.y) {
		    System.out.print("X");
		} else {
		    System.out.print(draw[grid[i][j]]);
		}
	    }
	    IO.print("");
	}
    }

    public void computeDistancesFromStart() {
        distances = new FloodFill().floodFill(grid,start.x,start.y);
    }

    public void minMaxPath() {
	for (int i=xmin; i<=xmax; i++) {
	    for (int j=ymin; j<=ymax; j++) {
		if (distances[i][j] > part1) {
		    part1 = distances[i][j];
		}
	    }
	}
    }

    public void part2() {
	for (int i=xmin; i<=xmax; i++) {
	    for (int j=ymin; j<=ymax; j++) {
		if (distances[i][j] >= 1000) {
		    part2++;
		}
	    }
	}
    }
    
    public static void main(String[] args) {
	Rooms r = new Rooms(args[0]);
	r.computeDistancesFromStart();
	r.minMaxPath();
	r.part2();
	IO.print("Part 1: " + r.part1);
	IO.print("Part 2: " + r.part2);
    }
}
