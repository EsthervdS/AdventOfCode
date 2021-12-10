import util.*;

public class Fish {

    public int initialTimer;
    public int daysLeft;
    public int newFishDays;

    public Fish (int t, int d, int n) {
	initialTimer = t;
	daysLeft = d;
	newFishDays = n;
    }
    public int computeNewFish() {
	if (initialTimer < daysLeft) {
	    if ((initialTimer + newFishDays) < daysLeft) {
		//compute how many
		return 1 + (int) Math.floor( (daysLeft - 1 - initialTimer) / newFishDays);
	    } else {
		//single fish based on initial timer
		return 1;
	    }
	} else {
	    //no new fish
	    return 0;
	}
    }

    public String toString() {
	return "Fish: " + initialTimer + " inState " + daysLeft + " days left\n";
    }
}
