import util.*;
import java.util.*;

public class Replacement {

    public String in, out;
    
    public Replacement(String i, String o) {
	in = i;
	out = o;
    }

    public String toString() {
	return (in + " => " + out);
    }

}
