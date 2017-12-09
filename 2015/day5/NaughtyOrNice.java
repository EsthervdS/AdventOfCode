import util.*;
import java.util.*;

public class NaughtyOrNice {

    ArrayList<String> lines;
    
    public NaughtyOrNice(String fileName) {
	lines = IO.readFile(fileName);
    }

    public int howManyAreNice() {
	int nice = 0;
	for(int i=0; i<lines.size(); i++) {
	    boolean vowels = false, dbl = false, allowed = false;
	    vowels = threeVowels(lines.get(i));
	    dbl = doubleLetters(lines.get(i));
	    allowed = onlyAllowedSubstrings(lines.get(i));
	    if (vowels && dbl && allowed) {
		nice++;
	    }
	    
	}
	return nice;
    }

    private boolean threeVowels(String str) {
	char[] cs = str.toCharArray();
	int v = 0;
	for(int i=0; i<str.length(); i++) {
	    if ((cs[i] == 'a') || (cs[i] == 'e') || (cs[i] == 'i') || (cs[i] == 'o') || (cs[i] == 'u')) {
		v++;
	    }
	}
	return (v >= 3);
    }

    private boolean doubleLetters(String str) {
	char[] cs = str.toCharArray();
	boolean dbl = false;
	for (int i=0; i<str.length()-1; i++) {
	    if (cs[i]==cs[i+1]) dbl=true;
	}
	return dbl;
    }

    private boolean onlyAllowedSubstrings(String str) {
	boolean all = true;
	for (int i=0; i<str.length()-1; i++) {
	    String check = str.substring(i,i+2);
	    if (check.equals("ab") || check.equals("cd") || check.equals("pq") || check.equals("xy")) {
		all = false;
	    }
	}
	return all;
    }
    
    public static void main(String[] args) {
	NaughtyOrNice non = new NaughtyOrNice(args[0]);
	IO.print("Part 1: " + non.howManyAreNice());
    }
    
}
