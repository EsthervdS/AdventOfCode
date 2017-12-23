import util.*;
import java.util.*;

public class HCF {

    ArrayList<String> instructions;
    HashMap<String,Long> registers;
    int curr, answer, i;
    boolean done;
    
    public HCF(String fileName) {
	instructions = IO.readFile(fileName);
	registers = new HashMap<String,Long>();
	for (int i=0; i<8; ++i) {
	    registers.put(""+((char) ('a'+i)),0L);
	}
	curr=answer=0;
	done = false;
    }

    public void process1() {
	while (!done) {
	    String[] line = instructions.get(curr).split(" ");
	    String thisInst = line[0].trim();
	    //snd rcv 1 par, set add mul mod jgz 2 pars
	    String reg = line[1].trim();
	    String valStr, jumpString;
	    long val = -1;
	    long jumpVal = -1;
	    if (thisInst.equals("jnz")) {
		//either int or reg follows
		valStr = line[1].trim();
		if (Util.isInteger(valStr)) {
		    val = Long.parseLong(valStr);
		} else {
		    val = registers.get(valStr);
		}
		jumpString = line[2].trim();
		jumpVal = Long.parseLong(jumpString);		
	    } else {
		valStr = line[2].trim();
		//1st reg 2nd reg/num
		if (Util.isInteger(valStr)) {
		    val = Long.parseLong(valStr);
		} else {
		    val = registers.get(valStr);
		}
	    }

	    switch(thisInst) {
		//set sub mul jnz
	    case "set" : {
		registers.remove(reg);
		registers.put(reg, val);
		curr++;
		break;
	    }
	    case "sub" : {
		long temp = registers.get(reg);
		registers.remove(reg);
		registers.put(reg,temp-val);
		curr++;
		break;
	    }
	    case "mul" : {
		long temp = registers.get(reg);
		registers.remove(reg);
		registers.put(reg,temp*val);
		curr++;
		answer++;
		break;
	    }
	    case "jnz" : {
		if (val!=0) {
		    curr += jumpVal;
		} else {
		    curr++;
		}
		break;
	    }
	    default: break;
	    }
	    if (curr<0 || curr>=instructions.size()) done = true;
	}
	IO.print("Part 1: " + answer);
    }

    public void resetA1() {
	registers.clear();
	registers.put("a",1L);
	for (int i=1; i<8; ++i) {
	    registers.put(""+((char) ('a'+i)),0L);
	}
    }

    public void process2() {
	int a,b,c,d,e,f,g,h;
	a=1;
	b=c=d=e=f=g=h=0;
	
	b= 84;
	c= b;
	if(a!=0){
	    b = b*100 + 100000;
	    c = b + 17000;
	}
	done = false;
	while (!done) { //stop when b==c
	    f= 1;
	    d= 2;
	    e= 2;
	    for(d=2;d*d <= b; d++){ 
		if( (b%d==0) ){
		    f=0;
		    break;
		}
	    }
	    if(f==0) 
		h++;
	    g = b-c;
	    b+= 17;
	    if (g==0) done = true;
	} 

	IO.print("Part 2: " + h);
    }
    
    public static void main(String[] args) {
	HCF hcf = new HCF(args[0]);
	hcf.process1();
	hcf.process2();
    }
}
