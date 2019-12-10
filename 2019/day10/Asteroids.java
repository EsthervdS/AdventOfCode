import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Asteroids {

    public ArrayList<String> lines;
    public int cols,rows;
    public int[][] map, blocked;
    public ArrayList<Asteroid> asts;
    public Asteroid bestOne, lastOne;
    public int curScore, maxScore;


    public int GCD(int a, int b) {
	if (b==0) return a;
	return GCD(b,a%b);
    }
    
    public Asteroids(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	init();
    }

    public void init() {
	cols = lines.get(0).length();
	rows = lines.size();
	map = new int[cols][rows];
	asts = new ArrayList<Asteroid>();
	int k = 1;
	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		char c = lines.get(j).charAt(i);
		map[i][j] = (c == '.') ? 0 : 1;
		if (map[i][j]==1) {
		    asts.add(new Asteroid(i,j,k));
		    k++;
		}
	    }
	}
	
	maxScore = -1;
    }

    public void initBlocked() {
        blocked = new int[cols][rows];
	for (int i=0; i<cols; i++) {
	    for (int j=0; j<rows; j++) {
		blocked[i][j] = 0;
	    }
	}
    }

    public void fillBlocked(Asteroid a, Asteroid b) {
	//add locations that b blocks for a to blocked array
	if (!a.equals(b)) {
	    int dx = b.x - a.x;
	    int dy = b.y - a.y;
	    
	    //normalize dx and dy
	    if (dy == 0 && dx != 0) {
		//horizontal
		dx = (dx < 0) ? -1 : 1;
	    } else if (dy != 0 && dx == 0) {
		//vertical
		dy = (dy < 0) ? -1 : 1;
	    } else {
		int d = Math.abs(GCD(dx,dy));
		if (Math.abs(d) != 1) {
		    dx = dx / d;
		    dy = dy / d;
		}
	    }
	    int tx = b.x + dx;
	    int ty = b.y + dy;
	    
	    while ( (tx >= 0) && (tx < cols) && (ty >= 0) && (ty < rows) ) {
		blocked[tx][ty] = 1;
		tx += dx;
		ty += dy;
	    }
	    
	}
    }

    public void part1() {
	//for each asteroid, compute grid points by other asteroids
	//count visible asteroids and check for largest number
	for (Asteroid a : asts) {
	    
	    initBlocked();
	    
	    for (Asteroid b : asts) {
		fillBlocked(a,b);
	    }
	    int nVisible = 0;
	    for (Asteroid b : asts) {
		if (blocked[b.x][b.y]==0 && (!a.equals(b))) {
		    nVisible++;
		}
	    }
	    if (nVisible > maxScore) {
		maxScore = nVisible;
		bestOne = a;
	    }
	}
	//IO.print("Best one is " + bestOne.id + ": " + bestOne.toString());
	IO.print("Part 1: " + maxScore);

    }

    public void part2() {
	//sort asteroids except bestOne in order of increasing angle and (in case of ties) distance from best
	//remove them (skip ties until next rotation)
	//count nr of eliminated asteroids and output the 200th
	ArrayList<Asteroid> enemies = new ArrayList<Asteroid>();
	
	for (Asteroid a : asts) {
	    if (!a.equals(bestOne)) {
		//compute angle and radius and set these in a
		a.setRadius(bestOne);
		a.setAngle(bestOne);
		enemies.add(a);
	    }
	}
	Collections.sort(enemies);
	
	int shot = 1;
	int curIndex = 0;

	while (shot < 200) {
	
	    lastOne = enemies.remove(curIndex);
	    while ( (curIndex < enemies.size()) && (enemies.get(curIndex).angle == lastOne.angle) ) {
		//skip all asteroids hiding behind lastOne
		curIndex++;
	    }

	    //check if we completed a rotation
	    if (curIndex == enemies.size()) {
		curIndex=0;
	    }
	    shot++;
	}
	IO.print("Part 2: " + (lastOne.x*100 + lastOne.y));
    }
    
    public static void main(String[] args) {
	Asteroids a = new Asteroids(args[0]);
	a.part1();
	a.part2();
		
     }
}
