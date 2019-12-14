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
	    IO.print("Interval [" + low + "," + high + "]");
	    BigInteger mid = low.add(high).add(BigInteger.ONE).divide(new BigInteger("2"));

	    //1000000000000
	    //6260048108

	    IO.print("Computing ores for fuel = " + mid); 
	    BigInteger res = findOre(mid);
	    IO.print("Result = " + res);
	    if (res.compareTo(bound) < 0) {
		low = mid;
	    } else {
		high = mid.subtract(BigInteger.ONE);
	    }
	}
	IO.print("Part 2: " + low);
    }

    public BigInteger findOre(BigInteger fuel) {
	
	parseInput();
	inventory.put("FUEL",fuel);
	
	while (toProcess.size() > 0) {
	    String s = toProcess.remove(0);
	    Reaction r = findReaction(s);

	    //use output of reaction
	    BigInteger curInvOut = inventory.get(s);
	    int reactionAmount = r.output.amount;
	    int times = 0;
	    BigInteger temp = inventory.get(s);
	    while (temp.compareTo(BigInteger.ZERO) > 0) {
		times++;
		temp = temp.subtract(new BigInteger(reactionAmount+""));
	    }
	    inventory.put(s,temp);
	    
	    if (temp.compareTo(BigInteger.ZERO) < 0) {
		if (!excess.contains(s)) excess.push(s);
	    }

	    //add input of reaction to inventory
	    for (Pair p : r.inputs) {
		if (!p.name.equals("ORE")) {
		    if (!toProcess.contains(p.name)) toProcess.push(p.name);
		}
		    
	        BigInteger curInv = inventory.get(p.name);
		inventory.put(p.name,curInv.add(new BigInteger(""+p.amount*times)));
	    }
	    
	}

	while (excess.size() > 0) {
	    String s = excess.remove(0);
	    Reaction r = findReaction(s);
	    if (r.inputs.size() == 1 && r.inputs.get(0).name.equals("ORE")) {

		int amount = Math.abs(r.output.amount);
		int oreAmount = Math.abs(r.inputs.get(0).amount);
		int times = 0;
		BigInteger temp = inventory.get(s);
		BigInteger ores = inventory.get("ORE");
		while (temp.abs().compareTo(new BigInteger(""+amount)) > 0) {
		    temp.add(new BigInteger(""+amount));
		    ores.subtract(new BigInteger(""+oreAmount));
		}
		inventory.put(s,temp);
		inventory.put("ORE",ores);

	    }
	}
	return inventory.get("ORE");
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
