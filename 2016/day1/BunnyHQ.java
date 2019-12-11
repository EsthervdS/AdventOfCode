import java.util.*;
import util.*;

public class BunnyHQ {

    public static final int N = 0;
    public static final int E = 1;
    public static final int S = 2;
    public static final int W = 3;
    
    public ArrayList<String> lines;
    public String[] dirs;
    int xpos,ypos,dir;
    public HashMap<Integer,Integer> grid;
    int xf,yf;
    boolean firstSet;
    
    public BunnyHQ(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	String[] dirs = lines.get(0).split(", ");
	xpos = ypos = 0;
	dir = N;
	grid = new HashMap<Integer,Integer>();
	firstSet = false;
	
	for (String d : dirs) {
	    char c = d.charAt(0);
	    int steps = Integer.parseInt(d.substring(1));
	    changeDir(c);
	    move(steps);
	}
	
	IO.print("Part 1: " + (Math.abs(xpos) + Math.abs(ypos)));
	IO.print("Part 2: " + (Math.abs(xf) + Math.abs(yf)));
    }

    public int getIndex(int i, int j) {
	return 1000000*i + j;
    }

    public String getDir() {
	String s = "";
	switch(dir) {
	case N: s = "N"; break;
	case E : s = "E"; break;
	case S : s = "S"; break;
	case W : s = "W"; break;
	default : break;
	}
	return s;
    }
    
    public void changeDir(char d) {
	if (d == 'L') {
	    //CCW
	    dir = (4 + (dir - 1)) % 4;
	} else {
	    //rot = 1 => CW
	    dir = (dir + 1) % 4;
	}
    }

    public void checkPos() {
	if (grid.get(getIndex(xpos,ypos)) == null) {
	    grid.put(getIndex(xpos,ypos),1);
	} else if (!firstSet) {
	    xf = xpos;
	    yf = ypos;
	    firstSet = true;
	}
    }

    public void move(int steps) {
	
	switch(dir) {
	case N: for (int i=0; i<steps; i++) {
		ypos--;
		checkPos();
	    }
	    break;
	case E : for (int i=0; i<steps; i++) {
		xpos++;
		checkPos();
	    }
	    break;
	case S : for (int i=0; i<steps; i++) {
		ypos++;
		checkPos();
	    }
	    break;
	case W : for (int i=0; i<steps; i++) {
		xpos--;
		checkPos();
	    }
	    break;
	default : break;
	}
    }
    

    public static void main(String[] args) {
	BunnyHQ b = new BunnyHQ(args[0]);
     }
}
