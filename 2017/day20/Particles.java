
import util.*;
import java.util.*;

public class Particles {

    ArrayList<String> lines;
    int minIndex;
    int minAcc,minVel,minPos;
    ArrayList<Particle> particles,pcopy;
    ArrayList<Boolean> colliding;
    
    public Particles(String fileName) {
	lines = IO.readFile(fileName);
	minIndex = 0;
	minAcc = 10000000;
	ArrayList<Integer> minAccs = new ArrayList<Integer>();
	ArrayList<Integer> minVels = new ArrayList<Integer>();
	ArrayList<Integer> minPoss = new ArrayList<Integer>();
	minVel = 10000000;
	minPos = 10000000;
	particles = new ArrayList<Particle>();
	
	for (int i=0; i<lines.size(); i++) {
	    particles.add(new Particle(lines.get(i)));
	    String p = lines.get(i);
	    if ( getAcc(p) <= minAcc ) {
		minAcc = getAcc(p);
		minAccs.add(i);
	    }
	}
	for (int i : minAccs) {
	    String p = lines.get(i);
	    if (getAcc(p) == minAcc) {
		if (getVel(p) <= minVel) {
		    minVel = getVel(p);
		    minVels.add(i);
		}
	    }
	}
	for (int i : minVels) {
	    String p = lines.get(i);
	    if (getVel(p) == minVel) {
		if (getPos(p) <= minPos) {
		    minPos = getVel(p);
		    minPoss.add(i);
		}

	    }
	}
	IO.print("Part 1: " + minPoss.get(0));
	
	colliding = new ArrayList<Boolean>(particles.size());
	for (int i=0; i<particles.size(); i++) {
	    colliding.add(false);
	}
    }
    
    public int getAcc(String line) {
	Particle p = new Particle(line);
	
	return (Math.abs(p.ax)+Math.abs(p.ay)+Math.abs(p.az));
    }

    public int getVel(String line) {
	Particle p = new Particle(line);
	
	return (Math.abs(p.vx)+Math.abs(p.vy)+Math.abs(p.vz));
    }

    public int getPos(String line) {
	Particle p = new Particle(line);
	
	return (Math.abs(p.px)+Math.abs(p.py)+Math.abs(p.pz));
    }

    public boolean collide(Particle p1, Particle p2) {
	boolean coll = false;
	IO.print("Checking 2 particles: " + p1.toString() + " and " + p2.toString());
	IO.print("DetX = " + detX(p1,p2));
	IO.print("At time 10: p0: " + p1.getX(10) + " - " + p1.getY(10) + " - " + p1.getZ(10));
	IO.print("Formula p0: " + p1.px + " + " + p1.vx + " * 10 " + " + 1/2 * " + p1.ax + " 10^2");
	IO.print("At time 10: p1: " + p2.getX(10) + " - " + p2.getY(10) + " - " + p2.getZ(10));
	IO.print("Formula p1: " + p2.px + " + " + p2.vx + " * 10 " + " + 1/2 * " + p2.ax + " 10^2");
	// solve positions for t
	if ( !(detX(p1,p2)<0) ) {
	    //1 or 2 solutions
	    // if for one of the t's y & z are equal for both particles, return true
	    int a = (p1.ax - p2.ax) / 2;
	    int b = (p1.vx - p2.vx);
	    int c = (p1.px - p2.px);
	    IO.print("a = " + a + " | b = " + b + " | c = " + c);

	    int t1 = (int) ((-b + Math.sqrt(b*b - 4*a*c)) / 2*a);
	    int t2 = (int) ((-b - Math.sqrt(b*b - 4*a*c)) / 2*a);
	    IO.print("t1 = " + t1 + " and t2 = " + t2);
	    if ( (t1>=0) && (p1.getY(t1) == p2.getY(t1)) && (p1.getZ(t1) == p2.getZ(t1)) ) {
		coll = true;
	    } else if ( (t2>=0) && (p1.getY(t2) == p2.getY(t2)) && (p1.getZ(t2) == p2.getZ(t2)) ) {
		coll = true;
	    }
	    
	}
	return coll;
    }

    public boolean isInteger(double d) {
	return ((d == Math.floor(d)) && !Double.isInfinite(d));
    }

    public double detX(Particle p1, Particle p2) {
	// b^2 - 4 ac = (v1-v2)^2 - 4 * .5 * (a1-a2) * (p1-p2)
	double a = 0.5 * (p1.ax - p2.ax);
	double b = (p1.vx-p2.vx);
	double c = (p1.px - p2.px);
	
	return (b*b - 4 * a * c);
    }
    

    public void removeCollidingParticles() {
	// for every pair of particles check for collisions
	// if collision : set colliding flag

	for (int i=0; i<particles.size(); i++) {//particles.size(); ++i) {
	    Particle p1 = particles.get(i);
	    for (int j=(i+1); j<particles.size(); j++) {//particles.size(); ++j) {
		Particle p2 = particles.get(j);
		if ( (i==0) && (j==1) ) {
		    boolean b = collide(p1,p2);
		    if (b) colliding.set(i,true);
		    if (b) colliding.set(j,true);
		}
	    }
	}
	ArrayList<Particle> pcopy = new ArrayList<Particle>(particles);
	// pass through list and remove particles with flag true
	for (Particle p : pcopy) {
	    if (colliding.get(pcopy.indexOf(p))) {
		particles.remove(p);
	    }
	}
    }

    public void bruteForce() {

	for (int t=0; t<5000; t++) {
	    for (Particle p : particles) {
		p.move();
	    }
	    if (t<=9) {
		Particle p0 = particles.get(0);
		Particle p1 = particles.get(1);
		IO.print("t=" + t + " p0: " + p0.currX + " - " + p0.currY + " - " + p0.currZ);
		IO.print("t=" + t + " p1: " + p1.currX + " - " + p1.currY + " - " + p1.currZ);
	    }
	    for (int i=0; i<particles.size(); ++i) {
		Particle p1 = particles.get(i);
		for (int j=(i+1); j<particles.size(); ++j) {
		    Particle p2 = particles.get(j);
		    if (p1.collides(p2)) {
			//IO.print("collision particles " + i + " and " + j + " at time  " + t);

			colliding.set(i,true);
			colliding.set(j,true);
		    }
		}
	    }
	}
	ArrayList<Particle> pcopy = new ArrayList<Particle>(particles);
	// pass through list and remove particles with flag true
	for (Particle p : pcopy) {
	    if (colliding.get(pcopy.indexOf(p))) {
		particles.remove(p);
	    }
	}
	
    }

    
    public static void main(String[] args) {
	Particles ps = new Particles(args[0]);
	ps.removeCollidingParticles();
	IO.print("Part 2 Analytic: " + ps.particles.size());
	Particles ps2 = new Particles(args[0]);
	ps2.bruteForce();
	IO.print("Part 2 Brute force: " + ps2.particles.size());
    }
}
