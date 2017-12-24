import util.*;
import java.util.*;

public class Bridges {

    ArrayList<String> components;
    ArrayList<ArrayList<String>> todo;
    int maxStrength,maxLength,maxLengthStrength;
    HashMap<Integer,Integer> maxs;
    
    public Bridges(String fileName) {
	components = IO.readFile(fileName);
	todo = new ArrayList<ArrayList<String>>();
	maxStrength = -1;
	maxs = new HashMap<Integer,Integer>();
	for (String comp : components) {
	    String[] ports = comp.split("/");
	    if (ports[0].equals("0")) {
		ArrayList<String> l = new ArrayList<String>();
		l.add(comp.trim());
		todo.add(l);
		int s = Integer.parseInt(ports[1]);
		if (s>maxStrength) {
		    maxStrength = s;
		    maxs.put(1,s);
		}
	    }

	}
	maxLength = 1;
	maxLengthStrength = maxStrength;

    }

    public void process() {
	while (todo.size() > 0) {
	    ArrayList<String> bridge = todo.remove(0);
	    //get all compatible next comps from components
	    ArrayList<String> nextComps = getNextPossibleComponents(bridge);
	    //if empty, this bridge is finished
	    if (nextComps.size()==0) {
		//compute strength and check if this is max
		int s = strength(bridge);
		int l = bridge.size();
		if (!maxs.containsKey(l)) {
		    maxLength = l;
		    maxs.put(l,s);
		}
		if (s > maxStrength) {
		    maxStrength = s;
		    maxs.put(l,s);
		}

	    } else {
		//otherwise, for all comps, create new bridge and add to todo
		for (String comp : nextComps) {
		    ArrayList<String> newBridge = new ArrayList<String>();
		    newBridge.addAll(bridge);
		    newBridge.add(comp);
		    int s = strength(newBridge);
		    int l = newBridge.size();
		    todo.add(newBridge);
		}
	    }

	}
	IO.print("Part 1: " + maxStrength);
	IO.print("Part 2: " + maxs.get(maxLength));
    }

    public int strength(ArrayList<String> bridge) {
	int s=0;
	for (String comp : bridge) {
	    String[] ns = comp.split("/");
	    s += Integer.parseInt(ns[0]) + Integer.parseInt(ns[1]);
	}
	return s;
    }
    
    public boolean alreadyThere(ArrayList<String> bridge, String comp) {
	for (String bc : bridge) {

		
	    if (bc.equals(comp)) return true;
	    String[] ns = bc.split("/");
	    String rev = ns[1] + "/" + ns[0];
	    if (rev.equals(comp)) return true;

	}

	return false;
    }
    
    public ArrayList<String> getNextPossibleComponents(ArrayList<String> bridge) {
	ArrayList<String> nextComps = new ArrayList<String>();
	for (String comp : components) {
	    if (!alreadyThere(bridge,comp)) {
		String[] ns = comp.split("/");
		String lastComp = bridge.get(bridge.size()-1);
		String[] lns = lastComp.split("/");
		if (ns[0].equals(lns[1])) {
		    nextComps.add(comp);
		} else if (ns[1].equals(lns[1])) {
		    String rev = ns[1] + "/" + ns[0];
		    nextComps.add(rev);
		}
	    }
	}
	return nextComps;
    }
    
    
    public static void main(String[] args) {
	Bridges b = new Bridges(args[0]);
	b.process();
    }
}
