class Node {

    public String color;
    public int quantity;

    public Node(String c, int q) {
	color = c;
	quantity = q;
    }

    public String toString() {
	return "[" + color + " : " + quantity + "] ";
    }
}
