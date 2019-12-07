import java.io.*;
import java.util.*;
import util.*;
import java.time.*;


public class Amplifier {

    //0: running, 1: waiting for input, 2: outputting, 3: halted
    public static final int RUNNING = 0;
    public static final int OUTPUTWAIT = 1;
    public static final int HALTED = 2;
    
    public ArrayList<String> lines;
    public ArrayList<Integer> codes;
    public int curpos;
    public int input1,input2,output; //input1 is phase, input2 is signal
    public boolean firstInput;
    public int state;
    public String id;

    
    public Amplifier(ArrayList<Integer> c, int inp1, int inp2, String s) {

	firstInput = true;
	//deep copy of codes
        codes = new ArrayList<Integer>();
	for (Integer i : c) {
	    codes.add(i);
	}
	input1 = inp1;
	input2 = inp2;
	id = s;
	curpos = 0;
	state = RUNNING;
    }

    public void step() {

	int firstInstr = codes.get(curpos);
	// tens and ones = opcode
	int opcode = firstInstr % 100;
	if (opcode == 99) {
	    state = HALTED;
	    return;
	}
	// parameter modes
	// hundreds is parmode 1
	// thousands is parmode 2
	// tenthousands is parmode 3
	int parmode1 = (firstInstr / 100) % 10;
	int parmode2 = (firstInstr / 1000) % 10;
	int parmode3 = (firstInstr / 10000) % 10;
	int par1,par2,mem;
	par1 = par2 = mem = -1;
	
	if (opcode == 1 || opcode == 2 || opcode >= 5) {
	    par1 = (parmode1==0) ? codes.get(codes.get(curpos + 1)) : codes.get(curpos+1);
	    par2 = (parmode2==0) ? codes.get(codes.get(curpos + 2)) : codes.get(curpos+2);
	}
	
	if (opcode == 1 || opcode == 2) {
	    //addition or multiplication

	    //Parameters that an instruction writes to will never be in immediate mode.
	    mem = codes.get(curpos + 3);
	    if (parmode3>0) IO.print("ERROR");

	    //perform instruction and set memory to result
	    int res = (opcode == 1) ? (par1 + par2) : (par1 * par2);
	    codes.set(mem, res);
	    curpos += 4;
	    
	} else if (opcode == 3) {
	    //ask input and write to first par

	    par1 = codes.get(curpos + 1);
	    if (firstInput) {
		codes.set(par1,input1);		    
		firstInput = false;
	    } else {
		codes.set(par1,input2);		    
	    }
	    curpos += 2;

	    
	} else if (opcode == 4) {
	    int res = (parmode1 == 0) ? codes.get(codes.get(curpos + 1)) : codes.get(curpos + 1);
	    output = res;
	    curpos += 2;
	    state = OUTPUTWAIT;
	    
	} else if (opcode == 5) {
	    //jump-if-true
	    if (par1 != 0) {
		curpos = par2;
	    } else {
		curpos += 3;
	    }
	} else if (opcode == 6) {
	    //jump-if-false
	    if (par1 == 0) {
		curpos = par2;
	    } else {
		curpos += 3;
	    }

	} else if (opcode == 7) {
	    //less than
	    //Parameters that an instruction writes to will never be in immediate mode.
	    mem = codes.get(curpos + 3);
	    int res = (par1 < par2) ? 1 : 0;
	
	    codes.set(mem,res);
	    curpos += 4;
	    
	} else if (opcode == 8) {
	    //equals
	    //Parameters that an instruction writes to will never be in immediate mode.
	    mem = codes.get(curpos + 3);
	    int res = (par1 == par2) ? 1 : 0;
	
	    codes.set(mem,res);
	    curpos += 4;
		
	} else {
	    IO.print("Wrong opcode?!");
	}
    }

    public void runUntilOutput() {
	//run this amp until output is given or amp is halted
	state = RUNNING;
	while(state == RUNNING) {
	    step();
	}
    }
    
}
