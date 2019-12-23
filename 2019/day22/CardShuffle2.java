import java.io.*;
import java.util.*;
import util.*;
import java.math.*;
import java.time.*;

public class CardShuffle2 {

    public ArrayList<String> lines;
    public BigInteger deckSize;

    public CardShuffle2(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	
	deckSize = new BigInteger("119315717514047");

	//119315717514047
	//112395405898232274
	IO.print("D = " + deckSize.toString());
	BigInteger x = bigint(2020);
	BigInteger y = processReverseInput(x);
	BigInteger z = processReverseInput(y);

	IO.print("x = " + x.toString());
	IO.print("y = " + y.toString());
	IO.print("z = " + z.toString());
	IO.print("");
	//A = (Y-Z) * modinv(X-Y, deckSize) % deckSize
	BigInteger modInv = (x.subtract(y)).modInverse(deckSize);
	BigInteger A = ((y.subtract(z)).multiply(modInv));

	//B = (Y-A*X) % deckSize
	BigInteger B = y.subtract(A.multiply(x)).mod(deckSize);
	IO.print("A = " + A.toString());
	IO.print("B = " + B.toString());

	BigInteger times = new BigInteger("101741582076661");

	//(pow(A, times, deckSize)*X + (pow(A, times, deckSize)-1) * modinv(A-1, deckSize) * B) % deckSize

	//(pow(A, times, deckSize)*X 
	BigInteger firstPart = A.modPow(times,deckSize).multiply(x);
	//(pow(A, times, deckSize)-1)
	BigInteger secondPart = A.modPow(times,deckSize).subtract(bigint(1));

	//modinv(A-1, deckSize) * B)
	BigInteger thirdPart = ((A.subtract(bigint(1))).modInverse(deckSize)).multiply(B);

	BigInteger part2 = firstPart.add(secondPart.multiply(thirdPart)).mod(deckSize);
	IO.print(part2.toString());
    }

    public BigInteger bigint(int l) {
	return new BigInteger(l+"");
    }
    
    public BigInteger processReverseInput(BigInteger b) {
	BigInteger res = bigint(0);
	for (int i=lines.size()-1; i>=0; i--) {
	    String line = lines.get(i);
	    String[] ss = line.split(" ");
	    //IO.print(line);
	    if (ss[0].equals("cut")) {
		int n = Integer.parseInt(ss[ss.length-1]);
		res = reverseCut(n,b);
	    } else if (ss[1].equals("with")) {
		int n = Integer.parseInt(ss[ss.length-1]);
		res = reverseDealInc(n,b);
	    } else {
		res = reverseDeal(b);
	    }
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
	CardShuffle2 cs = new CardShuffle2(args[0]);
     }
}
