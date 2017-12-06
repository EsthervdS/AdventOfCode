import util.*;
import java.util.*;

public class Reallocation {

    private String memoryString;
    private int[] memory;
    private ArrayList<String> configurations;
    private int columns;
	
    public Reallocation(String fileName) {

	memoryString = IO.readFile(fileName).get(0);
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
	    s += Integer.toString(memory[i]);
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
	    if (seenBefore) break;
	}
	return seenBefore;
    }
    
    public void redistribute() {
	
	//find column with max #blocks
	int maxIndex = getIndexOfMax();
	int distBlocks = memory[maxIndex];
	
	//set that column to zero
	memory[maxIndex] = 0;
	
	//iterate over columns and add 1 until all blocks are redistributed
	for(int i=1; i<=distBlocks; i++) {
	    memory[(maxIndex+i) % columns]++;
	}
	
	//check if configuration has been seen before. If so, repeat. Otherwise: done
	if (!seenThisConfigBefore()) {
	    configurations.add(configString(memory));
	    redistribute();
	} else {
	    IO.print("DONE! It took " + configurations.size() + " redistributions.");
	}
    }

    public static void main(String[] args) {

	Reallocation ra = new Reallocation(args[0]);
	ra.redistribute();
	
    }
}
