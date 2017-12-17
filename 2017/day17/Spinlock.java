import util.*;
import java.util.*;

public class Spinlock {

    int steps;
    int curr;
    int afterZero, size;
    ArrayList<Integer> buffer;
    
    public Spinlock(int s) {
	steps = s;
	buffer = new ArrayList<Integer>();
	buffer.add(0);
	buffer.add(1);
	curr = 1;
	afterZero = -1;
	
    }

    private void performSteps() {
	curr = (curr + steps) % buffer.size();
	buffer.add(curr+1,buffer.size());
	curr++;
    }

    public void reset() {
	size = 2;
	curr = 1;
    }

    public void performSteps2() {
	curr = (curr + steps) % size;
	if (curr==0) {
	    afterZero = size;
	}
	size++;		
	curr++;
    }
    
    public void process() {
	for (int i=2; i<=2017; ++i) {
	    performSteps();
	}
	IO.print("Part 1: " + buffer.get( (curr+1) % buffer.size() ) );
	reset();
	for (int i=1; i<50000000; ++i) {
	    performSteps2();
	}
	IO.print("Part 2: " + afterZero);
    }

    public String toString() {
	String res = "";
	for (int i=0; i<buffer.size(); ++i) {
	    if (i==curr) res += "(";
	    res += buffer.get(i);
	    if (i==curr) res += ")";
	    res += " ";
	}
	return res;
    }
    public static void main(String[] args) {
	int steps = 301;
	Spinlock sl = new Spinlock(steps);
	sl.process();
    }
}
