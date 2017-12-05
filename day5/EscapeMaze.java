import util.*;
import java.util.*;

public class EscapeMaze {

    public ArrayList<String> lines;
    public ArrayList<Integer> offsets;
    public int N;
    
    public EscapeMaze(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	offsets = new ArrayList<Integer>(lines.size());
	for (int i=0; i<lines.size(); i++) {
	    offsets.add(i,Integer.parseInt(lines.get(i)));
	}
	N = lines.size();
    }

    public void escape() {
	boolean done = false;
	int pos = 0;
	int steps= 0;
	while(!done) {
	    int oldpos = pos;
	    pos += offsets.get(pos);
  
	    if (offsets.get(oldpos) >= 3) {
		offsets.set(oldpos, offsets.get(oldpos)-1);
	    } else {
		offsets.set(oldpos, offsets.get(oldpos)+1);
	    }
	    
	    steps++;

	    if ((pos < 0) || (pos >= N)) {
		done = true;
	    }
	}
	System.out.println("Escaped the maze! That took " + steps + " steps.");
	
    }
    
    public static void main(String[] args) {
    
	EscapeMaze maze = new EscapeMaze(args[0]);
	maze.escape();

    }
    
}


