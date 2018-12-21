import java.util.*;
import util.*;
    
public class Position {

    public int x,y,dist;

    public Position(int i, int j, int d) {
	x=i;
	y=j;
	dist = d;
    }

    public Position(Position q) {
	x = q.x;
	y = q.y;
	dist = q.dist;
    }

    public String toString() {
	return ( "("+x+","+y+")" );
    }
    
    public boolean equals(Position q) {
	return (this.x == q.x && this.y == q.y);
    }

    public Position north() {
	return new Position(x,y-1,dist);
    }
    public Position west() {
	return new Position(x-1,y,dist);
    }
    public Position east() {
	return new Position(x+1,y,dist);
    }
    public Position south() {
	return new Position(x,y+1,dist);
    }
    
}
