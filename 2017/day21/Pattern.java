import util.*;

public class Pattern {

    int size;
    boolean[][] p;
    
    public Pattern(String input) {
	if (!input.equals("")) {
	    String[] lines = input.split("/");
	    size = lines.length;
	    p = new boolean[size][size];
	    
	    for (int i=0; i<size; i++) {
		for (int j=0; j<size; j++) {
		    if (lines[i].charAt(j) == '#') {
			p[i][j] = true;
		    } else {
			p[i][j] = false;
		    }
		}
	    }
	} else {
	    size = 0;
	}
    }

    public Pattern(Pattern q) {
	int size = q.size;
	p = new boolean[size][size];
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		p[i][j] = q.p[i][j];
	    }
	}
    }

    public Pattern(Pattern[][] fractal, int nS, int sS) {
	//fractal is 2D-array of Patterns (squares)
	//nS is #squares per dimension
	//sS is squaresize
	size = nS*sS;
	p = new boolean[size][size];
	for (int i=0; i<nS; i++) {
	    for (int j=0; j<nS; j++) {
		for (int k=0; k<sS; k++) {
		    for (int l=0; l<sS; l++) {
			p[i*sS + k][j*sS + l] = fractal[i][j].p[k][l];
		    }
		}
	    }
	}
    }
    
    public Pattern flipHorizontally() {
	// ..#/..#/..# => #../#../#..
	Pattern pt = new Pattern("");
	pt.size = this.size;
	pt.p = new boolean[this.size][this.size];
	
	for (int i=0;i<size;i++) {
	    //rows stay the same
	    for (int j=0; j<size; j++) {
		//cols flipped
		pt.p[i][j] = p[i][size-1-j];
	    }
	}
	return pt;
    }

    public Pattern flipVertically() {
	// ###/.../... => .../.../###
	Pattern pt = new Pattern("");
	pt.size = this.size;
	pt.p = new boolean[this.size][this.size];
	
	for (int i=0;i<size;i++) {
	    //rows stay the same
	    for (int j=0; j<size; j++) {
		//cols flipped
		pt.p[i][j] = p[size-1-i][j];
	    }
	}
	return pt;	
    }

    public Pattern rotateClockwise() {
	// #../#../#.. => ###/.../...
	Pattern pt = new Pattern("");
	pt.size = this.size;
	pt.p = new boolean[this.size][this.size];

	//123 => 741 
	//456 => 852 
	//789 => 963 

	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		pt.p[i][j] = p[size-1-j][i];
	    }
	}
	return pt;
    }

    public Pattern rotateCounterclockwise() {
	// #../#../#.. => .../.../###
	Pattern pt = new Pattern("");
	pt.size = this.size;
	pt.p = new boolean[this.size][this.size];

	//123 => 369 
	//456 => 258
	//789 => 147

	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		pt.p[i][j] = p[j][size-1-i];
	    }
	}

	return pt;
    }

    public boolean matches(Pattern q) {
	for (int i=0; i<size; ++i) {
	    for (int j=0; j<size; j++) {
		if (q.p[i][j] != p[i][j]) return false;
	    }
	}
	return true;
    }


    public boolean isInstanceOf(Pattern q) {

	//p0 = q
	Pattern p1 = q.rotateClockwise(); //90
	Pattern p2 = p1.rotateClockwise(); //180
	Pattern p3 = p2.rotateClockwise(); //270
	Pattern p4 = q.flipHorizontally(); //mirror
	Pattern p5 = p4.rotateClockwise(); //90 mirror
	Pattern p6 = p5.rotateClockwise(); //180 mirror
	Pattern p7 = p6.rotateClockwise(); //270 mirror
	if (matches(q)) return true;
	if (matches(p1)) return true;
	if (matches(p2)) return true;
	if (matches(p3)) return true;
	if (matches(p4)) return true;
	if (matches(p5)) return true;
	if (matches(p6)) return true;
	if (matches(p7)) return true;
	
	return false;

    }

    public String toString() {
	String res = "";
	//res += "Pattern of size: " + size + " x " + size + "\n";
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if (p[i][j]) {
		    res+="#";
		} else {
		    res+=".";
		}

	    }
	    res += "\n";
	}
	return res;
    }

    public int pixelsOn() {

	int c=0;
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if (p[i][j]) c++;
	    }
	}
	return c;
	
    }
}
