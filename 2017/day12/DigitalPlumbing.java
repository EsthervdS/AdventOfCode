import util.*;
import java.util.*;

public class DigitalPlumbing {

    ArrayList<String> lines;
    LinkedHashSet<String> groupZero;
    
    public DigitalPlumbing(String fileName) {
	lines = IO.readFile(fileName);
	groupZero = new LinkedHashSet<String>();
	groupZero.add("0");
    }

    public void process() {
	addToGroupZero();
	IO.print("Part 1: " + groupZero.size());
    }

    public void addToGroupZero() {
	int prevSize = 0;
	while (groupZero.size() != prevSize) {
	    prevSize = groupZero.size();
	    for (String l : lines) {
		String[] ls = l.split("<->");
		String thisPipe = ls[0].trim();
		if (groupZero.contains(thisPipe)) {
		    //add pipes after <-> to groupZero
		    //l = 2 <-> 0, 3, 4
		    //ls = ["2","0, 3, 4"]
		    String[] otherPipes = ls[1].split(", ");
		    for (int i=0; i<otherPipes.length; i++) {
			groupZero.add(otherPipes[i].trim());
		    }
		}
	    }
	    
	}
    }
	
    public static void main(String[] args) {
	DigitalPlumbing dp = new DigitalPlumbing(args[0]);
	dp.process();
    }
}
