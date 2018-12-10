import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Marbles {

    public int nPlayers;
    public Marble currentMarble;
    public int nMarbles;
    public int marblesPlayed;
    public int lastMarble;
    public Marble head,last;
    public int turn;
    public long[] score;
    
    public Marbles(int nP, int lM) {
	nPlayers = nP;
	lastMarble = lM;
	nMarbles = 1;
	head = new Marble(0);
	last = head;
	currentMarble = head;
	marblesPlayed = 0;
	turn = 0;
	score = new long[nPlayers+1];
	for (int i=1; i<nPlayers+1; i++) {
	    score[i] = 0;
	}
    }


    public void printMarbles() {
	System.out.print("[" + turn + "]");
	Marble it = head;
	while (it != null) {
	    if (it == currentMarble) {
		System.out.print("("+it.value+")");
	    } else {
		System.out.print(" "+it.value+" ");
	    }
	    it = it.next;
	}
	System.out.println("");
    }

    public void turn() {
	turn = (turn % nPlayers) + 1;
	marblesPlayed++;
	if (marblesPlayed == (nMarbles/20)) IO.print(".");
	int insertPos = 1;
	if (marblesPlayed % 23 != 0) {
	    //insertM is place where new marble should be inserted
	    Marble insertM;
	    if (marblesPlayed == 1) {
		insertM = last.next;
	    } else if (marblesPlayed == 2) {
		insertM = head.next;
	    } else {
		insertM = currentMarble.next;
		if (insertM == null) {
		    insertM = head.next;
		} else {
		    insertM = insertM.next;
		}
	    }

	    Marble thisM = new Marble(marblesPlayed);
	    //special case: inserting at the end of the list
	    if (insertM == null) {
		last.next = thisM;
		thisM.prev = last;
		last = thisM;
	    } else if (insertM == head) {
		//special case: inserting at the beginning of the list
		thisM.next = head;
		head.prev = thisM;
		head = thisM;
	    } else {
		//regular case
		Marble temp = insertM.prev;
		insertM.prev = thisM;
		thisM.next = insertM;
		thisM.prev = temp;
		if (temp != null) temp.next = thisM;
	    }
	    currentMarble = thisM;
	} else {
	    //23
	    score[turn] += marblesPlayed;
	    Marble removeM = currentMarble;
	    for (int i=0; i<7; i++) {
		if (removeM.prev == null) {
		    removeM = last;
		} else {
		    removeM = removeM.prev;
		}
	    }
	    score[turn] += removeM.value;
	    removeM.prev.next = removeM.next;
	    removeM.next.prev = removeM.prev;
	    currentMarble = removeM.next;
	}
	//printMarbles();
    }




    public long maxScore() {
	long maxS = -1;
	for (int i=1; i<=nPlayers; i++) {
	    if (score[i] > maxS) maxS = score[i];
	}
	return maxS;
    }
    
    public static void main(String[] args) {
	int nP = Integer.parseInt(args[0]);
	int max = Integer.parseInt(args[1]);	
	Marbles m = new Marbles(nP,max);
	//m.printMarbles();
	Instant before = Instant.now();
	
	for (int i=0; i<max; i++) m.turn();
	IO.print("Part 1: " + m.maxScore());
	Instant after = Instant.now();
	long difference = Duration.between(before, after).toMillis(); 
	IO.print(difference+"ms");
	m = new Marbles(nP,max*100);
	before = Instant.now();
	for (int i=0; i<max*100; i++) m.turn();
	IO.print("Part 2: " + m.maxScore());
	after = Instant.now();
	difference = Duration.between(before, after).toMillis(); 
	IO.print(difference+"ms");
	
    }
}
