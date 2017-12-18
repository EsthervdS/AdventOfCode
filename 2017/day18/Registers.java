import util.*;
import java.util.*;

public class Registers {

    ArrayList<String> instructions;
    HashMap<String,Long> registers;
    int curr;
    boolean done;
    long lastFreq;
    
    public Registers(String fileName) {
	instructions = IO.readFile(fileName);
	registers = new HashMap<String,Long>();
	for (int i=0; i<26; ++i) {
	    registers.put(""+((char) ('a'+i)),0L);
	}
	curr=0;
	done = false;
	lastFreq = -1;
    }

    public void process() {
	
	while (!done) {
	    String[] line = instructions.get(curr).split(" ");
	    String thisInst = line[0].trim();
	    //snd rcv 1 par, set add mul mod jgz 2 pars
	    String reg = line[1].trim();
	    String valStr;
	    long val = -1;
	    if (!thisInst.equals("snd") && !thisInst.equals("rcv")) {
		//either int or reg follows
		valStr = line[2].trim();
		if (Util.isInteger(valStr)) {
		    val = Long.parseLong(valStr);
		} else {
		    val = registers.get(valStr);
		}		
	    }
	    switch(thisInst) {
	    case "snd" : {
		sound(registers.get(reg));
		curr++;
		break;
	    }
	    case "set" : {
		set(reg,val);
		curr++;
		break;
	    }
	    case "add" : {
		add(reg,val);
		curr++;
		break;
	    }
	    case "mul" : {
		mul(reg,val);
		curr++;
		break;
	    }
	    case "mod" : {
		mod(reg,val);
		curr++;
		break;
	    }
	    case "rcv" : {
		if (registers.get(reg) != 0) {
		    recover();
		} else {
		    curr++;
		}
		break;
	    }
	    case "jgz" : {
		if (registers.get(reg) > 0) {
		    jump(val);
		} else {
		    curr++;
		}
		break;
	    }
	    default: break;
	    }
	}
    }


    public void set(String reg, long i) {
	registers.remove(reg);
	registers.put(reg, i);
    }

    public void add(String reg, long i) {
	long temp = registers.get(reg);
	temp += i;
	registers.remove(reg);
	registers.put(reg,temp);
    }

    public void mul(String reg, long i) {
	long temp = registers.get(reg);
	temp *= i;
	registers.remove(reg);
	registers.put(reg,temp);
    }

    public void mod(String reg, long i) {
	long temp = registers.get(reg);
	temp = temp % i;
	registers.remove(reg);
	registers.put(reg,temp);
    }

    public void recover() {
	IO.print("Part 1: " + lastFreq);
	done = true;
    }
    
    public void sound(long i) {
	lastFreq = i;
    }

    public void jump(long i) {
	if ( (curr + i >= instructions.size()) || (curr+i < 0) ) {
	    done = true;
	} else {
	    curr += i;
	}
    }

    
    
    public static void main(String[] args) {
	Registers regs = new Registers(args[0]);
	regs.process();

	Program p0 = new Program(args[0], 0, null);
	Program p1 = new Program(args[0], 1, p0);
	p0.setOther(p1);
	while(true) {

	    if (p0.process() && p1.process()) break;
	    if (p0.done && p1.done) break;

	    while (!p0.done && !p0.wait) {
		p0.process();
	    }

	    while (!p1.done && !p1.wait) {
		p1.process();
	    }
	}
	
	IO.print("Part 2: " + p1.nSend);
    }
}
