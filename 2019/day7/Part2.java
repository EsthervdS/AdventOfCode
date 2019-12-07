import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Part2 {

    public ArrayList<String> lines;
    public ArrayList<Integer> codes;
    public int curScore, maxScore;
    
    public Part2(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	codes = new ArrayList<Integer>();
	String[] c = lines.get(0).split(",");
	for (int i=0; i<c.length; i++) {
	    codes.add(Integer.parseInt(c[i]));
	}
	curScore = 0;
	maxScore = -1*Integer.MAX_VALUE;
	//compute permutations of phases 5-9
	ArrayList<Integer> phases = new ArrayList<Integer>();
	for (int i=5; i<10; i++) phases.add(i);
	Permutations<Integer> obj = new Permutations<Integer>();
	ArrayList<ArrayList<Integer>> permutations = obj.permute(phases);

	//for each permutation
	    //run program until Amplifier E halts
		//compute curScore and check if it is max

	Amplifier A,B,C,D,E;
	
	for (ArrayList<Integer> order : permutations) {
	    //create Amplifiers A - E with correct phases as first inputs

	    A = new Amplifier(codes,order.get(0),0,"A");
	    A.runUntilOutput();
	    B = new Amplifier(codes,order.get(1),A.output,"B");
	    B.runUntilOutput();
	    C = new Amplifier(codes,order.get(2),B.output,"C");
	    C.runUntilOutput();
	    D = new Amplifier(codes,order.get(3),C.output,"D");
	    D.runUntilOutput();
	    E = new Amplifier(codes,order.get(4),D.output,"E");
	    E.runUntilOutput();
	    A.input2 = E.output;
	    while (! (E.state == Amplifier.HALTED)) {
		A.runUntilOutput();
		B.input2 = A.output;
		B.runUntilOutput();
		C.input2 = B.output;
		C.runUntilOutput();
		D.input2 = C.output;
		D.runUntilOutput();
		E.input2 = D.output;
		E.runUntilOutput();
		A.input2 = E.output;
	    }
	    //run 
	    curScore = E.output;
	    if (curScore > maxScore) {
		maxScore = curScore;
	    }
	}

	IO.print("Part 2: " + maxScore);
    }


   
    public static void main(String[] args) {
        Part2 t = new Part2(args[0]);
    }
}
