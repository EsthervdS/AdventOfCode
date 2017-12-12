import util.*;
import java.util.*;

public class Wires {

    public static int part1;
    ArrayList<String> lines;
    HashMap<String,String> expressions;
    
    public Wires(String fileName) {
	lines = IO.readFile(fileName);
	expressions = new HashMap<String,String>();
    }
    
    public void process(boolean part2) {
	// *** Grammar ***
	// expr: <inst> -> <wire>
	// <wire> : String
	// <inst> : Integer | <wire> <bi-oper> <wire> | <un-oper> <wire>
	// <bi-oper> : AND | OR | LSHIFT | RSHIFT
	// <un-oper> : NOT

	//build hashmap with expressions
	for(int i=0; i<lines.size(); i++) {
	    
	    String toWire = lines.get(i).split("->")[1].trim();
	    String instruction = lines.get(i).split("->")[0].trim();
	    if (part2) {
		if (toWire.equals("b")) {
		    expressions.put(toWire,part1+"");
		} else {
		    expressions.put(toWire,instruction);
		}
		    
	    } else {
		expressions.put(toWire,instruction);
	    }
	}
	String finalWire = "a";
	if (!part2) {
	    part1 = evaluate(finalWire);
	    IO.print("Part 1: " + part1);
	} else {
	    IO.print("Part 2: " + evaluate(finalWire));
	}
    }

    private int evaluate(String s) {
	int i=-1;
	String cur = s;
	while(i == -1) {
	    if (isInteger(cur)) {
		i = Integer.parseInt(cur);
	    } else {
		String exp = expressions.get(cur);
		String[] tokens = exp.split(" ");
		    
		if (tokens.length == 1) {
		    if (isInteger(exp)) {
			i = Integer.parseInt(exp);
		    } else {
			i = evaluate(exp);
		    }
		} else {
		    if (tokens.length == 2) {
			//NOT
			int operand = evaluate(tokens[1]);
			i = ~operand;
			if (i<0) i = 65536 + i;
		    } else {
			//AND / OR / LSHIFT / RSHIFT
			String op = tokens[1];
			switch (op) {
			case "AND" : {
			    i = evaluate(tokens[0]) & evaluate(tokens[2]);
			    break;
			}
			case "OR" : {
			    i = evaluate(tokens[0]) | evaluate(tokens[2]);
			    break;
			}
			case "LSHIFT" : {
			    i = evaluate(tokens[0]) << evaluate(tokens[2]);
			    break;
			}
			case "RSHIFT" : {
			    i = evaluate(tokens[0]) >> evaluate(tokens[2]);
			    break;
			}
			default:  {
			    IO.print("XXXXXXXXX");
			    break;
			}
			}
		    }
		}
	    }
	    expressions.remove(cur);
	    expressions.put(cur,i+"");
	}
	return i;
    }
    
    private boolean isInteger(String s) {
	try { 
	    Integer.parseInt(s); 
	} catch(NumberFormatException e) { 
	    return false; 
	} catch(NullPointerException e) {
	    return false;
	}
	// only got here if we didn't return false
	return true;
    }

    public static void main(String[] args) {
	Wires w = new Wires(args[0]);
	w.process(false);
	Wires w2 = new Wires(args[0]);
	w2.process(true);
	
    }
}
