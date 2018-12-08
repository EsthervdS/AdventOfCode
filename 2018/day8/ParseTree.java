import java.io.*;
import java.util.*;
import util.*;

public class ParseTree {

    public String line;
    public Tree thisNode;
    
    public ParseTree(String fileName) {
	line = IO.readFile(fileName).get(0);
    }

    public void parseTree(String s) {
	thisNode = new Tree(s);
	//IO.print(thisNode.toString());
    }
     
    public static void main(String[] args) {
	ParseTree pt = new ParseTree(args[0]);
	pt.parseTree(pt.line);
	IO.print("Part 1: " + pt.thisNode.part1());
	IO.print("Part 2: " + pt.thisNode.part2());
    }
}
