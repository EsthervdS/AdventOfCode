import java.util.*;
import util.*;

public class Turing {

    int nSteps;
    int nOnes;
    int steps;
    char state;
    int pos;
    HashMap<Integer,Boolean> tape;
    
    public Turing (int nS) {
	nSteps = nS;
	nOnes = 0;
	steps = 0;
	state = 'A';
	tape = new HashMap<Integer,Boolean>();
	tape.put(0,false);
	pos=0;
    }

    public void perform() {
	while (steps<nSteps) {
	    //IO.print("Step " + steps + ": tape " + tape.toString() + " pos = " + pos);
	    
	    if (state=='A') {
		if (tape.get(pos) == null) {
		    //new pos in tape
		    tape.put(pos,true);
		    nOnes++;
		    pos++;
		    state = 'B';
		} else if (!tape.get(pos)) {
		    //0
		    tape.put(pos,true);
		    nOnes++;
		    pos++;
		    state = 'B';		    
		} else {
		    //1
		    tape.put(pos,false);
		    nOnes--;
		    pos--;
		    state='C';
		}
	    } else if (state=='B') {
		if (tape.get(pos) == null) {
		    //new pos in tape
		    tape.put(pos,true);
		    nOnes++;
		    pos--;
		    state = 'A';
		} else if (!tape.get(pos)) {
		    //0
		    tape.put(pos,true);
		    nOnes++;
		    pos--;
		    state = 'A';
		} else {
		    //1 - keep 1
		    pos++;
		    state = 'D';
		}
	    } else if (state=='C') {
		if (tape.get(pos) == null) {
		    //new pos in tape
		    tape.put(pos,true);
		    nOnes++;
		    pos++;
		    state = 'A';

		} else if (!tape.get(pos)) {
		    //0
		    tape.put(pos,true);
		    nOnes++;
		    pos++;
		    state = 'A';

		} else {
		    //1
		    tape.put(pos,false);
		    nOnes--;
		    pos--;
		    state='E';
		}

	    } else if (state=='D') {
		if (tape.get(pos) == null) {
		    //new pos in tape
		    tape.put(pos,true);
		    nOnes++;
		    pos++;
		    state = 'A';

		} else if (!tape.get(pos)) {
		    //0
		    tape.put(pos,true);
		    nOnes++;
		    pos++;
		    state = 'A';
		} else {
		    //1
		    tape.put(pos,false);
		    nOnes--;
		    pos++;
		    state='B';
		}

	    } else if (state=='E') {
		if (tape.get(pos) == null) {
		    //new pos in tape
		    tape.put(pos,true);
		    nOnes++;
		    pos--;
		    state = 'F';

		} else if (!tape.get(pos)) {
		    //0
		    tape.put(pos,true);
		    nOnes++;
		    pos--;
		    state = 'F';

		} else {
		    //1 - keep 1
		    pos--;
		    state = 'C';
		}

	    } else {
		//state F
		if (tape.get(pos) == null) {
		    //new pos in tape
		    tape.put(pos,true);
		    nOnes++;
		    pos++;
		    state = 'D';
		} else if (!tape.get(pos)) {
		    //0
		    tape.put(pos,true);
		    nOnes++;
		    pos++;
		    state = 'D';
		} else {
		    //1 - keep 1
		    pos++;
		    state = 'A';
		}

	    }

	    steps++;	    
	}

	IO.print("Part 1 : " + nOnes);

    }
	
    
    
    public static void main(String args[]) {
	Turing t = new Turing(12919244);
	t.perform();
    }
}
