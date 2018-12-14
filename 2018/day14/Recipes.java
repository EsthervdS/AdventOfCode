import java.util.*;
import util.*;

public class Recipes {

    public int limit;
    public String limitString;
    public int firstCur,secondCur;
    public int nodes;
    public ArrayList<Integer> reps;
    public boolean part2found;
    public int rounds;
    public int[] key;
    
    public Recipes(String n, int p) {
	limit = Integer.parseInt(n);
	limitString = n;

	key = new int[n.length()];
	for (int i=0; i<n.length(); i++) {
	    key[i] = Character.getNumericValue(n.charAt(i));
	}
	reps = new ArrayList<Integer>();
	reps.add(3);
	reps.add(7);
	firstCur = 0;
	secondCur = 1;
	part2found = false;
	if (p==1) part2found = true;	
    }

    public void display() {
	for(int t=0; t<reps.size(); t++) {
	    if (t==firstCur) {
		System.out.print("("+reps.get(t)+")");
	    } else if (t==secondCur) {
		System.out.print("["+reps.get(t)+"]");
	    } else {
		System.out.print(" "+reps.get(t)+" ");
	    }
	}
	IO.print("");
    }

    public void addRecipes() {
	int score = reps.get(firstCur) + reps.get(secondCur);
	if (score < 10) {
	    reps.add(score);
	    if (reps.size() > limitString.length()) {
		if (match()) {
		    part2found = true;
		}
	    }
	} else {
	    reps.add(score / 10);
	    if (reps.size() > limitString.length()) {
		if (match()) {
		    part2found = true;
		}
	    }
	    if (!part2found) {
		reps.add(score % 10);
		if (reps.size() > limitString.length()) {
		    if (match()) {
			part2found = true;
		    }
		}
	    }
	}
	firstCur = (firstCur+1+reps.get(firstCur)) % reps.size();
	secondCur = (secondCur+1+reps.get(secondCur)) % reps.size();
    }

    public boolean match() {
	boolean res = true;
	int offset = reps.size() - limitString.length();
	if (offset > 0) {
	    for (int i=0; i<limitString.length(); i++) {
		res = res && (key[i] == reps.get(offset+i));
	    }
	}
	return res;
    }
    
    public String part1() {
	int i = reps.size()-10;
	String res="";
	while (i<reps.size()) {
	    res += reps.get(i);
	    i++;
	}
	return res;
    }
    
    public static void main(String[] args) {
	Recipes r = new Recipes(args[0],1);	    
	while (r.reps.size() < (r.limit+10)) {
	    r.addRecipes();
	}
	IO.print("Part 1: " + r.part1());
	r = new Recipes(args[0],2);
	while (!r.part2found) {
	    r.addRecipes();
	}
	IO.print("Part 2: " + (r.reps.size() - r.limitString.length()));
    }
}
