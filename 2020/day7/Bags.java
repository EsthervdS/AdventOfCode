import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Bags {

    public ArrayList<String> lines;
    public HashMap<String,ArrayList<String>> mapping;
    
    public Bags(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	mapping = new HashMap<String,ArrayList<String>>();
	
	//light red bags contain 1 bright white bag, 2 muted yellow bags.
	for (String line : lines) {
	    String[] s = line.split(" contain ");
	    if (s[1].equals("no other bags.")) {
		//empty mapping for left hand side
		mapping.put(s[0].split(" bags")[0],new ArrayList<String>());
	    } else {
		//add left hand side as key and right hand side as list
		String[] bs = s[1].split(", ");
		ArrayList<String> temp = new ArrayList<String>();
		for (String cbag : bs) {
		    String[] t = cbag.split(" ");
		    temp.add(t[1]+" "+t[2]);
		}
		mapping.put(s[0].split(" bags")[0],temp);
	    }
	}
    }

    public int part1() {
	int res = 0;

	ArrayDeque<String> toDo = new ArrayDeque<String>();
	ArrayList<String> done = new ArrayList<String>();	
	toDo.add("shiny gold");
	
	while (toDo.size() > 0) {
	    IO.print(toDo.toString());
	    String search = toDo.pop();
	    for (String key : mapping.keySet()) {
		if (mapping.get(key).contains(search) && (!done.contains(key))) {
		    res++;
		    toDo.push(key);
		}
	    }
	    done.add(search);
	}
	
	return res;
    }

    public long part2() {
	return -1;
    }

    
    public static void main(String[] args) {
	Bags b = new Bags(args[0]);
	IO.print("Part 1: " + b.part1());
	IO.print("Part 2: " + b.part2());
    }
}
