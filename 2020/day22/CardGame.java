import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class CardGame {

    public ArrayList<String> lines;
    public ArrayList<Integer> player1,player2;
    
    public CardGame(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	player1 = new ArrayList<Integer>();
	player2 = new ArrayList<Integer>();
	
	int i=1;
	while (!lines.get(i).equals("")) {
	    player1.add(Integer.parseInt(lines.get(i)));
	    i++;
	}
	i++; i++;
	while (i<lines.size()) {
	    player2.add(Integer.parseInt(lines.get(i)));
	    i++;
	}
    }

    public long part1() {
	while ((player1.size()>0) && (player2.size()>0)) {
	    turn1();
	}
	if (player1.size() == 0) {
	    return score(player2);
	} else {
	    return score(player1);
	}
    }

    public void turn1() {
	int one = player1.remove(0);
	int two = player2.remove(0);
	if (one > two) {
	    player1.add(one);
	    player1.add(two);
	} else {
	    player2.add(two);
	    player2.add(one);
	}
    }

    public long score(ArrayList<Integer> list) {
	long res = 0;
	for (int i=1; i<=list.size(); i++) {
	    res += i*(list.get(list.size()-i));
	}
	return res;
    }
    
    public long part2() {
	return -1;
    }

    
    public static void main(String[] args) {
	CardGame cg = new CardGame(args[0]);
	IO.print("Part 1: " + cg.part1());
	IO.print("Part 2: " + cg.part2());
    }
}
