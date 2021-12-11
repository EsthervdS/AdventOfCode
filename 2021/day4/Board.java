import util.*;

public class Board {

    int[][] numbers;
    boolean[][] checked;
    
    public Board(int[][] nrs) {
	numbers = new int[5][5];
	checked = new boolean[5][5];

	for (int i=0; i<5; i++) {
	    for (int j=0; j<5; j++) {
		numbers[i][j] = nrs[i][j];
		checked[i][j] = false;
	    }
	}
    }

    public void reset() {
	for (int i=0; i<5; i++) {
	    for (int j=0; j<5; j++) {
		checked[i][j] = false;
	    }
	}
    }
    
    public void processCall(int call) {
	for (int i=0; i<5; i++) {
	    for (int j=0; j<5; j++) {
		if (numbers[i][j] == call) checked[i][j] = true;
	    }
	}
    }
    
    public int score(int lastCall) {
	int sum = 0;
	for (int i=0; i<5; i++) {
	    for (int j=0; j<5; j++) {
		if (!checked[i][j]) sum += numbers[i][j];
	    }
	}
	return sum * lastCall;
    }

    public boolean wins() {
	boolean res = false;
	//check rows
	for (int j=0; j<5; j++) {
	    if (checked[0][j] && checked[1][j] && checked[2][j] && checked[3][j] && checked[4][j]) res = true;
	}
	
	//check cols
	for (int i=0; i<5; i++) {
	    if (checked[i][0] && checked[i][1] && checked[i][2] && checked[i][3] && checked[i][4]) res = true;
	}

	return res;
    }
    
    public String toString() {
	String res = "";
	for (int j=0; j<5; j++) {
	    for (int i=0; i<5; i++) {
		if (numbers[i][j] < 10) res = res + " ";
		if (checked[i][j]) {
		    res  = res + "X" + numbers[i][j] + " ";
		} else {
		    res = res + " " + numbers[i][j] + "  ";
		}
	    }
	    res = res + "\n";
	}
	return res;
    }
}
