import util.*;

class Ship {

    public static final int NORTH = 0;
    public static final int WEST = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    
    public int direction;
    public int x, y;
    
    public Ship() {
	direction = EAST;
	x = y = 0;
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
}
