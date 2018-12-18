import util.*;

public class Unit implements Comparable<Unit> {
    public int hp,ap;
    public boolean elve;
    public int x,y;
    public boolean alive;
    
    public Unit(boolean e, int h, int a, int posx, int posy) {
	elve = e;
	hp = h;
	ap = a;
	x = posx;
	y = posy;
	alive = true;
    }

    
    public void move(int step) {
	boolean debug = false;
	if (step == 0) {
	    if (debug) IO.print("Moving up");
	    y--;
	}  else if (step == 1) {
	    if (debug) IO.print("Moving left");
	    x--;
	} else if (step == 2) {
	    if (debug) IO.print("Moving right");
	    x++;
	} else if (step == 3) {
	    if (debug) IO.print("Moving down");
	    y++;
	}
    }
    
    @Override
    public int compareTo(Unit u2) {
	if ( (y < u2.y) || ((y == u2.y) && (x < u2.x)) ) {
	    return -1;
	} else if (x==u2.x && y==u2.y) {
	    return 0;
	} else {
	    return 1;
	}
    }
    
    public String toString() {
	String res = "";
	res = res + (elve ? "Elve" : "Goblin");
	res += (" at (" + x + ","+y+") : HP " + hp);
	return res;
    }
}
