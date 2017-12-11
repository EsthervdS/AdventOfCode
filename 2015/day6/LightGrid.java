import util.*;
import java.util.*;

public class LightGrid {

    ArrayList<String> lines;
    boolean[][] lights;
    int[][] brightness;
    
    public LightGrid(String fileName) {
	lines = IO.readFile(fileName);
	lights = new boolean[1000][1000];
	brightness = new int[1000][1000];
	for (int i=0; i<1000; i++) {
	    for (int j=0; j<1000; j++) {
		lights[i][j] = false;
		brightness[i][j] = 0;
	    }
	}
    }

    public void process() {
	for (int i=0; i<lines.size(); i++) {
	    String[] ins = lines.get(i).split(" ");
	    String action = "";
	    int c = 0;
	    if (ins[0].equals("toggle")) {
		action = "toggle";
		c = 1;
	    } else {
		action = ins[1];
		c = 2;
	    }
	    int sX, eX, sY, eY;
	    sX = Integer.parseInt(ins[c].split(",")[0]);
	    sY = Integer.parseInt(ins[c].split(",")[1]);
	    eX = Integer.parseInt(ins[c+2].split(",")[0]);
	    eY = Integer.parseInt(ins[c+2].split(",")[1]);
	    changeRange(sX,eX,sY,eY,action);
	}
    }

    private void changeRange(int sX, int eX, int sY, int eY, String action) {
	for (int i=sX; i<=eX; i++) {
	    for (int j=sY; j<=eY; j++) {
		change(i,j,action);
	    }
	}
    }

    private void change(int i, int j, String action) {
	switch(action) {
	case "on" : {
	    lights[i][j] = true;
	    brightness[i][j] += 1;
	    break;
	}
	case "off" : {
	    lights[i][j] = false;
	    if (brightness[i][j]>0) brightness[i][j]-= 1;
	    break;
	}
	case "toggle" : {
	    lights[i][j] = !lights[i][j];
	    brightness[i][j] += 2;
	    break;
	}
	default : break;
	}
    }

    public int nLightsOn() {
	int n = 0;
	for (int i=0; i<1000; i++) {
	    for (int j=0; j<1000; j++) {
		if (lights[i][j]) {
		    n++;
		}
	    }
	}
	return n;
    }

    public int totalBrightness() {
	int b = 0;
	for (int i=0; i<1000; i++) {
	    for (int j=0; j<1000; j++) {
		b += brightness[i][j];
	    }
	}
	return b;
    }
    
    public static void main(String[] args) {
	LightGrid lg = new LightGrid(args[0]);
	lg.process();
	IO.print("Part 1: " + lg.nLightsOn());
	IO.print("Part 2: " + lg.totalBrightness());
    }
}
