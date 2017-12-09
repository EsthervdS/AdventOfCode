import util.*;
import java.util.*;
import java.util.stream.*;

public class Route {

    String route;
    ArrayList<String> houses,houses2;
    
    public Route(String fileName) {
	route = IO.readFile(fileName).get(0);
	houses = new ArrayList<String>();
	houses.add("0/0");
	houses2 = new ArrayList<String>();
	houses2.add("0/0");
    }

    public void process() {
    	int x=0, y=0;
	int santaX = 0, santaY = 0;
	int robotX = 0, robotY = 0;
	
	for(int i=0; i<route.length(); i++) {
	    switch(route.charAt(i)) {
	    case '>' : {
		x++;
		if (i%2==0) santaX++; else robotX++;
		break;
	    }
	    case '<' : {
		x--;
		if (i%2==0) santaX--; else robotX--;		
		break;
	    }
	    case '^' : {
		y++;
		if (i%2==0) santaY++; else robotY++;
		break;
	    }
	    case 'v' : {
		y--;
		if (i%2==0) santaY--; else robotY--;
		break;
	    }
	    default : break;
	    }
	    String coords = x + "/" + y;
	    String coords2 = "";
	    if (i%2==0) {
		coords2 = santaX + "/" + santaY;
	    } else {
		coords2 = robotX + "/" + robotY;
	    }
	    /* part 1 */
	    if (!houses.contains(coords)) {
		houses.add(coords);
	    }
	    /* part 2 */
	    if (!houses2.contains(coords2)) {
		houses2.add(coords2);
	    }
	}
	IO.print("Part 1: " + houses.size());
	IO.print("Part 2: " + houses2.size());

}
    public static void main(String[] args) {
	Route r = new Route(args[0]);
	r.process();
    }
}
