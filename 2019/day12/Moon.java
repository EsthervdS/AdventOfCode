import java.util.*;
import util.*;

public class Moon {

    public int[] pos;
    public int[] vel;

    public Moon (int i, int j, int k) {
	pos = new int[3];
	pos[0] = i;
	pos[1] = j;
	pos[2] = k;
	vel = new int[3];
	vel[0] = vel[1] = vel[2] = 0;
    }

    public void updateVelocity(int vi, int vj, int vk) {
	vel[0] += vi;
	vel[1] += vj;
	vel[2] += vk;
    }

    public void updatePosition() {
	pos[0] += vel[0];
	pos[1] += vel[1];
	pos[2] += vel[2];
    }

    public int potentialEnergy() {
	return Math.abs(pos[0]) + Math.abs(pos[1]) + Math.abs(pos[2]);
    }

    public int kineticEnergy() {
	return Math.abs(vel[0]) + Math.abs(vel[1]) + Math.abs(vel[2]);
    }

    public String toString() {
	return "pos : <" + pos[0] + "," + pos[1] + "," + pos[2] + "> | vel :  <" + vel[0] + "," + vel[1] + "," + vel[2] + ">" ;
    }
}
