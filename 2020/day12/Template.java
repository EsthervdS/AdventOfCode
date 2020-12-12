import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Template {

    public ArrayList<String> lines;
    
    public Template(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
    }

    public long part1() {
	return -1;
    }

    public long part2() {
	return -1;
    }

    
    public static void main(String[] args) {
	Template t = new Template(args[0]);
	IO.print("Part 1: " + t.part1());
	IO.print("Part 2: " + t.part2());
    }
}
