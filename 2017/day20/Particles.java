
import util.*;
import java.util.*;

public class Particles {

    ArrayList<String> lines;
    int minIndex;
    int minAcc,minVel,minPos;
    ArrayList<Particle> particles,pcopy;
    ArrayList<Boolean> colliding;
    
    public Particles(String fileName, boolean part1) {
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
	if (part1) IO.print("Part 1: " + minPoss.get(0));
	
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

	// solve positions for t
	double a = 0.5*(p1.ax - p2.ax);
	double b = ( (p1.vx + 0.5*p1.ax) - p2.vx - 0.5*p2.ax );
	double c = (p1.px - p2.px);
	
	if ( (b*b - 4*a*c) >= 0 ) {
	    //1 or 2 solutions
	    // if for one of the t's y & z are equal for both particles, return true

	    double t1 =  ((-b + Math.sqrt(b*b - 4*a*c)) / (2*a));
	    double t2 =  ((-b - Math.sqrt(b*b - 4*a*c)) / (2*a));
	    if ( (isInteger(t1)) && (t1>=0) && (p1.getY((int) t1) == p2.getY((int) t1)) && (p1.getZ((int) t1) == p2.getZ((int) t1)) ) {
		coll = true;
	    } else if ( (isInteger(t2) ) && (t2>=0) && (p1.getY((int) t2) == p2.getY((int) t2)) && (p1.getZ((int) t2) == p2.getZ((int) t2)) ) {
		coll = true;
	    }
	    
	}
	return coll;
    }

    public boolean isInteger(double d) {
	return ((d == Math.floor(d)) && !Double.isInfinite(d));
    }

    public void removeCollidingParticles() {
	// for every pair of particles check for collisions
	// if collision : set colliding flag

	for (int i=0; i<particles.size(); i++) {//particles.size(); ++i) {
	    Particle p1 = particles.get(i);
	    for (int j=(i+1); j<particles.size(); j++) {//particles.size(); ++j) {
		Particle p2 = particles.get(j);
		boolean b = collide(p1,p2);
		if (b) colliding.set(i,true);
		if (b) colliding.set(j,true);
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
	Particles ps = new Particles(args[0], true);
	ps.removeCollidingParticles();
	IO.print("Part 2 Analytic: " + ps.particles.size());
	Particles ps2 = new Particles(args[0], false);
	ps2.bruteForce();
	IO.print("Part 2 Brute force: " + ps2.particles.size());
    }
}
