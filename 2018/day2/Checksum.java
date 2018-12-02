import java.io.*;
import java.util.*;
import util.*;

public class Checksum {

    public ArrayList<String> lines;
    public int checksum;
    public int twos, threes;
    public Checksum(String fileName) {
	lines = new ArrayList<String>();	
	lines = IO.readFile(fileName);
	checksum = 0;
	int twos=0,threes=0;
    }

    public void parse(String s) {
	HashMap<Character,Integer> counts = new HashMap<Character,Integer>();
	for (Character c : s.toCharArray()) {
	    if (counts.containsKey(c)) {
		int curr = counts.get(c);
		counts.replace(c,++curr);
	    } else {
		counts.put(c,1);
	    }
	}
	compute(counts.values());
    }

    public void compute(Collection<Integer> counts) {
	boolean two = false, three = false;
	for (int k : counts) {
	    if ( (k==2) && (!two) ) {
		twos++;
		two = true;
	    }
	    if ( (k==3) && (!three) ) {
		threes++;
		three = true;
	    }
	}
    }

    public boolean differsByOne(String s1, String s2) {
	int nDiffs = 0;
	for (int i=0; i<s1.length(); i++) {
	    if (s1.charAt(i) != s2.charAt(i)) {
		nDiffs++;
	    }
	}
	return (nDiffs == 1);
    }
    
    public String sameLetters(String s1, String s2) {
	String res = "";
	for (int i=0; i<s1.length(); i++) {
	    if (s1.charAt(i) == s2.charAt(i)) {
		res = res + s1.charAt(i);
	    }
	}
	return res;
    }

    public static void main(String[] args) {
	Checksum cs = new Checksum(args[0]);
	boolean found = false;
	for (String line : cs.lines) {
	    cs.parse(line);
	}

	IO.print("Part 1: " + cs.twos * cs.threes);

	for (String line1 : cs.lines) {
	    for (String line2 : cs.lines) {
		if (cs.differsByOne(line1,line2)) {
		    IO.print("Part 2: " + cs.sameLetters(line1,line2));
		    found = true;
		}
		if (found) break;
	    }
	    if (found) break;
	}
	    
	
    }
}
