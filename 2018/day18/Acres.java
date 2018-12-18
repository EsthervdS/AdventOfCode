import java.util.*;
import util.*;

public class Acres {
    ArrayList<String> lines;
    char[][] grid;

    public Acres(String filename) {
	lines = IO.readFile(filename);
	grid = Grid.buildGrid(lines);

	while (!Grid.sameResourceValue) {
	    Grid.minute();
	    if (Grid.minutesPassed == 10) {
		IO.print("Part 1: " + Grid.part1());
	    }
	}
	int prevTime = Grid.prevValues.get(Grid.part1());
	int t = (1000000000 - prevTime) % Grid.loop;
	IO.print("Part 2: " + Grid.prevValuesRev.get(prevTime+t));
	
    }
    
    public static void main(String[] args) {
	Acres r = new Acres(args[0]);
    }
}
