import util.*;
import java.util.*;

public class WrappingPaper {

    private ArrayList<String> dimensions;
    private int totalPaper;
    private int totalRibbon;
    
    public WrappingPaper(String fileName) {

	dimensions = IO.readFile(fileName);
	
    }

    private int paperNeeded(int l, int w, int h) {
	int side1 = l*w;
	int side2 = w*h;
	int side3 = h*l;
	int extra = 0;
	if ((side1 <= side2) && (side1 <= side3)) {
	    extra = side1;
	} else if ((side2 <= side1) && (side2 <= side3)) {
	    extra = side2;
	} else {
	    extra = side3;
	}
	return (2*side1) + (2*side2) + (2*side3) + extra;
    }

    private int ribbonNeeded(int l, int w, int h) {
	int min1, min2 = 10000;

	if ((l<=w) && (l<=h) && (w<=h)) {
	    //l <= w <= h
	    min1 = l;
	    min2 = w;
	} else if ((l<=h) && (l<=w) && (h<=w)) {
	    // l <= h <= w
	    min1 = l;
	    min2 = h;
	} else if ((w<=l) && (w<=h) && (l<=h)) {
	    // w <= l <= h
	    min1 = w;
	    min2 = l;
	} else if ((w<=l) && (w<=h) && (h<=l)) {
	    // w <= h <= l
	    min1 = w;
	    min2 = h;
	} else if ((h<=l) && (h<=w) && (l<=w)) {
	    // h <= l <= w
	    min1 = h;
	    min2 = l;
	} else {
	    // h <= w <= l
	    min1 = h;
	    min2 = w;
	}
	int sum = (2*min1) + (2*min2) + (l*w*h);
	return sum;
    }
    
    public void compute() {
	
	for (String s : dimensions) {
	    IO.print("Dimensions of present: " + s);
	    String[] dims = s.split("x");
	    int length = Integer.parseInt(dims[0]);
	    int width  = Integer.parseInt(dims[1]);
	    int height = Integer.parseInt(dims[2]);
	    
	    totalPaper += paperNeeded(length,width,height);
	    totalRibbon += ribbonNeeded(length,width,height);
	}

	IO.print("Part 1: " + totalPaper);
	IO.print("Part 2: " + totalRibbon);
    }

    public static void main(String[] args) {
	WrappingPaper wp = new WrappingPaper(args[0]);
	wp.compute();
    }
}
