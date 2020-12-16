import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Bitmask {

    public ArrayList<String> lines;
    public HashMap<Integer,Integer> mask;
    public HashMap<Long,Long> memory;
    public int programPointer;
    
    public Bitmask(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	mask = new HashMap<Integer,Integer>();
	memory = new HashMap<Long,Long>();
	programPointer = 0;
    }

    public void handleMask() {
	mask = new HashMap<Integer,Integer>();
	String m = lines.get(programPointer).split(" ")[2];
	for (int i=0; i<m.length(); i++) {
	    if (m.charAt(i) != 'X') {
		int val = Integer.parseInt(m.charAt(i)+"");
		mask.put(m.length()-i-1,val);
	    }
	}
	IO.print(mask.toString());
    }

    public void handleMemory() {
	String line = lines.get(programPointer);
	long index = Long.parseLong(line.split("]")[0].split("\\[")[1]);
	long value = Long.parseLong(line.split(" ")[2]);
	
	StringBuilder binaryValue = new StringBuilder(Long.toBinaryString(value));
	for (int l : mask.keySet()) {
	    if (l < binaryValue.length()) {
		binaryValue.setCharAt(binaryValue.length()-l-1,(new String(mask.get(l)+"").charAt(0)));
	    } else {
		if (mask.get(l) == 1) {
		    String toAdd = "1";
		    for (int ii=0;ii<l-binaryValue.length(); ii++) {
			toAdd = toAdd+"0";
		    }
		    binaryValue.insert(0,toAdd);
		}
	    }
	}
	value = Long.parseLong(binaryValue.toString(),2);
	memory.put(index,value);
    }
    
    public long part1() {
	while (programPointer < lines.size()) {
	    String line = lines.get(programPointer);
	    if (line.charAt(1) == 'a') {
		handleMask();
	    } else {
		handleMemory();
	    }
	    programPointer++;
	}
	long res = 0;
	for (Long l : memory.keySet()) res += memory.get(l);
	return res;
    }

    public long part2() {
	return -1;
    }

    
    public static void main(String[] args) {
	Bitmask b = new Bitmask(args[0]);
	IO.print("Part 1: " + b.part1());
	IO.print("Part 2: " + b.part2());
    }
}
