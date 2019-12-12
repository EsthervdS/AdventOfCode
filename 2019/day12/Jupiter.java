import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
import java.math.*;
public class Jupiter {

    public ArrayList<String> lines;
    public Moon[] moons;
    public int[][] diff;
    public ArrayList<String> prevX;
    public ArrayList<String> prevY;
    public ArrayList<String> prevZ;
    public boolean xFound,yFound,zFound;
    public int xrepeat,yrepeat,zrepeat;

    public Jupiter(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	moons = new Moon[4];
	diff = new int[4][3];
	prevX = new ArrayList<String>();
	prevY = new ArrayList<String>();
	prevZ = new ArrayList<String>();
	xFound = yFound = zFound = false;

	for (int i=0; i<4; i++) {
	    int xpos = Integer.parseInt( (lines.get(i).split(",")[0]).split("=")[1]);
	    int ypos = Integer.parseInt( (lines.get(i).split(",")[1]).split("=")[1]);
	    int zpos = Integer.parseInt( (lines.get(i).split(",")[2]).split("=")[1].split(">")[0]);
	    moons[i] = new Moon(xpos,ypos,zpos);
	}

	int step = 0;
        String part2 = "";
	
	String xs = "";
	String ys = "";
	String zs = "";

	for (int i=0; i<4; i++) {
	    xs += moons[i].pos[0] + "" + moons[i].vel[0];
	    ys += moons[i].pos[1] + "" + moons[i].vel[1];
	    zs += moons[i].pos[2] + "" + moons[i].vel[2];
	}
	prevX.add(xs);
	prevY.add(ys);
	prevZ.add(zs);

	while (part2.equals("")) {
	    //reset diff for this round	    
	    for (int i=0; i<4; i++) {
		for (int k=0; k<3; k++) {
		    diff[i][k] = 0;
		}
	    }

	    //compute gravity
	    for (int i=0; i<4; i++) {
		for (int j=i+1; j<4; j++) {
		    //pair of moons
		    for (int k=0; k<3; k++) {
			//x,y,z
			if (moons[i].pos[k] < moons[j].pos[k]) {
			    diff[i][k] += 1;
			    diff[j][k] += -1;
			} else if (moons[i].pos[k] > moons[j].pos[k]) {
			    diff[i][k] += -1;
			    diff[j][k] += 1;
			} //else equal, zero change
		    }		
		}
	    }
	    String thisPos = "";
	    for (int i=0; i<4; i++) {
		moons[i].updateVelocity(diff[i][0],diff[i][1],diff[i][2]);
		moons[i].updatePosition();
		thisPos += moons[i].toString();
	    }
	    step++;
	    	    
	    xs = "";
	    ys = "";
	    zs = "";
	    for (int i=0; i<4; i++) {
		xs += moons[i].pos[0] + "" + moons[i].vel[0];
		ys += moons[i].pos[1] + "" + moons[i].vel[1];
		zs += moons[i].pos[2] + "" + moons[i].vel[2];
	    }
	    if (prevX.contains(xs) && !xFound) {
		xrepeat = step;
		xFound = true;
		//IO.print("Found X repeat: " + step);
	    } else {
		prevX.add(xs);
	    }
	    if (prevY.contains(ys) && !yFound) {
		yrepeat = step;
		yFound = true;
		//IO.print("Found Y repeat: " + step);		
	    } else {
		prevY.add(ys);
	    }
	    if (prevZ.contains(zs) && !zFound) {
		zrepeat = step;
		zFound = true;
		//IO.print("Found Z repeat: " + step);		
	    } else {
		prevZ.add(zs);
	    }
	    if (xFound && yFound && zFound) {
		BigInteger x = new BigInteger(xrepeat+"");
		BigInteger y = new BigInteger(yrepeat+"");
		BigInteger z = new BigInteger(zrepeat+"");
		BigInteger gcd1 = x.gcd(y);
		BigInteger lcm1 = x.multiply(y).divide(gcd1);
		BigInteger gcd2 = lcm1.gcd(z);
		BigInteger ans = lcm1.multiply(z).divide(gcd2);
		part2 = ans.toString();
		IO.print("Part 2: " + part2);
	    }

	    if (step == 1000) {
		int part1 = 0;
		for (Moon m : moons) {
		    part1 += m.kineticEnergy() * m.potentialEnergy();
		}
		IO.print("Part 1: " + part1);
	    }

	}
	

    }

    public static void main(String[] args) {
	Jupiter j = new Jupiter(args[0]);
     }
}
