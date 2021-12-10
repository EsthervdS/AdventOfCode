import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class LanternFish {

    public ArrayList<String> lines;
    public ArrayList<Integer> ints;
    public int newFishDays,totalDays;

	
    public LanternFish(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	ints = new ArrayList<Integer>();
	
	String[] intStrings = lines.get(0).split(",");
	for (String nr : intStrings) {
	    ints.add(Integer.parseInt(nr));
	}

	newFishDays = 7;
	totalDays = 256;
    }

    public int part1() {
	int res = ints.size();;
	Queue<Fish> toDo = new LinkedList<>();
	for (int i : ints) {
	    toDo.add(new Fish(i,totalDays,newFishDays));
	}
	
	while (! toDo.isEmpty() ) {
	    Fish cur = toDo.remove();
	    int newFish = cur.computeNewFish();
	    res += newFish;

	    for (int i=0; i<newFish; i++) {
		int remainingDays = cur.daysLeft-(cur.initialTimer+1+i*newFishDays);
		if (remainingDays >= newFishDays) toDo.add(new Fish(8,remainingDays,newFishDays));
	    }
	}
	
	return res;
    }

    public int part2() {
	return 0;
    }

    public static void main(String[] args) {
	     LanternFish lf = new LanternFish(args[0]);
	     IO.print("Part 1: " + lf.part1());
	     IO.print("Part 2: " + lf.part2());
     }
}
