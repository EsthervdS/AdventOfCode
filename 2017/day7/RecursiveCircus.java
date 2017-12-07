import java.util.*;
import util.*;

public class RecursiveCircus {

    ArrayList<String> programs;
    ArrayList<Tree> currTrees;
    
    public RecursiveCircus(String fileName) {
	programs = IO.readFile(fileName);
	currTrees = new ArrayList<Tree>();
    }

    public void computeBottomProgram() {

	for (String p : programs) {
	    String[] tokens = p.split(" ");
	    String thisNode = tokens[0];
	    int thisWeight = Integer.parseInt(tokens[1].substring(1,tokens[1].length()-1));
	    Tree thisTree = new Tree(thisNode,thisWeight);

	    if (tokens.length > 2) {
		//get string of current node
		
		int firstChild = 3;
		Tree child;
		
		for (int i = firstChild; i<tokens.length; i++) {
		    //possible situations
		    //1 : completely new tree
		    //2 : child of this tree is already in currTrees
		    //3 : this tree is already a child in a tree in currTrees

		    //get string of current child 
		    String childToken = tokens[i];
		    if (childToken.contains(",")) {
			childToken = childToken.substring(0,tokens[i].length()-1);
		    }

		    //situtation 2: check if any of the children already are in currTrees and if so, add to this tree
		    boolean childKnown = false;

		    for (Tree t : currTrees) {
			childKnown = childKnown || t.containsNode(childToken);
			if (childKnown) {
			    thisTree.addChild(t);
			    currTrees.remove(t);
			    break;
			}
		    }

		    // if child is new, create new tree and add to this tree
		    if (!childKnown) {
			child = new Tree(childToken,0);
			thisTree.addChild(child);
		    }
		    
		}
		//looped over all children in program
		//now check situation 3
		//loop over currTrees and check every tree if thisNode is an element in it
		//if so, replace existing child node with currently built tree
	    }
	    
	    boolean alreadyThere = false;
	    for (Tree t : currTrees) {
		boolean currTreeContainsThisNode = t.containsNode(thisNode);
		if (currTreeContainsThisNode) {
		    Tree parent = t.getNode(thisNode).getParent();
		    parent.removeChild(parent.getNode(thisNode));
		    parent.addChild(thisTree);
		    alreadyThere = true;
		    break;
		}
	    }
	    if (!alreadyThere) currTrees.add(thisTree);
	}
	IO.print("Current list of trees ---");
	for (Tree t : currTrees) {
	    //    IO.print(t.toString());
	}
	IO.print("Part 1: " + currTrees.get(0).getElement());

	int correction = currTrees.get(0).checkSums();
	IO.print("Part 2: " + correction);

    }

    public static void main(String[] args) {
	RecursiveCircus rc = new RecursiveCircus(args[0]);
	rc.computeBottomProgram();
    }
}
