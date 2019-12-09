import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Day9 {

    public ArrayList<String> lines;
    public ArrayList<Long> longs;
    public Intcode ic;

    public Day9(String fileName, int i) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	longs = new ArrayList<Long>();
	String[] s = lines.get(0).split(",");
	
	for (String number : s) {
	    longs.add(Long.parseLong(number,10));
	}
	
	ic = new Intcode(longs,i);
	while (ic.state == Intcode.RUNNING) {
	    ic.step();
	}
    }

    public static void main(String[] args) {
	Day9 t = new Day9(args[0],1);
	t = new Day9(args[0],2);
     }
}
