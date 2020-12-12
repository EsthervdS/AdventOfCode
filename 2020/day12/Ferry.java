import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Ferry {

    public ArrayList<String> lines;
    Ship ship;
    
    public Ferry(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	ship = new Ship();
    }

    public long part1() {
	for (String line : lines) ship.move(line);
	return Math.abs(ship.x + ship.y);
    }

    public long part2() {
	return -1;
    }

    
    public static void main(String[] args) {
	Ferry f = new Ferry(args[0]);
	IO.print("Part 1: " + f.part1());
	IO.print("Part 2: " + f.part2());
    }
}
