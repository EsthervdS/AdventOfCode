import java.io.*;
import java.util.*;
import util.*;

public class Layer {

    public int width, height;
    public int zeroes, ones, twos;
    public ArrayList<Integer> digits;
    
    public Layer(ArrayList<Integer> l, int w, int h) {
	digits = new ArrayList<Integer>();
	zeroes = ones = twos = 0;
	
	for (int i=0; i<(w*h); i++) {
	    digits.add(l.get(i));
	    if (digits.get(i) == 0) zeroes++;
	    if (digits.get(i) == 1) ones++;
	    if (digits.get(i) == 2) twos++;
	}
	width = w;
	height = h;
    }

    public String toString() {
	String res = "";
	for (int i=0; i<width*height; i++) {
	    res += digits.get(i);
	}
	return res;
    }

    public int getPixel(int i, int j) {
	//i is column, j is row
	return digits.get( (j*width) + i );
    }
}
