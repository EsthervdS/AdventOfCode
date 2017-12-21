import util.*;
import java.util.*;

public class Fractal {

    ArrayList<String> lines;
    HashMap<Pattern,Pattern> rules;
    int squareSize, fractalSize;
    Pattern currFractal;
    
    public Fractal(String fileName) {
	rules = new HashMap<Pattern,Pattern>();
	lines = IO.readFile(fileName);
	for (String line : lines) {
	    String str = line.split(" => ")[0].trim();
	    Pattern p = new Pattern(str);
	    String str2 = line.split(" => ")[1].trim();
	    Pattern q = new Pattern(str2);
	    rules.put(p,q);
	    squareSize = 3;
	    fractalSize = 3;
	    currFractal = new Pattern(".#./..#/###");

	}
	IO.print("Loaded " + rules.size() + " rules.");
    }

    public void process() {
	for (int it=1;it<=5; it++) {
	    IO.print("============ CURRENT FRACTAL before iteration " + it + " =======");
	    IO.print(currFractal.toString());
	    IO.print(currFractal.pixelsOn()+"\n=====\n");
	    
	    int nSquares = fractalSize / squareSize; // per dimension
	    //5 iterations
	    Pattern[][] squares = new Pattern[nSquares][nSquares];
	    
	    for (int i=0; i<nSquares; i++) {
		for (int j=0; j<nSquares; j++) {
		    //create patterns from current fractal (list?)
		    //square runs from i*squareSize to (i+1)*squareSize exclusive (eg 4 to 6)
		    String ps = patternString(currFractal.p,squareSize,i*squareSize,j*squareSize);
		    Pattern temp = new Pattern(ps);
		    squares[i][j] = temp;
		}
	    }


	    //increase fractalSize by #squares (each square adds 1)	    
	    fractalSize += nSquares;

	    Pattern[][] newFractal = new Pattern[nSquares][nSquares];
	    
	    for (int i=0; i<nSquares; i++) {
		for (int j=0; j<nSquares; j++) {
		    for (Pattern q : rules.keySet()) {
			if (q.size == squares[i][j].size) {
			    if (squares[i][j].isInstanceOf(q)) {
				//create new pattern based on rule
				newFractal[i][j] = rules.get(q);
				if (i==0 && j==1) {
				    IO.print("Match!");
				    IO.print(squares[i][j].toString());
				    IO.print("matches");
				    IO.print(q.toString());
				}
			    } 
			}
		    }
		}
	    }

	    //create new fractal from new patterns
	    squareSize++;
	    currFractal = new Pattern(newFractal,nSquares,squareSize);

	    //reset square size for next iteration
	    if (squareSize == 4) squareSize = 2;
	    if (squareSize == 3) squareSize = 3;

	}
	IO.print("Final fractal");
	IO.print(currFractal.toString());
	IO.print("Part 1: " + currFractal.pixelsOn());
    }

    private String patternString(boolean[][] p, int s, int sI, int sJ) {
	//create pattern input string from sxs pattern  in array p starting at index (sI,sJ) 
	String res = "";
	for (int i =sI; i<(sI+s); i++) {
	    for (int j=sJ; j<(sJ+s); j++) {
		if (p[i][j]) {
		    res += "#";
		} else {
		    res += ".";
		}
	    }
	    res += "/";
	}
	return res;
    }
    
    public static void main(String[] args) {
	Fractal f = new Fractal(args[0]);
	f.process();
    }
}
