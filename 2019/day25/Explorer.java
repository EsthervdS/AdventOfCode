import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Explorer {

    public ArrayList<String> lines;
    public Intcode droid;
    public ArrayList<Long> program;
    
    public Explorer(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	String[] ss = lines.get(0).split(",");
	program = new ArrayList<Long>();
	for (String s : ss) {
	    program.add(Long.parseLong(s));
	}

	droid = new Intcode(program);
	droid.displayOutput = true;
	droid.ASCIIOutput = true;
	droid.runUntilInput();

	while(true) {
	    String instr = IO.getLine("Next instruction: ");
	    //instr = instr.substring(0,instr.length()-1);
	    for (int i=0; i<instr.length(); i++) {
		char c = instr.charAt(i);
		droid.input.add((long) c);
	    }
	    droid.input.add(10L);
	    while (droid.input.size() > 0) {
		droid.runUntil();
	    }
	    droid.runUntilInput();
	    
	}
    }

    public static void main(String[] args) {
	Explorer ex = new Explorer(args[0]);

     }
}
