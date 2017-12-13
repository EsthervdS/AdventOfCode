import util.*;
import java.util.*;

public class Firewall {

    ArrayList<String> lines;
    Scanner[] scanners;
    int maxDepth;
    
    public Firewall(String fileName) {
	this.lines = IO.readFile(fileName);
	this.scanners = new Scanner[100];
	this.maxDepth = 0;
	
	for (String l : lines) {
	    int d = Integer.parseInt(l.split(":")[0].trim());
	    int r = Integer.parseInt(l.split(":")[1].trim());
	    scanners[d] = new Scanner(d,r);
	    if (d>this.maxDepth) this.maxDepth = d;
	}
    }

    public boolean getThroughSafe(int delay) {
	boolean safe = true;
	int currDepth = 0;
	while (currDepth <= maxDepth) {
	    if (scanners[currDepth] != null) {
		safe = safe && scanners[currDepth].safe(delay+currDepth);
	    }
	    currDepth++;
	}
	return safe;
    }
    
    public int computeSeverity(int delay) {
	int sev = 0;
	int currDepth = 0;
	while (currDepth <= maxDepth) {
	    if (scanners[currDepth] != null) {
		sev += scanners[currDepth].severity(delay+currDepth);
	    }
	    currDepth++;
	}
	return sev;
    }

    public static void main(String[] args) {
	Firewall fw = new Firewall(args[0]);

	IO.print("Part 1: " + fw.computeSeverity(0));
	
	int delay=0;
	while (true) {
	    if (fw.getThroughSafe(delay)) break;
	    delay++;
	}
	IO.print("Part 2: " + delay);
	
    }

}
