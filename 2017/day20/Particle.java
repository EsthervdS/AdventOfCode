public class Particle {

    String input;
    int ax,ay,az;
    int vx,vy,vz;
    int px,py,pz;

    double currVX,currVY,currVZ;
    double currX,currY,currZ;
    
    public Particle(String p) {
	input = p;

	//acceleration
	String accStr = input.split("a=")[1].trim();
	String[] coords = accStr.split(",");
	ax = Integer.parseInt(coords[0].trim().substring(1).trim());
	ay = Integer.parseInt(coords[1].trim());
	az = Integer.parseInt(coords[2].trim().substring(0,coords[2].length()-1).trim());	
	
	//velocity
	String vecStr = input.split("v=")[1].trim();
	coords = vecStr.split(",");
	vx = Integer.parseInt(coords[0].substring(1).trim());
	vy = Integer.parseInt(coords[1].trim());
	vz = Integer.parseInt(coords[2].trim().substring(0,coords[2].length()-1).trim());	
	
	//position
	String posStr = input.split("p=")[1].trim();
	coords = posStr.split(",");
	px = Integer.parseInt(coords[0].trim().substring(1).trim());
	py = Integer.parseInt(coords[1].trim());
	pz = Integer.parseInt(coords[2].trim().substring(0,coords[2].length()-1).trim());

	currVX = vx;
	currVY = vy;
	currVZ = vz;
	currX = px;
	currY = py;
	currZ = pz;
	
    }

    public void move() {
	currVX += ax;
	currVY += ay;
	currVZ += az;
	currX += currVX;
	currY += currVY;
	currZ += currVZ;
    }

    public boolean collides(Particle p2) {
	return ( (currX == p2.currX) && (currY == p2.currY) && (currZ == p2.currZ) );
    }

    public double getX(double t) {
	return (px + (vx * t) + (ax * t*(t+1)) / 2);
    }

    public double getY(double t) {
	return (py + (vy * t) + (ay * t*(t+1)) / 2);
    }

    public double getZ(double t) {
	return (pz + (vz * t) + (az * t*(t+1)) / 2);
    }

    public String toString() {
	return input;
    }

}
