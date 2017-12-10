import util.*;
import java.util.*;

public class KnotHash {

    ArrayList<String> lines;
    ArrayList<Integer> lengths;
    ArrayList<Integer> rope;
    int currpos, skip;
    boolean part1done;
    
    public KnotHash(String fileName, int l) {
	lines = IO.readFile(fileName);
	// get sequence of lengths
	lengths = new ArrayList<Integer>();

	String ascii = lines.get(0).trim();
	for(int i=0; i<ascii.length(); i++) {
	    lengths.add(ascii.charAt(i)+0);
	}

	//add suffix
	lengths.add(17); lengths.add(31); lengths.add(73);
	lengths.add(47); lengths.add(23);

	//IO.print(lengths.toString());
	//create initial rope
	rope = new ArrayList<Integer>(l);
	for (int i=0; i<l; i++) rope.add(i);
	currpos = 0;
	skip = 0;
	part1done = false;
    }

    private void process() {
	for (int i=0; i<lengths.size(); i++) {
	    reverseRange(currpos,lengths.get(i));
	    currpos = (currpos + skip + lengths.get(i)) % rope.size();
	    skip++;
	}
	if (!part1done) {
	    IO.print("Part 1: " + (rope.get(0) * rope.get(1)));
	    part1done = true;
	}
    }

    private void process2() {
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
	IO.print("Part 2: " + hexa);
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

    public static void main(String[] args) {
	int seqL = Integer.parseInt(args[1]);
	KnotHash kh = new KnotHash(args[0],seqL);

	kh.process2();

    }
}
