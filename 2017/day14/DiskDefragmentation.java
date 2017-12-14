import util.*;
import java.util.*;

public class DiskDefragmentation {

    ArrayList<String> lines;
    
    public DiskDefragmentation() {

    }

    public static int ones(char c) {
	int val = 0;
	if (Character.isDigit(c)) {
	    //c is digit
	    val = Character.getNumericValue(c);
	} else {
	    // c is in [a,f]
	    switch (c) {
	    case 'a' : { val = 10; break; }
	    case 'b' : { val = 11; break; }
	    case 'c' : { val = 12; break; }
	    case 'd' : { val = 13; break; }
	    case 'e' : { val = 14; break; }
	    case 'f' : { val = 15; break; }
	    default: break;
	    }
	}
	return Integer.bitCount(val);
    }
    
    public static int ones(String str) {
	//in: hexadecimal string of 32 characters
	//out: number of ones in byte-representation of input
	int ones = 0;
	int i = 0;
	while (i<str.length()) {
	    char c = str.charAt(i);
	    ones += ones(c);
	    i++;
	}
	return ones;
    }
    
    public static void main(String[] args) {

	String puzzle = "hwlqcszp";
	int nUsed = 0;
	for (int i=0; i<128; i++) {
	    KnotHash kh = new KnotHash(puzzle + "-" + i);
	    String out = kh.computeKnotHash();
	    nUsed += ones(out);
	}
	IO.print("Part 1: " + nUsed);

	
    }
    
}
