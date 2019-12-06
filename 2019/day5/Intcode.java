import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Intcode {

    public ArrayList<String> lines;
    public ArrayList<Integer> codes;
    public int curpos;

    public Intcode(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
    }

    public void init() {
	curpos = 0;
	codes = new ArrayList<Integer>();
	String[] c = lines.get(0).split(",");
	for (int i=0; i<c.length; i++) {
	    codes.add(Integer.parseInt(c[i]));
	}
    }

    public void step() {

	int firstInstr = codes.get(curpos);
	// tens and ones = opcode
	int opcode = firstInstr % 100;

	// parameter modes
	// hundreds is parmode 1
	// thousands is parmode 2
	// tenthousands is parmode 3
	int parmode1 = (firstInstr / 100) % 10;
	int parmode2 = (firstInstr / 1000) % 10;
	int parmode3 = (firstInstr / 10000) % 10;
	int par1,par2,mem;
	par1 = par2 = mem = -1;
	
	if (opcode == 1 || opcode == 2 || opcode >= 5) {
	    par1 = (parmode1==0) ? codes.get(codes.get(curpos + 1)) : codes.get(curpos+1);
	    par2 = (parmode2==0) ? codes.get(codes.get(curpos + 2)) : codes.get(curpos+2);
	}
	
	if (opcode == 1 || opcode == 2) {
	    //addition or multiplication

	    //Parameters that an instruction writes to will never be in immediate mode.
	    mem = codes.get(curpos + 3);
	    if (parmode3>0) IO.print("ERROR");

	    //perform instruction and set memory to result
	    int res = (opcode == 1) ? (par1 + par2) : (par1 * par2);
	    codes.set(mem, res);
	    curpos += 4;
	    
	} else if (opcode == 3) {
	    //input
	    Scanner scanner = new Scanner(System.in);
	    int i = scanner.nextInt();
	    //Parameters that an instruction writes to will never be in immediate mode.	    
	    par1 = codes.get(curpos + 1);
	    codes.set(par1,i);
	    curpos += 2;
	    
	} else if (opcode == 4) {
	    //output
	    int res = (parmode1 == 0) ? codes.get(codes.get(curpos + 1)) : codes.get(curpos + 1);
	    IO.print("OUTPUT: " + res);
	    curpos += 2;
	    
	} else if (opcode == 5) {
	    //jump-if-true
	    if (par1 != 0) {
		curpos = par2;
	    } else {
		curpos += 3;
	    }
	} else if (opcode == 6) {
	    //jump-if-false
	    if (par1 == 0) {
		curpos = par2;
	    } else {
		curpos += 3;
	    }

	} else if (opcode == 7) {
	    //less than
	    //Parameters that an instruction writes to will never be in immediate mode.
	    mem = codes.get(curpos + 3);
	    int res = (par1 < par2) ? 1 : 0;
	
	    codes.set(mem,res);
	    curpos += 4;
	    
	} else if (opcode == 8) {
	    //equals
	    //Parameters that an instruction writes to will never be in immediate mode.
	    mem = codes.get(curpos + 3);
	    int res = (par1 == par2) ? 1 : 0;
	
	    codes.set(mem,res);
	    curpos += 4;
	    
	} else {
	    IO.print("Wrong opcode?!");
	}

    }

    public void runProgram() {
	while (	codes.get(curpos) != 99 ) {
	    step();
	}
    }
    
    public void run() {
	init();
	runProgram();
    }

   
    public static void main(String[] args) {
	Intcode t = new Intcode(args[0]);
	t.run();

    }
}
