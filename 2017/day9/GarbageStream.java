import util.*;
import java.util.*;

public class GarbageStream {

    String str;
    int it;
    int score;
    
    public GarbageStream(String fileName) {
	str = IO.readFile(fileName).get(0);
	score = 0;
	it = 0;
    }

    private void process() {
	int l = 0, garCount = 0;
	boolean gar = false, neg = false;
	
	while (it < str.length()) {
	    char c = str.charAt(it);
	    
	    switch(c) {
	    case '{' : {
		//open new group
		if (!neg && !gar) {
		    l++;
		} else if (gar && !neg) {
		    garCount++;
		}
		break;
	    }
	    case '}' : {
		//close group
		if (!neg && !gar) {
		    score += l;
		    l--;
		}  else if (gar && !neg) {
		    garCount++;
		}
		break;
	    }
	    case '<' : {
		//garbage start
		if (!neg && !gar) {
		    gar = true;
		} else if (gar && !neg) {
		    garCount++;
		}
		break;
	    }
	    case '>' : {
		//garbage end
		if (!neg && gar) {
		    gar = false;
		} 
		break;
	    }
	    case '!' : {
		//don't increase garbage count
		break;
	    }
	    default: {
		if (gar && !neg) garCount++;
		break;
	    }
	    }
	    // stop neglecting if we were
	    if (!neg && (c=='!')) {
		neg = true;
	    } else {
		neg = false;
	    }

	    it++;
	} 
	//end of stream
	IO.print("Part 1: " + score);
	IO.print("Part 2: " + garCount);

    }
    
    public static void main(String[] args) {
	GarbageStream gs = new GarbageStream(args[0]);
	gs.process();
	
    }
}
