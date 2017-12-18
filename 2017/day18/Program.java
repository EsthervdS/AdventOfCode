import java.util.*;
import util.*;

public class Program {

    Program other;
    HashMap<String,Long> registers;
    ArrayList<String> instructions;
    int curr, id;    
    boolean wait;
    int nSend;
    LinkedList<Long> queue;
    String waitingReg;
    boolean done;

    public Program(String fileName, int ident, Program p) {
	id = ident;
	instructions = IO.readFile(fileName);
	registers = new HashMap<String,Long>();
	for (int i=0; i<26; ++i) {
	    if (i==15) { //p
		registers.put("p",(long) id);
	    } else {
		registers.put(""+((char) ('a'+i)),0L);
	    }
	}
	curr=0;
	done = false; //done is true when jumping out of the instructions or when a deadlock occurs
	nSend = 0;
	queue = new LinkedList<Long>();
	other = p;
	wait = false;
	done = false;
	waitingReg = "";
    }

    public void setOther(Program o) {
	other = o;
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

    public void receive(String reg) {
	if (queue.size()==0) {
	    wait = true;
	    waitingReg = reg;
	} else {
	    registers.put(reg,queue.poll());
	    wait = false;
	}
    }
    
    public void send(long i) {
	//other program sent i to me
	queue.add(i);
    }

    public void jump(long i) {
	if ( (curr + i >= instructions.size()) || (curr+i < 0) ) {
	    done = true;
	} else {
	    curr += i;
	}
    }

    public boolean process() {

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
	    if (!wait) {
		if (Util.isInteger(reg)) {
		    val = Long.parseLong(reg);
		} else {
		    val = registers.get(reg);
		}
		other.send(val);
		nSend++;
		curr++;
	    }
	    break;
	}
	case "set" : {
	    if (!wait) {
		set(reg,val);
		curr++;
	    }
	    break;
	}
	case "add" : {
	    if (!wait) {
		add(reg,val);
		curr++;
	    }
	    break;
	}
	case "mul" : {
	    if (!wait) {
		mul(reg,val);
		curr++;
	    }
	    break;
	}
	case "mod" : {
	    if (!wait) {
		mod(reg,val);
		curr++;
	    }
	    break;
	}
	case "rcv" : {
	    receive(reg);
	    if (!wait) curr++;
	    break;
	}
	case "jgz" : {
	    if (!wait) {
		long check = 0L;
		if (Util.isInteger(reg)) {
		    check = Long.parseLong(reg);
		} else {
		    check = registers.get(reg);
		}
		if (check>0) {
		    jump(val);
		} else {
		    curr++;
		}
	    }
	    break;
	}
	default: break;
	}
	return wait;
    }
}
