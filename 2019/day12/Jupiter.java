import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Jupiter {

    public ArrayList<String> lines;
    public Moon[] moons;
    public int[][] diff;
    
    public Jupiter(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);

	moons = new Moon[4];
	diff = new int[4][3];
	
	for (int i=0; i<4; i++) {
	    //<x=3, y=5, z=-1>
	    int xpos = Integer.parseInt( (lines.get(i).split(",")[0]).split("=")[1]);
	    int ypos = Integer.parseInt( (lines.get(i).split(",")[1]).split("=")[1]);
	    int zpos = Integer.parseInt( (lines.get(i).split(",")[2]).split("=")[1].split(">")[0]);
	    moons[i] = new Moon(xpos,ypos,zpos);
	}

	int step = 0;
	int part2 = 0;
	
	while (part2 == 0) {
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

	    for (int i=0; i<4; i++) {
		moons[i].updateVelocity(diff[i][0],diff[i][1],diff[i][2]);
		moons[i].updatePosition();
	    }
	    
	    step++;

	    if (step == 1000) {
		int part1 = 0;
		for (Moon m : moons) {
		    part1 += m.kineticEnergy() * m.potentialEnergy();
		}
		IO.print("Part 1: " + part1);
		break;
	    }


	}

    }

    public static void main(String[] args) {
	Jupiter j = new Jupiter(args[0]);
     }
}
