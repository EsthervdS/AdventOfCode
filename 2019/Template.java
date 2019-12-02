import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Template {

    public ArrayList<String> lines;

    public Template(String fileName) {
  	  lines = new ArrayList<String>();
  	  lines = IO.readFile(fileName);

      /*
      ints = new ArrayList<Integer>();
      for (String line : lines) {
        ints.add(Integer.parseInt(line,10));
      }
      */
    }

    public static void main(String[] args) {
	     Template t = new Template(args[0]);

     }
}
