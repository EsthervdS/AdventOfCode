import java.io.*;
import java.util.*;

public class Checksum {

    public static void main(String[] args) {
	int checksum = 0;
	ArrayList<String> lines = new ArrayList<String>();
	
	try {
	    int i=0;
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
	    String line;
	    while ((line = br.readLine()) != null) {
		lines.add(line);
		i++;
	    }
	    br.close();
	} catch (FileNotFoundException e) {
	    System.err.println("FileNotFoundException: " + e.getMessage());
	} catch (IOException e) {
	    System.err.println("IOException: " + e.getMessage());
	}
	System.out.println("Read " + lines.size() + " lines");

	for (int i=0; i<lines.size(); i++) {
	    //part 1: int min =10000;
	    //part 1: int max = -1;
	    int div = 0; // part 1: diff
	    String[] stringArray = lines.get(i).split("\t");
	    ArrayList<Integer> intsOfThisLine = new ArrayList<Integer>(20);
	    for (int j=0; j<stringArray.length; j++) {
		/* part 2 */
		int thisInt = Integer.parseInt(stringArray[j],10);
		intsOfThisLine.add(thisInt);

		//part 1: if (thisInt < min) min = thisInt;
		//part 1: if (thisInt > max) max = thisInt;
	    }
	    /* part 2 */
	    // iterate over all ints of this line and check for divisors
	    for (int j=0; j<intsOfThisLine.size(); j++) {
		for (int k=j+1; k<intsOfThisLine.size(); k++) {
		    int ij = intsOfThisLine.get(j);
		    int ik = intsOfThisLine.get(k);
		    if (ij > ik) {
			if (ij % ik == 0) div = ij / ik;
		    } else {
			if (ik % ij == 0) div = ik / ij;
		    }
		}
	    }
	    //part 1: diff = max - min;
	     
	    checksum += div; // part 1: += diff
	}

	System.out.println("Checksum = " + checksum);
	
    }
}
