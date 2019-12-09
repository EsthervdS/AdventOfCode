import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
//ADD: long integer support
//ADD: out of bounds code list

public class Intcode {

    //0: running, 1: waiting for input, 2: outputting, 3: halted
    public static final int RUNNING = 0;
    public static final int OUTPUTWAIT = 1;
    public static final int HALTED = 2;
    
    public HashMap<Long,Long> codes;
    public long curpos,relbase;
    public long input,output; 
    public int state;
    public String id;

    
    public Intcode(ArrayList<Long> c, long inp) {

	//deep copy of codes
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
    
    public void step() {

	//IO.print("-----step----");
	//IO.print("pos = " + curpos);
	//IO.print(codes.toString());

	long firstInstr = getCode(curpos);
	// tens and ones = opcode
	long opcode = firstInstr % 100;
	//IO.print("opcode = " + opcode);
	
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
	//IO.print("parmode1 = " + parmode1);
	//IO.print("parmode2 = " + parmode2);
	//IO.print("parmode3 = " + parmode3);
	
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

	if (opcode != 3) {
	    if (parmode1 == 0) {
		//position mode
		par1 = getCode(getCode(curpos + 1));
	    } else if (parmode1 == 1) {
		//immediate mode
		par1 = getCode(curpos+1);
	    } else {
		//parmode1 == 2 // relative mode
		//given a relative base of 50, a relative mode parameter of -7 refers to memory address 50 + -7 = 43.
		//The address a relative mode parameter refers to is itself plus the current relative base.
		par1 = getCode(relbase + getCode(curpos + 1));
	    }
	}
	
	if (opcode == 1 || opcode == 2 || ((opcode >= 5) && (opcode <= 8)) ) {	    
	    if (parmode2 == 0) {
		//position mode
		par2 = getCode(getCode(curpos + 2));
	    } else if (parmode2 == 1) {
		//immediate mode
		par2 = getCode(curpos + 2);
	    } else {
		//parmode2 == 2 // relative mode
		//given a relative base of 50, a relative mode parameter of -7 refers to memory address 50 + -7 = 43.
		//The address a relative mode parameter refers to is itself plus the current relative base.
		par2 = getCode(relbase + getCode(curpos + 2));
	    }
	}
	//IO.print("par1= " + par1);
	//IO.print("par2= " + par2);

	
	if (opcode == 1 || opcode == 2 || opcode == 7 || opcode == 8) {
	    if (parmode3 <= 1) {
		//position mode
		par3 = getCode(curpos + 3);
	    } else {
		//parmode1 == 2 // relative mode
		//given a relative base of 50, a relative mode parameter of -7 refers to memory address 50 + -7 = 43.
		//The address a relative mode parameter refers to is itself plus the current relative base.
		par3 = relbase + getCode(curpos + 3);
	    }
	}
	//IO.print("par3= " + par3);
	

	switch((int) opcode) {
	case 1:
	    res = (par1 + par2);
	    setCode(par3, res);
	    curpos += 4;
	    break;
	case 2:
	    res = (par1 * par2);
	    setCode(par3, res);
	    curpos += 4;
	    break;
	case 3:
	    if (parmode1 <= 1) {
		//position mode
		par1 = getCode(curpos + 1);
	    } else {
		//parmode1 == 2 // relative mode
		//given a relative base of 50, a relative mode parameter of -7 refers to memory address 50 + -7 = 43.
		//The address a relative mode parameter refers to is itself plus the current relative base.
		par1 = relbase + getCode(curpos + 1);
	    }
	    setCode(par1,input); 
	    curpos += 2;
	    break;
	case 4:
	    output = par1;
	    IO.print("OUTPUT => " + output);
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
	    res = (par1 < par2) ? 1 : 0;
	    setCode(par3,res);
	    curpos += 4;
	    break;
	case 8:
	    res = (par1 == par2) ? 1 : 0;
	    setCode(par3,res);
	    curpos += 4;
	    break;
	case 9:
	    relbase += par1;
	    curpos += 2;
	    break;
	default:
	    IO.print("Wrong opcode?!");
	    break;
	}
    }

 }
