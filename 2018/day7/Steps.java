import java.io.*;
import java.util.*;
import util.*;

public class Steps {

    public ArrayList<String> lines;
    public TreeSet<String> steps;
    public HashMap<String,ArrayList<String>> rules;
    public HashMap<String,ArrayList<String>> prelims;
    
    public Steps(String fileName) {
	lines = new ArrayList<String>();	
	lines = IO.readFile(fileName);

	steps = new TreeSet<String>();
	rules = new HashMap<String,ArrayList<String>>();
	prelims = new HashMap<String,ArrayList<String>>();

	//read in rules
	for (String line : lines) {
	    String first = line.charAt(5)+""; 
	    String last = line.charAt(36)+"";
	    steps.add(first);
	    steps.add(last);
	    if (rules.containsKey(first)) {
		ArrayList<String> prev = rules.get(first);
		prev.add(last);
		rules.replace(first,prev);
	    } else {
		ArrayList<String> cur = new ArrayList<String>();
		cur.add(last);
		rules.put(first,cur);
	    }
	}
	//compute preliminaries for each step
	for(String s : steps) {
	    prelims.put(s,new ArrayList<String>());
	    for (String k : rules.keySet()) {
		if (rules.get(k).contains(s)) {
		    ArrayList<String> prev = prelims.get(s);
		    prev.add(k);
		    prelims.replace(s,prev);		    
		}
	    }
	}

    }

    
    public static void main(String[] args) {
	Steps cc = new Steps(args[0]);
	String part1 = "";
	ArrayList<String> toDo = new ArrayList<String>(cc.steps);
	Collections.sort(toDo);
	int i=0;
	
	while (toDo.size() > 0) {
	    //find next step to be performed
	    String next = toDo.get(i);
	    while (!cc.prelims.get(next).isEmpty()) {
		i++;
		next = toDo.get(i);
	    }

	    //add next step to result and remove from all preliminaries 
	    part1 += next;
	    i=0;
	    toDo.remove(next);
	    for (String pr : cc.prelims.keySet()) {
		if (cc.prelims.get(pr).contains(next)) {
		    ArrayList<String> prev = cc.prelims.get(pr);
		    prev.remove(next);
		    cc.prelims.replace(pr,prev);
		}
	    }
	}

	IO.print("Part 1: " + part1);

    }

}
