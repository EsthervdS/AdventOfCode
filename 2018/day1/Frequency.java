import java.io.*;
import java.util.*;
import util.*;

public class Frequency {

    public ArrayList<String> lines;
    public ArrayList<Integer> ints;
    public int freq;
    public Frequency(String fileName) {
  	  lines = new ArrayList<String>();
  	  lines = IO.readFile(fileName);
  	  freq = 0;
      ints = new ArrayList<Integer>();
      for (String line : lines) {
        ints.add(Integer.parseInt(line,10));
      }
    }

    public static void main(String[] args) {
	     Frequency f = new Frequency(args[0]);

       boolean twice = false;
       HashSet<Integer> freqs = new HashSet<Integer>();
       freqs.add(0);
       int size = 1;
       int i = 0, part1 = 0, part2 = 0;

	     while (!twice) {
         if (i==f.ints.size()) {
           if (part1 == 0) part1 = f.freq;
           i=0;
         }
         f.freq += f.ints.get(i);
         freqs.add(f.freq);
         if (freqs.size() == size) {
           twice = true;
           part2 = f.freq;
         } else {
           size = freqs.size();
           i++;
         }
       }
       IO.print("Part 1: " + part1);
       IO.print("Part 2: " + part2);
    }

}
