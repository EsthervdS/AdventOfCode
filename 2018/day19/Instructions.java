import java.util.*;
import util.*;

public class Instructions {
    ArrayList<String> lines;
    long[] registers;
    int nregs;
    int ip;
    String[] instructions;
    Operations o;
    int count;
    int bound;
    
    public Instructions(String filename, int n) {
	nregs = n;
	lines = IO.readFile(filename);
	init();
	boolean debug = true;
	
	while (true) {
	    registers[bound] = ip;
	    
	    if (ip >= instructions.length) break;
	    if (debug && count > 200) break;
	    count++;
	    if (debug) {
		System.out.print("ip="+ip + " [");
		for (int i=0;i<nregs;i++) {
		    System.out.print(registers[i]);
		    if (i<(nregs-1)) System.out.print(", ");
		}
		System.out.print("]  -- " + instructions[ip] + " -- [" );
	    }
	    String[] curInst = instructions[ip].split(" ");
	    String op = curInst[0];
	    long a = Long.parseLong(curInst[1]);
	    long b = Long.parseLong(curInst[2]);
	    long c = Long.parseLong(curInst[3]);
	    
	    registers = o.perform(registers,op,a,b,c);
	    ip = (int) registers[bound];
	    if (debug) {
		for (int i=0;i<nregs;i++) {
		    System.out.print(registers[i]);
		    if (i<(nregs-1)) System.out.print(", ");
		}
		System.out.print("]"+"\n");
	    }
	    ip++;
	    if (ip > instructions.length) break;
	}
    }

    public void init() {
	registers = new long[nregs];
	for (int it=0; it<nregs; it++) {
	    registers[it] = 0;
	}
	registers[0] = 1;
	count = 0;
	int i=0;
        o = new Operations(nregs);
	bound = Integer.parseInt(lines.get(i).substring(4,lines.get(i).length()));
	IO.print("ip = " + bound);

	instructions = new String[lines.size()-1];
	while (i<lines.size()-1) {
	    instructions[i] = lines.get(i+1);
	    //IO.print("inst " + i + " = " + instructions[i]);
	    i++;
	}
    }

    public static void main(String[] args) {
	Instructions i = new Instructions(args[0],6);
	IO.print("Part 1: " + i.registers[0]);
    }
}
