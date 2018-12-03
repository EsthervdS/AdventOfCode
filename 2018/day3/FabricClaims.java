import java.io.*;
import java.util.*;
import util.*;

public class FabricClaims {

    public ArrayList<String> lines;
    public ArrayList<Claim> claims;
    public int overlap, part2;
    
    public FabricClaims(String fileName) {
	lines = new ArrayList<String>();	
	lines = IO.readFile(fileName);
	int id = 1;
	claims = new ArrayList<Claim>();
	
	for (String line : lines) {
	    part2 = -1;
	    String offset = line.split(" @ ")[1].split(": ")[0];
	    int x = Integer.parseInt(offset.split(",")[0]);
	    int y = Integer.parseInt(offset.split(",")[1]);

	    String dims = line.split(" @ ")[1].split(": ")[1];
	    int w = Integer.parseInt(dims.split("x")[0]);
	    int h = Integer.parseInt(dims.split("x")[1]);
	    
	    Claim c = new Claim(id,x,y,w,h);
	    claims.add(c);
	    id++;
	}
	overlap = 0;
    }

    public static void main(String[] args) {
	FabricClaims fc = new FabricClaims(args[0]);

	for (int i=0; i<1100; i++) {
	    for (int j=0; j<1100; j++) {
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

	int uni = -1;
	for (Claim c : fc.claims) {
	    boolean isUnique = true;
	    for (Claim d : fc.claims) {
		if (c.id != d.id) {	
		    if (c.overlaps(d)) {
			isUnique = false;
		    }
		}
	    }
	    if (isUnique) {
		uni = c.id;
	    }
	}
					
	IO.print("Part 2: " + uni);
    }
}
