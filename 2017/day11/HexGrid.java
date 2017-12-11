import util.*;
import java.util.*;

public class HexGrid {

    ArrayList<String> lines;
    int currX,currY,currZ;
    int maxSteps;
    
    public HexGrid(String fileName) {
	lines = IO.readFile(fileName);
	maxSteps = 0;
    }

    public void process() {
	for (int i=0; i<lines.size(); i++) {
	    String[] directions = lines.get(i).split(",");
	    currX = 0; currY = 0; currZ=0;
	    processDirections(directions);
	    IO.print("Part 1 : "  + computePathLength());
	    
	}
    }

    public int computePathLength() {
	return (Math.abs(currX) + Math.abs(currY) + Math.abs(currZ) ) / 2;
    }

    public void processDirections(String[] dirs) {
	for (int i=0; i<dirs.length; i++) {
	    switch (dirs[i]) {
	    case "n" : {
		currX++; currY++;
		break;
	    }
	    case "s" : {
		currX--; currY--;
		break;
	    }
	    case "ne" : {
		currX++; currZ++;
		break;
	    }
	    case "nw" : {
		currY++; currZ--;
		break;
	    }
	    case "se" : {
		currY--; currZ++;
		break;
	    }
	    case "sw" : {
		currX--; currZ--;
		break;
	    }
	    default: {
		break;
	    }
	    }
	    if (computePathLength() > maxSteps) {
		maxSteps = computePathLength();
	    }
	}
	
    }
    public static void main(String[] args) {
	HexGrid hg = new HexGrid(args[0]);
	hg.process();
	IO.print("Part 2: " + hg.maxSteps);
    }
}
