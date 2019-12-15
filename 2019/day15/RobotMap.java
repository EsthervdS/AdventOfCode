import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
import java.lang.*;

public class RobotMap {

    public static int NORTH = 1;
    public static int SOUTH = 2;
    public static int WEST = 3;
    public static int EAST = 4;
    public static int WHITE = 0;
    public static int BLACK = 1;
    public static int GRAY = 2;
    
    public ArrayList<String> lines;
    public ArrayList<Long> program;
    public Intcode robot;
    public HashMap<Integer,Integer> grid;
    public HashMap<Integer,Location> administration;
    public PriorityQueue<Location> toProcess;
    public boolean destFound;
    public Location root;
    public int curX,curY,minX,minY,maxX,maxY;
    public int part1,maxDist;
    
    public RobotMap(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	program = new ArrayList<Long>();
	String[] instructions = lines.get(0).split(",");
	for (String instr : instructions) {
	    program.add(Long.parseLong(instr));
	}
	robot = new Intcode(program,0);
	robot.runUntilInput();
	//input 0 is not correct!
	
	init(0,0);
	try {
            Thread.sleep(2000);
	} catch (Exception e) {
            System.out.println(e);
	}
	buildMap();
	IO.print("Part 1: " + part1);
	try {
            Thread.sleep(2000);
	} catch (Exception e) {
            System.out.println(e);
	}
	init(curX,curY);
	buildMap();
	IO.print("Part 2: " + maxDist);
	
    }

    public void init(int x, int y) {
	initCanvas();
	minX = minY = Integer.MAX_VALUE;
	maxX = maxY = -1*Integer.MAX_VALUE;
	maxDist = -1*Integer.MAX_VALUE;

	grid = new HashMap<Integer,Integer>();
	administration = new HashMap<Integer,Location>();
	destFound = false;

	//queue of locations to visit
	toProcess = new PriorityQueue<Location>();
	//create root and add to queue
	curX = x;
	curY = y;
	root = new Location(x,y,0,null);
	root.color = BLACK;
	root.dist = 0;
	root.element = Location.OPEN;
	grid.put(index(x,y),Location.OPEN);
        administration.put(index(x,y),root);
	toProcess.add(root);
    }

    public void initCanvas() {
	StdDraw.enableDoubleBuffering();
	StdDraw.setPenRadius(0.001);
	StdDraw.setXscale(-40,40);
	StdDraw.setYscale(-40,40);;
	StdDraw.setPenColor(StdDraw.BLACK);
    }

    public void buildMap() {
	while (toProcess.size() > 0) {
	    Location cur = toProcess.remove();
	    if (cur.dist > maxDist) maxDist = cur.dist;
	    int poll = moveRobot(cur.x,cur.y);
	    if (poll == 2) {
	        part1 = cur.dist;
	    }
	    
	    if (!administration.containsKey(index(cur.x-1,cur.y))) {
		Location left = new Location(cur.x-1,cur.y,-1,null);
		processNeighbour(left,cur);
	    }
	    if (!administration.containsKey(index(cur.x+1,cur.y))) {		
		Location right = new Location(cur.x+1,cur.y,-1,null);
		processNeighbour(right,cur);
	    }
	    if (!administration.containsKey(index(cur.x,cur.y-1))) {		
		Location top = new Location(cur.x,cur.y-1,-1,null);
		processNeighbour(top,cur);
	    }
	    if (!administration.containsKey(index(cur.x,cur.y+1))) {		
		Location bottom = new Location(cur.x,cur.y+1,-1,null);
		processNeighbour(bottom,cur);
	    }
	    
	    cur.color = BLACK;
	    drawGrid();
	}

    }
    public void processNeighbour(Location loc, Location cur) {
	if (loc.x < minX) minX = loc.x;
	if (loc.x > maxX) maxX = loc.x;
	if (loc.y < minY) minY = loc.y;
	if (loc.y > maxY) maxY = loc.y;
	
	if (notIn(toProcess,loc)) {
	    //getDir(Location cur, Location next) {
	    int canMove = moveRobotOneStep(getDir(cur,loc));
	    if (canMove >= 1) {
		moveRobotOneStep(getDir(loc,cur));
		loc.parent = cur;
		loc.dist = cur.dist+1;
		toProcess.add(loc);
		grid.put(index(loc.x,loc.y),3);
		if (canMove == 2) {
		    part1 = loc.dist;
		}
	    } else {
		grid.put(index(loc.x,loc.y),Location.WALL);
	    }
	    administration.put(index(loc.x,loc.y),loc);
	}
    }
    
    
    public String printDir(int d) {
	switch(d) {
	case 1 : return "NORTH"; 
	case 2 : return "SOUTH"; 
	case 3 : return "WEST"; 
	case 4 : return "EAST"; 
	default : return "";
	}
    }

    public int moveRobotOneStep(int dir) {
	//curX and curY are updated elsewhere
	robot.input = dir;
	robot.runUntilInput();
	return (int) robot.output;
    }
    
    public int moveRobot(int newX, int newY) {
	//move robot from oldX to oldY to root
	//keep track of curPos in case you run into a wall
	Location cur = administration.get(index(curX,curY));
	Location target = administration.get(index(newX,newY));
	Location next = cur.parent;
	Location prev = cur;
	Stack<Location> path = new Stack<Location>();

	if (!neighbours(cur,target)) {
	//precondition: robot is waiting for input
	    while (!(next == null)) {
		int result = moveRobotOneStep(getDir(cur,next));
		//output is always 0 because we've been here before
		
		cur = next;
		next = cur.parent;
		curX = cur.x;
		curY = cur.y;
	    }
	    //compute path to new pos
	    Location temp = target;
	    while (temp != root) {
		path.push(temp);
		temp = temp.parent;
	    }
	} else {
	    path.push(target);
	}
	prev = cur;
	//move robot from root to newX and newY
	while (path.size() > 0) {
	    next = path.pop();
	    moveRobotOneStep(getDir(cur,next));
	    prev = cur;
	    cur = next;
	    curX = cur.x;
	    curY = cur.y;
	}
	if (!(cur.x == root.x && cur.y == root.y)) {
	    cur.element = (int) robot.output;
	    grid.put(index(curX,curY),cur.element);
	    if (robot.output == 0) {
		//wall - backtrack 1 step
		curX = prev.x;
		curY = prev.y;
	    }
	}
	if ((int) robot.output == 2) {
	    part1 = target.dist;
	}
	return (int) robot.output;
    }

    public boolean neighbours(Location l1, Location l2) {
	boolean res = false;
	if (Math.abs(l1.x-l2.x) == 1 && l1.y == l2.y) res = true;
	if (Math.abs(l1.y-l2.y) == 1 && l1.x == l2.x) res = true;
	return res;
    }

    public int getDir(Location cur, Location next) {
	if (next.x < cur.x && next.y == cur.y) {
	    //move west
	    return WEST;
	} else if (next.x > cur.x && next.y == cur.y) {
	    //move east
	    return EAST;
	} else if (next.y < cur.y && next.x == cur.x) {
	    //move south
	    return SOUTH;
	} else {
	    //move north
	    return NORTH;
	}
    }
    
    public boolean notIn(PriorityQueue<Location> q, Location l) {
	boolean res = true;
	for (Location loc : q) {
	    if (loc.isSame(l)) {
		res = false;
		break;
	    }
	}
	return res;
    }
    

    public int index(int i, int j) {
	return 10000*i + j;
    }
    
    public void printGrid() {

	for(int j=minY; j<=maxY; j++) {
	    for(int i=minX; i<=maxX; i++) {
		if (i==0 && j==0) {
		    System.out.print("@");
		} else {
		    Integer element = grid.get(index(i,j));
		    //IO.print(loc.toString());
		    if (! (element == null) ) {
			if (element == Location.WALL) {
			    System.out.print("#");		    
			} else if (element == Location.OPEN) {
			    System.out.print(".");
			} else if (element == Location.TARGET) {
			    System.out.print("X");
			} else if (element == 3) {
			    System.out.print("o");
			}
		    } else {
			System.out.print("_");
		    }
		}
			   
	    }
	    IO.print("");
	}
	IO.print("");
    }
    
    public void drawGrid() {
	StdDraw.clear();
	for(int j=minY; j<=maxY; j++) {
	    for(int i=minX; i<=maxX; i++) {
		if (i==root.x && j==root.y) {
		    StdDraw.setPenColor(StdDraw.BOOK_RED);
		    StdDraw.filledCircle(i,j,0.3);
		    StdDraw.setPenColor(StdDraw.BLACK);		    
		} else {
		    Integer element = grid.get(index(i,j));
		    //IO.print(loc.toString());
		    if (! (element == null) ) {
			if (element == Location.WALL) {
			    
			    StdDraw.filledSquare(i,j,0.5);
			} else if (element == Location.OPEN) {
			    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
			    StdDraw.filledSquare(i,j,0.5);			    
			    StdDraw.setPenColor(StdDraw.BLACK);
			} else if (element == Location.TARGET) {
			    StdDraw.setPenColor(StdDraw.YELLOW);
			    StdDraw.filledCircle(i,j,0.5);			    
			    StdDraw.setPenColor(StdDraw.BLACK);
			} else if (element == 3) {
			    StdDraw.setPenColor(StdDraw.GREEN);
			    StdDraw.filledSquare(i,j,0.5);			    
			    StdDraw.setPenColor(StdDraw.BLACK);
			}
		    }
		}
			   
	    }
	}
	StdDraw.show();
    }

    public static void main(String[] args) {
	RobotMap r = new RobotMap(args[0]);
     }
}
