import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Game {

    public ArrayList<String> lines;
    public ArrayList<Integer> numbers;
    public HashMap<Long,Pair> spoken;
    
    public Game(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	numbers = new ArrayList<Integer>();

	String[] nrS = lines.get(0).split(",");
	for (String n : nrS) {
	    numbers.add(Integer.parseInt(n));
	}
    }

    public long run(long times) {
	spoken = new HashMap<Long,Pair>();

	for (int i=1; i<numbers.size()+1; i++) {
	    Pair p = new Pair(i);
	    spoken.put((long) numbers.get(i-1),p);
	}

	long turn = numbers.size()+1;
	long current = numbers.get(numbers.size()-1);

	while (turn <= times) {

	    if (spoken.get(current).previous == -1) {
		//new
		current = 0;
		Pair p = new Pair(turn,spoken.get(current).latest);
		spoken.put(current, p);
	    } else {
		//not new
		Pair spokenTurns = spoken.get(current);
		current = (spokenTurns.latest - spokenTurns.previous);
		Pair p = spoken.get(current);
		if (p == null) {
		    p = new Pair(turn);
		}  else {
		    p = new Pair(turn,p.latest);
		}
		spoken.put(current,p);
	    }
	    turn++;
	}
	return current;
    }

    public long part1() {
	return run(2020L);
    }

    public long part2() {
	return run(30000000L);
    }

    
    public static void main(String[] args) {
	Game g = new Game(args[0]);
	IO.print("Part 1: " + g.part1());
	IO.print("Part 2: " + g.part2());
    }
}
