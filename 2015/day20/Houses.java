import util.*;
import java.util.*;

public class Houses {
    long limit;
    long[] gifts,gifts2;
    int n;
    
    public Houses(String input) {
	limit = Long.parseLong(input);
	n = (int) limit;
	gifts = new long[n];
	gifts2 = new long[n];
	for (int i=0; i<n; i++) {
	    gifts[i] = 0;
	    gifts2[i] = 0;
	}
	//Part 1
	for (int house=1; house<n; house++) {
	    for (int elve=1; elve<=Math.sqrt(house); elve++) {
		if ((house % elve) == 0) {
		    if (elve==house/elve) {
			gifts[house] += elve*10;
		    } else {
			gifts[house] += (elve + house/elve)*10;
		    }
		}
		

	    }
	    if (gifts[house] >= limit) {
		IO.print("Part 1 : " + house);
		break;
	    }
	}

	//Part 2
	for (int elve=1; elve<=n; elve++) {
	    for (int house=elve; (house<=elve*50 && house<n); house+=elve) {
		if (elve==house/elve) {
		    gifts2[house] += elve*11;
		} else {
		    gifts2[house] += (elve + house/elve)*11;
		}
	    }
	}
	for (int i=1; i<(n/10*50); i++) {
	    if (gifts2[i]>=limit) {
		IO.print("Part 2 : " + i);
		break;
	    }
	}
    }
    public static void main(String[] args) {
	Houses hs = new Houses(args[0]);
    }

}
