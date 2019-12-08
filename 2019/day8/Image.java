import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Image {

    public ArrayList<String> lines;
    public ArrayList<Integer> ints;
    public int width, height, nLayers;
    public ArrayList<Layer> layers;

    public Image(String fileName) {
	width = 25;
	height = 6;

	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	
	ints = new ArrayList<Integer>();
	for (int i=0; i<lines.get(0).length(); i++) {
	    ints.add(Integer.parseInt((lines.get(0).charAt(i)+""),10));
	}

	nLayers = ints.size() / (width * height);
        layers = new ArrayList<Layer>();
	
	for (int i=0; i<nLayers; i++) {
	    Layer l = new Layer(new ArrayList<Integer>(ints.subList(i * (width*height), (i+1) * (width*height))), width, height);
	    //IO.print("Layer #" + i + " => " + l.toString());
	    layers.add(l);
	}
    }

    public void part1() {
	int minZeroes = Integer.MAX_VALUE;
	int minZeroesIndex = -1;
	
	for (int i=0; i<nLayers; i++) {
	    int curZ = layers.get(i).zeroes;
	    if (curZ < minZeroes) {
		minZeroes = curZ;
		minZeroesIndex = i;
	    }
	}
	Layer part1L = layers.get(minZeroesIndex);
	IO.print("Part 1: " + (part1L.ones * part1L.twos));
    }

    public void part2() {
	//for each pixel in the resulting image
	//traverse layers from front to back until non-transparent (=2) is encountered
	int[][] im = new int[width][height];
	for (int i=0; i<width; i++) {
	    for (int j=0; j<height; j++) {
		im[i][j] = 2;
		int k = 0;
		while ( (k<nLayers) && (layers.get(k).getPixel(i,j) == 2) ) {
		    k++;
		}
		if (k<nLayers) im[i][j] = layers.get(k).getPixel(i,j);
	    }
	}
	IO.print("Part 2: ");
	printImage(im);
    }

    public void printImage(int[][] im) {
	for (int j=0; j<height; j++) {
	    for (int i=0; i<width; i++) {
		if (im[i][j] == 0) System.out.print(" ");
		if (im[i][j] == 1) System.out.print("X");
		if (im[i][j] == 2) System.out.print(" ");
	    }
	    System.out.println("");
	}
    }
    
    public static void main(String[] args) {
	Image im = new Image(args[0]);
	im.part1();
	im.part2();
     }
}
