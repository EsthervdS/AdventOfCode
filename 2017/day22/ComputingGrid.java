import util.*;
import java.util.*;

public class ComputingGrid {

    ArrayList<String> lines;
    int answer,answer1;
    boolean[][] grid;
    HashMap<Pair<Integer,Integer>,Character> grid1;
    int x,y;
    int x1,y1;
    char dir,dir1;
    int rows,cols;
    int offset;
    boolean part1done;
    
    public ComputingGrid(String fileName) {
	lines = IO.readFile(fileName);
	answer = 0;
	answer1 = 0;
	int n = 10000;
	grid = new boolean[n][n];
	grid1 = new HashMap<Pair<Integer,Integer>,Character>();
	for (int i=0; i<n; i++) {
	    for (int j=0; j<n; j++) {
		grid[i][j] = false;
	    }
	}

	rows = lines.size();
	cols = lines.get(0).length();
	offset = (n / 2) - rows;
	
	for (int i=0; i<rows; i++) {
	    String line = lines.get(i);
	    for (int j=0; j<cols; j++) {
		char c = line.charAt(j);
		if (c=='#') {
		    Pair<Integer,Integer> p = new Pair<Integer,Integer>( (offset+i), (offset+j) );
		    grid[offset+i][offset+j] = true;
		    grid1.put(p,'#');
		}
	    }
	}
	part1done = false;
	x = offset+(rows/2);
	y = offset+(cols/2);
	x1 = offset+(rows/2);
	y1 = offset+(cols/2);
	dir = 'u';
	dir1 = 'u';
    }

    private void showPartGrid() {
	int slack = 5;
	for (int i=0; i<rows+2*slack; i++) {
	    for (int j=0; j<cols+2*slack; j++) {
		int px = offset-slack+i;
		int py = offset-slack+j;
		if (px==x && py==y) {
		    System.out.print("[");
		} else {
		    System.out.print(" ");
		}
		if (grid[px][py]) {
		    System.out.print("#");
		} else {
		    System.out.print(".");
		}
		if (px==x && py==y) {
		    System.out.print("]");
		} else {
		    System.out.print(" ");
		}		
	    }
	    System.out.println("");
	}
    }
    
    private void turnLeft() {
	if (dir == 'u') {
	    dir = 'l';
	} else if (dir == 'r') {
	    dir = 'u';
	} else if (dir == 'd') {
	    dir = 'r';
	} else {
	    //l
	    dir = 'd';
	}
    }

    private void turnRight() {
	if (dir == 'u') {
	    dir = 'r';
	} else if (dir == 'r') {
	    dir = 'd';
	} else if (dir == 'd') {
	    dir = 'l';
	} else {
	    //l
	    dir = 'u';
	}
    }

        private void turnLeft1() {
	if (dir1 == 'u') {
	    dir1 = 'l';
	} else if (dir1 == 'r') {
	    dir1 = 'u';
	} else if (dir1 == 'd') {
	    dir1 = 'r';
	} else {
	    //l
	    dir1 = 'd';
	}
    }

    private void turnRight1() {
	if (dir1 == 'u') {
	    dir1 = 'r';
	} else if (dir1 == 'r') {
	    dir1 = 'd';
	} else if (dir1 == 'd') {
	    dir1 = 'l';
	} else {
	    //l
	    dir1 = 'u';
	}
    }

    private void reverse1() {
	if (dir1 == 'u') {
	    dir1 = 'd';
	} else if (dir1 == 'r') {
	    dir1 = 'l';
	} else if (dir1 == 'd') {
	    dir1 = 'u';
	} else {
	    //l
	    dir1 = 'r';
	}
    }
    
    private void move() {
	if (dir == 'u') {
	    x--;
	} else if (dir == 'r') {
	    y++;
	} else if (dir == 'd') {
	    x++;
	} else {
	    //l
	    y--;
	}	
    }

    private void move1() {
	if (dir1 == 'u') {
	    x1--;
	} else if (dir1 == 'r') {
	    y1++;
	} else if (dir1 == 'd') {
	    x1++;
	} else {
	    //l
	    y1--;
	}	
    }

    public void burst() {
	/* part 1 */
	if (!part1done) {
	    if (grid[x][y]) {
		//infected
		turnRight();
		grid[x][y] = false;
		move();
	    } else {
		//clean
		turnLeft();
		grid[x][y] = true;
		answer++;
		move();
	    }
	}
	/* part 2 */
	Pair<Integer,Integer> coords = new Pair<Integer,Integer>(x1,y1);
	if (!grid1.containsKey(coords)) {
	    grid1.put(coords,'.');
	}
	char c = grid1.get(coords);
	if (c == '.') { 
	    // . => left & W
	    turnLeft1();
	    grid1.remove(coords);
	    grid1.put(coords,'W');
	    move1();
	} else if (c == 'W') {
	    // W => don't turn & #
	    grid1.remove(coords);
	    grid1.put(coords,'#');
	    move1();
	    answer1++;
	} else if (c == '#') {
	    // # => right & F
	    turnRight1();
	    grid1.remove(coords);
	    grid1.put(coords,'F');
	    move1();
	} else {
	    // F => reverse & .
	    reverse1();
	    grid1.remove(coords);
	    grid1.put(coords,'.');
	    move1();

	}
    }

    public void process() {
	for (int i=0; i<10000000; i++) {
	    burst();

	    if (i==9999) {
		IO.print("Part 1: " + answer);
		part1done = true;
	    }
	}

	IO.print("Part 2: " + answer1);
    }
    
    public static void main(String[] args) {
	ComputingGrid cg = new ComputingGrid(args[0]);
	cg.process();
    }
}
