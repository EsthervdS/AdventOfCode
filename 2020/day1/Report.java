import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Report {

    public ArrayList<String> lines;
    public ArrayList<Integer> ints;

    public Report(String fileName) {
  	  lines = new ArrayList<String>();
  	  lines = IO.readFile(fileName);

      ints = new ArrayList<Integer>();
      for (String line : lines) {
        ints.add(Integer.parseInt(line,10));
      }
    }

    public int part1() {
	boolean found = false;
	int res = -1;
	int i=0;
	while (!found) {
	    int cur = ints.get(i);
	    if (containsComplement(cur)) {
		res = cur*(2020-cur);
		found = true;
	    } else {
		i++;
	    }
	}
	return res;
    }

    public int part2() {
	boolean found = false;
	int res = -1;
	for (int i=0;i<ints.size();i++) {
	    for (int j=0;j<ints.size();j++) {
		int f = ints.get(i);
		int s = ints.get(j);
		int cur = f + s;
		if (containsComplement(cur)) {
		    res = f*s*(2020-f-s);
		    break;
		}
	    }
	}
	return res;
    }

    public boolean containsComplement(int cur) {
	int compl = 2020 - cur;
	if (ints.contains(compl)) {
	    return true;
	} else {
	    return false;
	}
    }
    
    public static void main(String[] args) {
	     Report r = new Report(args[0]);
	     IO.print("Part 1: " + r.part1());
	     IO.print("Part 2: " + r.part2());
     }
}
