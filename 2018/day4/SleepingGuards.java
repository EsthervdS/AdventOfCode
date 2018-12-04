import java.io.*;
import java.util.*;
import util.*;

public class SleepingGuards {

    public ArrayList<String> lines;
    public HashMap<Integer,Guard> guards;

    public SleepingGuards(String fileName) {
	lines = new ArrayList<String>();	
	lines = IO.readFile(fileName);
	Collections.sort(lines);
	guards = new HashMap<Integer,Guard>();
	int i=0;
	Shift curShift = new Shift();
	
	while (i<lines.size()) {
	    //iterate over all input lines, creating shifts and adding actions
	    String line = lines.get(i);
	    //IO.print(line);
	    if (line.split("]")[1].trim().charAt(0) == 'G') {
		curShift.computeAsleep();
		//start new shift
		curShift = new Shift(line);
		int gid = curShift.guardid;
		if (guards.containsKey(gid)) {
		    guards.get(gid).addShift(curShift);
		} else {
		    Guard g = new Guard(gid);
		    g.addShift(curShift);
		    guards.put(gid,g);
		}
	    } else {
		//add this line to curShift
		int min = Integer.parseInt(line.split(" ")[1].split(":")[1].split("]")[0]);
		if (line.split("]")[1].charAt(1)=='f') {
		    //falls asleep
		    curShift.addAction(min,"asleep");
		} else {
		    //wakes up
		    curShift.addAction(min,"awake");
		}
	    }
	    i++;
	}
	//process last shift
	curShift.computeAsleep();    

	for(Guard g : guards.values()) {
	    g.computeShifts();
	}	
    }

    public static void main(String[] args) {
	SleepingGuards sg = new SleepingGuards(args[0]);
	Guard maxG = new Guard(-1);
	int maxM = -1;
	for(Guard g : sg.guards.values()) {
	    int gMins = g.totalMinutesAsleep();
	    if (gMins > maxM) {
		maxG = g;
		maxM = gMins;
	    }
	}
	IO.print("Part 1: " + maxG.id * maxG.maxMinute);

	int maxCount = -1;
	maxG = new Guard(-1);
	for(Guard g : sg.guards.values()) {
	    int gCount = g.maxShifts;
	    if (gCount > maxCount) {
		maxCount = gCount;
		maxG = g;
	    }
	}
	IO.print("Part 2: " + maxG.id * maxG.maxMinute);

    }

}
