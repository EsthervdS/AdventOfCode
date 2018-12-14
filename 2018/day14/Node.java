public class Node {
    public int quality;
    public Node prev,next;
    public int index;
    
    public Node(int q, int i) {
	quality = q;
	index = i;
	prev = next = null;
    }

    public Node get(int i) {
	Node res;
	if (i==0) {
	    res = this;
	} else {
	    res = next.get(i-1);
	}
	return res;
    }

    public String toString() {
	return ("n(" + index + ") = " + quality);
    }
}
