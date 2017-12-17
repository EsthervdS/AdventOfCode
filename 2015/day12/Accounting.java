import java.util.regex.*;
import java.util.*;
import java.io.*;

public class Accounting {
    public static void main(String[] args) {            
	Pattern number=Pattern.compile("((-\\d+)|(\\d+))");                                                     //match numbers     
	Pattern combo=Pattern.compile("(\\{[-\\w\\,\\:\\\"]*\\})|(\\[[-\\w\\,\\\"]*\\])");                      //match arrays and objects  
	try
	    {
		Scanner scanner = new Scanner(new File(args[0]));     
		String strLine = scanner.nextLine();            
		int sum=0;
		//a)
		Matcher m=number.matcher(strLine);
		while (m.find()) 
		    sum+=Integer.parseInt(m.group(0));                                                              //sum all numbers
		System.out.println("Part 1: " +sum);
		// b)
		Matcher k;
		m=combo.matcher(strLine);           
		while(m.find())
		    {
			int subsum=0;     
			if(!(m.group(0).matches("\\{[-\\w\\d\\,\\:\\\"]*\\}") && m.group(1).matches(".*:\"red\".*")))   //ignore {.:"red".}
			    {
				k=number.matcher(m.group(0));                                                               
				while (k.find())                                                                            //sum up array or object
				    subsum+=Integer.parseInt(k.group(0));
			    }
			strLine=strLine.substring(0, m.start())+subsum+strLine.substring(m.end()); 
			m=combo.matcher(strLine);
		    }
		System.out.println("Part 2: " +strLine); 
		scanner.close();
	    }
	catch (Exception e) {e.printStackTrace();}
    }
}
