import util.*;

public class Tree {

    public int nChildren, nMeta;
    public StringBuilder remainder;
    public int[] meta;
    public Tree[] children;
    
    public Tree(String parseString) {
	
	if (parseString!="") {
	    //parse number of children and number of meta data
	    String[] parts = parseString.split(" ");
	    nChildren = Integer.parseInt(parts[0]);
	    nMeta = Integer.parseInt(parts[1]);
	    meta = new int[nMeta];
	    children = new Tree[nChildren];
	    //compute remaining string
	    remainder = new StringBuilder("");
	    for (int i=0; (2+i)<parts.length; i++) {
		remainder.append(parts[2+i]+" ");
	    }
	    //parse children
	    StringBuilder rem = new StringBuilder(remainder);
	    for (int c=0; c<nChildren; c++) {
		Tree child = new Tree(rem.toString());
		children[c] = child;
		rem = child.remainder;
	    }
	    //get new remainder
	    if (nChildren > 0) remainder = new StringBuilder(children[nChildren-1].remainder);
	    //parse meta data
	    parts = remainder.toString().split(" ");
	    for (int m=0; m<nMeta; m++) {
		meta[m] = Integer.parseInt(parts[m]);
	    }
	    //compute new remainder
	    remainder = new StringBuilder();
	    for (int i=0; (nMeta+i)<parts.length; i++) {
		remainder.append(parts[nMeta+i] + " ");
	    }
	} else {
	    nChildren = 0;
	    nMeta = 0;
	    remainder = new StringBuilder("");
	}
    }

    public int part1() {
	int sum = 0;
	for (int m=0; m<nMeta; m++) {
	    sum += meta[m];
	}
	for (int c=0; c<nChildren; c++) {
	    sum += children[c].part1();
	}
	return sum;
    }

    public int part2() {
	int sum = 0;
	if (nChildren==0) {
	    for (int m=0; m<nMeta; m++) {
		sum += meta[m];
	    }
	} else {
	    for (int m=0; m<nMeta; m++) {
		if (meta[m] <= nChildren) {
		    sum += children[meta[m]-1].part2();
		}
	    }
	}
	return sum;
    }
    
    public String toString() {
	String res= "(c=" + nChildren + ", m = " + nMeta + " : ";
	for (int i=0; i<nChildren; i++) {
	    res += children[i].toString();
	}
	for (int m=0; m<nMeta; m++) {
	    if (m==nMeta-1) {
		res += (meta[m]);
	    } else {
		res += (meta[m] + " ");
	    }
	}
	res += ") ";				       
	return res;
    }
}
