import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class ASCII {

    public ArrayList<String> lines;
    public Intcode robot;
    public ArrayList<Long> program;
    public int[][] grid;
    public int xMax,yMax;
    ArrayList<Coord> intersections;
    
    public ASCII(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	String[] ss = lines.get(0).split(",");
	program = new ArrayList<Long>();
	for (String s : ss) {
	    program.add(Long.parseLong(s));
	}

	//output 0?
	robot = new Intcode(program,0);
	int it = 0;
	int x,y;
	x = y = 0;
	xMax = 50;
	yMax = 43;

	grid = new int[xMax][yMax];
	
	while (it<(xMax*yMax)) {
	    robot.runUntilOutput();
	    int p = (int) robot.output;
	    grid[x][y] = p;
	    
	    x++;
	    if (p == 10) {
		x=0;
		y++;
	    }
	    it++;
	}
	IO.print("Part 1: " + findAlignmentParameterSum());

	program.set(0,2L);
	robot = new Intcode(program,0);
	robot.runUntilInput();
	//A=65 B=66 C=67
	//routine = A,B,C,B,C,B,C
        String mainMovementRoutine = "A,B,A,C,B,A,C,B,A,C\n";

	//L=76, R=82
	//49 = 1, 50 = 2, 52 = 4, 54 = 6, 56 = 8

        String functionA = "L,12,L,12,L,6,L,6\n";
	String functionB = "R,8,R,4,L,12\n";
	String functionC = "L,12,L,6,R,12,R,8\n";
	String video = "n\n";
	long oldOutput = robot.output;

	for (int i=0; i<mainMovementRoutine.length(); i++) {
	    robot.input = (long) mainMovementRoutine.charAt(i);
	    robot.runUntilInput();
	}
	for (int i=0; i<functionA.length(); i++) {
	    robot.input = (long) functionA.charAt(i);
	    robot.runUntilInput();
	}
	for (int i=0; i<functionB.length(); i++) {
	    robot.input = (long) functionB.charAt(i);
	    robot.runUntilInput();
	}
	for (int i=0; i<functionC.length(); i++) {
	    robot.input = (long) functionC.charAt(i);
	    robot.runUntilInput();
	}
	robot.input = (long) video.charAt(0);
	robot.runUntilInput();
	robot.input = (long) video.charAt(1);
	while (robot.output < 100) {
	    robot.runUntilOutput();
	}
        IO.print("Part 2: " + robot.output);    
    }

    public int findAlignmentParameterSum() {
	int res = 0;
	//assumption: intersections are not on the border rows or columns
	for (int i=1; i<xMax-1; i++) {
	    for (int j=1; j<yMax-1; j++) {
		if (grid[i][j] == 35) {
		    if (grid[i-1][j] == 35 && grid[i+1][j] == 35 && grid[i][j-1]==35 && grid[i][j+1] == 35) {
			res += (i * j);
		    }
		}
	    }
	}

	return res;
    }
    
    
    public void printChar(int c) {
	System.out.format("%1$-2c", (char) c);
    }

    public static void main(String[] args) {
	ASCII a = new ASCII(args[0]);

     }
}
