import util.*;
import java.util.*;
import java.lang.*;

public class DigitalPlumbing {

    ArrayList<String> lines;
    HashSet<String> currGroup;
    LinkedHashMap<String,String[]> connections;
    LinkedHashMap<String,String[]> stillopen;
    
    public DigitalPlumbing(String fileName) {
	lines = IO.readFile(fileName);
	currGroup = new HashSet<String>();
	connections = new LinkedHashMap<String,String[]>();
	stillopen = new LinkedHashMap<String,String[]>();
	init();
    }

    public HashSet<String> getComponent(String start) {
	HashSet<String> curr = new HashSet<String>();
	HashSet<String> open = new HashSet<String>();
	HashSet<String> visited = new HashSet<String>();

	open.add(start);

	while(open.size()>0) {
	    String next = open.iterator().next();
	    if (!visited.contains(next)) {
		visited.add(next);
		if (connections.containsKey(next)) {
		    for (String c : connections.get(next)) {
			if (!visited.contains(c)) {
			    open.add(c);
			}
		    }
		}
		if (!curr.contains(next)) curr.add(next);
		open.remove(next);
	    }
	}
	return curr;
    }
    
    public void init() {
	for (String l : lines) {
	    String thisPipe = l.split("<->")[0].trim();
	    String[] otherPipes = l.split("<->")[1].split(", ");
	    for (int i=0; i<otherPipes.length; i++) otherPipes[i] = otherPipes[i].trim();
	    connections.put(thisPipe,otherPipes);
	}
	stillopen = new LinkedHashMap<String,String[]>(connections);
    }
    
    public static void main(String[] args) {
	DigitalPlumbing dp = new DigitalPlumbing(args[0]);

	HashSet<String> group = dp.getComponent("0");
	IO.print("Part 1: " + group.size());

	for (String p : group) {
	    dp.stillopen.remove(p);
	}

	int cnt = 1;
	
	while (dp.stillopen.size() > 0) {
	    String curr = dp.stillopen.keySet().iterator().next();
	    group = dp.getComponent(curr);
	    cnt++;
	    for (String p : group) {
		dp.stillopen.remove(p);
	    }
	    
	}
	IO.print("Part 2: " + cnt);
    }
}
