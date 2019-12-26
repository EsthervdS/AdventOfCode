import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
import java.math.*;

public class Bugs {

    public ArrayList<String> lines;
    public int[][] grid;
    public boolean found;
    public ArrayList<Integer> biod;
    public HashMap<Integer,int[][]> levels;
    public int minLevel,maxLevel;
    public int min, iters;
    
    public Bugs(String fileName) {

	init(fileName);
	part1();
	part2();
    }

    public void part2() {
	min = 1;
	for (int i=0; i<iters; i++) {
	    /*
	    IO.print("*********************************************************");
	    IO.print("Minute " + (i+1));
	    IO.print("*********************************************************");
	    */
	    levels = evolve2();
	    min++;
	}
	IO.print("Part 2: " + bugs());
    }
    
    public void part1() {
	while (!found) {
	    grid = evolve();
	    int b = biodiversity();
	    if (!biod.contains(b)) {
		biod.add(b);
	    } else {
		IO.print("Part 1: " + b);
		found = true;
	    }
	}
    }

    public int bugs() {
	int res = 0;
	for (int i=minLevel; i<=maxLevel; i++) {
	    int[][] l = levels.get(i);
	    for (int j=0; j<5; j++) {
		for (int k=0; k<5; k++) {
		    res += l[j][k];
		}
	    }
	}
	return res;
    }

    public void init(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	if (fileName.equals("test.txt")) {
	    iters = 10;
	} else {
	    iters = 200;
	}
	grid = new int[5][5];
	levels = new HashMap<Integer,int[][]>();
	levels.put(0,grid);
	for (int i=0; i<5; i++) {
	    for (int j=0; j<5; j++) {
		grid[i][j] = (lines.get(j).charAt(i) == '#') ? 1 : 0;
	    }
	}
	minLevel = maxLevel = 0;
	biod = new ArrayList<Integer>();
	biod.add(biodiversity());
	found = false;
    }
    
    public int[][] evolve() {
	int[][] res = new int[5][5];
	for (int i=0; i<5; i++) {
	    for (int j=0; j<5; j++) {
		int c = countNeighbors(i,j);
		if (c!=1 && grid[i][j]==1) {
		    res[i][j] = 0;
		} else if ((c==1 || c==2) && grid[i][j] == 0) {
		    res[i][j] = 1;
		} else {
		    res[i][j] = grid[i][j];
		}
	    }
	}
	return res;
    }
    
    public HashMap<Integer,int[][]> evolve2() {
        HashMap<Integer,int[][]> res = new HashMap<Integer,int[][]>();
	
	minLevel--;
	int[][] minl = new int[5][5];
	for (int i=0; i<5; i++) {
	    for (int j=0; j<5; j++) {
		minl[i][j] = 0;
	    }
	}
	levels.put(minLevel,minl);

	maxLevel++;
	int[][] maxl = new int[5][5];
	for (int i=0; i<5; i++) {
	    for (int j=0; j<5; j++) {
		maxl[i][j] = 0;
	    }
	}
	levels.put(maxLevel,maxl);

	for (int l=minLevel; l<=maxLevel; l++) {
	    int[][] lev = levels.get(l);
	    int[][] temp = new int[5][5];
	    for (int j=0; j<5; j++) {
		for (int i=0; i<5; i++) {

		    if (i==2 && j==2) {
			temp[i][j] = 0;
		    } else {
			int c = countNeighbors2(l,i,j);			
			if (c!=1 && lev[i][j]==1) {
			    temp[i][j] = 0;
			} else if ((c==1 || c==2) && lev[i][j] == 0) {
			    temp[i][j] = 1;
			} else {
			    temp[i][j] = lev[i][j];
			}
		    }
		}
	    }
	    temp[2][2] = 0;
	    res.put(l,temp);
	    /*
	    IO.print("---------------------------------------------------------");
	    IO.print(" Depth " + l + ":");
	    printGrid(temp);
	    IO.print("---------------------------------------------------------");
	    */
	}
	return res;
	
    }

    public boolean newMinLevelNeeded() {
	int[][] g = levels.get(minLevel);
	boolean res = false;
	for (int i=0; i<4; i++) {
	    for (int j=0; j<4; j++) {
		if (isOnOuterEdge(i,j) && g[i][j]==1) {
		    res = true;
		    break;
		}
	    }
	}
	return res;
    }

    public boolean newMaxLevelNeeded() {
	int[][] g = levels.get(maxLevel);
	boolean res = false;
	for (int i=0; i<4; i++) {
	    for (int j=0; j<4; j++) {
		if (isOnInnerEdge(i,j) && g[i][j]==1) {
		    res = true;
		    break;
		}
	    }
	}
	return res;
    }

    public int biodiversity() {
	int res = 0;
	int k=0;
	while (k<25) {
	    if (grid[k%5][k/5]==1) {
		res += Math.pow(2,k);
	    }
	    k++;
	}
	return res;
    }
	
    public int countNeighbors(int i, int j) {
	int res = 0;
	if (i>0) {
	    res += grid[i-1][j];
	}
	if (i<4) {
	    res += grid[i+1][j];
	}
	if (j>0) {
	    res += grid[i][j-1];
	}
	if (j<4) {
	    res += grid[i][j+1];
	}
	return res;
    }


    public int add(int[][] g, int i, int j) {
	if (i==2 && j==2) {
	    return 0;
	} else {
	    return g[i][j];
	}
    }
    
    
    public int countNeighbors2(int l, int i, int j) {
	int res = 0;
	int nbs = 0;
	int[][] lev = levels.get(l);

	if (isOnInnerEdge(i,j) && l < maxLevel) {
	    int[][] innerLev = levels.get(l+1);
	    //if above innerLev, check top row
	    if (i==2 && j==1) {
		for (int ii=0;ii<5; ii++) {
		    res += add(innerLev,ii,0);
		    nbs++;
		}
	    }
	    //if below innerLev, check bottom row
	    if (i==2 && j==3) {
		for (int ii=0;ii<5; ii++) {
		    res += add(innerLev,ii,4);
		    nbs++;
		}
	    }
	    //if to the right of innerLev, check left row
	    if (i==1 && j==2) {
		for (int jj=0;jj<5; jj++) {
		    res += add(innerLev,0,jj);
		    nbs++;
		}
	    }
	    //if to the left of innerLev, check right row
	    if (i==3 && j==2) {
		for (int jj=0;jj<5; jj++) {
		    res += add(innerLev,4,jj);
		    nbs++;
		}
	    }
	}
	if (isOnOuterEdge(i,j) && l > minLevel) {
	    int[][] outerLev = levels.get(l-1);
	    //below outerLev
	    if (j==0) {
		res += add(outerLev,2,1);
		nbs++;
	    }
	    //above outerLev
	    if (j==4) {
		res += add(outerLev,2,3);
		nbs++;
	    }
	    //to the right of outerLev
	    if (i==0) {
		res += add(outerLev,1,2);
		nbs++;
	    }
	    //to the left of outerLev
	    if (i==4) {
		res += add(outerLev,3,2);
		nbs++;
	    }
	}
	if (i>0) {
	    res += add(lev,i-1,j);
	    nbs++;
	}
	if (i<4) {
	    res += add(lev,i+1,j);
	    nbs++;
	}
	if (j>0) {
	    res += add(lev,i,j-1);
	    nbs++;
	}
	if (j<4) {
	    res += add(lev,i,j+1);
	    nbs++;
	}
	//if (l==maxLevel) IO.print((5*j+i+1) + ": " + nbs + " neighbors");
	return res;
    }

    public void printGrid(int[][] g) {
	for (int j=0; j<5; j++) {
	    for (int i=0; i<5; i++) {
		if (g[i][j] == 1) {
		    System.out.print('#');
		} else if (i==2 && j==2) {
		    System.out.print('?');
		} else {
		    System.out.print('.');
		}
	    }
	    IO.print("");
	}
    }

    public boolean isOnInnerEdge(int i, int j) {
	if ((i==2 && j==1) || (i==1 && j==2) || (i==3 && j==2) || (i==2 && j==3)) {
	    return true;
	} else {
	    return false;
	}
    }
    
    public boolean isOnOuterEdge(int i, int j) {
	if ((i==0) || (i==4) || (j==0) || (j==4)) {
	    return true;
	} else {
	    return false;
	}
    }

    public static void main(String[] args) {
	Bugs b = new Bugs(args[0]);
     }
}
