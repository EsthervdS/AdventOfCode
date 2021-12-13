import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class DiagnosticReport {

    public ArrayList<String> lines;
    public ArrayList<Integer> ints;
    public int[][] numbers;
    public int width,height;
    public StringBuilder epsilon, gamma;
    
    public DiagnosticReport(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	width = lines.get(0).length();
	height = lines.size();
    }

    public int part1() {
	epsilon = new StringBuilder("");
	gamma = new StringBuilder("");
	
	for (int i=0; i<width; i++) {
	    int ones = 0;
	    for (int j=0; j<height; j++) {
		if (lines.get(j).charAt(i) == '1') ones++;
	    }
	    int mC = common(lines,i,true);
	    int lC = 1-mC;
	    gamma.append(mC+"");
	    epsilon.append(lC+"");
	}
	return Integer.parseInt(gamma.toString(),2) * Integer.parseInt(epsilon.toString(),2);
    }

    public int part2() {
	String oxygen = computeRating(true);
	String co2scrubber = computeRating(false);
	return Integer.parseInt(oxygen,2) * Integer.parseInt(co2scrubber,2);
    }

    public String computeRating(boolean most) {
	ArrayList<String> copyLines = new ArrayList<String>(lines);
	int index = 0;
	while (copyLines.size() > 1) {
	    copyLines = filterLines(copyLines,index,most);
	    if (copyLines.size() == 1) break;
	    index++;
	}
	return copyLines.get(0);
    }

    public ArrayList<String> filterLines(ArrayList<String> list, int i, boolean m) {
	ArrayList<String> cl = new ArrayList<String>();
	char lC = '0';
	char mC = '0';
	
	if (m) {
	    int mostC = common(list,i,true);
	    mC = (char) ('0' + mostC);
	    lC = (char) ('0' + (1-mostC));
	} else {
	    int leastC = common(list,i,false);
	    lC = (char) ('0' + leastC);
	    mC = (char) ('0' + (1-leastC));
	}
	for (String l : list) {
	    if (m && (l.charAt(i) == mC)) cl.add(l);
	    if (!m && (l.charAt(i) == lC)) cl.add(l);
	}
	return cl;
    }

    public int common(ArrayList<String> l, int i, boolean m) {
	int ones = 0;
	for (int j=0; j<l.size(); j++) {
	    if (l.get(j).charAt(i) == '1') ones++;
	}
	if (m) {
	    if (ones >=  l.size()/2.0) { 
		return 1;
	    } else {
		return 0;
	    }     
	} else {
	    //!m so least common
	    if (ones < l.size()/2.0) { 
		return 1;
	    } else {
		return 0;
	    }
	}
    }
    
    public static void main(String[] args) {
	     DiagnosticReport dr = new DiagnosticReport(args[0]);
	     IO.print("Part 1: " + dr.part1());
	     IO.print("Part 2: " + dr.part2());
     }
}
