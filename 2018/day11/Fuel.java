import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Fuel {
    int serial;
    int max;
    int maxPower;
    int[][][] power;
    int maxI,maxJ,maxSize;
    
    public Fuel(int s, int m) {
	serial = s;
	max = m;
	maxPower = -1*Integer.MAX_VALUE;
	power = new int[m+1][m+1][m+1];
    }

    public int fuel(int i, int j) {
	int rackID = i+10;
	int temp = rackID * j + serial;
	temp = temp * rackID;
	temp = temp / 100;
	temp = temp % 10;
	return (temp - 5);
    }

    public void findLargest(int size) {
	maxPower = -1*Integer.MAX_VALUE;
	maxI = -1; maxJ = -1; maxSize = -1;
	for (int i=1; i<=max; i++) {
	    for (int j=1; j<=max; j++) {
		power[i][j][1] = fuel(i,j);
	    }
	}
	for (int s=2; s<=size; s++) {
	    for (int i=1; i<=(max-s); i++) {
		for (int j=1; j<=(max-s); j++) {
		    int sum = power[i][j][s-1];
		    for (int k=0; k<s; k++) {
			//add left column to the right
			sum += power[i+s-1][j+k][1];
			//add bottom row 
			sum += power[i+k][j+s-1][1];
		    }
		    //add bottom right corner
		    sum -= power[i+s-1][j+s-1][1];
		    power[i][j][s] = sum;
		    if (sum>maxPower) {
			maxPower = sum;
			maxI = i;
			maxJ = j;
			maxSize = s;
		    }
		}
	    }
	}
    }
    
    public static void main(String[] args) {
	Fuel f = new Fuel(8979,300);
	f.findLargest(3);
        IO.print("Part 1: "+f.maxI+","+f.maxJ);
	f.findLargest(300);
        IO.print("Part 2: "+f.maxI+","+f.maxJ+","+f.maxSize);
	
    }
}
