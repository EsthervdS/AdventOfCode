import util.*;
import java.util.*;

public class Passwords {

    ArrayList<String> lines;
    String currPW;
    
    
    public Passwords(String first) {
	currPW = first;
    }

    public String next(String str) {
	//increase least significant
	//if this is already z, turn to a and increase next char to the left
	String res = "";
	if (str.equals("")) {
	    res = "a";
	} else {
	    char c = str.charAt(str.length()-1);
	    if (c=='z') {
		res = next(str.substring(0,str.length()-1)) + "a";
	    } else {
		res = str.substring(0,str.length()-1) + (char) (c+1);
	    }
	}
	return res;
    }


    private boolean straight(String str) {
	//one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz
	boolean valid = false;
	int i=0;
	char c1,c2,c3;
	c1 = str.charAt(i);
	c2 = str.charAt(i+1);
	c3 = str.charAt(i+2);
	while (i< (str.length()-2) ) {
	    if ( (c3 == (char) (c2+1)) && (c2 == (char) (c1+1)) ) {
		valid = true;
		break;
	    }
	    c1 = c2;
	    c2 = c3;
	    c3 = str.charAt(i+2);
	    i++;
	}
	return valid;
    }

    private boolean letters(String str) {
	for (int i=0; i<str.length(); ++i) {
	    char c = str.charAt(i);
	    if ( (c == 'i') || (c == 'o') || (c == 'l') ) return false;
	}
	return true;
    }

    private boolean doublepair(String str) {
	int i=0;
	char c = '!';
	boolean valid = false;
	while(i < str.length()-1 ) {
	    char c1 = str.charAt(i);
	    char c2 = str.charAt(i+1);
	    if (c1 == c2) {
		if (c != '!') {
		    valid = true;
		    break;
		} else {
		    c = c1;
		}
		i += 2;
	    } else {
		i++;
	    }
	}
	return valid;
    }

    private boolean allThree(String str) {
	return ( straight(str) && letters(str) && doublepair(str) );
    }
    
    private String nextValid() {
	String next = next(currPW);
	while (!allThree(next)) {
	    currPW = next;
	    next = next(currPW);
	}
	return next;
    }
    
    public static void main(String[] args) {
	String input = "cqjxjnds";//"ghijklmn";//"abcdefgh";//
	IO.print(input);
	Passwords pw = new Passwords(input);
	pw.currPW = pw.nextValid();
	IO.print(pw.currPW);
	IO.print(pw.nextValid());
    }
}
