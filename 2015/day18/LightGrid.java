import util.*;
import java.util.*;

public class LightGrid {

    ArrayList<String> lines;
    boolean[][] lights;
    int[][] brightness;
    int n = 100;
    int nRuns = 100;
    
    public LightGrid(String fileName) {
	lines = IO.readFile(fileName);
	lights = new boolean[n][n];
	brightness = new int[n][n];
	for (int i=0; i<n; i++) {
	    for (int j=0; j<n; j++) {
		boolean on = false;
		if (lines.get(i).charAt(j) == '#') on = true;
		if (on) {
		    brightness[i][j] = 1;
		} else {
		    brightness[i][j] = 0;
		}
	    }
	}
    }

    public int totalBrightness() {
	int b = 0;
	for (int i=0; i<n; i++) {
	    for (int j=0; j<n; j++) {
		b += brightness[i][j];
	    }
	}
	return b;
    }

    public int isOn(int i, int j) {
	if ((i<0) || (i>=n) || (j<0) || (j>=n)) {
	    return 0;
	} else {
	    return brightness[i][j];
	}
    }

    public int neighborsOn(int i, int j) {
	int sum = 0;
	boolean b = (i==0) && (j==1);
	sum += isOn(i-1,j-1);
	sum += isOn(i-1,j);
	sum += isOn(i-1,j+1);
	sum += isOn(i,j-1);
	sum += isOn(i,j+1);
	sum += isOn(i+1,j-1);
	sum += isOn(i+1,j);
	sum += isOn(i+1,j+1);
	return sum;
    }
    
    public void oneRun(boolean part2) {
	int[][] newGrid = new int[n][n];
	for (int i=0; i<n; i++) {
	    for (int j=0; j<n; j++) {
		//check all neighbors
		int sum = neighborsOn(i,j);
		if (brightness[i][j] == 1) {
		    //on: stay on when 2 or 3 neighbors are on
		    if ((sum == 2) || (sum == 3)) {
			newGrid[i][j] = 1;
		    } else {
			newGrid[i][j] = 0;
		    }
		} else {
		    //off: turn on when exactly 3 neighbors are on
		    if (sum == 3) {
			newGrid[i][j] = 1;
		    } else {
			newGrid[i][j] = 0;
		    }
		}
		if (part2) {
		    if ((i==0 && j==0) || (i==0 && j==n-1) || (i==n-1 && j==0) || (i==n-1 && j==n-1)) {
			newGrid[i][j] = 1;
		    }
		}
	    }
	}
	brightness = newGrid.clone();
    }

    public void setCornersOn() {
	brightness[0][0]     = 1;
	brightness[n-1][0]   = 1;
	brightness[0][n-1]   = 1;
	brightness[n-1][n-1] = 1;	
    }
    
    public void printGrid() {
	for (int i=0; i<n; i++) {
	    for (int j=0; j<n; j++) {
		if (brightness[i][j] == 1) {
		    System.out.print('#');
		} else {
		    System.out.print('.');
		}
	    }
	    System.out.println("");
	}
	System.out.println("");
    }
    
    public static void main(String[] args) {
	LightGrid lg = new LightGrid(args[0]);

	for (int i=0; i<lg.nRuns; i++) {
	    lg.oneRun(false);
	}
	IO.print("Part 1: " + lg.totalBrightness());

	lg = new LightGrid(args[0]);
	lg.setCornersOn();
	
	for (int i=0; i<lg.nRuns; i++) {
	    lg.oneRun(true);
	}
	IO.print("Part 2: " + lg.totalBrightness());
    }
}
