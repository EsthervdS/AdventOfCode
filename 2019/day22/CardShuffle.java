import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class CardShuffle {

    public ArrayList<String> lines;
    public int N = 10007;
    public int[] cards;
    public int id2019;

    public CardShuffle(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	init();
	processInput();
	IO.print("Part 1: " + id2019);

	//N = 119315717514047L;
	init();

	//101741582076661
    }

    public void init() {
	cards = new int[N];
	for (int i=0; i<N; i++) {
	    cards[i] = i;
	}
	id2019 = 2019;
    }

    public void processInput() {
	for (String line : lines) {
	    //cut -4394
	    //deal with increment 9
	    //deal into new stack
	    String[] ss = line.split(" ");
	    //IO.print(line);
	    if (ss[0].equals("cut")) {
		int n = Integer.parseInt(ss[ss.length-1]);
		cards = cut(n);
	    } else if (ss[1].equals("with")) {
		int n = Integer.parseInt(ss[ss.length-1]);
		cards = deal(n);
	    } else {
		cards = dealNew();
	    }
	    //printCards();
	}

    }

    public void printCards() {
	for (int i=0; i<N; i++) {
	    System.out.print(cards[i] + " ");
	}
	IO.print("");
    }

    public int[] dealNew() {
	int[] temp = new int[N];
	for (int i=0; i<N; i++) {
	    temp[i] = cards[N-1-i];
	    if (cards[N-1-i] == 2019) id2019 = i;
	}
	return temp;
    }

    public int[] cut(int n) {
	int[] temp = new int[N];
	if (n>=0) {
	    int offset = N-n;
	    for (int i=0; i<n; i++) {
		temp[offset+i] = cards[i];
		if (cards[i] == 2019) id2019 = offset+i;
	    }
	    for (int i=0; i<offset; i++) {
		temp[i] = cards[n+i];
		if (cards[n+i] == 2019) id2019 = i;		
	    }
	} else {
	    //negative cut
	    int offset = N-Math.abs(n);
	    //move bottom to top
	    for (int i=0; i<Math.abs(n); i++) {
		temp[i] = cards[offset+i];
		if (cards[offset+i] == 2019) id2019 = i;		
	    }
	    for (int i=Math.abs(n); i<N; i++) {
		temp[i] = cards[i-Math.abs(n)];
		if (cards[i-Math.abs(n)] == 2019) id2019 = i;		
	    }
	    
	    
	}
	return temp;
    }

    public int[] deal(int n) {
	int[] temp = new int[N];
	for (int i=0; i<N; i++) {
	    temp[(i*n)%N] = cards[i];
	    if (cards[i] == 2019) id2019 = (i*n) % N;
	}
	return temp;
    }
    
    public static void main(String[] args) {
	CardShuffle cs = new CardShuffle(args[0]);

     }
}
