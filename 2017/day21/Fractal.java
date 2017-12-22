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
	long starttime = System.currentTimeMillis();
	for (int it=1;it<=18; it++) {
	    
	    long elapsed = System.currentTimeMillis();
	    
	    if (fractalSize % 2 == 0) {
		squareSize = 2;
	    } else {
		squareSize = 3;
	    }
	    
	    int nSquares = fractalSize / squareSize; // per dimension

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

	    int perc = 1;
	    long startIteration = System.currentTimeMillis();
	    for (int i=0; i<nSquares; i++) {
		
		for (int j=0; j<nSquares; j++) {
		    for (Pattern q : rules.keySet()) {
			
			if (q.size == squares[i][j].size) {
			    if (squares[i][j].isInstanceOf(q)) {
				//create new pattern based on rule
				newFractal[i][j] = rules.get(q);
			    } 
			}
		    }
		}
	    }

	    //create new fractal from new patterns
	    squareSize++;
	    currFractal = new Pattern(newFractal,nSquares,squareSize);
	    
	    if (it==5) IO.print("Part 1: " + currFractal.pixelsOn());

	}
	IO.print("Part 2: " + currFractal.pixelsOn());
    }

    private String patternString(boolean[][] p, int s, int sI, int sJ) {
	//create pattern input string from sxs pattern  in array p starting at index (sI,sJ) 
	StringBuilder res = new StringBuilder("");
	for (int i =sI; i<(sI+s); i++) {
	    for (int j=sJ; j<(sJ+s); j++) {
		if (p[i][j]) {
		    res.append('#');
		} else {
		    res.append('.');
		}
	    }
	    res.append('/');
	}
	return res.toString();
    }
    
    public static void main(String[] args) {
	Fractal f = new Fractal(args[0]);
	f.process();
    }
}
