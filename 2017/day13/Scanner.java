public class Scanner {

    int depth;
    int range;
    
    public Scanner(int d, int r) {
	this.depth = d;
	this.range = r;
    }

    public boolean safe(int time) {
	return (time % ((this.range-1) * 2) != 0);
    }
    
    public int severity(int time) {
	int sev = 0;
	if ( time % ((this.range-1) * 2) == 0) {
	    sev = this.depth * this.range;
	    //System.out.println("Caught at depth : " + this.depth + " at time " + time + " and sev = " + sev);
	}
	return sev;
    }

}
