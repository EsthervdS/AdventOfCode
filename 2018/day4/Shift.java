import java.io.*;
import java.util.*;
import util.*;

class Shift {

    public String startdate;
    public int shour, sminute;
    public int guardid;
    public ArrayList<Action> actions; // sorted by time
    public boolean[] asleep;
    
    public Shift() {
	actions = new ArrayList<Action>();
	asleep = new boolean[60];
	for (int i=0; i<60; i++) asleep[i]=false;
    }
    public Shift(String line) {
	startdate = line.split(" ")[0].substring(1,11);
	shour = Integer.parseInt(line.split(" ")[1].split(":")[0]);
	sminute = Integer.parseInt(line.split(" ")[1].split(":")[1].split("]")[0]);
	guardid = Integer.parseInt(line.split("#")[1].split(" ")[0]);
	actions = new ArrayList<Action>();
	asleep = new boolean[60];
	for (int i=0; i<60; i++) asleep[i]=false;	
    }

    public void addAction(Integer minute, String action) {
	actions.add(new Action(minute,action));
    }

    public void computeAsleep() {
	int prevmin = sminute;
	if (shour == 23) {
	    prevmin -= 60;
	}
	for(Action a : actions) {
	    if (a.action == "asleep") {
		//if asleep, start new counter
		prevmin = a.minute;
	    } else {
		//if awake, add min - prevmin to total
		for (int i=prevmin; i<a.minute; i++) asleep[i] = true;
	    }
	}
    }

    public int minutesAsleep() {
	int sum = 0;
	for (int i=0; i<60; i++) { if (asleep[i]) sum++; }
	return sum;
    }

}
