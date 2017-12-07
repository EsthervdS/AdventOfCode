import util.*;

public class ComputeFloor {

    public String instructions;
    public int iteration;
    
    public ComputeFloor(String fileName) {

	instructions = IO.readFile(fileName).get(0); 
	iteration = 0;
    }

    public int computeFloor() {
	int f = 0;
	boolean hitBasement = false;
	for (char c : instructions.toCharArray()) {
	    iteration++;
	    if (c == '(') {
		f++;
	    } else {
		f--;
	    }
	    if ((f<0) && !hitBasement) {
		IO.print("Part 2: " + iteration);
		hitBasement = true;
	    }
	}
	return f;
    }

    public static void main(String[] args) {

	ComputeFloor cf = new ComputeFloor(args[0]);
	IO.print("Part 1: " + cf.computeFloor());
    }
}
