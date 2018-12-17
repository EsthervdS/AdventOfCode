import java.util.*;
import util.*;
    
public class Position {

    public int x,y;

    public Position(int i, int j) {
	x=i;
	y=j;
    }

    public String toString() {
	return ( "("+x+","+y+")" );
    }
    
    public boolean equals(Position q) {
	return (this.x == q.x && this.y == q.y);
    }
}
