import util.*;

class Ship {

    public static final int NORTH = 0;
    public static final int WEST = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    
    public int direction;
    public int x, y;
    public int wx, wy, wq;
    public int sx, sy;
    
    public Ship() {
	direction = EAST;
	sx = sy = x = y = 0;
	wx = 10;
	wy = -1;
    }
    
    public void move(String s) {
	char operation = s.charAt(0);
	int value = Integer.parseInt(s.substring(1,s.length()));
	int ticks = 0;

	switch(operation) {
	case 'N' : y -= value; break;
	case 'S' : y += value; break;
	case 'E' : x += value; break;
	case 'W' : x -= value; break;
	case 'L' : //change direction CCW
	    ticks = value / 90;
	    for (int i=0; i<ticks; i++) direction = (direction+1) % 4;
	    break;
	case 'R' : //change direction CW
	    ticks = value / 90;
	    for (int i=0; i<ticks; i++) direction = (direction-1+4) % 4;
	    break;
	case 'F' : //move forward in current direction
	    if (direction == NORTH) y -= value;
	    if (direction == WEST) x -= value;
	    if (direction == SOUTH) y += value;
	    if (direction == EAST) x += value;
	    break;
	default : break;
	}
    }

    public void move2(String s) {
	char operation = s.charAt(0);
	int value = Integer.parseInt(s.substring(1,s.length()));
	int ticks = 0;

	switch(operation) {
	case 'N' : wy -= value; break;
	case 'S' : wy += value; break;
	case 'E' : wx += value; break;
	case 'W' : wx -= value; break;
	case 'L' : //rotate waypoint around ship CCW
	    ticks = value / 90;
	    for (int i=0; i<ticks; i++) rotateWPCCW();
	    break;
	case 'R' : //rotate waypoint around ship CW
	    ticks = value / 90;
	    for (int i=0; i<ticks; i++) rotateWPCW();
	    break;
	case 'F' : //move ship towards waypoint
	    for (int i=0; i<value; i++) {
		x += wx;
		y += wy;
	    }
	    break;
	default : break;
	}
	//IO.print(x + " - " + y + " | " + wx + " - " + wy) ;
    }

    public void rotateWPCW() {
	//rotate waypoint 90 degrees clockwise
	int oldx = wx;
	int oldy = wy;
	wx = -1 * oldy;
	wy = oldx;
    }

    public void rotateWPCCW() {
	//rotate waypoint 90 degrees counterclockwise
	int oldx = wx;
	int oldy = wy;
	wx = oldy;
	wy = -1 * oldx;
    }
}
