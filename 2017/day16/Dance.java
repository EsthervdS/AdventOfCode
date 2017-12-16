import util.*;
import java.util.*;

public class Dance {

    String instructions;
    int[] programs;
    int nPrograms;
    HashMap<String,String> repetitions;
    int cycle;

    public Dance(String fileName, int n) {
	instructions = (IO.readFile(fileName)).get(0);
	
	programs = new int[n];
	for (int i=0; i<n; i++) programs[i]=i;
	nPrograms = n;
	cycle = -1;
	repetitions = new HashMap<String,String>();
    }

    
    /* helpers */
    private int pIndex(char c) {
	//translation of program char to array index
	return c - 'a';
    }
    private int index(int p) {
	//return index in array at which p resides
	int i=0;
	while (programs[i]!=p) i++;
	return i;
    }
    private char toChar(int p) {
	return (char) ('a'+p);
    }

    private void spin(int k) {
	int[] temp = new int[k];
	for (int i=0;i<k;i++) {
	    temp[i] = programs[nPrograms-k+i];
	}
	shiftPrograms(k);
	for (int i=0;i<k;i++) {
	    programs[i] = temp[i];
	}
    }

    private void shiftPrograms(int k) {
	for (int i=(nPrograms-1);i>=k;i--) {
	    programs[i] = programs[i-k];
	}
    }
    
    private void exchange(int i, int j) {
	int temp = programs[i];
	programs[i] = programs[j];
	programs[j] = temp;
    }

    private void partner(int p, int q) {
	int pi = index(p); int qi = index(q);
	int temp = programs[pi];
	programs[pi] = programs[qi];
	programs[qi] = temp;
    }

    private int val(char c) {
	return (c-'0');
    }


    private void copySeq(String str) {
	for(int i=0; i<nPrograms; i++) {
	    programs[i] = pIndex(str.charAt(i));
	}

    }
    
    public void process(int ci) {
	if (repetitions.containsKey(toString())) {
		copySeq(repetitions.get(toString()));
		cycle = ci;
	} else {
	    String old = toString();
	    //process 1 dance
	    String[] inst = instructions.split(",");
	    for (int i=0; i<inst.length; i++) {
		String thisInst = inst[i];
		char c = thisInst.charAt(0);
		if (c == 's') {
		    //spin
		    int j = 1;
		    int k = 0;
		    char ch = thisInst.charAt(j);
		    while (Character.isDigit(ch) && j<thisInst.length()) {
			k = k*10 + val(ch);
			if (++j == thisInst.length()) break;
			ch = thisInst.charAt(j);
		    }
		    spin(k);
		} else if (c == 'x') {
		    //exchange
		    int j=1;
		    int i1=0;
		    char ch = thisInst.charAt(j);
		    while (Character.isDigit(ch) && j<thisInst.length()) {
			i1 = i1*10 + val(ch);
			if (++j == thisInst.length()) break;
			ch = thisInst.charAt(j);
		    }
		    j++; //skip '/'
		    int i2=0;
		    ch = thisInst.charAt(j);
		    while (Character.isDigit(ch) && j<thisInst.length()) {
			i2 = i2*10 + val(ch);
			if (++j == thisInst.length()) break;
			ch = thisInst.charAt(j);
		    }		
		    exchange(i1,i2);
		} else if (c == 'p') {
		    //partner
		    char p1 = thisInst.charAt(1);
		    char p2 = thisInst.charAt(3);
		    partner(pIndex(p1),pIndex(p2));
		} 
	    }
	    repetitions.put(old , toString());
	}
    }

    public void reset() {
	for (int i=0; i<nPrograms; i++) programs[i] = i;
    }
    
    public void oneBillionDances() {
	//find cycle length 60
	for (int i=0; i<1000000000; i++) {
	    process(i);
	    if (i==0) IO.print("Part 1: " + toString());
	    if (cycle != -1) {
		break;
	    }
	}
	reset();
	int nIts = (1000000000 % cycle);
	for (int i=0; i<nIts; i++) {
	    process(i);
	}
	
	IO.print("Part 2: " + toString());

    }
    
    public String toString() {
	String s = "";
	for(int i=0; i<nPrograms; i++) s += toChar(programs[i]);
	return s;
    }
    
    public static void main(String[] args) {
	Dance d = new Dance(args[0],16);
	d.oneBillionDances();
    }
}
