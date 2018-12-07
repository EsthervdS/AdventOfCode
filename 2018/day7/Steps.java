import java.io.*;
import java.util.*;
import util.*;

public class Steps {

    public ArrayList<String> lines;
    public TreeSet<String> steps;
    public HashMap<String,ArrayList<String>> rules;
    public HashMap<String,ArrayList<String>> prelims;
    public int nw;
    
    public Steps(String fileName) {
	lines = new ArrayList<String>();	
	lines = IO.readFile(fileName);

	steps = new TreeSet<String>();
	rules = new HashMap<String,ArrayList<String>>();
	prelims = new HashMap<String,ArrayList<String>>();

	readRules();
	nw = 5;
    }

    public void readRules() {
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
    }
    public void computePrelims() {
	//compute preliminaries for each step
	prelims = new HashMap<String,ArrayList<String>>();
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

    public void part1() {
	computePrelims();
	String part1 = "";
	ArrayList<String> toDo = new ArrayList<String>(steps);
	Collections.sort(toDo);
	int i=0;
	
	while (toDo.size() > 0) {
	    String next = toDo.get(i);
	    while (!prelims.get(next).isEmpty()) {
		i++;
		next = toDo.get(i);
	    }
	    part1 += next;
	    i=0;
	    toDo.remove(next);
	    for (String pr : prelims.keySet()) {
		prelims.get(pr).remove(next);
	    }
	}

	IO.print("Part 1: " + part1);
    }

    public void part2() {
	//reset preliminaries after part1
	computePrelims();
	int timer = 0;
	int finished = 0;

	ArrayList<String> toDo = new ArrayList<String>(steps);
	Collections.sort(toDo);

	String[] workerSteps = new String[nw];
	int[] workerTime = new int[nw];
	for (int w = 0; w<nw; w++) workerSteps[w] = ".";
	
	while (finished < steps.size()) {

	    ArrayList<Integer> idleWorkers = new ArrayList<Integer>();
	    for (int w=0; w<nw; w++) {
		if (workerSteps[w] == ".") {
		    idleWorkers.add(w);
		} else {
		    if (workerTime[w] == (int) (workerSteps[w].charAt(0) - 4)) {
			finished++;
			for (String pr : prelims.keySet()) {
			    prelims.get(pr).remove(workerSteps[w]);
			}			
			workerSteps[w] = ".";
			workerTime[w] = 0;
			idleWorkers.add(w);
		    } else {
			workerTime[w]++;
		    }
		}
	    }

	    while (idleWorkers.size() > 0) {
		int w = idleWorkers.get(0);
		idleWorkers.remove(0);
		int i=0;
		String next;
		if (toDo.size() > 0) {
		    next = toDo.get(i);
		    while (!prelims.get(next).isEmpty()) {
			i++;
			if (i==toDo.size()) break;
			next = toDo.get(i);
		    }
		    if (i<toDo.size()) {
			workerSteps[w] = next;
			workerTime[w] = 1;
			toDo.remove(next);
		    } else {
			break;
		    }
		}
	    }
	    if (finished < steps.size()) timer++;
	}
	IO.print("Part 2: " + timer);
    }
    
    public static void main(String[] args) {
	Steps cc = new Steps(args[0]);
	cc.part1();
	cc.part2();

    }

}
