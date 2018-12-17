import java.util.*;
import util.*;

public class Grid {
    public static char[][] grid;
    public static int xmin;
    public static int xmax;
    public static int ymin; 
    public static int ymax;

    public static char[][] buildGrid(ArrayList<String> lines) {
	xmin = Integer.MAX_VALUE;
	xmax = -1;
	ymin = Integer.MAX_VALUE;
	ymax = -1;
	
	for (String line : lines) {
	    int i=0;
	    if (line.charAt(i) == 'x') {
		//horizontal, eg. x=495, y=2..7
		String s = line.substring(2,line.length());
		int x = Integer.parseInt(s.split(",")[0]);
		while (line.charAt(i) != 'y') {
		    i++;
		}
		i++; i++;
		int i2 = i;
		while (line.charAt(i2) != '.') {
		    i2++;
		}
		int y1 = Integer.parseInt(line.substring(i,i2));
		int y2 = Integer.parseInt(line.substring(i2+2,line.length()));
		
		if (x<xmin) xmin = x;
		if (x>xmax) xmax = x;
		if (y1<ymin) ymin = y1;
		if (y2>ymax) ymax = y2;

	    } else {
		//vertical, eg. y=7, x=495..501
		String s = line.substring(2,line.length());
		int y = Integer.parseInt(s.split(",")[0]);
		while (line.charAt(i) != 'x') {
		    i++;
		}
		i++; i++;
		int i2 = i;
		while (line.charAt(i2) != '.') {
		    i2++;
		}
		int x1 = Integer.parseInt(line.substring(i,i2));
		int x2 = Integer.parseInt(line.substring(i2+2,line.length()));
		
	        if (x1<xmin) xmin = x1;
		if (x2>xmax) xmax = x2;
		if (y<ymin) ymin = y;
		if (y>ymax) ymax = y;
		
	    }
	}
	//IO.print("x in ["+xmin+","+xmax+"] and y in ["+ymin+","+ymax+"]");
	
	xmax = xmax+1;
	ymax = ymax+1;
	grid = new char[xmax+1][ymax+1];
	for (int i=0; i<=xmax; i++) {
	    for (int j=0; j<=ymax; j++) {
		grid[i][j] = '.';
	    }
	}
	for (String line : lines) {
	    int i=0;
	    if (line.charAt(i) == 'x') {
		//horizontal, eg. x=495, y=2..7
		String s = line.substring(2,line.length());
		int x = Integer.parseInt(s.split(",")[0]);
		while (line.charAt(i) != 'y') {
		    i++;
		}
		i++; i++;
		int i2 = i;
		while (line.charAt(i2) != '.') {
		    i2++;
		}
		int y1 = Integer.parseInt(line.substring(i,i2));
		int y2 = Integer.parseInt(line.substring(i2+2,line.length()));
		
		for (int j=y1; j<=y2; j++) {
		    grid[x][j] = '#';
		}
	    } else {
		//vertical, eg. y=7, x=495..501
		String s = line.substring(2,line.length());
		int y = Integer.parseInt(s.split(",")[0]);
		while (line.charAt(i) != 'x') {
		    i++;
		}
		i++; i++;
		int i2 = i;
		while (line.charAt(i2) != '.') {
		    i2++;
		}
		int x1 = Integer.parseInt(line.substring(i,i2));
		int x2 = Integer.parseInt(line.substring(i2+2,line.length()));
		
		for (int i3=x1; i3<=x2; i3++) {
		    grid[i3][y] = '#';
		}
	    }
	}  

	grid[500][0] = '+';
	return grid;
    }

    public static void display() {
	for (int j=0; j<=ymax; j++) {
	    if (j<10) System.out.print(" ");
	    if (j<100) System.out.print(" ");
	    System.out.print(j+" ");
	    for (int i=xmin-1; i<=xmax; i++) {
		System.out.print(grid[i][j]+"");
	    }
	    System.out.println("");
	}
    }

    public static void process(ArrayDeque<Position> toDo) {
	ArrayList<Position> done = new ArrayList<Position>();
	
	//toDo contains sources of water pouring down
	while (!toDo.isEmpty()) {
	    Position p = toDo.getFirst();
	    done.add(p);
	    toDo.removeFirst();

	    int i=p.x;
	    int j=p.y;
	    if (grid[i][j] != '~') {
		while (j<=ymax && grid[i][j] != '#' && grid[i][j] != '~' && grid[i][j] != '|') {
		    grid[i][j]='|';
		    j++;
		}
		if (j<=ymax) {
		    if (grid[i][j] != '|') {
			j--;
			Position q = new Position(i,j);
			//display();
			boolean overflow = false;
			while (!overflow) {
			    boolean overflowLeft, overflowRight;
			    Position leftSource, rightSource;
			    leftSource = rightSource = new Position(-1,-1);
			    overflowLeft = overflowRight = false;
			    int maxLeft, maxRight, k;
			    k=maxLeft=maxRight=0;
		    
			    while (((i-k)>=0) && (grid[i-k][j] != '#') && ((grid[i-k][j+1] == '#') || (grid[i-k][j+1] == '~'))) { 
				grid[i-k][j] = '~';
				if ((i-k-1) < 0) {
				    break;
				} else {
				    if (grid[i-k-1][j+1] == '.') {
					overflowLeft = true;
					break;
				    } else {
					k++;
				    }
				}
			    }
			    maxLeft = i-k;
			    if (grid[maxLeft][j]=='#') maxLeft++;
			    k=1;
			    while (((i+k)<=xmax) && (grid[i+k][j] != '#') && ((grid[i+k][j+1] == '#') || (grid[i+k][j+1] == '~'))) { 
				grid[i+k][j] = '~';
				if ((i+k+1) > xmax) {
				    break;
				} else {
				    if (grid[i+k+1][j+1] == '.') {
					overflowRight = true;
					break;
				    } else k++;
				}
			    }
			    maxRight = i+k;
			    if (grid[maxRight][j]=='#') maxRight--;
			    if (overflowLeft) {
				grid[maxLeft-1][j] = '+';
				Position newSource = new Position(maxLeft-1,j);
				if (!done.contains(newSource)) toDo.addFirst(newSource);
				for (int c=maxLeft; c<=maxRight; c++) {
				    if (grid[c][j] == '+') {
					break;
				    } else {
					grid[c][j] = '|';
				    }
				}
			    }
			    if (overflowRight) {
				grid[maxRight+1][j] = '+';
				Position newSource = new Position(maxRight+1,j);
				if (!done.contains(newSource)) toDo.addFirst(newSource);
				for (int c=maxLeft; c<=maxRight; c++) {
				    if (grid[c][j] == '+') {
					break;
				    } else {
					grid[c][j] = '|';
				    }
				}
			    }
			    overflow = overflowLeft || overflowRight;
		    
			    // go up a level
			    j--;
			}
		    }
		}
	    }
	    //display();
	}
    }

    public static int reachable() {
	int res = 0;
	for (int i=0; i<=xmax; i++) {
	    for (int j=ymin; j<=ymax-1; j++) {
		if (grid[i][j]=='~' || grid[i][j] == '|') res++;
	    }
	}
	return res;
    }
    
    public static int remaining() {
	int res = 0;
	for (int i=0; i<=xmax; i++) {
	    for (int j=ymin; j<=ymax-1; j++) {
		if (grid[i][j]=='~') res++;
	    }
	}
	return res;
    }

}
