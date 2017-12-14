import util.*;
import java.util.*;

public class KnotHash {

    ArrayList<String> lines;
    ArrayList<Integer> lengths;
    ArrayList<Integer> rope;
    int currpos, skip;
    
    public KnotHash(String ascii) {

	// get sequence of lengths
	lengths = new ArrayList<Integer>();

	for(int i=0; i<ascii.length(); i++) {
	    lengths.add(ascii.charAt(i)+0);
	}

	//add suffix
	lengths.add(17); lengths.add(31); lengths.add(73);
	lengths.add(47); lengths.add(23);

	//IO.print(lengths.toString());
	//create initial rope
	rope = new ArrayList<Integer>(256);
	for (int i=0; i<256; i++) rope.add(i);
	currpos = 0;
	skip = 0;
    }

    private void process() {
	for (int i=0; i<lengths.size(); i++) {
	    reverseRange(currpos,lengths.get(i));
	    currpos = (currpos + skip + lengths.get(i)) % rope.size();
	    skip++;
	}
    }

    public String computeKnotHash() {
	for (int i=0; i<64; i++) {
	    process();
	}

	//make dense hash from sparse hash
	ArrayList<Integer> denseHash = new ArrayList<Integer>();
	for (int i=0; i<16; i++) {
	    int xor = 0;
	    for (int j=i*16; j<(i+1)*16; j++) {
		xor ^= rope.get(j);
	    }
	    denseHash.add(xor);
	}
	
	//convert to hexadecimal
	String hexa = "";
	for(int i=0; i<16; i++) {
	    String s = Integer.toHexString(denseHash.get(i));
	    if (s.length() == 1) s = "0"+s;
	    hexa += s;
	}
	return hexa;
    }
    
    private void reverseRange(int sI, int l) {

	for(int i=0; i<l/2; i++) {
	    int i1 = ( (sI + i) % rope.size() );
	    int i2 = ( (sI+l-i-1) % rope.size() );

	    int temp = rope.get(i1);
	    rope.set(i1,rope.get(i2));
	    rope.set(i2,temp);
	}
    }

}
