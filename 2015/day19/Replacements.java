import util.*;
import java.util.*;

public class Replacements {

    ArrayList<String> lines;
    ArrayList<Replacement> repl;
    String target;
    TreeSet<String> uniques;
    
    public Replacements(String fileName) {
	lines = IO.readFile(fileName);
	repl = new ArrayList<Replacement>();
	
	for(String line : lines) {
	    if (line.split(" => ").length == 1) {
		break;
	    } else {
		repl.add(new Replacement(line.split(" => ")[0],line.split(" => ")[1]));
	    }
	}

	target = lines.get(lines.size()-1);
	uniques = new TreeSet<String>();
    }

    public int reduce() {
	int steps = 0;

	ArrayList<Replacement> replLongestFirst = new ArrayList<Replacement>(repl);
	Collections.sort(replLongestFirst);
	IO.print(replLongestFirst.toString());
	String cur = target;
	//while (cur != "e") {

	for (int j=0; j<5; j++) {
	    boolean match = false;
	    for (Replacement r : replLongestFirst) {
		
		IO.print("target = " + cur + " | replacement: " + r);
		for (int i=0; i<cur.length(); i++) {
		    if (i+r.out.length() <= cur.length()) {
			String subS = cur.substring(i,i+r.out.length());
			IO.print("subS = " + subS);
			if (subS.equals(r.out)) {
			    //replace and add
			    IO.print("found a match");
			    String newS = cur.substring(0,i) + r.in + cur.substring(i+r.out.length(),cur.length());
			    cur = newS;
			    steps++;
			    match = true;
			}
		    }
		}
	    }
	    if (!match) {
		//dead end
		cur = target;
		IO.print("RESET");
		steps = 0;
	    }
	}
	return steps;
    }

    public ArrayList<String> replacements(Replacement r, String t) {
	ArrayList<String> res = new ArrayList<String>();
	for (int i=0; i<t.length(); i++) {
	    if (i+r.in.length() <= t.length()) {
		String cur = t.substring(i,i+r.in.length());
		if (cur.equals(r.in)) {
		    //replace and add
		    String newS = t.substring(0,i) + r.out + t.substring(i+r.in.length(),t.length());
		    res.add(newS);
		}
	    }
	}
	return res;
    }
    
    public static void main(String[] args) {
	Replacements rp = new Replacements(args[0]);
	for (Replacement r : rp.repl) {
	    rp.uniques.addAll(rp.replacements(r,rp.target));
	}
	IO.print("Part 1: " + rp.uniques.size());

	IO.print("Part 2: " + rp.reduce());
    }

}
