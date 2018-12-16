import java.util.*;
import util.*;

public class Registers {
    ArrayList<String> lines;
    int[] registers;
    
    public Registers(String filename) {
	registers = new int[4];
	lines = IO.readFile(filename);
	int i=0;
	int count = 0;

	Operations.init();
	
	while (true) {
	    String before = lines.get(i); i++;
	    if (before.length() == 0) {
		i++;
		break;
	    }
	    String inst = lines.get(i);   i++;
	    String after = lines.get(i);  i++;
	    i++;

	    /* parse register values before */
	    int[] prev = new int[4];
	    String[] prevS = before.split(",");
	    prev[0] = Integer.parseInt(prevS[0].charAt(prevS[0].length()-1)+"");
	    prev[1] = Integer.parseInt(prevS[1].trim());
	    prev[2] = Integer.parseInt(prevS[2].trim());
	    prev[3] = Integer.parseInt(prevS[3].charAt(1)+"");

	    /* parse instruction */
	    String[] instS = inst.split(" ");
	    int opcode = Integer.parseInt(instS[0]);
	    int a = Integer.parseInt(instS[1]);
	    int b = Integer.parseInt(instS[2]);
	    int c = Integer.parseInt(instS[3]);
	    
	    /* parse register values after */
	    int[] next = new int[4];
	    String[] nextS = after.split(",");
	    next[0] = Integer.parseInt(nextS[0].charAt(nextS[0].length()-1)+"");
	    next[1] = Integer.parseInt(nextS[1].trim());
	    next[2] = Integer.parseInt(nextS[2].trim());
	    next[3] = Integer.parseInt(nextS[3].charAt(1)+"");

	    if (Operations.howMany(prev,opcode,a,b,c,next) >= 3) {
		count++;
	    }
	}
	
	IO.print("Part 1: " + count);
	registers[0] = 0;
	registers[1] = 0;
	registers[2] = 0;
	registers[3] = 0;
	Operations.computeMapping();
	
	while (i < lines.size()) {
	    String[] instS = lines.get(i).split(" ");
	    int opcode = Integer.parseInt(instS[0]);
	    int a = Integer.parseInt(instS[1]);
	    int b = Integer.parseInt(instS[2]);
	    int c = Integer.parseInt(instS[3]);
	    registers = Operations.perform(registers,opcode,a,b,c);
	    i++;
	}
	IO.print("Part 2: " + registers[0]);

    }

    public static void main(String[] args) {
	Registers r = new Registers(args[0]);

    }
}
