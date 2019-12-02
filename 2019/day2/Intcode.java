import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Intcode {

    public ArrayList<String> lines;
    public ArrayList<Integer> codes;
    public int curpos,noun,verb;

    public Intcode(String fileName, int n, int v) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	noun = n;
	verb = v;
    }

    public void init() {
	curpos = 0;
	codes = new ArrayList<Integer>();
	String[] c = lines.get(0).split(",");
	for (int i=0; i<c.length; i++) {
	    codes.add( Integer.parseInt(c[i]));
	}
	codes.set(1,noun);
	codes.set(2,verb);	
    }

    public void step() {
	int instruction = codes.get(curpos);
	int op1 = codes.get(codes.get(curpos + 1));
	int op2 = codes.get(codes.get(curpos + 2));
	int mem = codes.get(curpos + 3);
	if (instruction == 1) {
	    codes.set(mem, op1 + op2);
	} else if (instruction == 2) {
	    codes.set(mem, op1 * op2);
	} else {
	    IO.print("Wrong opcode?!");
	}
	curpos = curpos + 4;
    }

    public void runProgram() {
	while (	codes.get(curpos) != 99 ) {
	    step();
	}
    }
    
    public void part1() {
	init();
	runProgram();
	IO.print("Part 1: " + codes.get(0));
    }

    public void part2() {
	for (int v=99; v>=0; v--) {
	    for (int n=99; n>=0; n--) {
		noun = n;
		verb = v;
		init();
		runProgram();
		if (codes.get(0) == 19690720) {
		    IO.print("Part 2: " + (100*n + v));
		    break;
		}
	    }
	    if (codes.get(0) == 19690720) {
		break;
	    }
	}
    }
    
    public static void main(String[] args) {
	Intcode t = new Intcode(args[0],12,2);
	t.part1();
	t.part2();
    }
}
