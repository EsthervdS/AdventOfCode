import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Passwords {

    public ArrayList<String> lines;

    public Passwords(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
    }

    public Password createPW(String line) {
	String[] parts = line.split(": ");
	int min = Integer.parseInt(parts[0].split("-")[0]);
	int max = Integer.parseInt(parts[0].split("-")[1].split(" ")[0]);
	char c = parts[0].split("-")[1].split(" ")[1].charAt(0);
	String p = parts[1];
	return new Password(min,max,c,p);
    }
    
    public int part1() {
	int res = 0;
	for (String line : lines) {
	    Password pw = createPW(line);
	    if (pw.isValid()) res++;
	}
	return res;
    }

    public int part2() {
	int res = 0;
	for (String line : lines) {
	    Password pw = createPW(line);
	    if (pw.isValid2()) res++;
	}	
	return res;
    }

    
    public static void main(String[] args) {
	     Passwords p = new Passwords(args[0]);
	     IO.print("Part 1: " + p.part1());
	     IO.print("Part 2: " + p.part2());
     }
}
