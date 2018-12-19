import java.util.*;
import util.*;

public class Attack2 {
    ArrayList<String> lines;
    ArrayList<Unit> elves;
    ArrayList<Unit> goblins;
    ArrayList<Unit> units;
    int elvesAP,nElves;
    int[][] map;
    /// empty 0 - # wall 1 - G goblin 2 - E elve 3
    int rows,cols;
    int rounds;
    boolean debug,combat;
    static boolean elvesWon;
    
    public Attack2(String filename, int ap) {
	debug = false;//(ap==10) ? true : false;
	combat = true;
	elvesAP = ap;
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
		    Unit e = new Unit(true,200,ap,i,j);
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
	nElves = elves.size();

	units = new ArrayList<Unit>();
	units.addAll(elves);
	units.addAll(goblins);
	Collections.sort(units);
	if (debug) {
	    IO.print("Initially:");
	    displayMap();
	    displayUnits();
	}
	rounds = 0;

	while (combat) {
	    round();
	    if (debug) IO.print("Round no: " + rounds);
	    if (debug) IO.print("Goblins left: " + goblins.size());
	    if (debug) IO.print("Elves left: " + elves.size());
	    if (debug) IO.print("------------------------------");
	    if (debug) {
		IO.print("");
		System.out.print("After " + rounds + " round");
		if (rounds>1) System.out.print("s");
		IO.print(":");
		displayMap();
		displayUnits();
	    }
	}
	if (debug) IO.print("After battle ends:");
	if (debug) displayMap();
	if (debug) displayUnits();
	elvesWon = (elves.size() == nElves);
	
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
	    System.out.print(" ");
	    for (Unit u : units) {
		if (u.y == j) {
		    if (u.elve) {
			System.out.print("E(" + u.hp + ") ");
		    } else {
			System.out.print("G(" + u.hp + ") ");
		    }
		}
	    }
	    IO.print("");
	}
	IO.print("");
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
	boolean duringRound = false;
	for (Unit u : units) {
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
		duringRound = true;
		break;
	    } else if (u.alive) {
		move(u,targets);
		attack(u,targets);
	    }

	}
	purgeUnits();
	if (elves.size() == 0) {
	    int sum = computeSum(goblins);
	    if (!duringRound) rounds++;
	    IO.print("Combat ends after " + (rounds) + " full rounds");
	    IO.print("Goblins win with " + sum + " HP left");
	    IO.print("Elves had AP = " + elvesAP);
	    IO.print("Part 1: " + sum*rounds);
	    combat = false;
	}
	if (goblins.size() == 0) {	    
	    int sum = computeSum(elves);
	    if (!duringRound) rounds++;
	    IO.print("Combat ends after " + (rounds) + " full rounds");
	    IO.print("Elves win with " + sum + " HP left");
	    IO.print("Elves had AP = " + elvesAP);
	    IO.print("Score: " + sum*rounds);
	    combat = false;
	}	
	Collections.sort(units);
	Collections.sort(elves);
	Collections.sort(goblins);
	rounds++;
	if (debug) {
	    IO.print("---- ROUND " + rounds + " RESULTS ----");
	    displayMap();
	}
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
	    } else if (t.hp == lowestscore) {
	        Unit cl = (t.compareTo(res)<0) ? t : res;
		res = cl;
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
	        Position p = filterReachable(u,adjacents);

		if (p == null) {
		    //none of the positions adjacent to a target is reachable, end turn
		    if (debug) IO.print("No target positions found");
		} else {
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
		    for (int i=-1; i<rows*cols; i++) lengths.put(i, new ArrayList<Integer>());

		    int[][] dists = new FloodFill().floodFill(map,p.x,p.y);

		    
		    if ( (u.y-1 < 0) || (dists[u.x][u.y-1] == -1) ) {
			lengths.get(-1).add(0);
		    } else {
			lengths.get(dists[u.x][u.y-1]).add(0);
		    }
		    if ( (u.x-1 < 0) || (dists[u.x-1][u.y] == -1) ) {
			lengths.get(-1).add(1);
		    } else {
			lengths.get(dists[u.x-1][u.y]).add(1);
		    }
		    if ( (u.x+1 >= cols) || (dists[u.x+1][u.y] == -1) ) {
			lengths.get(-1).add(2);
		    } else {
			lengths.get(dists[u.x+1][u.y]).add(2);
		    }
		    if ( (u.y+1 >= rows) || (dists[u.x][u.y+1] == -1) ) {
			lengths.get(-1).add(3);
		    } else {
			lengths.get(dists[u.x][u.y+1]).add(3);
		    }
		    int step = -1;
		    // find first non-empty entry in lengths with key not -1
		    // find lowest element in this list -> 0 is top 1 is left etc this is the next step to perform
		    for (int k : lengths.keySet()) {
			if (k!=-1) {
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

    public Position filterReachable(Unit u, ArrayList<Position> adj) {
	int minDist = Integer.MAX_VALUE;
	Position res = null;
	
	int[][] distances = new FloodFill().floodFill(map,u.x,u.y);
	
	for (Position p : adj) {
	    if (distances[p.x][p.y] >= 0) {
		//reachable
		int dist = distances[p.x][p.y];
		if (dist < minDist) {
		    res = p;
		    minDist = dist;
		} else if (dist == minDist)  {
		    Position cl = (p.compareTo(res)<0) ? p : res;
		    res = cl;
		}
	    }
	}
	return res;
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
	int i = Integer.parseInt(args[1]);
	
	Attack2 a = new Attack2(args[0],i);
	a.elvesWon = (args[2].equals("1")) ? true : false;
	while (!a.elvesWon) {
	    if (a.debug) {
		IO.print("-----------------------");
		IO.print("Elves now have ap = " + i);
	    }
	    a = new Attack2(args[0],i);
	    if (a.debug) IO.print("--- END OF AP = " + i + " ---");
	    i++;
	}
	if (!args[2].equals("1") && a.debug) IO.print("Minimal AP for Elves to win without a single death = " + a.elves.get(0).ap);
    }
				
}
