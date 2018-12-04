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
	/*
	for(String u : rp.uniques) {
	    IO.print(u);
	}
	*/
    }

}
