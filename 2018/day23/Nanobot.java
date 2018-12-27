import java.util.*;
import java.math.*;
import util.*;

public class Nanobot {
    long x,y,z,r;
    
    public Nanobot(String input) {
	x = Long.parseLong(input.split("<")[1].split(",")[0]);
	y = Long.parseLong(input.split("<")[1].split(",")[1]);
	z = Long.parseLong(input.split("<")[1].split(",")[2].split(">")[0]);
	r = Long.parseLong(input.split("=")[2]);
    }

    public boolean inRange(Nanobot m) {
	return (r >= ( Math.abs(m.x-x) + Math.abs(m.y-y) + Math.abs(m.z-z) ));
    }

    public boolean inRange(Point3D p) {
	return (r >= ( Math.abs(p.x-x) + Math.abs(p.y-y) + Math.abs(p.z-z) ));	
    }
    
    public String toString() {
	return ( "<"+x+","+y+","+z+",r="+r+">"  );
    }
}
