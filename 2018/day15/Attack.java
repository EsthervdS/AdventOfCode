import java.util.*;
import util.*;

public class Attack {
    ArrayList<String> lines;
    ArrayList<Unit> elves;
    ArrayList<Unit> goblins;
    ArrayList<Unit> units;
    int[][] map;
    /// empty 0 - # wall 1 - G goblin 2 - E elve 3
    int rows,cols;
    int rounds;
    boolean debug,combat;
    
    public Attack(String filename) {
	debug = false;
	combat = true;
	lines = IO.readFile(filename);
	rows = lines.size();
	cols = lines.get(0).length();
	map = new int[cols][rows];
	elves = new ArrayList<Unit>();
	goblins = new ArrayList<Unit>();
	//read map
	for (int j=0; j<rows; j++) {
	    String line = lines.get(j);
	    for (int i=0; i<cols; i++) {
		switch(line.charAt(i)) {
		case('E'): {
		    Unit e = new Unit(true,200,3,i,j);
		    elves.add(e);
		    map[i][j] = 3;
		    break;
		}
		case('G'):{
		    Unit g = new Unit(false,200,3,i,j);
		    goblins.add(g);
		    map[i][j] = 2;
		    break;
		}
		case('#'):{
		    map[i][j] = 1;
		    break;
		}
		case('.'):{
		    map[i][j] = 0;
		}
		default: break;

		}
	    }
	}
	IO.print("Initially:");
	displayMap();
	units = new ArrayList<Unit>();
	units.addAll(elves);
	units.addAll(goblins);
	Collections.sort(units);
	displayUnits();
	rounds = 0;
    }

    public void displayUnits() {
	for (Unit u : units) IO.print(u.toString());
    }
    
    public void displayMap() {
	System.out.print("   ");
	for (int i=0; i<cols;i++) System.out.print(i+"");
	IO.print("");
	for (int j=0; j<rows; j++) {
	    if (j<10) System.out.print(" ");
	    System.out.print(j+" ");
	    for (int i=0; i<cols; i++) {
		char c ='X';
		if (map[i][j] == 0) c='.';
		if (map[i][j] == 1) c='#';
		if (map[i][j] == 2) c='G';
		if (map[i][j] == 3) c='E';
		System.out.print(c+"");
	    }
	    IO.print("");
	}
    }

    public ArrayList<Unit> alive(ArrayList<Unit> targets) {
	ArrayList<Unit> alt = new ArrayList<Unit>();
	for (Unit t : targets) {
	    if (t.alive) {
		alt.add(t);
	    }
	}
	return alt;
    }
    
    public void round() {
	/// empty 0 - # wall 1 - G goblin 2 - E elve 3
	int[][] newmap = new int[cols][rows];

	for (Unit u : units) {
	    System.out.print(".");
	    //units are sorted by reading order	    
	    //each unit performs a turn: first move (if necessary, if possible) then attack (if possible)
	    // if no targets available: combat ends => part1 answer = rounds * (sum of hp of remaining units)
	    ArrayList<Unit> targets = new ArrayList<Unit>();
	    if (u.elve) {
		targets = alive(goblins);
	    } else {
		targets = alive(elves);
	    }
	    if (targets.size() == 0) {
		combat = false;
		break;
	    } else if (u.alive) {
		move(u,targets);
		attack(u,targets);
	    }

	}
	IO.print("");
	purgeUnits();
	if (elves.size() == 0) {
	    int sum = computeSum(goblins);
	    if (debug) IO.print("Combat ends after " + (rounds) + " full rounds");
	    if (debug) IO.print("Goblins win with " + sum + " HP left");
	    
	    IO.print("Part 1: " + sum*rounds);
	    combat = false;
	}
	if (goblins.size() == 0) {	    
	    int sum = computeSum(elves);
	    if (debug) IO.print("Combat ends after " + (rounds) + " full rounds");
	    if (debug) IO.print("Elves win with " + sum + " HP left");
	    IO.print("Part 1: " + sum*rounds);
	    combat = false;
	}	
	Collections.sort(units);
	Collections.sort(elves);
	Collections.sort(goblins);
	rounds++;
    }

    public void purgeUnits() {
	ArrayList<Unit> newSet = new ArrayList<Unit>(units);

	if (debug) {
	    IO.print("Purging units");
	    IO.print(units.toString());
	}
	for (Unit u : units) {
	    if (debug) IO.print("Checking unit to see if it's still alive: " + u.toString());
	    if (!u.alive) {
		if (debug) IO.print("Removing dead unit: " + u.toString());
		if (u.elve) {
		    elves.remove(u);
		} else {
		    goblins.remove(u);
		}
		newSet.remove(u);
	    } 
	}
	units = newSet;
    }
    
    public void attack(Unit u, ArrayList<Unit> targets) {

	if (debug) IO.print("-------------");
	if (debug) IO.print("Unit attacking = " + u.toString());
	if (debug) IO.print("Targets: " + targets.toString());	

	ArrayList<Unit> adjacents = allAdjacents(u,targets);
	Collections.sort(adjacents);
	if (debug) IO.print("Unit is adjacent to these targets: " + adjacents.toString());

	if (adjacents.isEmpty()) {
	    if (debug) IO.print("Not adjacent, no attack");
	} else {
	    Unit t = lowest(adjacents);
	    if (debug) IO.print("Attacking this unit: " + t.toString());
	    t.hp -= u.ap;
	    if (t.hp <= 0) {
		if (debug) IO.print("Unit dies: " + t.toString());
		//target dies
		t.alive = false;
		map[t.x][t.y] = 0;
	    }
	}
    }

    public Unit lowest(ArrayList<Unit> targets) {
	int lowestscore = targets.get(0).hp;
	Unit res = targets.get(0);
	for (Unit t : targets) {
	    if (t.hp < lowestscore) {
		lowestscore = t.hp;
		res = t;
	    }
	}
	return res;
    }

    
    public boolean move(Unit u, ArrayList<Unit> targets) {
	boolean b = false;
	// return true if already adjacent to target (skip) or if step is made towards closest position in range
	// return false if no positions in range available

	if (debug) IO.print("-------------");
	if (debug) IO.print("Moving unit " + u.toString());
	if (debug) IO.print("Targets: " + targets.toString());
	if (debug) displayMap();
	ArrayList<Position> adjacents = new ArrayList<Position>();
	//find all available positions adjacent to this target
	for (Unit t : targets) {
	    adjacents.addAll(adjacent(u,t));
	}
	if (adjacents.size() == 0) {
	    b = false;
	} else {
	    Collections.sort(adjacents);
	    if (debug) IO.print("Adjacent: " + adjacents.toString());
	    //if already adjacent to target skip to attack (b=true)
	    if (isAdjacent(u,adjacents)) {
		if (debug) IO.print("Is already adjacent to a target - no move");
		b = true;
	    } else {

		// filter list adjacents for reachable positions
		HashMap<Position,Integer> reachable = filterReachable(u,adjacents);
		if (debug) IO.print("Reachable: " + reachable.toString());
		if (reachable.isEmpty()) {
		    //none of the positions adjacent to a target is reachable, end turn
		    
		} else {
		    // find closest of those, if tied: first in reading order
		    Position p = new Position(-1,-1); // p should be closest from reachable
		    int minDist = Integer.MAX_VALUE;
		    for (Position q : reachable.keySet() ) {
			if (reachable.get(q) < minDist) {
			    minDist = reachable.get(q);
			    p = q;
			} else if (reachable.get(q) == minDist) {
			    Position cl = (p.compareTo(q)<0) ? p : q;
			    p = cl;
			}
		    }
		    if (debug) IO.print("Moving towards closest of adjacents = " + p.toString());

		    // we have a reachable target
		    // find shortest paths to closest this position
		    //   when do we have a tie in shortest path? There are four possible next positions.
		    //   If more than one of those has the same length of shortest path towards the goal:
		    //     we have a tie and we should pick the step first in reading order 
		    // perform selected step
		    Position unit = new Position(u);
		    // top 0, left 1, right 2, bottom 3 (reading order)
		    HashMap<Integer,ArrayList<Integer>> lengths = new HashMap<Integer,ArrayList<Integer>>();
		    for (int i=0; i<rows*cols; i++) lengths.put(i, new ArrayList<Integer>());

		    Position[] left = new BFS().findPath(map, unit.left(), p);
		    Position[] right = new BFS().findPath(map, unit.right(), p);
		    Position[] top = new BFS().findPath(map, unit.top(), p);
		    Position[] bottom = new BFS().findPath(map, unit.bottom(), p);
		    
		    if (top == null) {
			lengths.get(0).add(0);
		    } else {
			lengths.get(top.length).add(0);
		    }
		    if (left == null) {
			lengths.get(0).add(1);
		    } else {
			lengths.get(left.length).add(1);
		    }
		    if (right == null) {
			lengths.get(0).add(2);
		    } else {
			lengths.get(right.length).add(2);
		    }
		    if (bottom == null) {
			lengths.get(0).add(3);
		    } else {
			lengths.get(bottom.length).add(3);
		    }
		    int step = -1;
		    // find first non-empty entry in lengths with key > 0
		    // find lowest element in this list -> 0 is top 1 is left etc this is the next step to perform
		    for (int k : lengths.keySet()) {
			if (k!=0) {
			    // length of the path cannot be 0
			    if (!lengths.get(k).isEmpty()) {
				step = lengths.get(k).get(0);
				break;
			    }
			}
		    }
		    //move and update map!
		    map[u.x][u.y] = 0;
		    u.move(step);
		    map[u.x][u.y] = (u.elve) ? 3 : 2;
		    if (debug) IO.print("New position of unit: (" + u.x + "," + u.y + ")");
		    b = true;
		}
	    }
	}
	return b;
    }

    public HashMap<Position,Integer> filterReachable(Unit u, ArrayList<Position> adj) {
	HashMap<Position,Integer> reachable = new HashMap<Position,Integer>();
	for (Position p : adj) {
	    if (debug) IO.print("Checking " + p.toString() + " for reachability");
	    Position up = new Position(u);
	    Position[] path0 = new BFS().findPath(map, up.top(), p); 
	    Position[] path1 = new BFS().findPath(map, up.left(), p);
	    Position[] path2 = new BFS().findPath(map, up.right(), p); 
	    Position[] path3 = new BFS().findPath(map, up.bottom(), p);
	    int d0 = (path0 == null) ? Integer.MAX_VALUE : path0.length;
	    int d1 = (path1 == null) ? Integer.MAX_VALUE : path1.length;
	    int d2 = (path2 == null) ? Integer.MAX_VALUE : path2.length;
	    int d3 = (path3 == null) ? Integer.MAX_VALUE : path3.length;

	    if ((d0 != Integer.MAX_VALUE) && (d0 <= d1) && (d0 <= d2) && (d0 <= d3) ) {
		if (debug) {
		    IO.print("Top path exists and is shortest:");
		    for (Position p1 : path0) {
			System.out.print(p1.toString());
		    }
		    IO.print("");
		}
		reachable.put(p,d0);
	    } else if ((d1 != Integer.MAX_VALUE) && (d1 <= d0) && (d1 <= d2) && (d1 <= d3) ) {
		if (debug) {
		    IO.print("Left path exists and is shortest:");
		    for (Position p1 : path1) {
			System.out.print(p1.toString());
		    }
		    IO.print("");
		}
		reachable.put(p,d1);
	    } else if ((d2 != Integer.MAX_VALUE) && (d2 <= d0) && (d2 <= d1) && (d2 <= d3) ) {
		if (debug) {
		    IO.print("Right path exists and is shortest:");
		    for (Position p1 : path2) {
			System.out.print(p1.toString());
		    }
		    IO.print("");
		}
		reachable.put(p,d2);
	    } else if ((d3 != Integer.MAX_VALUE) && (d3 <= d0) && (d3 <= d1) && (d3 <= d2) ) {
		if (debug) {
		    IO.print("Bottom path exists and is shortest:");
		    for (Position p1 : path3) {
			System.out.print(p1.toString());
		    }
		    IO.print("");
		}
		reachable.put(p,d3);
	    }	    
	}
	return reachable;
    }
    
    public Position closest(Unit u, ArrayList<Position> adj) {
	Position p = new Position(-1,-1);
	Position up = new Position(u.x,u.y);

	int minDist = Integer.MAX_VALUE;
	for (Position q : adj) {
	    Position[] path = new BFS().findPath(map, up, q);
	    int d = (path==null) ? Integer.MAX_VALUE : path.length;
	    if (d<minDist) {
		minDist = d;
		p = q;
	    } else if (d==minDist)  {
		Position cl = (p.compareTo(q)<0) ? p : q;
		p = cl;
	    }
	}
	return p;
    }
    
    public boolean isAdjacent(Unit u, ArrayList<Position> adj) {
	boolean res = false;
	for (Position p : adj) {
	    if ((p.x == u.x) && (p.y == u.y)) {
		res = true;
	    }
	}
	return res;
    }

    public ArrayList<Unit> allAdjacents(Unit u, ArrayList<Unit> adj) {
	ArrayList<Unit> res = new ArrayList<Unit>();
	for (Unit t : adj) {
	    Position up = new Position(u);
	    Position ut = new Position(t);
	    if (up.distanceTo(ut) == 1) {
		res.add(t);
	    }
	}
	return res;
    }
    
    public int computeSum(ArrayList<Unit> ul) {
	int res = 0;
	for (Unit u : ul) {
	    res += u.hp;
	}
	return res;
    }

    public ArrayList<Position> adjacent(Unit u, Unit t) {
	ArrayList<Position> res = new ArrayList<Position>();
	if ((t.x-1)>0) {
	    if ((map[t.x-1][t.y] == 0) || ((t.x-1 == u.x) && (t.y == u.y))) {
		res.add(new Position(t.x-1,t.y));
	    } 
	}
	if ((t.x+1)<cols) {
	    if ((map[t.x+1][t.y] == 0) || ((t.x+1 == u.x) && (t.y == u.y))) {
		res.add(new Position(t.x+1,t.y));
	    }
	}
	if ((t.y-1)>0) {
	    if ((map[t.x][t.y-1] == 0) || ((t.x == u.x) && (t.y-1 == u.y))) {
		res.add(new Position(t.x,t.y-1));
	    }
	}
	if ((t.y+1)<rows) {
	    if ((map[t.x][t.y+1] == 0) || ((t.x == u.x) && (t.y+1 == u.y))) {
		res.add(new Position(t.x,t.y+1));
	    }
	}
	return res;
    }
    
    public static void main(String[] args) {
	Attack a = new Attack(args[0]);
	while (a.combat) {
	    a.round();
	    IO.print("Round no: " + a.rounds);
	    IO.print("Goblins left: " + a.goblins.size());
	    IO.print("Elves left: " + a.elves.size());
	    IO.print("------------------------------");
	    if (a.debug) {
		IO.print("");
		System.out.print("After " + a.rounds + " round");
		if (a.rounds>1) System.out.print("s");
		IO.print(":");
		a.displayMap();
		a.displayUnits();
	    }
	}
	IO.print("After battle ends:");
	a.displayMap();
	a.displayUnits();
    }
				
}
