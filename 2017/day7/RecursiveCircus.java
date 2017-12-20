import java.util.*;
import util.*;

public class RecursiveCircus {

    ArrayList<String> programs;
    ArrayList<Tree> knownTrees;
    
    public RecursiveCircus(String fileName) {
	programs = IO.readFile(fileName);
	knownTrees = new ArrayList<Tree>();
    }

    public int indexKnownTree(String id) {
	int i = -1;
	for (Tree t : knownTrees) {
	    if (t.getElement().equals(id)) {
		i = knownTrees.indexOf(t);
	    }
	}
	return i;
    }

    public void computeBottomProgram() {
	ArrayList<String> toDoTrees = new ArrayList<String>();
	//add all leafs
	for (String line : programs) {
	    if (line.contains("->")) {
		toDoTrees.add(line);
	    } else {
		String wStr = line.split(" ")[1].trim();
		int weight = Integer.parseInt(wStr.substring(1,wStr.length()-1));
		String id = line.split(" ")[0].trim(); 
		Tree thisTree = new Tree(id,weight);
		knownTrees.add(thisTree);
	    }
	}
	//all leafs in knownTrees, all other lines in toDoTrees
	while (toDoTrees.size() > 0) {
	    String str = toDoTrees.remove(0);
	    String[] children = str.split("->")[1].split(",");
	    String wStr = str.split(" ")[1].trim();
	    int weight = Integer.parseInt(wStr.substring(1,wStr.length()-1));
	    String id = str.split(" ")[0].trim();
	    if (id.equals("kuvek")) IO.print("HIER1");
	    Tree thisTree = new Tree(id,weight);
	    for (int i=0; i<children.length; i++) {
		if (id.equals("kuvek")) IO.print("HIER2");
		children[i] = children[i].trim();
		int index = indexKnownTree(children[i]);
		if (index != -1) {
		    if (id.equals("kuvek")) IO.print("HIER3");
		    Tree c = knownTrees.get(index);
		    thisTree.addChild(c);
		    knownTrees.remove(c);
		} 
	    }
	    if (thisTree.hasChildren()) {
		if (id.equals("kuvek")) IO.print("HIER4");
		    
		knownTrees.add(thisTree);
	    } else {
		if (id.equals("kuvek")) IO.print("HIER5");
		toDoTrees.add(str);
	    }
	}
	IO.print(knownTrees.size() + "");
	IO.print("Part 1: " + knownTrees.get(0).getElement());
    }

    public void part2() {
	ArrayList<Tree> toCheck = allLeafs(knownTrees.get(0));

	while (toCheck.size() > 0) {
	    //check if sum of this leaf is same as sum of siblings
	    //if same, remove this one and siblings from toCheck
	    //add parent to toCheck
	    Tree t = toCheck.remove(0);
	    IO.print("Checking tree: " + t.getElement() + " : " + t.getSum());
	    ArrayList<Integer> sums = new ArrayList<Integer>();
	    sums.add(t.getSum());
	    ArrayList<Tree> siblings = t.getParent().getChildren();
	    //make sure t is at the front of the list
	    siblings.remove(t);
	    for (Tree s : siblings) {
		IO.print(s.getElement() + " : " + s.getSum());
		sums.add(s.getSum());
	    }
	    siblings.add(0,t);
	    int i = getDifferingIndex(sums);
	    if (i == -1) {
		for (Tree s : siblings) {
		    toCheck.remove(s);
		}
		toCheck.add(t.getParent());
	    } else {
		IO.print(sums.toString());
		IO.print(getDifferingIndex(sums)+ " is wrong index");
		IO.print(siblings.get(i).getElement() + " should be different");
		//correction should depend on sum of sibling & sum of children
		int sumChildren = 0;
		Tree corr = siblings.get(i);
		int correction = 0;
		if (i>0) {
		    correction = siblings.get(i-1).getSum();
		} else {
		    correction = siblings.get(i+1).getSum();
		}
		IO.print(correction + "");
		IO.print(t.getSum()+"");
		IO.print(t.getNumeric()+"");
		IO.print("Part 2: " + (correction - t.getSum() + t.getNumeric()));
		break;
	    }
	    
	}
    }

    public int getDifferingIndex(ArrayList<Integer> l) {
	HashSet<Integer> s = new HashSet<Integer>(l);
	int diff;
	if (s.size() == 1) {
	    return -1;
	} else {
	    diff = -1;
	    for (int i=0; i<l.size(); ++i) {
		int l1 = l.get(i);
		int l2 = l.get(i+1);
		if (l1 != l2) {
		    diff = i+1;
		    break;
		}
	    }
	    int l1 = l.get(1);
	    int l2 = l.get(2);
	    if ((diff==1) && (l1 == l2)) {
		diff = 0;
	    }
	}
	return diff;
    }
    
    public ArrayList<Tree> allLeafs(Tree t) {
	ArrayList<Tree> res = new ArrayList<Tree>();

	if (t.hasChildren()) {
	    ArrayList<Tree> tc = t.getChildren();
	    for (Tree c : tc) {
		res.addAll(allLeafs(c));
	    }
	} else {
	    res.add(t);
	}
	return res;

    }
    
    public static void main(String[] args) {
	RecursiveCircus rc = new RecursiveCircus(args[0]);
	rc.computeBottomProgram();
	rc.part2();
    }
}
