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
	boolean instOf = false;
	//p0 = this
	Pattern p1 = q.flipHorizontally();
	Pattern p2 = q.flipVertically();
	Pattern p3 = q.rotateClockwise();
	Pattern p4 = q.rotateCounterclockwise();
	Pattern p5 = p1.flipVertically();
	Pattern p6 = p3.flipVertically();
	Pattern p7 = p4.flipVertically();
	Pattern p8 = p3.flipHorizontally();
	Pattern p9 = p4.flipHorizontally();
	return ( matches(q) || matches(p1) || matches(p2) || matches(p3) || matches(p4) || matches(p5) || matches(p6) || matches(p7) || matches(p8) || matches(p9) );

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
	String str = this.toString();
	int c=0;
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if (p[i][j]) c++;
	    }
	}

	IO.print( "First: " + (str.length() - str.replace("#", "").length()) + " and second " + c);
	return ( str.length() - str.replace("#", "").length() );
	

	
    }
}
