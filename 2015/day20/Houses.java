import util.*;
import java.util.*;

public class Houses {
    long limit;
    long[] gifts;
    int n;
    boolean found;
    
    public Houses(String input) {
	limit = Long.parseLong(input);
	n = (int) (limit/10);
	gifts = new long[n];
	found = false;
	for (int i=0; i<n; i++) {
	    gifts[i] = 0;
	}
	for (int house=1; house<n; house++) {
	    for (int elve=1; elve<=Math.sqrt(house); elve++) {
		if ((house % elve) == 0) {
		    gifts[house] += (elve + house/elve)*10;
		}
		

	    }
	    int x = (int) Math.sqrt(house);
	    if (Math.pow(x,2) == house) {
		gifts[house] -= 10*Math.sqrt(house);
	    }
	    if (gifts[house] >= limit) {
		IO.print("Part 1 : " + house);
		found = true;
		break;
	    }
	}
    }
    
    public static void main(String[] args) {
	Houses hs = new Houses(args[0]);
	//IO.print("Part 1: " + rp.uniques.size());
	//IO.print("Part 2: " + rp.reduce());
    }

}
