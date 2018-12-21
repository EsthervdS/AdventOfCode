import java.util.*;
import util.*;

public class Tree {
    public String init;
    public boolean empty;
    public ArrayList<Tree> children;
    public Tree next;
    
    public Tree(String line) {
	int i = 0;
	init = "";
	children = new ArrayList<Tree>();
	next = null;

	if (line.equals("")) {
	    empty = true;
	} else {

	    empty = false;
	    char c = line.charAt(i);	    
	    while (i<line.length()) {
		c = line.charAt(i);
		if (c == '(') break;
		init = init + c;
		i++;
	    }

	    if (c == '(') {
		int level = 0;
		int splitIndex = i+1;
		ArrayList<String> childrenStrings = new ArrayList<String>();
		String remainder = line.substring(splitIndex,line.length());
		splitIndex = 0;
		StringBuilder curChild = new StringBuilder("");
		while (! ((level == 0) && (remainder.charAt(splitIndex) == ')'))) {
		    curChild = new StringBuilder("");
		    while (! ((level==0) && ((remainder.charAt(splitIndex) == '|') || (remainder.charAt(splitIndex) == ')')) ) ) {
			if (remainder.charAt(splitIndex) == '(') level++;
			if (remainder.charAt(splitIndex) == ')') level--;
			curChild.append(remainder.charAt(splitIndex));
			splitIndex++;
		    }
		    childrenStrings.add(curChild.toString());
		    if ((level==0) && (remainder.charAt(splitIndex) == ')')) {
			splitIndex++;
			break;
		    } else splitIndex++;
		}

		for (String childString : childrenStrings) {
		    if (!(childString.equals(""))) {
			Tree child = new Tree(childString);
			children.add(child);
		    }
		}
		if (splitIndex < remainder.length()-1) {
		    String nextS = remainder.substring(splitIndex,remainder.length());
		    next = new Tree(nextS);
		}
	    } else {
		// no branch
	    }
	}
    }

    public ArrayList<String> allPaths() {
	ArrayList<String> paths = new ArrayList<String>();
	ArrayList<String> nextPaths = new ArrayList<String>();
	ArrayList<String> childrenPaths = new ArrayList<String>();

	if ((children.size() == 0) && (next == null)) {
	    paths.add(init);
	}
	if (next != null) {
	    nextPaths = next.allPaths();
	}
	if (children.size() > 0) {
	    for (Tree c : children) {
		childrenPaths.addAll(c.allPaths());
	    }
	}
	if ((children.size() == 0) && (next != null)) {
	    paths.addAll(addPrefix(nextPaths,init));
	}
	if ((children.size() > 0) && (next == null) ) {
	    paths.addAll(addPrefix(childrenPaths,init));
	}
	if ( (children.size() > 0) && (next != null) ) {
	    for (Tree c : children) {
		ArrayList<String> childrenPathsWithPrefix = addPrefix(c.allPaths(), init);
		for (String cp : childrenPathsWithPrefix) {
		    for (String np : nextPaths) {
			paths.add(cp+np);
		    }
		}
	    }
	}
	return paths;
    }

    private ArrayList<String> addPrefix(ArrayList<String> childrenPaths, String prefix) {
	ArrayList<String> res = new ArrayList<String>();
	for (String cp : childrenPaths) {
	    StringBuilder sb = new StringBuilder(prefix);
	    sb.append(cp);
	    res.add(sb.toString());
	}
	return res;
    }

    public int pathLength() {
	int res = init.length();
	if (next != null) res += next.pathLength();
	return res;
    }

    public String toString() {
	return toString(0);
    }
    public String toString(int l) {
	StringBuilder res = new StringBuilder();

	for (int i=1; i<l; i++) {
	    res.append("   ");
	}
	res.append("TREE " + init + " \n");
	
	for (Tree c : children) {
	    res.append("   " + c.toString(l+1));
	}
	
	if (next != null) {
	    res.append(next.toString());
	}
	return res.toString();
    }
}
