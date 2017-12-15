import util.*;
import java.util.*;
import java.util.List;

public class ShortestRoute {

    ArrayList<String> lines;
    ArrayList<String> cities;
    int[][] dist;
    int nCities;
    
    public ShortestRoute(String fileName) {
	lines = IO.readFile(fileName);
	dist = new int[10][10];
	cities = new ArrayList<String>();
	nCities = 0;
	
	for (String l : lines) {
	    String city1 = l.split(" = ")[0].split(" to ")[0];
	    String city2 = l.split(" = ")[0].split(" to ")[1];
	    int distance = Integer.parseInt(l.split(" = ")[1].trim());

	    if (!cities.contains(city1)) {
		cities.add(city1);
		nCities++;
	    }
	    int i1 = cities.indexOf(city1);
	    
	    if (!cities.contains(city2)) {
		cities.add(city2);
		nCities++;
	    }
	    int i2 = cities.indexOf(city2);

	    dist[i1][i2] = distance;
	    dist[i2][i1] = distance;

	    dist[i1][i1] = 0;
	    dist[i2][i2] = 0;
	}
    }
    
    public ArrayList<ArrayList<String>> permute(ArrayList<String> input) {
        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        if (input.isEmpty()) {
            output.add(new ArrayList<String>());
            return output;
        }
        ArrayList<String> list = new ArrayList<String>(input);
        String head = list.get(0);
        ArrayList<String> rest =  new ArrayList<String>(list.subList(1, list.size()));
        for (ArrayList<String> permutations : permute(rest)) {
            ArrayList<ArrayList<String>> subLists = new ArrayList<ArrayList<String>>();
            for (int i = 0; i <= permutations.size(); i++) {
                ArrayList<String> subList = new ArrayList<String>();
                subList.addAll(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            output.addAll(subLists);
        }
        return output;
    }

    
    
    public void process() {
	int minDist = 100000;
	int maxDist = -1;

	ArrayList<ArrayList<String>> routes = permute(cities);
	//for (ArrayList<String> l : routes) IO.print(l.toString());

	for (ArrayList<String> route : routes) {
	    int thisDist = 0;
	    for (int i=0; i<(route.size()-1); i++) {
		thisDist += dist[cities.indexOf(route.get(i))][cities.indexOf(route.get(i+1))];
	    }
	    if (thisDist < minDist) minDist = thisDist;
	    if (thisDist > maxDist) maxDist = thisDist;
	}
	IO.print("Part 1: " + minDist);
	IO.print("Part 2: " + maxDist);
	
    }
    
    public static void main(String[] args) {
	ShortestRoute sr = new ShortestRoute(args[0]);
	sr.process();

    }
}
