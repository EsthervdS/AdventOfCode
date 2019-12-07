import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Intcode {

    public ArrayList<String> lines;
    public ArrayList<Integer> masterCodes,currentCodes;
    public int curpos;
    public boolean firstInput;
    public int[] inputs;
    public int maxScore,curScore;

    public Intcode(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	masterCodes = new ArrayList<Integer>();
	String[] c = lines.get(0).split(",");
	for (int i=0; i<c.length; i++) {
	    masterCodes.add(Integer.parseInt(c[i]));
	}
	curScore = 0;
	maxScore = -1*Integer.MAX_VALUE;
	//compute permutations
	ArrayList<Integer> phases = new ArrayList<Integer>();
	for (int i=0; i<5; i++) phases.add(i);
	Permutations<Integer> obj = new Permutations<Integer>();
	ArrayList<ArrayList<Integer>> permutations = obj.permute(phases);

	//for each permutation
	    //run program 5 times, passing through the outputs as inputs
		//compute curScore and check if it is max

	for (ArrayList<Integer> order : permutations) {
	    curScore = 0;
	    for (int i=0; i<5; i++) {
		run(order.get(i),curScore);
	    }
	    if (curScore > maxScore) {
		maxScore = curScore;
	    }
	}

	IO.print("Part 1: " + maxScore);
    }


    public void init(int i1, int i2) {
	//i1 is first input, i2 is second input
	curpos = 0;
	firstInput = true;
	inputs = new int[] {i1,i2};

	currentCodes = new ArrayList<Integer>();
	for (int i=0; i<masterCodes.size(); i++) {
	    currentCodes.add(masterCodes.get(i));
	}
    }

    public void step() {

	int firstInstr = currentCodes.get(curpos);
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
	    par1 = (parmode1==0) ? currentCodes.get(currentCodes.get(curpos + 1)) : currentCodes.get(curpos+1);
	    par2 = (parmode2==0) ? currentCodes.get(currentCodes.get(curpos + 2)) : currentCodes.get(curpos+2);
	}
	
	if (opcode == 1 || opcode == 2) {
	    //addition or multiplication

	    //Parameters that an instruction writes to will never be in immediate mode.
	    mem = currentCodes.get(curpos + 3);
	    if (parmode3>0) IO.print("ERROR");

	    //perform instruction and set memory to result
	    int res = (opcode == 1) ? (par1 + par2) : (par1 * par2);
	    currentCodes.set(mem, res);
	    curpos += 4;
	    
	} else if (opcode == 3) {
	    //old input with user input
	    /*
	    Scanner scanner = new Scanner(System.in);
	    int i = scanner.nextInt();
	    //Parameters that an instruction writes to will never be in immediate mode.	 
	    */
	    par1 = currentCodes.get(curpos + 1);
	    int res = (firstInput) ? inputs[0] : inputs[1];
	    if (firstInput) firstInput = false;
	    currentCodes.set(par1,res);
	    curpos += 2;
	    
	} else if (opcode == 4) {
	    int res = (parmode1 == 0) ? currentCodes.get(currentCodes.get(curpos + 1)) : currentCodes.get(curpos + 1);
	    //old output
	    //IO.print("OUTPUT: " + res);
	    curScore = res;

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
	    mem = currentCodes.get(curpos + 3);
	    int res = (par1 < par2) ? 1 : 0;
	
	    currentCodes.set(mem,res);
	    curpos += 4;
	    
	} else if (opcode == 8) {
	    //equals
	    //Parameters that an instruction writes to will never be in immediate mode.
	    mem = currentCodes.get(curpos + 3);
	    int res = (par1 == par2) ? 1 : 0;
	
	    currentCodes.set(mem,res);
	    curpos += 4;
	    
	} else {
	    IO.print("Wrong opcode?!");
	}

    }

    public void runProgram() {
	while (	currentCodes.get(curpos) != 99 ) {
	    step();
	}
    }
    
    public void run(int i1, int i2) {
	init(i1,i2);
	runProgram();
    }

   
    public static void main(String[] args) {
	Intcode t = new Intcode(args[0]);
    }
}
