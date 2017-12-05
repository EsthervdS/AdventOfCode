import java.io.*;
import java.util.*;
import util.*;

public class Checksum {

    public ArrayList<String> lines;
    public int checksum;
    public Checksum(String fileName) {
	lines = new ArrayList<String>();	
	lines = IO.readFile(fileName);
	checksum = 0;
	
    }


    public static void main(String[] args) {
	Checksum cs = new Checksum(args[0]);

	for (String line : lines) {
	    //part 1: int min =10000;
	    //part 1: int max = -1;
	    int div = 0; // part 1: diff
	    String[] stringArray = cs.line.split("\t");
	    ArrayList<Integer> intsOfThisLine = new ArrayList<Integer>(20);
	    for (String s : stringArray) {
		/* part 2 */
		int thisInt = Integer.parseInt(s,10);
		intsOfThisLine.add(thisInt);

		//part 1: if (thisInt < min) min = thisInt;
		//part 1: if (thisInt > max) max = thisInt;
	    }
	    /* part 2 */
	    // iterate over all ints of this line and check for divisors
	    for (int j=0; j<intsOfThisLine.size(); j++) {
		for (int k=j+1; k<intsOfThisLine.size(); k++) {
		    int ij = intsOfThisLine.get(j);
		    int ik = intsOfThisLine.get(k);
		    if (ij > ik) {
			if (ij % ik == 0) div = ij / ik;
		    } else {
			if (ik % ij == 0) div = ik / ij;
		    }
		}
	    }
	    //part 1: diff = max - min;
	     
	    cs.checksum += div; // part 1: += diff
	}

	System.out.println("Checksum = " + cs.checksum);
	
    }
}
