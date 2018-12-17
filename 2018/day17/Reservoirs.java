import java.util.*;
import util.*;

public class Reservoirs {
    ArrayList<String> lines;
    char[][] grid;
    ArrayDeque<Position> toDo;

    public Reservoirs(String filename) {
	lines = IO.readFile(filename);
	grid = Grid.buildGrid(lines);
	//Grid.display();
	toDo = new ArrayDeque<Position>();
	toDo.addFirst(new Position(500,0));
	Grid.process(toDo);
	//Grid.display();
	
    }
    
    public static void main(String[] args) {
	Reservoirs r = new Reservoirs(args[0]);
	IO.print("Part 1: " + Grid.reachable());
	IO.print("Part 2: " + Grid.remaining());
    }
}
