import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class BootCode {

    public ArrayList<String> lines;
    public Program p;
    
    public BootCode(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	p = new Program(lines);
    }

    public long part1() {
	return p.computeAccumulatorAtLoop(0,0);
    }

    public long part2() {
	return p.fixProgram();
    }

    
    public static void main(String[] args) {
	BootCode bc = new BootCode(args[0]);
	IO.print("Part 1: " + bc.part1());
	IO.print("Part 2: " + bc.part2());
    }
}
