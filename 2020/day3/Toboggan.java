import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Toboggan {

    public ArrayList<String> lines;
    public boolean[][] map;
    int cols, rows;
    
    public Toboggan(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	cols = lines.get(0).length();
	rows = lines.size();
	map = new boolean[cols][rows];
	for (int j=0; j<rows; j++) {
	    for (int i=0; i<cols; i++) {
		map[i][j] = (lines.get(j).charAt(i) == '.') ? false : true;
	    }
	}
    }

    public long numberOfTrees(int dX, int dY) {
        long res = 0;
	int curX, curY;
	curX = curY = 0;
	while (curY < rows) {
	    if (map[curX][curY]) res++;
	    curX = (curX + dX) % cols;
	    curY += dY;
	}
	return res;
    }

    public long part1() {
	return numberOfTrees(3,1);
    }

    public long part2() {
	return (numberOfTrees(1,1) * numberOfTrees(3,1) * numberOfTrees(5,1) * numberOfTrees(7,1) * numberOfTrees(1,2));
    }

    
    public static void main(String[] args) {
	     Toboggan t = new Toboggan(args[0]);
	     IO.print("Part 1: " + t.part1());
	     IO.print("Part 2: " + t.part2());
     }
}
