import util.*;

class Claim {

    public int id, xs, ys, w, h;

    public Claim(int i, int xx, int yy, int ww, int hh) {
	id = i;
	xs = xx;
	ys = yy;
	w  = ww;
	h  = hh;
    }

    public boolean isInside(int i, int j) {
	return ( ((i>=xs) && (i<xs+w) && (j>=ys) && (j<ys+h)) );
    }

    public boolean overlaps(Claim d) {
	boolean ov = d.isInside(xs,ys) || d.isInside(xs+w-1,ys) || d.isInside(xs,ys+h-1) || d.isInside(xs+w-1,ys+h-1);
	return ov;
	
    }
    
}
