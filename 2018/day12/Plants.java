import util.*;
import java.util.*;
import java.math.*;

public class Plants {

    ArrayList<String> lines;
    ArrayList<Replacement> repl;
    boolean[][] plants;
    int nP,offset,curGen;
    int leftMost, rightMost;
    int maxP;
    
    public Plants(String fileName) {
	lines = IO.readFile(fileName);
	repl = new ArrayList<Replacement>();
	String initial = lines.get(0).split(": ")[1];
	lines.remove(0); lines.remove(0);
	for(String line : lines) {
	    repl.add(new Replacement(line.split(" => ")[0],line.split(" => ")[1]));
	}
	nP = initial.length();
	maxP = nP*100000;
	plants = new boolean[2][maxP];
	offset = nP;
	for (int i=0; i<maxP;i++) {
	    if ((i<offset) || (i>=offset+nP)) {
		plants[0][i] = false;
	    } else {
		plants[0][i] = (initial.charAt(i-offset) == '#') ? true : false;
	    }
	    plants[1][i] = false;
	}
	curGen = 0;
	leftMost = offset;
	rightMost = offset+nP-1;
    }
    
    public void nextGen() {
	int nextGen = 1-curGen;
	for (int i=0;i<maxP; i++) plants[nextGen][i] = false;
	for (int i=leftMost-2; i<rightMost+2; i++) {
	    Replacement r = match(i);
	    plants[nextGen][i] = r.newm;
	}
	int l=0;
	while (!plants[nextGen][l]) l++;
	leftMost = l;
	int r=maxP-1;
	while (!plants[nextGen][r]) r--;
	rightMost = r;
	curGen = nextGen;
    }

    public Replacement match(int i) {
	//IO.print(showContext(i));
	for (Replacement r : repl) {
	    if ( (r.ll == plants[curGen][i-2]) && (r.l == plants[curGen][i-1])
		 && (r.m == plants[curGen][i])
		 && (r.r == plants[curGen][i+1]) && (r.rr == plants[curGen][i+2]) ) {
		return r;
	    }
	}
	return (new Replacement(".....","."));
    }

    public String showContext(int i) {
	String res = "";
	res += "Looking for match for plants: ";
	res += (plants[curGen][i]) ? "#" : ".";
	res += (plants[curGen][i+1]) ? "#" : ".";
	res += (plants[curGen][i+2]) ? "#" : ".";
	res += (plants[curGen][i+3]) ? "#" : ".";
	res += (plants[curGen][i+4]) ? "#" : ".";
	return res;
    }
    
    public void displayPlants() {
	for (int i=0;i<maxP;i++) {
	    if (plants[curGen][i]) {
		System.out.print("#");
	    } else {
		System.out.print(".");
	    }
	}
	IO.print("");
    }

    public int sum() {
	int res = 0;
	for (int i=leftMost; i<=rightMost; i++) {
	    if (plants[curGen][i]) {
		res += (i-offset);
	    }
	}
	return res;
    }
    
    public static void main(String[] args) {
	Plants rp = new Plants(args[0]);
	//System.out.print(" 0: "); rp.displayPlants();
	for(int i=1; i<=20; i++) {
	    rp.nextGen();
	    //if (i<10) System.out.print(" ");
	    //System.out.print((i) + ": " );
	    //rp.displayPlants();
	}
	IO.print("Part 1: " + rp.sum());
	rp = new Plants(args[0]);
	int prevSum = 0;
	int sum = rp.sum();
	//let's hope it converges to a pattern after 500 generations
	for (int i=1; i<=500; i++) {
	    rp.nextGen();
	    prevSum = sum;
	    sum = rp.sum();
	}
	int diff = sum - prevSum;
	String sum500 = Integer.toString(rp.sum());
	BigInteger sumfifty = new BigInteger(sum500);
	BigInteger almostfiftybillion = new BigInteger("49999999500");
	BigInteger interval = new BigInteger(diff+"");
	BigInteger part2 = (almostfiftybillion.multiply(interval)).add(sumfifty);
	IO.print("Part 2: " + part2.toString());
	
    }
}
