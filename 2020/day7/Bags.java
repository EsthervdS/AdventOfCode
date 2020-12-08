import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Bags {

    public ArrayList<String> lines;
    public HashMap<String,ArrayList<Node>> mapping;
    
    public Bags(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	mapping = new HashMap<String,ArrayList<Node>>();
	
	//light red bags contain 1 bright white bag, 2 muted yellow bags.
	for (String line : lines) {
	    String[] s = line.split(" contain ");
	    if (s[1].equals("no other bags.")) {
		//empty mapping for left hand side
		mapping.put(s[0].split(" bags")[0],new ArrayList<Node>());
	    } else {
		//add left hand side as key and right hand side as list
		String[] bs = s[1].split(", ");
		ArrayList<Node> temp = new ArrayList<Node>();
		for (String cbag : bs) {
		    String[] t = cbag.split(" ");
		    temp.add(new Node(t[1]+" "+t[2],Integer.parseInt(t[0])));
		}
		mapping.put(s[0].split(" bags")[0],temp);
	    }
	}
	IO.print(mapping.toString());
    }

    public int part1() {
	int res = 0;
	
	ArrayDeque<String> toDo = new ArrayDeque<String>();
	ArrayList<String> done = new ArrayList<String>();	
	toDo.add("shiny gold");
	
	while (toDo.size() > 0) {

	    String search = toDo.pop();
	    for (String key : mapping.keySet()) {
		if (!done.contains(key)) {
		    ArrayList<Node> nodes = mapping.get(key);
		    for (Node n : nodes) {
			if (n.color.equals(search)) {
			    res++;
			    toDo.push(key);
			}
		    }
		}
	    }
	    done.add(search);
	}
	return res;
    }

    public int part2() {
	return getBags("shiny gold");
    }

    public int getBags(String search) {
	ArrayList<Node> within = mapping.get(search);
	int res = 0;
	if (within.isEmpty()) {
	    res = 0;
	} else {
	    for (Node n : within) {
		res += n.quantity + (n.quantity * getBags(n.color));
	    }
	}
	return res;
    }
    
    public static void main(String[] args) {
	Bags b = new Bags(args[0]);
	IO.print("Part 1: " + b.part1());
	IO.print("Part 2: " + b.part2());
    }
}
