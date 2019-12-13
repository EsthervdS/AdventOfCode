package util;

import java.util.*;
import java.io.*;

public class IO {

    public static ArrayList<String> readFile(String fileName) {
	ArrayList<String> lines = new ArrayList<String>();	
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
	//System.out.println("Read " + lines.size() + " lines");
	return lines;
    }

    public static int getInt() {
	Scanner sc = new Scanner(System.in);
	System.out.print("Input int: ");
	int i = sc.nextInt();
	IO.print("");
	return i;
    }
    public static void print(String s) {
	System.out.println(s);
    }
}
