public class Cart implements Comparable<Cart> {
    public int x,y;
    public String dir;
    public int turns;
    public boolean crashed;
    //dir: ^,v,<,>

    public Cart(int px, int py, String d) {
	x = px;
	y = py;
	dir = d;
	turns = 0;
	crashed = false;
    }

    public void left() {
	String d = dir;
	if (d.equals("^")) {
	    x--;
	    dir = "<";
	}
	if (d.equals("<")) {
	    y++;
	    dir = "v";
	}
	if (d.equals("v")) {
	    x++;
	    dir = ">";
	}
	if (d.equals(">")) {
	    y--;
	    dir = "^";
	}
    }

    public void right() {
	String d = dir;
	if (d.equals("^")) {
	    x++;
	    dir = ">";
	}
	if (d.equals("<")) {
	    y--;
	    dir = "^";
	}
	if (d.equals("v")) {
	    x--;
	    dir = "<";
	}
	if (d.equals(">")) {
	    y++;
	    dir = "v";
	}
 
    }
    
     public void straight() {
	if (dir.equals("^")) {
	    y--;
	}
	if (dir.equals("<")) {
	    x--;
	}
	if (dir.equals("v")) {
	    y++;
	}
	if (dir.equals(">")) {
	    x++;
	}
    }

    @Override
    public int compareTo(Cart q) {
	//this point is smaller than q if y is smaller or if equal, if x is smaller
	if ( (y < q.y) || ((y == q.y) && (x < q.x)) ) {
	    return -1;
	} else if (x==q.x && y==q.y) {
	    return 0;
	} else {
	    return 1;
	}
    }

    public String toString() {
	String res = "";
	res += ("("+x+","+y+")");
	res += (" -- with direction " + dir + " CRASHED: " + crashed);
	return res + "\n"; 
    }
}
