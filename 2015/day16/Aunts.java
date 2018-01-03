import util.*;
import java.util.*;

public class Aunts {

    ArrayList<String> lines;
    ArrayList<Sue> aunties;
    Sue goal;
    
    public Aunts(String fileName) {
	lines = IO.readFile(fileName);
	aunties = new ArrayList<Sue>();
	goal = new Sue("Sue 0: children: 3, cats: 7, samoyeds: 2, pomeranians: 3, akitas: 0, vizslas: 0, goldfish: 5, trees: 3, cars: 2, perfumes: 1");	
    }

    public void process() {
	for (String line : lines) {
	    Sue s = new Sue(line);
	    aunties.add(s);
	    
	    if (s.matches(goal)) {
		IO.print("Part 1: " + s.id);
	    }
	    if (s.matches2(goal)) {
		IO.print("Part 2: " + s.id);
	    }
	}
    }
    
    public static void main(String[] args) {
	Aunts s = new Aunts(args[0]);

	s.process();
    }
}
