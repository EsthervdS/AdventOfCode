import java.util.*;
import util.*;

public class Carts {
    ArrayList<String> lines;
    ArrayList<Cart> carts;
    public int w,h;
    String[][] tracks;
    public boolean part1,part2;
    int lastx,lasty;
    
    public Carts(String filename) {
	lines = IO.readFile(filename);
	carts = new ArrayList<Cart>();
	w = lines.get(0).length();
	h = lines.size();
	tracks = new String[w][h];
	part1 = part2 = false;
	String[] dirC = {"|","/","\\","-","+"};
	String[] cC = {"^","v",">","<"};
	ArrayList<String> directions = new ArrayList<String>(Arrays.asList(dirC));
	ArrayList<String> cartchars = new ArrayList<String>(Arrays.asList(cC));
	String[] vertical = {"/","\\","|","+"};
	String[] horizontal = {"/","\\","-","+"};
	ArrayList<String> vert = new ArrayList<String>(Arrays.asList(vertical));
	ArrayList<String> hori = new ArrayList<String>(Arrays.asList(horizontal));
	int j=0;
	for (String line : lines) {
	    for (int i=0; i<w; i++) {
		String c = line.charAt(i)+"";
		if (cartchars.contains(c)) {
		    Cart cij = new Cart(i,j,c);
		    carts.add(cij);
		    tracks[i][j] = "X";
		    //detect later which track is beneath this cart!
		} else {
		    //empty space or direction
		    tracks[i][j] = c;
		}
	    }
	    j++;
	}

	for (Cart c : carts) {
	    String A,B,C,D;
	    A = B = C = D = "X";
	    String fill = "";
	    if (c.y>0) A = tracks[c.x][c.y-1];
	    if (c.x>0) B = tracks[c.x-1][c.y];
	    if (c.x<(w-1)) C = tracks[c.x+1][c.y];
	    if (c.y<(h-1)) D = tracks[c.x][c.y+1];
	    if (vert.contains(A) && vert.contains(D)) fill = "|";
	    if (vert.contains(A) && hori.contains(B)) fill = "/";
	    if (vert.contains(A) && hori.contains(C)) fill = "\\";
	    if (hori.contains(B) && hori.contains(C)) fill = "-";
	    if (hori.contains(B) && vert.contains(D)) fill = "\\";
	    if (hori.contains(C) && vert.contains(D)) fill = "/";
	    if (hori.contains(B) && hori.contains(C) && vert.contains(A) && vert.contains(D)) fill="+";
	    tracks[c.x][c.y] = fill;
	}
	//sort from top to bottom and left to right
	Collections.sort(carts);
    }

    
    public String left(String s) {
	String res = "";
	if (s.equals("^")) res = "<";
	if (s.equals("<")) res = "<";
	if (s.equals("v")) res = ">";
	if (s.equals(">")) res = "^";
	return res;
    }

    public String right(String s) {
	return left(left(left(s)));
    }

    public ArrayList<Cart> filter(ArrayList<Cart> ct) {
	ArrayList<Cart> l = new ArrayList<Cart>();
	for (Cart c : ct) {
	    if (!c.crashed) {
		l.add(c);
	    }
	}
	return l;
    }
    
    public void display() {
	Collections.sort(carts);
	ArrayList<Cart> toDo = new ArrayList<Cart>(carts);
	toDo = filter(toDo);
	for (int j=0; j<h; j++) {
	    for (int i=0; i<w; i++) {
		if (toDo.size()>0) {
		    Cart c = toDo.get(0);
		    if ( (!c.crashed) && (c.x==i && c.y==j)) {
			System.out.print(c.dir);
			toDo.remove(0);
			
		    } else {
			System.out.print(tracks[i][j]);
		    }
		} else {
		    System.out.print(tracks[i][j]);
		}
	    }
	    IO.print("");
	}
    }

    public boolean onlyOneLeft() {
	int count = 0;
	for (Cart c : carts) {
	    if (!c.crashed) {
		count++;
		lastx = c.x;
		lasty = c.y;
	    }
	}
	return (count == 1);
    }
    
    public void tick() {
	Collections.sort(carts);
	
	for (Cart c : carts) {
	    if (part2) {
		break;
	    } else if (!c.crashed) {
		//carts are sorted
		String d = c.dir;
		String t = tracks[c.x][c.y];
		
		if ((d.equals("^") || d.equals("v")) && t.equals("|")) {
		    //(^ of v) && |		=> dir blijft gelijk,	pos: ^ j-1 v j+1		
		    c.y = (d.equals("^")) ? (c.y-1) : (c.y+1);
		}
		if ((d.equals("<") || d.equals(">")) && t.equals("-")) {
		    //(< of >) && - 		=> dir blijft gelijk, 	pos: < i-1 > i+1
		    c.x = (d.equals("<")) ? (c.x-1) : (c.x+1);
		}
		if ((d.equals("<") && t.equals("\\")) || (d.equals(">") && t.equals("/"))) {
		    // < && \ of > && /	        => dir wordt ^		pos: j-1
		    c.dir = "^";
		    c.y--;
		}
		if ((d.equals("^") && t.equals("\\")) || (d.equals("v") && t.equals("/"))) {
		    //^ && \ of v && /  	=> dir wordt <		pos: i-1
		    c.dir = "<";
		    c.x--;
		}
		if ((d.equals(">") && t.equals("\\")) || (d.equals("<") && t.equals("/"))) {
		    //< && / of > && \	        => dir wordt v		pos: j+1
		    c.dir = "v";
		    c.y++;
		}
		if ((d.equals("^") && t.equals("/")) || (d.equals("v") && t.equals("\\"))) {
		    // ^ && / of v && \  	=> dir wordt >		pos: i+1
		    c.dir = ">";
		    c.x++;
		}
		//intersections
		if (t.equals("+")) {
		    switch(c.turns) {
		    case 0 : {
			c.left();
			break;
		    }
		    case 1 : {
			c.straight();
			break;
		    }
		    case 2 : {
			c.right();
			break;
		    }
		    default : {
			break;
		    }
		    }
		    c.turns = (c.turns+1) % 3;
		}

		//check for collisions
		for (Cart c2 : carts) {
		    if (c != c2) {
			if ( (!c.crashed) && (!c2.crashed) ) {
			    if (c.compareTo(c2) == 0) {
				if (!part1) {
				    IO.print("Part 1: " + c.x + "," + c.y);
				    part1 = true;
				}
				c.crashed = true;
				c2.crashed = true;
			    }
			}
		    }
		}
	    }
	}
	if (onlyOneLeft()) {
	    if (!part2) IO.print("Part 2: " + lastx + "," + lasty);
	    part2 = true;
	}

	Collections.sort(carts);

    }
    
    public static void main(String[] args) {
	Carts c = new Carts(args[0]);
	//c.display();
	while (!c.part2) {
	    c.tick();
	    //c.display();
	}
    }
}
