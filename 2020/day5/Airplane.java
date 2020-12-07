import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Airplane {

    public ArrayList<String> lines;
    
    public Airplane(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
    }

    public long getRow(String s) {
	//first seven chars define row nr
	int min = 0;
	int max = 127;
	int i=0;
	while (i<7) {
	    if (s.charAt(i) == 'F') {
		max = min + (max-min)/2;
	    } else {
		//'B'
		min = min + (max-min)/2 + 1;
	    }
	    i++;
	}
	return min;
    }

    public long getColumn(String s) {
	//final three chars define col nr
	int min = 0;
	int max = 7;
	int i=7;
	while (i<10) {
	    if (s.charAt(i) == 'L') {
		max = min + (max-min)/2;
	    } else {
		//'R'
		min = min + (max-min)/2 + 1;
	    }
	    i++;
	}
	return min;
    }

    public long getID(String s) {
	return getRow(s) * 8 + getColumn(s);
    }
    
    public long part1() {
	long maxID = -1;
	for(String line : lines) {
	    long ID = getID(line);
	    if (ID > maxID) {
		maxID = ID;
	    }
	}
	return maxID;
    }

    public long part2() {
	//IDs run from 0 to 1023
	boolean[] seatsTaken = new boolean[1024];
	for (int i=0; i<1024; i++) seatsTaken[i] = false;

	for(String line : lines) {
	    int ID = (int) getID(line);
	    seatsTaken[ID] = true;
	}
	int k = 0;
	while (!seatsTaken[k]) k++;
	while (seatsTaken[k]) k++;
	return k;
    }

    
    public static void main(String[] args) {
	Airplane a = new Airplane(args[0]);
	long timeNano = System.nanoTime(); 
	
	IO.print("Parse input: " + (System.nanoTime() - timeNano)/1000  + "µs");
	timeNano = System.nanoTime(); 
	IO.print("Part 1: " + a.part1());
	IO.print("Part 1: " + (System.nanoTime() - timeNano)/1000 + "µs");
	timeNano = System.nanoTime();
	IO.print("Part 2: " + a.part2());
	IO.print("Part 2: " + (System.nanoTime() - timeNano)/1000 + "µs");
    }
}
