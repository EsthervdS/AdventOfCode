import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Forms {

    public ArrayList<String> lines;
    public ArrayList<Group> groups;
    
    public Forms(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	int i=0;
	ArrayList<String> cur = new ArrayList<String>();
	groups = new ArrayList<Group>();
	
	while (i < lines.size()) {
	    String line = lines.get(i);	    
	    if (line.length() == 0) {
		groups.add(new Group(cur));
		cur = new ArrayList<String>();
	    } else {
		cur.add(line);
	    }
	    i++;
	}
	groups.add(new Group(cur));
    }

    public long part1() {
	int res = 0;
	for (Group g : groups) res += g.count1();
	return res;
    }

    public long part2() {
	int res = 0;
	for (Group g : groups) res += g.count2();
	return res;
    }

    
    public static void main(String[] args) {
	Forms f = new Forms(args[0]);
	IO.print("Part 1: " + f.part1());
	IO.print("Part 2: " + f.part2());
    }
}
