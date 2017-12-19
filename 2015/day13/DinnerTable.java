import util.*;
import java.util.*;

public class DinnerTable {

    ArrayList<String> lines;
    ArrayList<String> people;
    int[][] happiness;
    int nPersons;
    int maxHapp = -1000000;

    
    public DinnerTable(String fileName) {
	lines = IO.readFile(fileName);
	happiness = new int[10][10];
	people = new ArrayList<String>();
	nPersons = 0;
	//David would gain 41 happiness units by sitting next to Carol.
	for (String l : lines) {
	    String person1 = l.split(" ")[0];
	    String person2= l.split(" ")[10];
	    person2 = person2.substring(0,person2.length()-1);
	    int units = Integer.parseInt(l.split(" ")[3].trim());
	    if (l.split(" ")[2].equals("lose")) units = -units; 
	    if (!people.contains(person1)) {
		people.add(person1);
		nPersons++;
	    }
	    int i1 = people.indexOf(person1);
	    
	    if (!people.contains(person2)) {
		people.add(person2);
		nPersons++;
	    }
	    int i2 = people.indexOf(person2);

	    happiness[i1][i2] = units;

	    happiness[i1][i1] = 0;
	    happiness[i2][i2] = 0;
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
	maxHapp = -1000000;

	ArrayList<ArrayList<String>> configs = permute(people);

	for (ArrayList<String> config : configs) {
	    int thisHapp = 0;
	    for (int i=0; i<(config.size()-1); i++) {
		int p1 = people.indexOf(config.get(i));
		int p2 = people.indexOf(config.get(i+1));
		int happ1 = happiness[p1][p2];
		int happ2 = happiness[p2][p1];
		
		thisHapp = thisHapp + happ1 + happ2;
	    }
	    int h1 = happiness[people.indexOf(config.get(config.size()-1))][people.indexOf(config.get(0))];
	    int h2 = happiness[people.indexOf(config.get(0))][people.indexOf(config.get(config.size()-1))];
	    thisHapp  = thisHapp + h1 + h2;


	    if (thisHapp > maxHapp) maxHapp = thisHapp;
	}
    }
    
    public static void main(String[] args) {
	DinnerTable dt = new DinnerTable(args[0]);
	dt.process();
	IO.print("Part 1: " + dt.maxHapp);

	dt.people.add("Myself");
	int my = dt.people.indexOf("Myself");
	dt.nPersons++;
	for (int i=0; i<dt.nPersons; ++i) {
	    dt.happiness[i][my] = 0;
	    dt.happiness[my][i] = 0;
	}
	dt.process();
	IO.print("Part 2: " + dt.maxHapp);

    }
}
