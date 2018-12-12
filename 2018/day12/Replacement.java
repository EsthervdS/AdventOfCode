import util.*;
import java.util.*;

public class Replacement {
    String in,out;
    boolean ll,l,m,r,rr,newm;
    
    public Replacement(String i, String o) {
	in = i; out= o;
	ll = (i.charAt(0) == '#') ? true : false;
	l  = (i.charAt(1) == '#') ? true : false;
	m  = (i.charAt(2) == '#') ? true : false;
	r  = (i.charAt(3) == '#') ? true : false;
	rr = (i.charAt(4) == '#') ? true : false;
	newm = (o.charAt(0)=='#') ? true : false;
    }

    public String toString() {
	return (in + " => " + out);
    }

}
