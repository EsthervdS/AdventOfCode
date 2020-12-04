import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Passports {

    public ArrayList<String> lines;
    public ArrayList<Passport> pps;
    
    public Passports(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	pps = new ArrayList<Passport>();
	int i=0;
	ArrayList<String> cur = new ArrayList<String>();
	while (i < lines.size()) {
	    String line = lines.get(i);
	    
	    if (line.length() == 0) {
		pps.add(new Passport(cur));
		cur = new ArrayList<String>();
	    } else {
		cur.add(line);
	    }
	    i++;
	}
	pps.add(new Passport(cur));
    }

    public int part1() { 
	int res = 0;
	for (Passport p : pps) {
	    if (p.isValid1()) res++;
	}
	return res;
   }

    public long part2() {
	int res = 0;
	for (Passport p : pps) {
	    if (p.isValid2()) res++;
	}
	return res;
    }

    
    public static void main(String[] args) {
	Passports p = new Passports(args[0]);
	IO.print("Part 1: " + p.part1());
	IO.print("Part 2: " + p.part2());
    }
}
