import java.io.*;
import java.util.*;
import java.util.stream.*;
import util.*;
import java.time.*;

public class Preamble {

    public ArrayList<String> lines;
    public ArrayList<Long> numbers;
    public int pSize;

    
    public Preamble(String fileName, int k) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	numbers = new ArrayList<Long>();
	for (String line : lines) numbers.add(Long.parseLong(line));
	pSize = k;
    }

    public long part1() {

	ArrayList<Long> sums = allSums(0);
	int pos = pSize;
	while (sums.contains(numbers.get(pos))) {
	    pos++;
	    sums = allSums(pos-pSize);
	}
	return numbers.get(pos);
    }

    public long part2() {
	return -1;
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
