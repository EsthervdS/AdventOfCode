import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Fuel {

    public ArrayList<String> lines;
    public ArrayList<Integer> ints;

    public Fuel(String fileName) {
  	  lines = new ArrayList<String>();
  	  lines = IO.readFile(fileName);

      ints = new ArrayList<Integer>();
      for (String line : lines) {
        ints.add(Integer.parseInt(line,10));
      }
    }

    public int fuel(int mass) {
	return (mass / 3) - 2;
    }

    public int part1() {
	int t = 0;
	for (int m : ints) {
	    t += fuel(m);
	}
	return t;
    }

    public int part2() {
	int t = 0;
	for (int m : ints) {
	    int f = fuel(m);
	    t += f;
	    while (f > 0) {
		int f2 = fuel(f);
		if (f2>0) t += f2;
		f = f2;
	    }
	}
	return t;
    }
    
    public static void main(String[] args) {
	     Fuel f = new Fuel(args[0]);
	     IO.print("Part 1: " + f.part1());
	     IO.print("Part 2: " + f.part2());
     }
}
