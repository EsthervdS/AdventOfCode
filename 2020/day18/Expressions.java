import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Expressions {

    public ArrayList<String> lines;
    
    public Expressions(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	for (int i=0; i<lines.size(); i++) {
	    lines.set(i,lines.get(i).replaceAll("\\s+", ""));
	}
    }

    public long part1() {
	long res = 0;
	for (String line : lines) {
	    String temp = line;
	    String oldTemp = removeSimpleParens(line);
	    while (!temp.equals(oldTemp)) {
		oldTemp = temp;
		temp = removeSimpleParens(temp);
	    }
	    long term = evaluateWithoutParentheses(temp);
	    res += term;
	}
	return res;
    }

    public long evaluateWithoutParentheses(String l) {
	if (! (l.contains("*") || l.contains("+") ) ) {
	    return Long.parseLong(l);
	} else {
	    int i = 0;
	    if (l.charAt(i)=='-') i++;
	    while (Character.isDigit(l.charAt(i))) i++;
	    long f = Long.parseLong(l.substring(0,i));
	    int j = i+1;
	    if (l.charAt(j)=='-') j++;
	    while ((j<l.length()) && Character.isDigit(l.charAt(j))) j++;		
	    long s = Long.parseLong(l.substring(i+1,j));
	    
	    String t = "";
	    if (l.charAt(i) == '*') {
		t = (f*s)+"";
	    } else {
		t = (f+s)+"";
	    }
	    return evaluateWithoutParentheses( t + l.substring(j) );
	}
    }
    
    public String removeSimpleParens(String l) {
	int index = l.indexOf('(');
	if (index == -1) {
	    return l;
	} else {
	    boolean found = false;
	    int oldIndex = index;
	    String sub = "";
	    while (!found) {
		while (l.charAt(index)!=')') {
		    if (l.charAt(index) == '(') {
			//found nested parens
			//skip to next (
			oldIndex = index;
			index++;
		    } else {
			index++;
		    }
		}
		if (l.charAt(index)==')') {
		    sub = l.substring(oldIndex+1,index);
		    found = true;
		} else {
		    index++;
		}
		if (index>=l.length()) break;

	    }
	    if (found) {
		return l.substring(0,oldIndex) + evaluateWithoutParentheses(sub) + l.substring(index+1);
	    } else {
		return l;
	    }
	}
    }

    public long part2() {
	return -1;
    }

    
    public static void main(String[] args) {
	Expressions e = new Expressions(args[0]);
	IO.print("Part 1: " + e.part1());
	IO.print("Part 2: " + e.part2());
    }
}
