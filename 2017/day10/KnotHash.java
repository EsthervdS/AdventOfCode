import util.*;
import java.util.*;

public class KnotHash {

    ArrayList<String> lines;
    ArrayList<Integer> lengths;
    ArrayList<Integer> rope;
    int currpos, skip; 
    
    public KnotHash(String fileName, int l) {
	lines = IO.readFile(fileName);
	// get sequence of lengths
	lengths = new ArrayList<Integer>();

	String[] ls = lines.get(0).split(",");
	for(int i=0; i<ls.length; i++) {
	    lengths.add(Integer.parseInt(ls[i]));
	}

	//create initial rope
	rope = new ArrayList<Integer>(l);
	for (int i=0; i<l; i++) rope.add(i);
	currpos = 0;
	skip = 0;
    }

    private void process() {
	for (int i=0; i<lengths.size(); i++) {
	    reverseRange(currpos,lengths.get(i));
	    currpos = (currpos + skip + lengths.get(i)) % rope.size();
	    skip++;
	}
	IO.print("Part 1: " + (rope.get(0) * rope.get(1)));	
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
	kh.process();
    }
}
