import java.io.*;
import java.util.*;
import util.*;

public class Doors {

    public final static int WALL = 0;
    public final static int OPEN = 1;
    public final static int KEY = 2;
    public final static int DOOR = 3;
    public final static int ENTRANCE = 4;
 
    public ArrayList<String> lines;
    public static HashMap<Character,Coord> keys;
    public static HashMap<Character,Coord> doors;
    public int[][] grid;
    public int cols,rows;
    public static Coord entrance;
    public HashMap<Integer,Location> administration;
    public PriorityQueue<Location> toProcess;
    public HashMap<Integer,Character> allLocs;
    public ArrayList<Pair> distances;
    public HashMap<String,Integer> cache;
    
    public Doors(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	init();
	printGrid();

	createLocationsMap();
	computeDistances();
        HashSet<Character> keysCollected = new HashSet<Character>();
	ArrayList<Character> path = new ArrayList<Character>();
	path.add('@');

	IO.print("Part 1: " + shortestDistance(keysCollected,path));
    }

    public int shortestDistance(HashSet<Character> keysCollected, ArrayList<Character> path) {
	//IO.print("CACHE: " + cache.toString());
	int minDist = Integer.MAX_VALUE;
	int res = minDist;
	
	if (keysCollected.size() == keys.size()) {
	    res = 0;
	} else {

	    //from which key are we searching
	    Character c = path.get(path.size()-1);

	    //check cache
	    String cacheKey = c+"=>";
	    for (Character k : keysCollected) cacheKey+=k;	    
	    if (cache.containsKey(cacheKey)) {
		res = cache.get(cacheKey);
		
	    } else {
		ArrayList<Pair> options = getReachableKeys(c,keysCollected,path);
		for (Pair p : options) {

		    HashSet<Character> newKeys = new HashSet<Character>();
		    newKeys.addAll(keysCollected);
		    newKeys.addAll(p.keysInBetween());
		    
		    ArrayList<Character> newPath = new ArrayList<Character>();
		    newPath.addAll(path);
		    addKeys(newPath,p);
		    //IO.print("Checking new path: " + newPath.toString());
		    int dist = p.distance + shortestDistance(newKeys,newPath);
		    if (dist < minDist) minDist = dist;

		}
		res = minDist;
		cache.put(cacheKey,minDist);
	    }

	}
	return res;
    }

    
    
    public void init() {
        cols = lines.get(0).length();
        rows = lines.size();
	grid = new int[cols][rows];
	keys = new HashMap<Character,Coord>();
	doors = new HashMap<Character,Coord>();
	
	for (int i=0; i<cols; i++) {
	    for (int j=0; j<rows; j++) {
		char c = lines.get(j).charAt(i);
		if (c == '#') {
		    grid[i][j] = WALL;
		} else if (c == '.') {
		    grid[i][j] = OPEN;
		} else if (c == '@') {
		    grid[i][j] = ENTRANCE;
		    entrance = new Coord(i,j);
		} else if (Character.isLetter(c) && Character.isLowerCase(c)) {
		    grid[i][j] = KEY;
		    keys.put(c,new Coord(i,j));
		} else if (Character.isLetter(c) && Character.isUpperCase(c)) {
		    grid[i][j] = DOOR;
		    doors.put(c,new Coord(i,j));
		}
	    }
	}

    }

    public void computeDistances() {
	cache = new HashMap<String,Integer>();
	distances = new ArrayList<Pair>();
	for (int i=0; i<allLocs.size(); i++) {
	    for (int j=i+1; j<allLocs.size(); j++) {
		//unique pairs
		char c1 = allLocs.get(i);
		char c2 = allLocs.get(j);
		Pair p = new Pair(c1,c2);
		shortestPath(p);
		distances.add(p);
	    }
	}
    }

    public Pair findPair(char c1, char c2) {
	Pair p = new Pair(c1,c2);
	for (Pair q : distances) {
	    if (q.isSame(p) || q.isReverse(p)) {
		return q;
	    }
	}
	return (new Pair(' ',' '));
    }

    public void addKeys(ArrayList<Character> path, Pair next) {
	for (Character key : next.keysInBetween()) {
	    if (!path.contains(key)) {
		path.add(key);
	    }
	}
    }
    
    public ArrayList<Pair> getReachableKeys(char c, HashSet<Character> keys, ArrayList<Character> path) {
	ArrayList<Pair> temp = new ArrayList<Pair>();
	for (Pair p : distances) {
	    if (p.first == c || p.second == c) {
		//copy pair
		Pair q = new Pair(p);
		if (q.second == c) {
		    char t = q.second;
		    q.second = q.first;
		    q.first = t;
		    q.reverseInBetween();
		}
		q.addKeys(keys);
		if (!path.contains(q.second) && isKey(q.second) && q.isFeasible()) {
		    temp.add(q);
		}
	    }
	}
	return temp;
    }
    
    public void shortestPath(Pair p) {
	char start = p.first;
	char target = p.second;
	Coord src,end;
	int maxDist = -1*Integer.MAX_VALUE;
	src = findLocation(start);
	end = findLocation(target);
	administration = new HashMap<Integer,Location>();	
	toProcess = new PriorityQueue<Location>();
	Location root = new Location(src.x,src.y,0,null);
	toProcess.add(root);
	administration.put(index(src.x,src.y),root);				
	while (toProcess.size() > 0) {
	    Location cur = toProcess.remove();
	    
	    if (cur.dist > maxDist) maxDist = cur.dist;
	    if (cur.x == end.x && cur.y == end.y) {
	        p.distance = maxDist;
		Location temp = cur;
		while (temp != null) {
		    if (isDoor(temp.x,temp.y)) {
			p.inBetween.add(0,getDoor(temp.x,temp.y));
		    } else if (isKey(temp.x,temp.y)) {
			p.inBetween.add(0,getKey(temp.x,temp.y));
		    }
		    temp = temp.parent;
		}
		break;
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
	}
    }

    public void createLocationsMap() {
	allLocs = new HashMap<Integer,Character>();
	allLocs.put(0,'@');
	int k = 1;
	for (Character door : doors.keySet()) {
	    allLocs.put(k,door);
	    k++;
	}
	for (Character key : keys.keySet()) {
	    allLocs.put(k,key);
	    k++;
	}
    }

    public void processNeighbour(Location loc, Location cur) {	
	if (notIn(toProcess,loc)) {
	    if ((loc.x<0 || loc.x>cols-1) || (loc.y<0 || loc.y>rows-1)) {
		//skip
	    } else {
		int canMove = 0;
		canMove = grid[loc.x][loc.y];
		if (canMove > 0) {
		    loc.parent = cur;
		    loc.dist = cur.dist+1;
		    toProcess.add(loc);
		}
		administration.put(index(loc.x,loc.y),loc);
	    }
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

    public static Coord findLocation(char c) {
	Coord res = new Coord(-1,-1);
	if (c == '@') {
	    res = entrance;
	} else if (Character.isLowerCase(c)) {
	    res = keys.get(c);
	} else if (Character.isUpperCase(c)) {
	    res = doors.get(c);
	}
	return res;
    }

    public int index(int i, int j) {
	return 1000*i + j;
    }

    public static boolean isKey(char c) {
	return Character.isLowerCase(c);
	/*        Coord l = findLocation(c);
	return isKey(l.x,l.y);
	*/
    }
    
    public static boolean isKey(int i, int j) {
	return (getKey(i,j) != ' ');
    }

    public static char getKey(int i, int j) {
	char res = ' ';
	for (char c : keys.keySet()) {
	    if (keys.get(c).x==i && keys.get(c).y==j) {
		res = c;
	    }
	}
	return res;
    }

    public static boolean isDoor(char c) {
	return Character.isUpperCase(c);
	/*
        Coord l = findLocation(c);
	return isDoor(l.x,l.y);
	*/
    }

    public static boolean isDoor(int i, int j) {
	return (getDoor(i,j) != ' ');
    }

    public static char getDoor(int i, int j) {
	char res = ' ';
	for (char c : doors.keySet()) {
	    if (doors.get(c).x==i && doors.get(c).y==j) {
		res = c;
	    }
	}
	return res;
    }
    
    public void printGrid() {
	IO.print("");
	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		switch(grid[i][j]) {
		case WALL: System.out.print("#"); break;
		case OPEN: System.out.print("."); break;
		case ENTRANCE: System.out.print("@"); break;
		case KEY: System.out.print(""+getKey(i,j)); break;
		case DOOR: System.out.print(""+getDoor(i,j)); break;
		default: break;
		}
	    }
	    IO.print("");
	}
	IO.print("");
    }

    

    public static void main(String[] args) {
	Doors d = new Doors(args[0]);
    }

}
