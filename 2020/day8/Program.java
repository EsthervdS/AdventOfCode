import java.util.*;
import util.*;

class Program {

    public ArrayList<String> instructions;
    public HashMap<Integer,Integer> visited;
    
    public int position, accumulator;

    public Program(ArrayList<String> i) {
	instructions = i;
	position = 0;
	accumulator = 0;
    }

    public void step() {
	//exec instruction at pos
	String[] inst = instructions.get(position).split(" ");
	String op = inst[0];
	int val = Integer.parseInt(inst[1]);

	switch (op) {
	case "nop": {
	    position++;
	    break;
	}
	case "acc": {
	    accumulator += val;
	    position++;
	    break;
	}
	case "jmp": {
	    position += val;
	    break;
	}
	default : break;
	}
    }

    public int computeAccumulatorAtLoop(int p, int a) {
	runUntil(p,a);
	return accumulator;    
    }

    public void computeVisitedStates() {
	position = 0;
	accumulator = 0;
	visited = new HashMap<Integer,Integer>();

	while (!visited.containsKey(position)) {
	    visited.put(position,accumulator);
	    step();
	}
    }

    public int runUntil(int p, int a) {
	//return 0 if loop is found
	//return 1 if program terminates
	position = p;
	accumulator = a;
	HashSet<Integer> vis = new HashSet<Integer>();

	while (!vis.contains(position) && (position < instructions.size())) {
	    vis.add(position);
	    step();
	}
	if (vis.contains(position)) {
	    return 0;
	} else {
	    return 1;
	}
    }
    
    public int fixProgram() {
	//fill visited with all positions of possible corrupted instructions
	computeVisitedStates();

	for (int tempPos : visited.keySet()) {
	    //set to previous state
	    position = tempPos;
	    accumulator = visited.get(tempPos);	

	    //find current instruction
	    String[] inst = instructions.get(tempPos).split(" ");
	    String op = inst[0];
	    int val = Integer.parseInt(inst[1]);

	    if (op.equals("nop")) {
		//check if changing nop to jmp terminates program
		int res = runUntil(position+val,accumulator);
		if (res == 1) return accumulator;
	    } else if (op.equals("jmp")) {
		//check if changing jmp to nop terminates program
		int res = runUntil(position+1,accumulator);
		if (res == 1) return accumulator;
	    }
	}
	return -1;
    }
    
}
