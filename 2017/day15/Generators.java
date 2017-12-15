import util.*;

public class Generators {

    int factorA, factorB;
    int startA, startB;
    int divisor;
    
    public Generators(int sA, int sB) {

	factorA = 16807;
	factorB = 48271;
	divisor = 2147483647;
	startA = sA;
	startB = sB;
	    
    }

    private long computeNext(long prev, boolean isA) {
	if (isA) {
	    return ( (prev * factorA) % divisor );
	} else {
	    return ( (prev * factorB) % divisor );
	}
    }

    public void process() {
	long prevA = startA;
	long prevB = startB;
	int judgeCount = 0;
	long a = 1, b = 1;
	int zeroes;
	String bitsA, bitsB;
	
	for (int i=0; i<5000000; i++) {
	    a = 1;

	    while ( (a % 4) != 0) {
		
		a = computeNext(prevA, true);
		prevA = a;
	    }
	    b = 1;
	    while ( (b % 8) != 0) {
		b = computeNext(prevB, false);
		prevB = b;
	    }

	    long newA = 0;
	    for (int j=0;j<16;++j) {
		newA = (newA << 1) | (a & 1);
		a = a >> 1;
	    }
	    long newB = 0;
	    for (int j=0;j<16;++j) {
		newB = (newB << 1) | (b & 1);
		b = b >> 1;
	    }	    

	    if (newA == newB) judgeCount++;
	}
	IO.print("Part 1: " + judgeCount);
    }
    
    public static void main(String[] args) {
	int startA = 703;
	int startB = 516;
	int testA = 65;
	int testB = 8921;
	Generators dg = new Generators(startA,startB);
	//Generators dg = new Generators(testA,testB);
	dg.process();

    }
}
