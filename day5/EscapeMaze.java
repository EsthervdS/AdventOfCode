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
	for (String line : lines) {
	    offsets.add(Integer.parseInt(line));
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
	    System.out.println("steps = " + steps + "pos was " + oldpos + " pos will be " + pos

	    if ((pos < 0) || (pos >= N)) {
		done = true;
	    }
	}
	System.out.println("Escaped the maze! That took " + steps + " steps. Last computed index is " + pos);
	
    }
    
    public static void main(String[] args) {
    
	EscapeMaze maze = new EscapeMaze(args[0]);
	maze.escape();

    }
    
}


