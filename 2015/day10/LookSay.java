import util.*;
import java.util.*;

public class LookSay {
    

    public static void process(String start) {
	String res = ""; // current looksay number in sequence
	String prev = start; // previous looksay number in sequence
	
	
	for(int it=0;it<40;++it) {
	    //compute next looksay number in sequence
	    String temp = "";
	    int i = 0;
	    while (i<prev.length()) {
		//iterate over all chars in previous number
		char num = prev.charAt(i);
		int nChar = 1;
		while ( (i<(prev.length()-1)) && (num == prev.charAt(i+1)) ) {
		    //iterate to find subsequence of same digit
		    nChar++;
		    i++;
		}
		temp = temp + nChar + "" + num + "";
		i++;
	    }
	    prev = temp;
	    res = temp;
	}
	IO.print("Part 1: " + res.length());
    }
    
    public static void main(String[] args) {
	String startSeq = "1113122113";
	process(startSeq);
    }
}
