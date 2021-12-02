import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Surface {

    public ArrayList<String> lines;
    public ArrayList<Integer> ints;

    public Surface(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	ints = new ArrayList<Integer>();
	for (String line : lines) {
	    ints.add(Integer.parseInt(line,10));
	}
    }

    public int part1() {
	int res = 0;
	for (int i=1; i<ints.size(); i++) {
	    if (ints.get(i) > ints.get(i-1)) res++;
	}
	return res;
    }

    public int part2() {
	int res = 0;
	int prev = ints.get(0) + ints.get(1) + ints.get(2);
	for (int i = 3; i<ints.size(); i++) {
	    int cur = prev - ints.get(i-3) + ints.get(i);
	    if  (cur > prev) res++;
	    prev = cur;
	}
	return res;
    }

    public static void main(String[] args) {
	     Surface s = new Surface(args[0]);
	     IO.print("Part 1: " + s.part1());
	     IO.print("Part 2: " + s.part2());
     }
}
