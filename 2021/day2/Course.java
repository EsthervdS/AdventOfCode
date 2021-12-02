import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Course {

    public ArrayList<String> lines;
    public ArrayList<Integer> ints;
    public int hor,depth,aim;

    public Course(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	hor = depth = aim = 0;
    }

    public int part1() {

	for (String line : lines) {
	    String command = line.split(" ")[0];
	    int par = Integer.parseInt(line.split(" ")[1]);
	    switch(command) {
	    case "forward" : hor += par; break;
	    case "down" : depth += par; break;
	    case "up" : depth -= par; break;
	    default : break;
	    }
	}
	return hor*depth;
    }

    public int part2() {
	hor = depth = aim = 0;
	for (String line : lines) {
	    String command = line.split(" ")[0];
	    int par = Integer.parseInt(line.split(" ")[1]);
	    switch(command) {
	    case "forward" : {
		hor += par;
		depth += aim*par;
		break;
	    }
	    case "down" : aim += par; break;
	    case "up" :aim -= par; break;
	    default : break;
	    }
	}
	return hor*depth;
    }

    public static void main(String[] args) {
	     Course c = new Course(args[0]);
	     IO.print("Part 1: " + c.part1());
	     IO.print("Part 2: " + c.part2());
     }
}
