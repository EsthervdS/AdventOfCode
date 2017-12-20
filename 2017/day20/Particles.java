import util.*;
import java.util.*;

public class Particles {

    ArrayList<String> lines;
    int minIndex;
    int minAcc,minVec,minPos;
    
    public Particles(String fileName) {
	lines = IO.readFile(fileName);
	minIndex = 0;
	minAcc = 10000000;
	ArrayList<Integer> minAccs = new ArrayList<Integer>();
	ArrayList<Integer> minVecs = new ArrayList<Integer>();
	ArrayList<Integer> minPoss = new ArrayList<Integer>();
	minVec = 10000000;
	minPos = 10000000;

	for (int i=0; i<lines.size(); i++) {
	    String p = lines.get(i);
	    if ( getAcc(p) <= minAcc ) {
		minAcc = getAcc(p);
		minAccs.add(i);
	    }
	}
	for (int i : minAccs) {
	    String p = lines.get(i);
	    if (getAcc(p) == minAcc) {
		if (getVec(p) <= minVec) {
		    minVec = getVec(p);
		    minVecs.add(i);
		}
	    }
	}
	for (int i : minVecs) {
	    String p = lines.get(i);
	    if (getVec(p) == minVec) {
		if (getPos(p) <= minPos) {
		    minPos = getVec(p);
		    minPoss.add(i);
		}

	    }
	}
	IO.print("Part 1: " + minPoss.get(0));
    }
    
    public int getAcc(String line) {
	int acc = 0;
	int xAcc,yAcc,zAcc;
	String accStr = line.split("a=")[1].trim();
	String[] coords = accStr.split(",");
	xAcc = Math.abs(Integer.parseInt(coords[0].trim().substring(1).trim()));
	yAcc = Math.abs(Integer.parseInt(coords[1].trim()));
	zAcc = Math.abs(Integer.parseInt(coords[2].trim().substring(0,coords[2].length()-1).trim()));	
	
	return (xAcc+yAcc+zAcc);
    }

    public int getVec(String line) {
	int vec = 0;
	int xVec,yVec,zVec;
	String vecStr = line.split("v=")[1].trim();
	String[] coords = vecStr.split(",");
	xVec = Math.abs(Integer.parseInt(coords[0].substring(1).trim()));
	yVec = Math.abs(Integer.parseInt(coords[1].trim()));
	zVec = Math.abs(Integer.parseInt(coords[2].trim().substring(0,coords[2].length()-1).trim()));	
	
	return (xVec+yVec+zVec);
    }

    public int getPos(String line) {
	int pos = 0;
	int xPos,yPos,zPos;
	String posStr = line.split("p=")[1].trim();
	String[] coords = posStr.split(",");
	xPos = Math.abs(Integer.parseInt(coords[0].trim().substring(1).trim()));
	yPos = Math.abs(Integer.parseInt(coords[1].trim()));
	zPos = Math.abs(Integer.parseInt(coords[2].trim().substring(0,coords[2].length()-1).trim()));	
	
	return (xPos+yPos+zPos);
    }

    public static void main(String[] args) {
	Particles xx = new Particles(args[0]);
    }
}
