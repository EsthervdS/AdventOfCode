import util.*;
import java.util.*;
import java.util.stream.*;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Reindeer {

    ArrayList<String> lines;
    int seconds;
    
    public Reindeer(String fileName, int secs) {
	lines = IO.readFile(fileName);
	//Dancer can fly 7 km/s for 20 seconds, but then must rest for 119 seconds.
	seconds = secs;
    }

    public int getSpeed(String reindeer) {
	return Integer.parseInt(reindeer.split(" ")[3]);
    }

    public int getFlyingTime(String reindeer) {
	return Integer.parseInt(reindeer.split(" ")[6]);
    }

    public int getRestingTime(String reindeer) {
	return Integer.parseInt(reindeer.split(" ")[13]);
    }

    public int getCycleTime(String reindeer) {
	return (getRestingTime(reindeer) + getFlyingTime(reindeer));
    }

    public int cycleDistance(String reindeer) {
	return (getFlyingTime(reindeer) * getSpeed(reindeer));
    }
    
    public int winningDistance() {
	int maxDist = -1;
	for (String reindeer : lines) {
	    int thisDist = 0;
	    int wholeCycles = seconds / getCycleTime(reindeer);
	    thisDist += wholeCycles * cycleDistance(reindeer);
	    int remainder = seconds % getCycleTime(reindeer);
	    if (remainder >= getFlyingTime(reindeer) ) {
		thisDist += cycleDistance(reindeer);
	    } else {
		thisDist += remainder * getSpeed(reindeer);
	    }
	    
	    if (thisDist > maxDist) maxDist = thisDist;
	}
	return maxDist;
    }

    public int winningScore() {
	ArrayList<Integer> scores = new ArrayList<Integer>(lines.size());
	for (int i=0; i<lines.size(); ++i) scores.add(0);
	int maxDist = -1;
	int leadingHorse = -1;
	
	for (int i=1; i<seconds; i++) {
	    for (int j=0; j<lines.size(); j++) {
		//compute all distances
		String reindeer = lines.get(j);
		int thisDist = 0;
		int wholeCycles = i / getCycleTime(reindeer);
		thisDist += wholeCycles * cycleDistance(reindeer);
		int remainder = i % getCycleTime(reindeer);
		if (remainder >= getFlyingTime(reindeer) ) {
		    thisDist += cycleDistance(reindeer);
		} else {
		    thisDist += remainder * getSpeed(reindeer);
		}
		
		if (thisDist > maxDist) {
		    maxDist = thisDist;
		    leadingHorse = j;
		}
	    }
	    scores.set(leadingHorse,scores.get(leadingHorse)+1);
	}
	return scores.stream().max(Comparator.naturalOrder()).get();
    }
    
    public static void main(String[] args) {
	/*
	Reindeer rtest = new Reindeer("test_input.txt", 1000);
	IO.print("Test: " + rtest.winningDistance());
	IO.print("Test: " + rtest.winningScore());
	*/
	Reindeer rd = new Reindeer("input.txt", 2503);
	IO.print("Part 1: " + rd.winningDistance());
	IO.print("Part 2: " + rd.winningScore());

    }
}
