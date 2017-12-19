import util.*;
import java.util.*;

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
    
    public static void main(String[] args) {
	//Reindeer rd = new Reindeer("test_input.txt", 1000);
	//IO.print("Test: " + rd.winningDistance());
		 
	Reindeer rd = new Reindeer("input.txt", 2503);
	IO.print("Part 1: " + rd.winningDistance());
    }
}
