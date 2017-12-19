import util.*;
import java.util.*;

public class Tubes {

    ArrayList<String> lines;
    int nRows, nCols;
    char[][] maze;
    int currRow, currCol;
    String result;
    char currDir;
    int nSteps;
    
    public Tubes(String fileName) {
	lines = IO.readFile(fileName);
	nRows = lines.size();
	nCols = lines.get(0).length();

	maze = new char[nRows][nCols];
	
	for(int i=0; i<nRows; i++) {
	    String line = lines.get(i);
	    for(int j=0; j<nCols; j++) {
		maze[i][j] = line.charAt(j);
	    }
	}
	currRow = 0;
	currCol = 0;
	while (maze[currRow][currCol] == ' ') currCol++;
	currDir = 'd'; // d = down, u = up, l = left, r = right
	result = "";
	nSteps = 1;
    }


    private int nextX() {
	int x=currRow;
	if (currDir == 'd') x+=1;
	if (currDir == 'u') x-=1;
	return x;
    }

    private int nextY() {
	int y=currCol;
	if (currDir == 'r') y+=1;
	if (currDir == 'l') y-=1;
	return y;
    }

    private boolean nextAvailable() {
	boolean next = false;
	if ( !(currDir == 'd') && (currRow-1>=0) && (maze[currRow-1][currCol] != ' ') ) next = true;
	if ( !(currDir == 'u') && (currRow+1<nRows) && (maze[currRow+1][currCol] != ' ') ) next = true;
	if ( !(currDir == 'r') && (currCol-1>=0) && (maze[currRow][currCol-1] != ' ') ) next = true;
	if ( !(currDir == 'l') && (currCol+1<nCols) && (maze[currRow][currCol+1] != ' ') ) next = true;
	return next;
    }
    
    private boolean nextStep() {
	boolean next = true;
	currRow = nextX();
	currCol = nextY();
	nSteps++;
	
	if (!nextAvailable()) {
	    //we've reached the end!
	    next = false;
	} else {

	    char c = maze[currRow][currCol];
	    if (c == '+') {
		boolean dirChange = false;
		//check neighbors for new direction
		char c1 = ' ';
		//check top neighbor
		if ( !(currDir == 'd') && (currRow-1>=0) && (maze[currRow-1][currCol] != ' ')) {
		    c1 = maze[currRow-1][currCol];
		    currDir = 'u';
		    dirChange = true;
		}
		//check bottom neighbor
		if ( !(currDir == 'u') && (currRow+1<nRows) && !dirChange &&  (maze[currRow+1][currCol] != ' ')) {
		    c1 = maze[currRow+1][currCol];
		    currDir = 'd';
		    dirChange = true;
		}
		//check left neighbor
		if ( !(currDir == 'r') && (currCol-1>=0) && !dirChange &&  (maze[currRow][currCol-1] != ' ') ) {
		    c1 = maze[currRow][currCol-1];
		    currDir = 'l';
		    dirChange = true;
		}
		//check right neighbor
		if ( !(currDir == 'l') && (currCol+1<nCols) && !dirChange && (maze[currRow][currCol+1] != ' ')) {
		    c1 = maze[currRow][currCol+1];
		    currDir = 'r';
		}

		
	    } else if (c >= 'A' && c<='Z') {
		result += c;
	    } //else | or - and then just continue

	}

	return next;
    }

    public void process() {
	while (nextStep()) {
	    //nSteps++;
	}
	IO.print("Part 1: " + result);
	IO.print("Part 2: " + nSteps);

    }

    public static void main(String[] args) {
	Tubes t = new Tubes(args[0]);
	t.process();
    }
}
