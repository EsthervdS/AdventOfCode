import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Password {

    public int[] curnum,endnum;
    
    public Password() 
    {
	curnum = new int[] {1,8,3,5,6,4};
	endnum = new int[] {6,5,7,4,7,4};
	int c = 0;

	while (!equals(curnum,endnum)) {
	    if (satisfiesAll(curnum,6)) {
		c++;
	    }
	    increase(curnum);
	}
	IO.print("Part 1: " + c);

	c=0;
	curnum = new int[] {1,8,3,5,6,4};
	
	while (!equals(curnum,endnum)) {
	    if (satisfiesAll(curnum,2)) {
		c++;
	    }
	    increase(curnum);
	}
	IO.print("Part 2: " + c);
    }

	
    public boolean equals(int[] num, int[] num2) {
	boolean b = true;
	for (int i=0; i<num.length;i++) {
	    b = b && (num[i] == num2[i]);
	}
	return b;
    }
    
    public boolean satisfiesAll(int[] num, int con) {
	return (adjacent(num,con) && increasing(num));
    }

    public boolean adjacent(int[] num, int con) {
	int curdig = 1;
	boolean b = false;
	for (int i=1; i<num.length;i++) {
	    if (num[i-1] == num[i]) {
		curdig++;
	    } else {
		if ((curdig > 1) && (curdig <= con)) {
		    b = true;
		}
		curdig = 1;
	    }
	}
	if ((curdig > 1) && (curdig <= con)) b = true;
	return b;
    }
    
    public boolean increasing(int[] num) {
	boolean b = true;
	for (int i=1; i<num.length;i++) {
	    b = b && (num[i-1] <= num[i]);
	}
	return b;
    }

    public void increase(int[] num) {
	//increase one
	int i=num.length-1;
	while (i>=0) {
	    if (num[i]==9) {
		num[i]=0;
		i--;
	    } else {
		num[i]++;
		break;
	    }
	}
    }

    public void print(int[] num) {
	String s = "";
	for(int i=0; i<num.length; i++) {
	    s = s+num[i];
	}
	IO.print(s);
    }
    
    public static void main(String[] args) {
	Password p = new Password();
    }
}
