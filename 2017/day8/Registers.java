import util.*;
import java.util.*;
import java.util.stream.*;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Registers {

    ArrayList<String> instructions;
    HashMap<String,Integer> registers;
    int allTimeMax;
    
    public Registers(String fileName) {
	instructions = IO.readFile(fileName);
	registers = new HashMap<String,Integer>();
	allTimeMax = -10000;
    }

    public void process() {
	for(String ins : instructions) {
	    String[] tokens = ins.split(" ");
	    //parse instruction
	    String toChange = tokens[0];
	    String toDo = tokens[1];
	    int changeWith = Integer.parseInt(tokens[2]);
	    String toCheck = tokens[4];
	    String chOp = tokens[5];
	    int chValue = Integer.parseInt(tokens[6]);

	    //check if register toChange exists, if not add to HashMap with value 0
	    if (!registers.containsKey(toChange)) {
		registers.put(toChange,0);
	    }
	    //check if register toCheck exists, if not add to HashMap with value 0
	    if (!registers.containsKey(toCheck)) {
		registers.put(toCheck,0);
	    }

	    //check if toCheck chOp chValue (chOp in {'<','>','<=','>=','==','!='})
	    boolean perfIns = false;
	    int checkVal = registers.get(toCheck);

	    switch(chOp) {
	    case "<" : perfIns = (checkVal < chValue); break;
	    case ">" : perfIns = (checkVal > chValue); break;
	    case "<=" : perfIns = (checkVal <= chValue); break;
	    case ">=" : perfIns = (checkVal >= chValue); break;
	    case "==" : perfIns = (checkVal == chValue); break;
	    case "!=" : perfIns = (checkVal != chValue); break;
	    default: break;
	    }

	    //if not, do nothing
	    //if so, change register toChange with changeWith according to toDo (inc of dec)
	    int regCurr = registers.get(toChange);
	    if (perfIns) {
		if (toDo.equals("inc")) {
		    //inc
		    registers.put(toChange,regCurr+changeWith);
		} else {
		    //dec
		    registers.put(toChange,regCurr-changeWith);
		}
	    }
	    int regMax = registers.values().stream().max(Comparator.naturalOrder()).get();
	    if (regMax > allTimeMax) allTimeMax = regMax;
	    //IO.print(registers.toString());
	}
	int regMax = registers.values().stream().max(Comparator.naturalOrder()).get();
	IO.print("Part 1: " + regMax);
	IO.print("Part 2: " + allTimeMax);
    }

    public static void main(String[] args) {

	Registers regs = new Registers(args[0]);
	regs.process();
    }

}
    
