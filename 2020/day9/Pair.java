class Pair {

    public long first, second;

    public Pair(long f, long s) {
	first = f;
	second = s; 
    }

    public long sum() {
	return first+second;
    }
    
    public String toString() {
	return "("+first+","+second+")";
    }
}
