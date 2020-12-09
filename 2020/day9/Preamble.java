import java.io.*;
import java.util.*;
import java.util.stream.*;
import util.*;
import java.time.*;

public class Preamble {

    public ArrayList<String> lines;
    public ArrayList<Long> numbers;
    public int pSize;
    public long part1;

    
    public Preamble(String fileName, int k) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	numbers = new ArrayList<Long>();
	for (String line : lines) numbers.add(Long.parseLong(line));
	pSize = k;
    }

    public long part1() {
	//find first number that is not a sum of a pair of numbers from the preamble
	ArrayList<Long> sums = allSums(0);
	int pos = pSize;
	while (sums.contains(numbers.get(pos))) {
	    pos++;
	    sums = allSums(pos-pSize);
	}
	part1 = numbers.get(pos);
	return part1;
    }

    public long part2() {
	//find consecutive list of numbers that sums up to $part1
	boolean found = false;
	ArrayList<Long> foundInterval = new ArrayList<Long>();
	int startpos = 0;
	while (!found) {
	    int i=0;
	    while (sum(foundInterval) < part1) {
		foundInterval.add(numbers.get(startpos + i));
		i++;
	    }
	    if (sum(foundInterval) == part1) {
		found = true;
	    } else {
		startpos++;
		foundInterval = new ArrayList<Long>();
	    }
	   
	}
	ArrayList<Long> sorted = new ArrayList<Long>(foundInterval);
	Collections.sort(sorted);
	return sorted.get(0)+sorted.get(sorted.size()-1);
    }
    
    public long sum(ArrayList<Long> l) {
	long res = 0;
	for (Long val : l) res += val;
	return res;
    }
	
    public ArrayList<Pair> allPreamblePairs(int offset) {
	ArrayList<Pair> res = new ArrayList<Pair>();
	for (int i=offset; (i<numbers.size() && i<(offset+pSize)); i++) {
	    for (int j=i+1; (j<numbers.size() && j<(offset+pSize)); j++) {
		res.add(new Pair(numbers.get(i),numbers.get(j)));
	    }
	}
	return res;
    }

    public ArrayList<Long> allSums(int offset) {
	ArrayList<Long> res = new ArrayList<Long>();
	for (Pair p : allPreamblePairs(offset)) res.add(p.sum());
	return res;
    }
    
    public static void main(String[] args) {
	Preamble p = new Preamble(args[0], Integer.parseInt(args[1]));
	IO.print("Part 1: " + p.part1());
	IO.print("Part 2: " + p.part2());

    }
}
