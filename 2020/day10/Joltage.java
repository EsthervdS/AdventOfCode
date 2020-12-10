import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Joltage {

    public ArrayList<String> lines;
    public ArrayList<Long> numbers;
    public long combis;
    
    public Joltage(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	numbers = new ArrayList<Long>();
	for (String line : lines) numbers.add(Long.parseLong(line));
    }

    public long part1() {
	int oneDiff = 0;
	int threeDiff = 1;
	
	ArrayList<Long> sorted = new ArrayList<Long>(numbers);
	Collections.sort(sorted);

	long curVolt = 0;

	for (long i : sorted) {
	    if ((i-1) == curVolt) {
		oneDiff++;
	    }
	    if ((i-3) == curVolt) {
		threeDiff++;
	    }
	    curVolt = i;
	}
	return oneDiff * threeDiff;
    }

    public long part2() {
	ArrayList<Long> sorted = new ArrayList<Long>(numbers);
	Collections.sort(sorted);
	sorted.add(0,0L);
	sorted.add(sorted.get(sorted.size()-1)+3L);

	long curVolt = 0;
	int oneSeq = 0;
	combis = 1;
	
	for (long i : sorted) {

	    if ((i-1) == curVolt) {
		oneSeq++;
	    }
	    if ((i-3) == curVolt) {
		if (oneSeq == 2) combis = combis * 2;
		if (oneSeq == 3) combis = combis * 4;
		if (oneSeq == 4) combis = combis * 7;
		oneSeq = 0;
	    }
	    curVolt = i;
	}
	return combis;
    }

    
    public static void main(String[] args) {
	long timeNano = System.nanoTime(); 

	Joltage j = new Joltage(args[0]);
	IO.print("Parse input: " + (System.nanoTime() - timeNano)/1000  + "µs");
	timeNano = System.nanoTime(); 
	IO.print("Part 1: " + j.part1());
	IO.print("Part 1: " + (System.nanoTime() - timeNano)/1000 + "µs");
	timeNano = System.nanoTime();
	IO.print("Part 2: " + j.part2());
	IO.print("Part 2: " + (System.nanoTime() - timeNano)/1000 + "µs");
    }
}
