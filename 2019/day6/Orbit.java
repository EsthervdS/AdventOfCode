import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
public class Orbit {

    public ArrayList<String> lines;
    public Tree orbits;
    public String startNode;
    public ArrayList<Tree> trees;
    
    public Orbit(String fileName) {
	lines = new ArrayList<String>();
	trees = new ArrayList<Tree>();
	
	lines = IO.readFile(fileName);

	part1();
	part2();
	
    }

    public void part1() {
	int nNodes = 0;
	
	for (String line : lines) {

	    String[] s = line.split("\\)"); 
	    //trees is a list of all (sub)trees currently known
	    //anchor: node representing the left object
	    //ship: node representing the right object
	    //for each encountered orbit map line:
	    //find the anchor in one of the trees
	    Tree anchor = findNode(trees,s[0]);
	    if (anchor.isEmpty()) {
		//if this is not available:
		//     create a new anchor node in the tree list for the left object node
		anchor = new Tree(s[0],0);
		trees.add(anchor);
	    }
	    
	    //find the ship in the list of trees
	    Tree ship = findNode(trees,s[1]);
	    if (ship.isEmpty()) {
		//if this is not available:
		//     create a new ship node for the right node
		
		ship = new Tree(s[1],0);
	    } else {
		//if this is available:
		//     remove ship from list of trees
		trees.remove(ship);
	    }
	    //add ship as a child to anchor
	    //update the numerics in the ship tree (+anchor.getNumeric()+1)
	    anchor.addChild(ship);
	    ship.incAllNumerics(anchor.getNumeric()+1);
	    

	    nNodes++;

	}

	//1 tree remaining
	IO.print("Part 1: " + trees.get(0).sumOfNumerics());

    }

    public void part2() {
	ArrayList<String> sanL = new ArrayList<String>();
	ArrayList<String> youL = new ArrayList<String>();
	Tree sanP = trees.get(0).getNode("SAN").getParent();
	Tree youP = trees.get(0).getNode("YOU").getParent();
	
	while (sanP != null) {
	    sanL.add(sanP.getElement());
	    sanP = sanP.getParent();
	}
	while (youP != null) {
	    youL.add(youP.getElement());
	    youP = youP.getParent();
	}
	
	int it = 0;
	String sS = sanL.get(sanL.size()-1-it);
	String yS = youL.get(youL.size()-1-it);
	while (sS.equals(yS) ) {
	    it++;
	    sS = sanL.get(sanL.size()-1-it);
	    yS = youL.get(youL.size()-1-it);	    
	}
	
	IO.print("Part 2: " + ((sanL.size()-it) + (youL.size()-it)));
    }
    
    public Tree findNode(ArrayList<Tree> tl, String s) {
	Tree res = new Tree("",0);
	for(Tree t : tl) {
	    res = t.getNode(s);
	    if (res.getElement().equals(s)) {
		//found it!
		break;
	    }
	}
	return res;
    }

    public static void main(String[] args) {
	Orbit o = new Orbit(args[0]);

     }
}
