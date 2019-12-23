import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Network {

    public ArrayList<String> lines;
    public ArrayList<Long> program;
    public HashMap<Long,Intcode> computers;
    public HashMap<Long,ArrayList<Long>> outputs;
    public long natX, natY, lastNatY;
    public boolean part1,part2;
    public long millis;
    
    public Network(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	part1 = part2 = false;

        millis = System.currentTimeMillis();
	
	init();

	while (!part2) {

	    for (long i=0; i<50; i++) {
		Intcode cur = computers.get(i);
		process(cur);
	    }
	    
	    boolean allIdle = true;
	    for (long i=0; i<50; i++) {
		allIdle = allIdle && computers.get(i).isIdle;
	    }
	    if (allIdle) {
		sendPacket(0L,natX,natY);
	    }
	}
    }

    public void process(Intcode cur) {

	cur.runUntil();

	if (cur.state == Intcode.OUTPUTWAIT) {
	    long addr = cur.id;
	    ArrayList<Long> curOutputs = outputs.get(addr);
	    curOutputs.add(cur.output);
	    
	    if (curOutputs.size()== 3) {
		
		long destination = curOutputs.remove(0);
		long x = curOutputs.remove(0);
		long y = curOutputs.remove(0);
		
		sendPacket(destination,x,y);
	    }
	} else if (cur.state == Intcode.INPUTWAIT && cur.input.size() == 0) {
	    cur.isIdle = true;
	}
    }

    public void sendPacket(long destination, long x, long y) {

	if (destination >= 0 && destination < 50) {
	    Intcode dest = computers.get(destination);
	    dest.input.add(x);
	    dest.input.add(y);
	    dest.isIdle = false;
	}

	if (destination == 255) {
	    if (!part1) {
		IO.print("Part 1: " + y);
		IO.print("Part 1 computed in " + (System.currentTimeMillis() - millis) + "ms");
		part1 = true;
	    }
	    natX = x;
	    natY = y;
	}
	if (destination == 0) {
	    if (lastNatY == natY) {
		IO.print("Part 2: " + y);
		IO.print("Part 2 computed in " + (System.currentTimeMillis() - millis) + "ms");
		part2 = true;
	    }
	    lastNatY = y;
	}
    }
    
    public void init() {
	program = new ArrayList<Long>();
	String[] ss = lines.get(0).split(",");
	for (String s : ss) {
	    program.add(Long.parseLong(s,10));
	}
        outputs = new HashMap<Long,ArrayList<Long>>();
	for (long i=0; i<50; i++) {
	    outputs.put(i,new ArrayList<Long>());
	}
	natX = natY = lastNatY = 0;
	
	computers = new HashMap<Long,Intcode>();
	for (long i=0; i<50; i++) {
	    Intcode cur = new Intcode(program);
	    cur.input.add(i);
	    cur.ASCIIOutput = false;	    
	    cur.runUntil();
	    computers.put(i,cur);
	}
    }

    public static void main(String[] args) {
	Network n = new Network(args[0]);
     }
}
