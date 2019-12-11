import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Robot {

    public ArrayList<String> lines;
    public ArrayList<Long> program;
    public Intcode robot;
    public int part1,x,y,dir;
    public HashMap<Integer,Integer> grid;
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    
    
    public Robot(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	String ss[] = lines.get(0).split(",");
	
	program = new ArrayList<Long>();	
	for (String s : ss) {
	    program.add(Long.parseLong(s,10));
	}

	init(0);
	run();
	IO.print("Part 1: " + part1);

	init(1);
	run();
	IO.print("Part 2:");
	showGrid();
    }

    public void init(int in) {
	x = y = 0;
	dir = UP;
	robot = new Intcode(program,in);
        grid = new HashMap<Integer,Integer>();	
    }

    public void run() {
	while (robot.state != Intcode.HALTED) {
	    robot.runUntilOutput();
	    setPaint(x,y,(int) robot.output);
	    robot.runUntilOutput();
	    changeDir((int) robot.output);
	    move();
	    robot.input = getPaint(x,y);
	}
    }

    public int getIndex(int i, int j) {
	return 1000000*i + j;
    }

    public String getDir() {
	String res = "";
	switch(dir) {
	case UP : res = "^"; break;
	case RIGHT : res = ">"; break;
	case DOWN : res = "v"; break;
	case LEFT : res = "<"; break;
	default : break;
	}
	return res;
    }
    
    public int getPaint(int i, int j) {
	if (grid.get(getIndex(i,j)) == null) {
	    return 0;
	} else {
	    return grid.get(getIndex(i,j));
	}
    }

    public void setPaint(int i, int j, int p) {
	if (grid.get(getIndex(i,j)) == null) {
	    part1++;
	}
	grid.put(getIndex(i,j),p);
    }

    public void changeDir(int rot) {
	//0 means it should turn left 90 degrees, and 1 means it should turn right 90 degrees
	//UP:0 LEFT: 3
	if (rot == 0) {
	    //CCW
	    dir = (4 + (dir - 1)) % 4;
	} else {
	    //rot = 1 => CW
	    dir = (dir + 1) % 4;
	}
    }

    public void move() {
	switch(dir) {
	case UP: y--; break;
	case RIGHT : x++; break;
	case DOWN : y++; break;
	case LEFT : x--; break;
	default : break;
	}
    }

    public void showGrid() {
	for (int j=-2; j<8; j++) {
	    for (int i=-2; i<43; i++) {
		if (i==x && j==y) {
		    System.out.print(getDir());
		} else {
		    int p = getPaint(i,j);
		    if (p == 0) {
			System.out.print(".");
		    } else {
			System.out.print("#");
		    }
		}
	    }
	    IO.print("");
	}
	IO.print("");
    }
    
    public static void main(String[] args) {
	     Robot t = new Robot(args[0]);

     }
}
