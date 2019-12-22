import java.util.*;
import util.*;

public class Pair implements Comparable<Pair> {

    public Character first, second;
    public int distance;
    public ArrayList<Character> inBetween;
    public HashSet<Character> withKeys;
    
    public Pair(char f, char s) {
	first = f;
	second = s;
	distance = -1;
	inBetween = new ArrayList<Character>();
	withKeys = new HashSet<Character>();
    }

    public Pair(Pair p) {
	first = p.first;
	second = p.second;
	distance = p.distance;
	inBetween = new ArrayList<Character>();
	for (Character c : p.inBetween) {
	    inBetween.add(c);
	}
	withKeys = new HashSet<Character>();
	for (Character c : p.withKeys) {
	    withKeys.add(c);
	}
    }
    
    public boolean isSame(Pair p) {
	if (first == p.first && second == p.second) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean isReverse(Pair p) {
	if (first == p.second && second == p.first) {
	    return true;
	} else {
	    return false;
	}
    }

    public void reverseInBetween() {
	ArrayList<Character> temp = new ArrayList<Character>();
	for (Character c : inBetween) {
	    temp.add(0,c);
	}
	inBetween = temp;
    }

    public ArrayList<Character> keysInBetween() {
	ArrayList<Character> temp = new ArrayList<Character>();
	for (Character c : inBetween) {
	    if (Character.isLowerCase(c)) {
		temp.add(c);
	    }
	}
	return temp;
    }
    
    public boolean isFeasible() {
	boolean res = true;
	ArrayList<Character> iBCopy = new ArrayList<Character>();
	for (Character c : inBetween) {
	    iBCopy.add(c);
	}
	for (char k : withKeys) {
	    iBCopy.remove((Character) Character.toUpperCase(k));
	}
	while (iBCopy.size() > 0) {
	    char c = iBCopy.remove(0);
	    if (Doors.isKey(c)) {
		iBCopy.remove((Character) Character.toUpperCase(c));
	    } else if (Doors.isDoor(c)) {
		//unopened door!
		res = false;
		break;
	    }
	}
	return res;
    }

    public void addKeys(HashSet<Character> keys) {
	withKeys.addAll(keys);
    }
    
    public String toString() {
	return "("+first+"-"+second+"): " + distance + " inBetween: " + inBetween.toString();
    }

    public int compareTo(Pair p) {
	if (distance < p.distance) {
	    return 1;
	} else {
	    return (distance == p.distance) ? 0 : -1;
	}
    }
    
}
