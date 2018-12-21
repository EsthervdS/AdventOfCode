import java.util.*;
import util.*;

public class Instructions {
    ArrayList<String> lines;
    long[] registers;
    int nregs;
    int ip,part1,part2;
    String[] instructions;
    Operations o;
    int count;
    int bound;
    HashSet<Long> r4values;
    long prevr4;
    
    public Instructions(String filename, int n, int r0) {
	nregs = n;
	part1 = -1;
	lines = IO.readFile(filename);
	init(r0);
	r4values = new HashSet<Long>();
	boolean debug = false;
	
	while (true) {
	    registers[bound] = ip;
	    
	    if (ip >= instructions.length) {
		IO.print("Program halted for r0 = " + r0 + " after " + count + " instructions executed");		
		break;
	    }

	    count++;
	    String[] curInst = instructions[ip].split(" ");
	    String op = curInst[0];
	    long a = Long.parseLong(curInst[1]);
	    long b = Long.parseLong(curInst[2]);
	    long c = Long.parseLong(curInst[3]);
	    
	    if (debug) {
		System.out.print("ip="+ip + " [");
		for (int i=0;i<nregs;i++) {
		    System.out.print(registers[i]);
		    if (i<(nregs-1)) System.out.print("\t ");
		}
		System.out.print("]  -- " + instructions[ip] + " -- [" );
	    }
	    
	    registers = o.perform(registers,op,a,b,c);
	    ip = (int) registers[bound];
	    if (ip == 29) {
		if (part1 == -1) {
		    part1 = (int) registers[4];
		    IO.print("Part1: " + part1);
		}
		int prevSize = r4values.size();
		r4values.add(registers[4]);
		if (r4values.size() == prevSize) {
		    //repetition found
		    IO.print("Part 2: " + prevr4);
		} else {
		    prevr4 = registers[4];
		}
		
		/*
		for (int i=0;i<nregs;i++) {
		    System.out.print(registers[i]);
		    if (i<(nregs-1)) System.out.print("\t ");
		}
		System.out.print("]"+"\n");
		*/
	    }
	    ip++;
	    if (ip > instructions.length) {
		IO.print("Program halted for r0 = " + r0 + " after " + count + " instructions executed");
		break;
	    }

	}
    }

    public void init(int r0) {
	//IO.print("Trying for r[0] = " + r0);
	registers = new long[nregs];
	for (int it=0; it<nregs; it++) {
	    registers[it] = 0;
	}
	registers[0] = r0;
	count = 0;
	int i=0;
        o = new Operations(nregs);
	bound = Integer.parseInt(lines.get(i).substring(4,lines.get(i).length()));
	//IO.print("ip = " + bound);

	instructions = new String[lines.size()-1];
	while (i<lines.size()-1) {
	    instructions[i] = lines.get(i+1);
	    //IO.print("inst " + i + " = " + instructions[i]);
	    i++;
	}
    }

    public static void main(String[] args) {
	Instructions i;
	i = new Instructions(args[0],6,0);
    }
}
