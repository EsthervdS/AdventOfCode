import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class SpringDroid {

    public ArrayList<String> lines;
    public Intcode droid;
    public ArrayList<Long> program;
    
    public SpringDroid(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	String[] ss = lines.get(0).split(",");
	program = new ArrayList<Long>();
	for (String s : ss) {
	    program.add(Long.parseLong(s));
	}

	droid = new Intcode(program);
	String instruction = "NOT A J\nNOT B T\nOR T J\nNOT C T\nOR T J\nAND D J\nWALK\n";
	for (int i=0; i<instruction.length(); i++) {
	    droid.input.add((long) instruction.charAt(i));
	}
	droid.displayOutput = true;
	droid.ASCIIOutput = true;
	droid.runUntilOutput();
	while (droid.output < 255) {
	    if (droid.output >= 255) {
		droid.ASCIIOutput = false;		
	    }
	    droid.runUntilOutput();
	}
        IO.print("Part 1: " + droid.output);
	
	droid = new Intcode(program);
	//(!A | !B | (!C & H)) & D
        instruction = "NOT A J\nNOT B T\nOR T J\nNOT C T\nAND H T\nOR T J\nAND D J\nRUN\n";
	for (int i=0; i<instruction.length(); i++) {
	    droid.input.add((long) instruction.charAt(i));
	}
	droid.displayOutput = true;
	droid.ASCIIOutput = true;
	droid.runUntilOutput();
	while (droid.output < 255) {
	    if (droid.output >= 255) {
		droid.ASCIIOutput = false;		
	    }
	    droid.runUntilOutput();
	}
        IO.print("Part 2: " + droid.output);    

    }

    
    public void printChar(int c) {
	System.out.format("%1$-2c", (char) c);
    }

    public static void main(String[] args) {
	SpringDroid sd = new SpringDroid(args[0]);

     }
}
