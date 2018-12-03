import java.io.*;
import java.util.*;
import util.*;

public class FabricClaims {

    public ArrayList<String> lines;
    public ArrayList<Claim> claims;
    public int overlap, uni;
    
    public FabricClaims(String fileName) {
	lines = new ArrayList<String>();	
	lines = IO.readFile(fileName);
	claims = new ArrayList<Claim>();
	
	for (String line : lines) {

	    int id = Integer.parseInt(line.split(" @ ")[0].split("#")[1]);
	    String offset = line.split(" @ ")[1].split(": ")[0];
	    int x = Integer.parseInt(offset.split(",")[0]);
	    int y = Integer.parseInt(offset.split(",")[1]);

	    String dims = line.split(" @ ")[1].split(": ")[1];
	    int w = Integer.parseInt(dims.split("x")[0]);
	    int h = Integer.parseInt(dims.split("x")[1]);
	    
	    Claim c = new Claim(id,x,y,w,h);
	    claims.add(c);

	}
	overlap = 0;
	uni = -1;
    }

    public static void main(String[] args) {
	FabricClaims fc = new FabricClaims(args[0]);

	for (int i=0; i<1000; i++) {
	    for (int j=0; j<1000; j++) {
		int ov = 0;
		for (Claim c : fc.claims) {
		    if (c.isInside(i,j)) {
			ov++;
			if (ov>1) {
			    fc.overlap++;
			    break;
			}
		    }
		    if (ov>1) break;
		}
	    }
	}
	
	IO.print("Part 1: " + fc.overlap);

	for (Claim c : fc.claims) {
	    boolean isUnique = true;
	    int ov = 0;
	    for (Claim d : fc.claims) {
		if (c.id != d.id) {	
		    if (c.overlaps(d)) {
			isUnique = false;
			ov++;
		    }
		}
	    }
	    IO.print("Claim " + c.id + " has " + ov + " overlapping other claims");
	    if (isUnique) {
		fc.uni = c.id;
	    }
	}
					
	IO.print("Part 2: " + fc.uni);
    }
}
