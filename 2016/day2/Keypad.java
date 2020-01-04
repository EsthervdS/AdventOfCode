import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Keypad {

    public ArrayList<String> lines;
    public char[][] pad;
    public int curx,cury,maxcur;
    public String answer;
    
    public Keypad(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	part1();
	part2();
	
    }

    public void part1() {
	pad = new char[][] {{'1','2','3'},{'4','5','6'},{'7','8','9'}};
	maxcur = 3;
	curx = cury = 1;
	processAllMoves();
	IO.print("Part 1: " + answer);
    }

    public void part2() {
	pad = new char[][] {
	    {'X','X','1','X','X'},
	    {'X','2','3','4','X'},
	    {'5','6','7','8','9'},
	    {'X','A','B','C','X'},
	    {'X','X','D','X','X'}
	};

	maxcur = 5;
	curx = 0;
	cury = 2;
	processAllMoves();
	IO.print("Part 2: " + answer);
    }
    
    public void processAllMoves() {
	answer = "";
	for (String line : lines) {
	    for (int i=0; i<line.length(); i++) {
		processMove(line.charAt(i));
	    }
	    answer += pad[cury][curx];
	}
    }
    
    public void processMove(char c) {
	
	switch(c) {
	case 'U':
	    if (cury > 0) {
		if (pad[cury-1][curx] != 'X') {
		    cury--;
		}
	    }
	    break;
	case 'D':
	    if (cury < maxcur-1) {
		if (pad[cury+1][curx] != 'X') {
		    cury++;
		}
	    }
	    break;
	case 'L':
	    if (curx > 0) {
		if (pad[cury][curx-1] != 'X') {
		    curx--;
		}
	    }
	    break;
	case 'R': 
	    if (curx < maxcur-1) {
		if (pad[cury][curx+1] != 'X') {
		    curx++;
		}
	    }
	    break;
        default: break;
	}
    }

    public static void main(String[] args) {
	Keypad t = new Keypad(args[0]);
     }
}
