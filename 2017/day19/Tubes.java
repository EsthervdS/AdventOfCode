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
	IO.print(nRows + " x " + nCols);

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
	
	//IO.print("Start at (" + currRow + ", " + currCol + ")");
	nSteps = 0;
    }


    private int nextX() {
	int x=currRow;
	switch(currDir) {
	case 'd': {
	    x += 1;
	    break;
	}
	case 'u': {
	    x -= 1;
	    break;
	}	    
	default: {
	    //left or right: x doesn't change
	    break;
	}
	}
	return x;
    }

    private int nextY() {
	int y=currCol;
	switch(currDir) {
	case 'r': {
	    y += 1;
	    break;
	}
	case 'l': {
	    y -= 1;
	    break;
	}	    
	default: {
	    //down or up: y doesn't change
	    break;
	}
	}
	return y;
    }

    private boolean nextStep() {
	boolean next = true;
	//IO.print("curr = (" + currRow + ", " + currCol + ") with dir = " + currDir + " and nSteps = " + nSteps);
	
	if ((nextX() < 0) || (nextX() >= nRows) || (nextY() < 0) || (nextY() >= nCols)) {
	    //we've reached the end!
	    next = false;
	} else {

	    currRow = nextX();
	    currCol = nextY();
	    //nSteps++;
	    char c = maze[currRow][currCol];

	    if (c == '+') {
		boolean dirChange = false;
		//check neighbors for new direction
		char c1 = ' ';
		//check top neighbor
		if ( !(currDir == 'd') && (currRow-1>=0) ) {
		    if (maze[currRow-1][currCol] != ' ') {
			c1 = maze[currRow-1][currCol];
			currDir = 'u';
			dirChange = true;
		    }
		}
		//check bottom neighbor
		if ( !(currDir == 'u') && (currRow+1<nRows) && !dirChange ) {
		    if (maze[currRow+1][currCol] != ' ') {
			c1 = maze[currRow+1][currCol];
			currDir = 'd';
			dirChange = true;
		    }
		}
		//check left neighbor
		if ( !(currDir == 'r') && (currCol-1>=0) && !dirChange ) {
		    if (maze[currRow][currCol-1] != ' ') {
			c1 = maze[currRow][currCol-1];
			currDir = 'l';
			dirChange = true;
		    }
		}
		//check right neighbor
		if ( !(currDir == 'l') && (currCol+1<nCols) && !dirChange) {
		    if (maze[currRow][currCol+1] != ' ') {
			c1 = maze[currRow][currCol+1];
			currDir = 'r';
		    }
		}
		//currRow = nextX();
		//currCol = nextY();
		//nSteps++;
		if (c1 >= 'A' && c1 <='Z') {
		    result += c1;
		}
		
	    } else if (c >= 'A' && c<='Z') {
		result += c;
	    } //else | or - and then just continue

	}
	return next;
    }

    public void process() {
	while (nextStep()) {
	    nSteps++;
	}
	IO.print("Part 1: " + result);
	IO.print("Part 2: " + nSteps);

    }

    public static void main(String[] args) {
	Tubes t = new Tubes(args[0]);
	t.process();
    }
}
