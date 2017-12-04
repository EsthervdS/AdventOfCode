import java.util.*;
import java.io.*;

public class Passphrase {
    public static ArrayList<String> lines = new ArrayList<String>();	

    public static void readFile(String fileName) {
	try {
	    int i=0;
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
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
    }

    public static boolean isAnagram(String s1, String s2) {
	char[] t1 = s1.toCharArray();
	char[] t2 = s2.toCharArray();
	Arrays.sort(t1);
	Arrays.sort(t2);
	String c1 = new String(t1);
	String c2 = new String(t2);
	return (c1.equals(c2));
    }

    public static void main(String[] args) {

	readFile(args[0]);
	int valid = 0;
	for (int i=0; i<lines.size(); i++) {
	    //check within a line for duplicate words
	    String[] words = lines.get(i).split(" ");
	    boolean thisIsValid = true;
	    for (int j=0; j<words.length; j++) {
		for (int k=j+1; k<words.length; k++) {
		    if (isAnagram(words[j], words[k])) {
			thisIsValid = false;
		    }
		}		    
	    }
	    if (thisIsValid) valid++;
	}
	System.out.println(valid);
	
    }

}
