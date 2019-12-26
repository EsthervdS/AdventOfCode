import util.*;
import java.util.*;
import java.math.*;

public class CardShufflePart2 {


    public ArrayList<String> lines;
    public BigInteger deckSize,times;

    public CardShufflePart2(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	
	deckSize = new BigInteger("119315717514047");
	times = new BigInteger("101741582076661");

	IO.print("Part 2: " + seekPosition(2020));
    }

    public BigInteger bigint(int l) {
	return new BigInteger(l+"");
    }

    private BigInteger seekPosition(int position) {
	var calc = new BigInteger[] {bigint(1), bigint(0)};
	var res = processReverseInput(calc);
	var pow = res[0].modPow(times, deckSize);
	return pow.multiply(bigint(position)).add(res[1].multiply(pow.add(deckSize).subtract(bigint(1))).multiply(res[0].subtract(bigint(1)).modPow(deckSize.subtract(bigint(2)), deckSize))).mod(deckSize);
    }
    
    public BigInteger[] processReverseInput(BigInteger[] res) {
	
	for (int i=lines.size()-1; i>=0; i--) {
	    String line = lines.get(i);
	    String[] ss = line.split(" ");
	    //IO.print(line);
	    if (ss[0].equals("cut")) {

		int n = Integer.parseInt(ss[ss.length-1]);
		res[0] = res[0];
		res[1] = res[1].add(bigint(n));

	    } else if (ss[1].equals("with")) {

		int n = Integer.parseInt(ss[ss.length-1]);
	        BigInteger p = bigint(n).modPow(deckSize.subtract(bigint(2)), deckSize);
		for(int k=0; k<2; k++) res[k] = res[k].multiply(p);
		
	    } else {

	        res[0] = res[0].multiply(bigint(-1));
		res[1] = res[1].add(bigint(1)).multiply(bigint(-1));
	    }
	    res[0] = res[0].mod(deckSize);
	    res[1] = res[1].mod(deckSize);
	}
	
	return res;
    }
    public BigInteger reverseDeal(BigInteger i) {
	return deckSize.subtract(bigint(1)).subtract(i);
    }

    public BigInteger reverseCut(int n, BigInteger i) {
	return (i.add(bigint(n)).add(deckSize)).mod(deckSize);
    }

    public BigInteger reverseDealInc(int n, BigInteger i) {
	//modinv(deckSize, D) * i % D
	return ((bigint(n).modInverse(deckSize)).multiply(i)).mod(deckSize);
    }
    
    public static void main(String[] args) {
	CardShufflePart2 cs = new CardShufflePart2(args[0]);
     }

}
