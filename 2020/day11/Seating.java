import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Seating {

    public ArrayList<String> lines;
    public int[][] seats;
    public int rows, cols, occupied;
    public boolean stateChanged;
    
    public Seating(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	rows = lines.size();
	cols = lines.get(0).length();
	seats = new int[cols][rows];
	stateChanged = true;
	occupied = 0;

	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		char c = lines.get(j).charAt(i);
		// empty L is 0
		if (c=='L') seats[i][j] = 0;
		// occupied # is 1
		if (c=='#') seats[i][j] = 1;
		// floor . is 2
		if (c=='.') seats[i][j] = 2;
	    }
	}

    }

    public void printSeats() {
	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		char c = ' ';
		if (seats[i][j] == 0) c='L';
		if (seats[i][j] == 1) c='#';
		if (seats[i][j] == 2) c='.';
		System.out.print(c);
	    }
	    IO.print("");
	}
	IO.print("");
    }

    public int next(int i, int j) {
	int res = seats[i][j];
	if ((seats[i][j] == 0) && (occupiedNeighbors(i,j)==0)) {
	    res = 1;
	    stateChanged = true;
	    occupied++;
	}
	if ((seats[i][j] == 1) && (occupiedNeighbors(i,j)>=4)) {
	    res = 0;
	    stateChanged = true;
	    occupied--;
	}
	return res;
    }

    public int[][] nextSeats() {
	stateChanged = false;
	int[][] temp = new int[cols][rows];
	for (int i=0; i<cols; i++) {
	    for (int j=0; j<rows; j++) {
		temp[i][j] = next(i,j);
	    }
	}
	return temp;
    }

    public int occupiedNeighbors(int i, int j) {
	int res = 0;
	//top-left
	if ( (i>0) && (j>0) && (seats[i-1][j-1] == 1) ) res++;
	//left 
	if ( (i>0) && (seats[i-1][j] == 1) ) res++;
	//bottom-left
	if ( (i>0) && (j<rows-1) && (seats[i-1][j+1] == 1) ) res++;
	//bottom
	if ( (j<rows-1) && (seats[i][j+1] == 1) ) res++;
	//bottom-right
	if ( (i<cols-1) && (j<rows-1) && (seats[i+1][j+1] == 1) ) res++;
	//right
	if ( (i<cols-1) && (seats[i+1][j] == 1) ) res++;
	//top-right
	if ( (i<cols-1) && (j>0) && (seats[i+1][j-1] == 1) ) res++;
	//top
	if ( (j>0) && (seats[i][j-1] == 1) ) res++;

	return res;
    }
    
    public long part1() {
	while (stateChanged) {
	    seats = nextSeats();
	    //printSeats();
	}	
	return occupied;
    }

    public long part2() {
	return -1;
    }

    public static void main(String[] args) {
	Seating s = new Seating(args[0]);
	IO.print("Part 1: " + s.part1());
	IO.print("Part 2: " + s.part2());
    }
}
