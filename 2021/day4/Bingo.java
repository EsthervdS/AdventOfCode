import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
import java.math.*;

public class Bingo {

    public ArrayList<String> lines;
    public ArrayList<Integer> calls;
    public ArrayList<Board> boards;
	
    public Bingo(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	//read calls
	calls = new ArrayList<Integer>();
	String[] intStrings = lines.get(0).split(",");
	for (String nr : intStrings) {
	    calls.add(Integer.parseInt(nr));
	}

	//read boards
	boards = new ArrayList<Board>();
	for (int i=2; i<lines.size(); i+=6) {
	    readBoard(i);
	}
    }

    public void readBoard(int i) {
	int j=0;
	int[][] temp = new int[5][5];
	while (j<5) {
	    String cur = lines.get(i+j);
	    for (int k=0; k<5; k++) {
	        temp[k][j] = Integer.parseInt(new String(cur.charAt(k*3) + "" + cur.charAt(k*3+1)).trim());
	    }
	    j++;
	}
	Board b = new Board(temp);
	boards.add(b);
    }
    
    public int part1() {
	int score = 0;
	for (int c : calls) {
	    for (Board b : boards) {
		b.processCall(c);
		if (b.wins()) {
		    score = b.score(c);
		    break;
		}
	    }
	    if (score != 0) break;
	}
	return score;
    }
   
    public int part2() {
	for (Board b : boards) b.reset();
	int score = 0;
	for (int c : calls) {
	    int i=0;
	    while (i<boards.size()) {
		Board b = boards.get(i);
		b.processCall(c);
		if (b.wins()) {
		    if (boards.size() == 1) {
			score = b.score(c);
			boards.remove(b);
			break;
		    } else {
			boards.remove(b);
		    }
		} else {
		    i++;
		}
	    }
	    if (score > 0) break;
	}
	return score;
    }

    public static void main(String[] args) {
	     Bingo b = new Bingo(args[0]);
	     IO.print("Part 1: " + b.part1());
	     IO.print("Part 2: " + b.part2());
     }
}
