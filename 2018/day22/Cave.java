import java.util.*;
import java.math.*;
import util.*;

public class Cave {
    boolean debug;
    int depth, xt, yt;
    int xmax,ymax;
    int[][] types;
    int[][] erosion;
    long[][] geologic;
    HashMap<Integer,ArrayList<Integer>> allowed;
    int[][][] distances;
    
    public Cave(int d, int x, int y) {
	depth = d;
	xt = x;
	yt = y;
	debug = true; //false;
	init();
	if (debug) showCave();
	computeDistances();
	if (debug) showDistances();
	
    }

    public void init() {

	if (xt==10) {
	    xmax = 15;
	    ymax = 15;
	} else {
	    xmax = xt+200;
	    ymax = yt+200;
	}

		   
	computeTypes();
	
	// rock 0, wet 1, narrow 2
	// torch 0, climbing gear 1, neither 2
	allowed = new HashMap<Integer,ArrayList<Integer>>();
	ArrayList<Integer> rock = new ArrayList<Integer>(Arrays.asList(0,1));
	ArrayList<Integer> wet = new ArrayList<Integer>(Arrays.asList(1,2));
	ArrayList<Integer> narrow = new ArrayList<Integer>(Arrays.asList(0,2));

	allowed.put(0,rock);
	allowed.put(1,wet);
	allowed.put(2,narrow);
	distances = new int[xmax][ymax][3];
	for (int i=0; i<xmax; i++) {
	    for (int j=0; j<ymax; j++) {
		for (int k=0; k<3; k++) {
		    distances[i][j][k] = -1;
		}
	    }
	}
    }

    public ArrayList<Position> neighbors(Element e) {
	ArrayList<Position> res = new ArrayList<Position>();
	if (e.x-1 > 0) {
	    res.add(new Position(e.x-1,e.y));
	}
	if (e.y-1 > 0) {
	    res.add(new Position(e.x,e.y-1));
	}
	if (e.x+1 < xmax) {
	    res.add(new Position(e.x+1,e.y));
	}
	if (e.y+1 < ymax) {
	    res.add(new Position(e.x,e.y+1));
	}
	return res;
    }

    public ArrayList<Integer> otherTools(int t) {
	if (t==0) {
	    return (new ArrayList<Integer>(Arrays.asList(1,2)));
	} else if (t==1 ) {
	    return (new ArrayList<Integer>(Arrays.asList(0,2)));	    
	} else { // t==2
	    return (new ArrayList<Integer>(Arrays.asList(0,1)));
	}
    }
    
    public void computeDistances() {
	//queue of elements sorted by increasing distance
	ArrayList<Element> toDo = new ArrayList<Element>();
	toDo.add(new Element(0,0,0,0));
	while (toDo.size() > 0) {
	    Element cur = toDo.get(0);

	    if (debug) IO.print("Popped " + cur.toString());// + " from toDo = " + toDo.toString());
	    if (debug) IO.print("toDo length = " + toDo.size());
	    toDo.remove(0);
	    if (debug) IO.print("toDo length = " + toDo.size());

	    int curDist = distances[cur.x][cur.y][cur.t];
	    if (debug) IO.print("curDist = " + curDist);
	    if ((cur.d < curDist) || (curDist == -1)) {
		//we've improved on the shortest path
		if (debug) IO.print("New best distance = " + cur.d + " for tool " + cur.t);
		distances[cur.x][cur.y][cur.t] = cur.d;
		for (int t2 : otherTools(cur.t)) {
		    int toolDist = distances[cur.x][cur.y][t2];
		    if ((cur.d+7<toolDist) || (toolDist == -1)) distances[cur.x][cur.y][t2] = cur.d+7;
		}

		//check for all 4 neighbors 
		for (Position nb : neighbors(cur)) {

		    if (debug) IO.print("Checking neighbor: " + nb.toString());
		    //check if current tool is allowed in neighbor region
		    if (allowed.get(types[nb.x][nb.y]).contains(cur.t)) {
			//keep tool
			if (debug) IO.print("Current tool = " + cur.t + " switching to region: " + types[nb.x][nb.y] + " so We can keep the tool");
			int oldDist = distances[nb.x][nb.y][cur.t];
			if ( ((cur.d+1)< oldDist) || (oldDist == -1) ) {
			    //distances[nb.x][nb.y][cur.t] = cur.d+1;
			    if (debug) IO.print("Adding neighbor " + nb.toString() + " to queue with tool " + cur.t + " and distance " + ((int) (cur.d+1)));
			    Element e = new Element(nb.x,nb.y,cur.t,cur.d+1);
			    if (!toDo.contains(e)) toDo.add(e);
			
			} else {
			    // else: don't add, no improvement
			    if (debug) IO.print("No improvement, not adding to the queue");
			}
		    } else {
			//change tool: 2 options
			if (debug) IO.print("Current tool = " + cur.t + " switching to region: " + types[nb.x][nb.y] + " so we have to switch the tool");
			for (int t2 : otherTools(cur.t)) {
			    int oldDist = distances[nb.x][nb.y][t2];
			    if (debug) IO.print("For tool " + t2 + " best distance was: " + oldDist);
			    if ( ((cur.d+8)< oldDist) || (oldDist == -1) )  {
				//distances[nb.x][nb.y][t2] = cur.d+7;
				if (debug) IO.print("Adding neighbor " + nb.toString() + " to queue with tool " + t2 + " and distance " + ((int) (cur.d+8)));
				Element e = new Element(nb.x,nb.y,t2,cur.d+8);
				if (!toDo.contains(e)) toDo.add(e);
			    } else {
				if (debug) IO.print("No improvement, not adding to the queue");
			    }
			}
		    }
		}
	    } else {
		//we've been here before with a shorter distance, so skip this element
	    }
	    Collections.sort(toDo);
	    //if (debug) IO.print("toDo is now " + toDo.toString());
	    
	}
    }

    public void showDistances() {
        IO.print("----TORCH----");
	for (int j=0; j<ymax; j++) {
	    for (int i=0; i<xmax; i++) {
		System.out.print(distances[i][j][0] + "\t");
	    }
	    IO.print("");
	}
	IO.print("----GEAR----");
	for (int j=0; j<ymax; j++) {
	    for (int i=0; i<xmax; i++) {
		System.out.print(distances[i][j][1] + "\t");
	    }
	    IO.print("");
	}
	IO.print("----NEITHER----");
	for (int j=0; j<ymax; j++) {
	    for (int i=0; i<xmax; i++) {
		System.out.print(distances[i][j][2] + "\t");
	    }
	    IO.print("");
	}
    }

    
    public int part2() {
	return distances[xt][yt][0];
    }
    
    public void computeTypes() {
	types = new int[xmax][ymax];
	erosion = new int[xmax][ymax];
        geologic = new long[xmax][ymax];
	for (int j=0; j<ymax; j++) {
	    for (int i=0; i<xmax; i++) {
		types[i][j] = -1;
		erosion[i][j] = -1;
		geologic[i][j] = -1;
	    }
	}

	ArrayDeque<Position> toDo = new ArrayDeque<Position>();
	toDo.addLast(new Position(0,0));
	toDo.addLast(new Position(xt,yt));
	int min = (xmax > ymax) ? ymax : xmax;
	for (int level = 0; level < min; level++) {
	    toDo.addLast(new Position(level,level));
	    for(int i=level; i<xmax; i++) toDo.addLast(new Position(i,level));
	    for(int i=level; i<ymax; i++) toDo.addLast(new Position(level,i));
	}

	while (toDo.size() > 0) {
	    Position p = toDo.removeFirst();
	    if ((p.x==0 && p.y==0) || (p.x==xt && p.y == yt)) {
		geologic[p.x][p.y] = 0;	
	    } else if (p.y==0) {
		geologic[p.x][p.y] = p.x * 16807;
	    } else if (p.x==0) {
		geologic[p.x][p.y] = p.y * 48271;
	    } else {
		geologic[p.x][p.y] = erosion[p.x-1][p.y] * erosion[p.x][p.y-1];
	    }
	    erosion[p.x][p.y] = (int) (geologic[p.x][p.y] + depth) % 20183;
	    types[p.x][p.y] = erosion[p.x][p.y] % 3;
	    
	}
    }

    public void showCave() {
	for (int j=0; j<ymax; j++) {
	    for (int i=0; i<xmax; i++) {
		if (i==0 && j==0) {
		    System.out.print("M");
		} else if (i==xt && j==yt) {
		    System.out.print("T");
		} else if (types[i][j] == 0) {
		    System.out.print(".");
		} else if (types[i][j] == 1) {
		    System.out.print("=");
		} else if (types[i][j] == 2) {
		    System.out.print("|");
		}

	    }
	    IO.print("");
	}
    }

    public int part1() {
	int risk = 0;
	for (int j=0; j<=yt; j++) {
	    for (int i=0; i<=xt; i++) {
		risk += types[i][j];
	    }
	}
	return risk;
    }

    


    
    public static void main(String[] args) {
	ArrayList<String> in = IO.readFile(args[0]);
	int d = Integer.parseInt(in.get(0).split(" ")[1]);
	int x = Integer.parseInt(in.get(1).split(" ")[1].split(",")[0]);
	int y = Integer.parseInt(in.get(1).split(" ")[1].split(",")[1]);
	Cave c = new Cave(d,x,y);
	IO.print("Part 1: " + c.part1());
	IO.print("Part 2: " + c.part2());

	
    }

}
