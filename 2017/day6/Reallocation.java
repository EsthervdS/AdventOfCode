import util.*;
import java.util.*;

public class Reallocation {

    private int[] memory;
    private ArrayList<String> configurations;
    private int columns;
	
    public Reallocation(String fileName) {

	String memoryString = IO.readFile(fileName).get(0);
	String[] blocks = memoryString.split("\t");
	memory = new int[blocks.length];
	int i=0;
	for (String b : blocks) {
	    memory[i] = Integer.parseInt(b);
	    i++;
	}
	columns = blocks.length;
	configurations = new ArrayList<String>();
	configurations.add(configString(memory));
    }

    private String configString(int[] config) {
	String s = "";
	for (int i=0; i<columns; i++) {
	    s += Integer.toString(memory[i]) + "/";
	}
	return s;
    }

    private int getIndexOfMax() {
	int ind = 0;
	int max = -1;
	for (int i=0; i<columns; i++) {
	    //break tie to lowest index
	    if (memory[i] > max) {
		max = memory[i];
		ind = i;
	    }
	}
	return ind;
    }

    private boolean seenThisConfigBefore() {
	boolean seenBefore = false;
	for (String s :  configurations) {
	    seenBefore = (s.equals(configString(memory)));
	    if (seenBefore) {
		IO.print("Part 1: " + configurations.size());
		IO.print("Part 2: " + (configurations.size() - configurations.indexOf(s)));
		break;
	    }
	}
	return seenBefore;
    }
    
    public void redistribute() {
	
	//find column with max #blocks
	int maxIndex = getIndexOfMax();
	int distBlocks = memory[maxIndex];

	//iterate over columns and redisitribute blocks
	for(int i=1; i<=distBlocks; i++) {
	    memory[(maxIndex+i) % columns]++;
	    memory[maxIndex]--;
	}

	//check if configuration has been seen before. If so, repeat. Otherwise: done
	if (!seenThisConfigBefore()) {
	    configurations.add(configString(memory));
	    redistribute();
	}
    }

    public static void main(String[] args) {

	Reallocation ra = new Reallocation(args[0]);
	ra.redistribute();
	
    }
}
