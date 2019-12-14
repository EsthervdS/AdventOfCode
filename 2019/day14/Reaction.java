import util.*;
import java.util.*;

public class Reaction {

    ArrayList<Pair> inputs;
    Pair output;
    
    public Reaction(ArrayList<Pair> i, Pair o) {
	inputs = i;
	output = o;
    }

    public Reaction() {

    }
      
    public String toString() {
	return (inputs.toString() + " => " + output.toString());
    }
}
 
