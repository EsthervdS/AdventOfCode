import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
import java.math.*;

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
	totalDays = 80;
    }

    public long computeNewFish(int initialTimer, int daysLeft) {
	if (initialTimer < daysLeft) {
	    if ((initialTimer + newFishDays) <= daysLeft) {
		//compute how many
		int children = 1 + (int) Math.floor( (daysLeft - 1 - initialTimer) / 7);
		long temp  = children;
		for (int i=0; i<children; i++) {
		    int remainingDays = daysLeft-(initialTimer+1+i*newFishDays);
		    if (remainingDays >= newFishDays) {
			long t = computeNewFish(8,remainingDays);
			temp += t;
		    }
		}
		return temp; 
	    } else {
		//single fish based on initial timer
		return 1L;
	    }
	} else {
	    //no new fish
	    return 0L;
	}
    }

    public long part1() {
	int res = ints.size();;

	for (int i : ints) {
	    long newFish = computeNewFish(i,totalDays);
	    res += newFish;

	}
	return res;
    }
   
    public long part2() {
	totalDays = 256;
	long res = (long) ints.size();
	HashMap<Integer,Long> cache = new HashMap<Integer,Long>();
	for (int i : ints) {
	    if (cache.containsKey(i)) {
		res += cache.get(i);
	    } else {
		long newFish = computeNewFish(i,totalDays);
		res += newFish;
		cache.put(i,newFish);
	    }

	}
	
	return res;
    }

    public static void main(String[] args) {
	     LanternFish lf = new LanternFish(args[0]);
	     IO.print("Part 1: " + lf.part1());
	     IO.print("Part 2: " + lf.part2());
     }
}
