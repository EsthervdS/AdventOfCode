
import util.*;
import java.util.*;

public class DiskDefragmentation {

    ArrayList<String> lines;
    int[][] grid;
    int[][] regLabel;
    int nCells;
    HashMap<Integer,TreeSet<Integer>> equiv;
    
    public DiskDefragmentation(int n) {
	grid = new int[n][n];
	regLabel = new int[n][n];
	for (int i=0; i<n; i++) {
	    for (int j=0; j<n; j++) {
		regLabel[i][j] = 0;
	    }
	}
	nCells = n;
	equiv = new HashMap<Integer,TreeSet<Integer>>();
    }


    public void buildGrid(String puzzle) {
	for (int i=0; i<nCells; i++) {
	    //iterate rows
	    String thisRow = "";
	    KnotHash kh = new KnotHash(puzzle + "-" + i);
	    String row = kh.computeKnotHash();
	    //get binarystring for this row
	    int j = 0;
	    while (j<row.length()) {
		//iterate over chars
		char c = row.charAt(j);
		int v = value(c);
		String bits = Integer.toBinaryString(v);
		if (bits.length() == 1) {
		    bits = "000" + bits;
		} else if (bits.length() == 2) {
		    bits = "00" + bits;
		} else if (bits.length() == 3) {
		    bits = "0" + bits;
		}
		thisRow += bits;
		j++;
	    }
	    fillRow(i,thisRow);
	}
    }

    private void fillRow(int rIndex, String bits) {
	for (int i=0; i<bits.length(); i++) {
	    if (bits.charAt(i) == '1') {
		grid[rIndex][i] = 1;
	    } else {
		grid[rIndex][i] = 0;
	    }
	}
    }

    public void addEquivalence(int smaller, int larger) {

	TreeSet<Integer> newChildren = new TreeSet<Integer>();
	TreeSet<Integer> sChildren = new TreeSet<Integer>();
	TreeSet<Integer> lChildren = new TreeSet<Integer>();
	TreeSet<Integer> sSiblings = new TreeSet<Integer>();
	TreeSet<Integer> lSiblings = new TreeSet<Integer>();

	newChildren.add(smaller);
	newChildren.add(larger);
	
	//get children of smaller
	if (equiv.containsKey(smaller)) {
	    newChildren.addAll(equiv.get(smaller));
	    equiv.remove(smaller);
	}

	//get siblings of smaller
	for (int k : equiv.keySet()) {
	    if (equiv.get(k).contains(smaller)) {
		newChildren.add(k);
		newChildren.addAll(equiv.get(k));
		equiv.remove(k);
		break;
	    }
	}

	//get children of larger
	if (equiv.containsKey(larger)) {
	    newChildren.addAll(equiv.get(larger));
	    equiv.remove(larger);
	}

	//get siblings of larger
	for (int k : equiv.keySet()) {
	    if (equiv.get(k).contains(larger)) {
		newChildren.add(k);
		newChildren.addAll(equiv.get(k));
		equiv.remove(k);
		break;
	    }
	}

	int newKey = newChildren.first();
	equiv.put(newKey, newChildren);
    }

    public int value(char c) {
	int val = 0;
	if (Character.isDigit(c)) {
	    //c is digit
	    val = Character.getNumericValue(c);
	} else {
	    // c is in [a,f]
	    val = c - '`' + 9;
	}
	return val;	
    }

    public static int ones(char c) {
	int val = 0;
	if (Character.isDigit(c)) {
	    //c is digit
	    val = Character.getNumericValue(c);
	} else {
	    // c is in [a,f]
	    val = c - '`' + 9;
	}
	return Integer.bitCount(val);
    }
    
    public static int ones(String str) {
	//in: hexadecimal string of 32 characters
	//out: number of ones in byte-representation of input
	int ones = 0;
	int i = 0;
	while (i<str.length()) {
	    char c = str.charAt(i);
	    ones += ones(c);
	    i++;
	}
	return ones;
    }

    public int computeRegions() {
	int regions = 1;
	/* first pass */
	for (int i=0; i<nCells; i++) {
	    for (int j=0; j<nCells; j++) {
		if (grid[i][j] == 1) {
		    boolean nbReg = false;
		    int nLab = -1, wLab = -1;
		    //check for north neighbor
		    if ((i>0) && (regLabel[i-1][j] != 0)) {
			nLab = regLabel[i-1][j];
		    }
		    //check for west neighbor
		    if ((j>0) && (regLabel[i][j-1] != 0)) {
			wLab = regLabel[i][j-1];
		    }

		    if ( (nLab > 0) || (wLab > 0) ) {

			//label this one with the min
			if (nLab > 0) {
			    if (wLab > 0) {
				regLabel[i][j] = Math.min(nLab,wLab);
			    } else {
				regLabel[i][j] = nLab;
			    }
			} else if (wLab > 0) regLabel[i][j] = wLab;
			
			if ( (nLab > 0) && (wLab > 0) ) {
			    //both, so larger is child of smaller
			    if (nLab < wLab) {
				addEquivalence(nLab, wLab);
				//check if nLab is a value of some other key
				
			    } else {
				if (nLab != wLab) {
				    addEquivalence(wLab, nLab);
				}
			    }
			}
		    
		    } else {
			regLabel[i][j] = regions;
			regions++;
		    }
		}
	    }
	}

	/* second pass */
	for (int i=0; i<nCells; i++) {
	    for (int j=0; j<nCells; j++) {
		if (regLabel[i][j] != 0) {
		    int sLab = -1, eLab = -1;
		    //check for south neighbor
		    if ((i<(nCells-1)) && (regLabel[i+1][j] != 0)) {
			//find min region#
			sLab = regLabel[i+1][j];
		    }

		    //check for east neighbor
		    if ((j<(nCells-1)) && (regLabel[i][j+1] != 0)) {
			eLab = regLabel[i][j+1];
		    }

		    for (int k : equiv.keySet() ) {
			if (equiv.get(k).contains(regLabel[i][j])) {
			    regLabel[i][j] = k;
			}
		    }
		    
		}
	    }
	}

	return countRegions();
    }

    public int countRegions() {
	int r = 0;
	TreeSet<Integer> regions = new TreeSet<Integer>();
	
	for (int i=0; i<nCells; i++) {
	    for (int j=0; j<nCells; j++) {
		if (regLabel[i][j] != 0) regions.add(regLabel[i][j]);
	    }
	}
	return regions.size();
    }
    
    public static void main(String[] args) {

	/* part 1 */
	String puzzle = "hwlqcszp";
	int nUsed = 0;
	for (int i=0; i<128; i++) {
	    KnotHash kh = new KnotHash(puzzle + "-" + i);
	    String out = kh.computeKnotHash();
	    nUsed += ones(out);
	}
	IO.print("Part 1: " + nUsed);

	/* part 2 */
	DiskDefragmentation dd = new DiskDefragmentation(128);
	dd.buildGrid(puzzle);
	IO.print("Part 2: "+dd.computeRegions());
	
    }
}
