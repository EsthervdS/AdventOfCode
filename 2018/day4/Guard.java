import java.io.*;
import java.util.*;
import util.*;

class Guard {

    public int id;
    public ArrayList<Shift> shifts;
    public int[] counts;
    public int maxMinute;
    public int maxShifts;

    public Guard(int i) {
	id = i;
	shifts = new ArrayList<Shift>();
	counts = new int[60];
	for (int j=0; j<60; j++) counts[j] = 0;	
    }

    public void addShift(Shift s) {
	shifts.add(s);
    }
    
    public int totalMinutesAsleep() {
	int sum = 0;
	for (Shift shift : shifts) {
	    sum += shift.minutesAsleep();
	}
	return sum;
    }

    public void computeShifts() {
	//count all sleep shifts per minute
	for (Shift s : shifts) {
	    for (int i=0; i<60; i++) {
		if (s.asleep[i]) counts[i]++;
	    }
	}
	maxShifts = -1;
	maxMinute = -1;
	for (int i=0; i<60; i++) {
	    if (counts[i]>maxShifts) {
		maxShifts = counts[i];
		maxMinute = i;
	    }
	}
    }

}
