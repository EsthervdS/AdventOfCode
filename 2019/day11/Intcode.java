import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Intcode {

    public static final int RUNNING = 0;
    public static final int HALTED = 1;
    public static final int OUTPUTWAIT = 2;
    
    public HashMap<Long,Long> codes;
    public long curpos,relbase;
    public long input,output; 
    public int state;
    public String id;

    
    public Intcode(ArrayList<Long> c, long inp) {
	long it = 0;
        codes = new HashMap<Long,Long>();
	for (Long i : c) {
	    codes.put(it,i);
	    it++;
	}
	input = inp;
	curpos = relbase = 0;
	state = RUNNING;
    }

    public long getCode(long pos) {
	if (pos < 0) {
	    IO.print("ERROR: negative index!");
	    return -1*Long.MAX_VALUE;
	}
	if (codes.get(pos) == null) {
	    return 0;
	} else {
	    return codes.get(pos);
	}
    }

    public void setCode(long pos, long l) {
	codes.put(pos,l);
    }

    public long getLiteral(long parmode, long pos) {
	if (parmode <= 1) {
	    //position mode (or immediate, should not occur)
	    return getCode(pos);
	} else {
	    //relative mode
	    return relbase + getCode(pos);
	}
    }

    public long getInterpreted(long parmode, long pos) {
	if (parmode == 0) {
	    //position mode
	    return getCode(getCode(pos));
	} else if (parmode == 1) {
	    //immediate mode
	    return getCode(pos);
	} else {
	    //relative mode
	    return getCode(relbase + getCode(pos));
	}
    }

    public void printState() {
	IO.print("ROBOT || state = " + state + " | input = " + input + " | output = " + output);
    }
    
    public void step() {
	long firstInstr = getCode(curpos);
	// tens and ones = opcode
	long opcode = firstInstr % 100;
	
	if (opcode == 99) {
	    state = HALTED;
	    return;
	}
	// parameter modes
	// hundreds is parmode 1
	// thousands is parmode 2
	// tenthousands is parmode 3
	long parmode1 = (firstInstr / (long) 100) % 10;
	long parmode2 = (firstInstr / (long) 1000) % 10;
	long parmode3 = (firstInstr / (long) 10000) % 10;
	long par1,par2,par3,res;
	par1 = par2 = par3 = res = -1;
	
	//opcodes (with * is written to)
	//1 addition par1 par2 *par3
	//2 multiplication par1 par2 *par3
	//3 input *par1
	//4 output par1
	//5 jump-if-true par1 par2
	//6 jump-if-false par1 par2
	//7 less-than par1 par2 *par3
	//8 equals par1 par2 *par3
	//9 modify relative base par1

	par1 = (opcode == 3) ? getLiteral(parmode1, curpos + 1) : getInterpreted(parmode1, curpos+1);
	
	if (opcode == 1 || opcode == 2 || ((opcode >= 5) && (opcode <= 8)) ) {	    
	    par2 = getInterpreted(parmode2, curpos+2);
	}
	
	if (opcode == 1 || opcode == 2 || opcode == 7 || opcode == 8) {
	    par3 = getLiteral(parmode3, curpos + 3);
	}
	

	switch((int) opcode) {
	case 1:
	    //addition
	    res = (par1 + par2);
	    setCode(par3, res);
	    curpos += 4;
	    break;
	case 2:
	    //multiplication
	    res = (par1 * par2);
	    setCode(par3, res);
	    curpos += 4;
	    break;
	case 3:
	    //input
	    setCode(par1,input); 
	    curpos += 2;
	    break;
	case 4:
	    //output
	    output = par1;
	    //IO.print("OUTPUT => " + output);
	    state = OUTPUTWAIT;
	    curpos += 2;
	    break;
	case 5:
	    //jump-if-true
	    if (par1 != 0) {
		curpos = par2;
	    } else {
		curpos += 3;
	    }
	    break;
	case 6:
	    //jump-if-false
	    if (par1 == 0) {
		curpos = par2;
	    } else {
		curpos += 3;
	    }
	    break;
	case 7:
	    //less than
	    res = (par1 < par2) ? 1 : 0;
	    setCode(par3,res);
	    curpos += 4;
	    break;
	case 8:
	    //equals
	    res = (par1 == par2) ? 1 : 0;
	    setCode(par3,res);
	    curpos += 4;
	    break;
	case 9:
	    //modify relative base
	    relbase += par1;
	    curpos += 2;
	    break;
	default:
	    IO.print("Wrong opcode?!");
	    break;
	}
    }

    public void runUntilOutput() {
	if (state == OUTPUTWAIT) {
	    state = RUNNING;
	}
	
	while (state != OUTPUTWAIT && state != HALTED) {
	    step();
	}
    }
 }
