import util.*;
import java.util.*;

public class Replacement implements Comparable<Replacement> {

    public String in, out;
    
    public Replacement(String i, String o) {
	in = i;
	out = o;
    }

    public String toString() {
	return (in + " => " + out);
    }

    @Override
    public int compareTo(Replacement r2) {
	int l1, l2;
	l1 = this.out.length();
	l2 = r2.out.length();
	if (l1 > l2) {
	    return -1;
	} else if (l1 == l2) {
	    return 0;
	} else {
	    return 1;
	}
    }

}
