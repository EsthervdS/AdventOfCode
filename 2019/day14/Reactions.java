import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
import java.lang.*;
import java.math.*;

public class Reactions {

    public ArrayList<String> lines;
    public ArrayList<Reaction> reactions;
    public HashMap<String,BigInteger> inventory;
    public HashMap<String,BigInteger> curInventory;
    public Stack<String> toProcess;
    public Stack<String> excess;
    
    public Reactions(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	parseInput();

	BigInteger oneFuel = findOre(BigInteger.ONE);
	IO.print("Part 1: " + oneFuel.toString());

	//binary search
        BigInteger bound = new BigInteger("1000000000000");
        BigInteger low = bound.divide(oneFuel);
        BigInteger high = low.multiply(new BigInteger("2"));

	while (low.compareTo(high) < 0) {
 	    BigInteger mid = low.add(high).add(BigInteger.ONE).divide(new BigInteger("2"));

	    BigInteger res = findOre(mid);
	    if (res.compareTo(bound) < 0) {
		low = mid;
	    } else {
		high = mid.subtract(BigInteger.ONE);
	    }
	}
	IO.print("Part 2: " + low.toString());
	
	
    }

    public BigInteger findOre(BigInteger fuel) {
	
	//deep copy inventory
	curInventory = new HashMap<String,BigInteger>();
	for (String s : inventory.keySet()) {
	    curInventory.put(s,inventory.get(s));
	}
	curInventory.put("FUEL",new BigInteger(""+fuel));
	toProcess = new Stack<String>();
	toProcess.push("FUEL");
	
	while (toProcess.size() > 0) {
	    String s = toProcess.remove(0);
	    Reaction r = findReaction(s);

	    //use output of reaction
	    BigInteger curInvOut = curInventory.get(s);
	    BigInteger reactionAmount = r.output.amount;

	    BigInteger cur = curInvOut;
	    BigInteger times = cur.divide(reactionAmount);
	    if (!cur.remainder(reactionAmount).equals(BigInteger.ZERO)) {
		times = times.add(BigInteger.ONE);
	    }
	    BigInteger temp = cur.subtract(times.multiply(reactionAmount));
	    curInventory.put(s,temp);
	    
	    if (temp.compareTo(BigInteger.ZERO) < 0) {
		if (!excess.contains(s)) excess.push(s);
	    }

	    //add input of reaction to curInventory
	    for (Pair p : r.inputs) {
		if (!p.name.equals("ORE")) {
		    if (!toProcess.contains(p.name)) {
			toProcess.push(p.name);
		    }
		}
		
	        BigInteger curInv = curInventory.get(p.name);
		BigInteger mul = p.amount.multiply(new BigInteger(""+times));
		curInventory.put(p.name,curInv.add(mul));
	    }
	}

	while (excess.size() > 0) {
	    String s = excess.remove(0);
	    Reaction r = findReaction(s);
	    if (r.inputs.size() == 1 && r.inputs.get(0).name.equals("ORE")) {

	        BigInteger amount = r.output.amount;
	        BigInteger oreAmount = r.inputs.get(0).amount;
		int times = 0;
	        BigInteger temp = curInventory.get(s);
	        BigInteger ores = curInventory.get("ORE");
		while (temp.abs().compareTo(amount) > 0) {
		    temp = temp.add(amount);
		    ores = ores.subtract(oreAmount);
		}
		curInventory.put(s,temp);
		curInventory.put("ORE",ores);

	    }
	}
	return curInventory.get("ORE");
    }
    
    public Reaction findReaction(String s) {
	Reaction res = new Reaction();
	for (Reaction r : reactions) {
	    String o = r.output.name;
	    if (o.equals(s)) {
		res = r;
		break;
	    }
	}
	return res;
    }
    
    public void parseInput() {
	inventory = new HashMap<String,BigInteger>();
	toProcess = new Stack<String>();
        excess = new Stack<String>();
	reactions = new ArrayList<Reaction>();
	inventory.put("ORE",BigInteger.ZERO);
	//parse reactions and find rule for fuel
	
	for(String line : lines) {
	    String[] ins = line.split(" => ")[0].split(", ");
	    String out = line.split(" => ")[1];
	    Pair o = new Pair(out);
	    if (o.name.equals("FUEL")) {
		toProcess.push(o.name);
	    } else {
		inventory.put(o.name,BigInteger.ZERO);
	    }
	    ArrayList<Pair> i = new ArrayList<Pair>();
	    for (String s : ins) {
		i.add(new Pair(s));
	    }
	    reactions.add(new Reaction(i,o));
	}

    }

    public void printInventory() {
	IO.print("Inventory = " + inventory.toString());
    }

    public void printQueue() {
	IO.print("Queue = " + toProcess.toString());
    }

    public static void main(String[] args) {
	Reactions r = new Reactions(args[0]);

     }
}
