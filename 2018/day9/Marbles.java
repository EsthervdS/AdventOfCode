import java.io.*;
import java.util.*;
import util.*;

public class Marbles {

    public int nPlayers;
    public int currentPos;
    public int nMarbles;
    public int marblesPlayed;
    public int lastMarble;
    public ArrayList<Integer> marbles;
    public int turn;
    public int[] score;
    
    public Marbles(int nP, int lM) {
	nPlayers = nP;
	lastMarble = lM;
	currentPos = 0;
	nMarbles = 1;
	marbles = new ArrayList<Integer>();
	marbles.add(0);
	marblesPlayed = 0;
	turn = 0;
	score = new int[nPlayers+1];
	for (int i=1; i<nPlayers+1; i++) {
	    score[i] = 0;
	}
    }


    public void printMarbles() {
	System.out.print("[" + turn + "]");
	for (int i=0; i<marbles.size(); i++) {
	    if (i==currentPos) {
		System.out.print("("+marbles.get(i)+")");
	    } else {
		System.out.print(" "+marbles.get(i)+" ");
	    }
	}
	System.out.println("");
    }

    public void turn() {
	turn = (turn % nPlayers) + 1;
	marblesPlayed++;
	if (marblesPlayed == (nMarbles/20)) IO.print(".");
	int insertPos = 1;
	if (marblesPlayed % 23 != 0) {
	    if (marblesPlayed>2) {
		if ((currentPos+2) == marbles.size()) {
		    marbles.add(marblesPlayed);
		    insertPos = marbles.size()-1;
		} else {
		    insertPos = (currentPos+2) % marbles.size();
		    marbles.add(insertPos,marblesPlayed);
		}
	    } else {
		marbles.add(1,marblesPlayed);
	    }
	    currentPos = insertPos;
	} else {
	    //23
	    score[turn] += marblesPlayed;
	    int removePos = currentPos - 7;
	    if (removePos < 0) removePos = (currentPos + marbles.size() - 7);
	    score[turn] += marbles.get(removePos);
	    marbles.remove(removePos);
	    currentPos = removePos;
	}
	//printMarbles();
    }




    public int maxScore() {
	int maxS = -1;
	for (int i=1; i<=nPlayers; i++) {
	    if (score[i] > maxS) maxS = score[i];
	}
	return maxS;
    }
    
    public static void main(String[] args) {
	int max = 71307;
	int nPlayers = 439;
	Marbles m = new Marbles(nPlayers,max);
	//m.printMarbles();
	for (int i=0; i<max; i++) m.turn();
	IO.print("Part 1: " + m.maxScore());
	m = new Marbles(nPlayers,max*100);
	for (int i=0; i<max*100; i++) m.turn();
	IO.print("Part 2: " + m.maxScore());
	
    }
}
