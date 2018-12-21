import java.util.*;
import util.*;

public class FloodFill {

    int rows, cols;
    int[][] distances;
    int[][] map;
    int sx,sy;

    public int[][] floodFill(final int[][] m, final int x, final int y) {
	rows = m[0].length;
	cols = m.length;
        map = m;
	sx = x;
	sy = y;
	boolean[][] hits = new boolean[cols][rows];
        distances = new int[cols][rows];
	for (int i=0; i<cols; i++) {
	    for (int j=0; j<rows; j++) {
		distances[i][j] = -1;
	    }
	}

	//showMap();
	Queue<Position> queue = new LinkedList<Position>();
	queue.add(new Position(x, y, 0));
	
	while (!queue.isEmpty()) {
	    Position p = queue.remove();
	    
	    if(floodFillMapDo(map,hits,p)) {
		//IO.print("floodFill for p = " + p.toString() + " north: " + m[p.x][p.y-1] + " boolean: " + (m[x][p.y-1]==3));
		//passing north through a door -
		if ( (p.y-1 >= 0) && (m[p.x][p.y-1]==3) ) {
		    //IO.print("NORTH Add x,y-2");
		    queue.add(new Position(p.x,p.y - 2,p.dist+1));
		}
		//passing south through a door -
		if ( (p.y+1 < rows) && (m[p.x][p.y+1]==3) ) {		
		    //IO.print("SOUTH Add x,y+2");
		    queue.add(new Position(p.x,p.y + 2,p.dist+1));
		}
		//passing west through a door |
		if ( (p.x-1 >= 0) && (m[p.x-1][p.y]==2) ) {
		    //IO.print("WEST Add x-2,y");		    
		    queue.add(new Position(p.x - 2,p.y,p.dist+1));
		}
		//passing east through a door |
		if ( (p.x+1 < cols) && (m[p.x+1][p.y]==2) ) {
		    //IO.print("EAST Add x+2,y");
		    queue.add(new Position(p.x + 2,p.y,p.dist+1));
		}
	    }
	}
	//showDistances();
	return distances;
    }


    private boolean floodFillMapDo(int[][] map, boolean[][] hits, Position p) {
	int x = p.x;
	int y = p.y;
	int d = p.dist;
	
	if (y < 0) return false;
	if (x < 0) return false;
	if (y > rows-1) return false;
	if (x > cols-1) return false;
	
	if (hits[x][y]) return false;

	if (! ((x==sx) && (y==sy)) ) {
	    if (map[x][y] != 1) 
		return false;
	}
	
	// valid, paint it
	
	distances[x][y] = d;
	hits[x][y] = true;
	return true;
    }

    public void showDistances() {
	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		if (distances[i][j] >= 0) System.out.print(" ");
		if (distances[i][j] < 10) System.out.print(" ");
		System.out.print(distances[i][j] + ";");
	    }
	    System.out.println("");
	}
	System.out.println("");
    }

    public void showMap() {
	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		System.out.print(map[i][j] + " ");
	    }
	    System.out.println("");
	}
	System.out.println("");
    }

    public static void main(String[] args) {
	int[][] m = { {0,1,0,0}, {1,0,0,0}, {1,1,0,0}, {0,1,0,0}, {0,0,0,0} };
        int[][] d = new FloodFill().floodFill(m, 4, 3);

    }
    
}
