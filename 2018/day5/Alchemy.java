import util.*;
import java.util.*;

public class Alchemy {

    public Alchemy() {

    }
    
    public String react(String inputString) {
	String res = inputString;
	int oldlength = res.length()+2;
	while (res.length() != oldlength) {
	    oldlength = res.length();
	    for (int i=0; i<res.length()-1; i++) {
		Character a = res.charAt(i);
	        Character b = res.charAt(i+1);
		if ( ((Character.isLowerCase(a) && Character.isUpperCase(b)) || (Character.isUpperCase(a) && Character.isLowerCase(b))) && (Character.toLowerCase(a) == Character.toLowerCase(b))) {
		    String newS = "";
		    if (i+2 > res.length()) {
			newS = res.substring(0,i);
		    } else if (i==0) {
			newS = res.substring(i+2,res.length());
		    } else {
			newS = res.substring(0,i) + res.substring(i+2,res.length());
		    }
		    res = newS;
		    break;
		}

	    }
	}
	return res;
    }

    public String remove(String in, Character c) {
	String res = in;
	int i=0;
	while (i<res.length()) {
	    Character cur = (Character) res.charAt(i);
	    if ( (cur==c) || (cur==Character.toUpperCase(c)) ) {
		res = res.substring(0,i) + res.substring(i+1,res.length());
	    } else {
		i++;
	    }
	}
	return res;
    }
    
    public static void main(String[] args) {
	String input = IO.readFile(args[0]).get(0);
	
	Alchemy a = new Alchemy();
	IO.print("Part 1: " + a.react(input).length());

	//Part 2
	input = IO.readFile(args[0]).get(0);

	TreeSet<Character> units = new TreeSet<Character>();
	for (Character c : input.toCharArray()) {
	    units.add(Character.toLowerCase(c));
	}
	int minLength = input.length();
	
	for (Character u : units) {
	    String cur = a.remove(input,u);
	    String res = a.react(cur);
	    if (res.length() < minLength) {
		minLength = res.length();
	    }
	}
	IO.print("Part 2: " + minLength);
    }


}
