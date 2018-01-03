import util.*;
import java.util.*;

public class Sue {

    int id;
    HashMap<String,Integer> attributes;
    
    public Sue(String input) {


	attributes = new HashMap<String,Integer>();
	//Sue 500: pomeranians: 10, cats: 3, vizslas: 5
	String[] tokens = input.split(" ");

	String idStr = tokens[1];
	id = Integer.parseInt(idStr.substring(0,idStr.length()-1));
	int iters = 3;
	if (id == 0) {
	    iters = 10;
	    IO.print("Creating Sue goal");
	}
	for (int i=0; i<iters; i++) {

	    String name = tokens[2+i*2];
	    name = name.substring(0,name.length()-1);
	    String nStr = tokens[3+i*2];
	    if (i<iters-1) nStr = nStr.substring(0,nStr.length()-1);
	    int n = Integer.parseInt(nStr);
	    attributes.put(name,n);
	}
	
    }

    public boolean matches(Sue goal) {
	boolean res = true;

	for (String attr : this.attributes.keySet()) {
	    if (goal.attributes.containsKey(attr)) {

		res = res && (this.attributes.get(attr) == goal.attributes.get(attr));
	    }
	}
	return res;
    }
    

    public boolean matches2(Sue goal) {
	boolean res = true;

	for (String attr : this.attributes.keySet()) {
	    if (goal.attributes.containsKey(attr)) {
		int thisN = this.attributes.get(attr);
		int goalN = goal.attributes.get(attr);
		if (attr.equals("cats") || attr.equals("trees")) {
		    res = res && (thisN > goalN);
		} else if (attr.equals("pomeranians") || attr.equals("goldfish")) {
		    res = res && (thisN < goalN);
		} else {
		    res = res && (thisN == goalN);
		}
	    }
	}
	return res;
    }

}
