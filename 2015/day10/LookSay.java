import util.*;
import java.util.*;

public class LookSay {
    

    public static String process(String start) {
	StringBuilder res = new StringBuilder(); // current looksay number in sequence

	for (int i=0; i<start.length(); ++i) {
	    //iterate over all chars in previous number
	    char num = start.charAt(i);
	    int nChar = 1;
	    while ( (i+1<start.length()) && (num == start.charAt(i+1)) ) {
		//iterate to find subsequence of same digit
		++nChar;
		++i;
	    }
	    res.append(nChar);
	    res.append(num);
	}
	return res.toString();

    }
    
    public static void main(String[] args) {
	String next = "1113122113";

	for (int i=0; i<50; ++i) {
	    next = process(next);
	    if (i == 39) IO.print("Part 1: " + next.length());
	}
	IO.print("Part 2: " + next.length());    }
}
