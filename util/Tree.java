package util;

import java.util.*;
import util.*;

public class Tree {

    private String element;
    private int numeric;
    private int sum;
    private ArrayList<Tree> children;
    private Tree parent;
    
    public Tree(String thisNode, int num, Tree[] chldn, int nChildren) {
	//internal node
	element = new String(thisNode);
	numeric = num;
	children = new ArrayList<Tree>();
	sum = numeric;
	for(Tree c : chldn) {
	    children.add(c);
	    c.setParent(this);
	    sum += c.getSum();
	}
	parent = null;
	
    }
    public Tree(String thisNode, int num) {
	//leaf
	element = new String(thisNode);
	numeric = num;
	children = new ArrayList<Tree>();
	parent = null;
	sum = numeric;
    }

    public int getSum() {
	return sum;
    }
    
    public String getElement() {
	return element;
    }

    public int getNumeric() {
	return numeric;
    }

    public ArrayList<Tree> getChildren() {
	return children;
    }

    public void addChild(Tree c) {
	children.add(c);
	c.setParent(this);
	sum += c.getSum();
    }

    public void removeChild(Tree c) {
	c.unsetParent();
	children.remove(c);
	sum -= c.getSum();
    }

    public boolean hasChildren() {
	return (children.size() > 0);
    }

    public void setParent(Tree p) {
	parent = p;
    }
    public void unsetParent() {
	parent = null;
    }

    public Tree getParent() {
	return parent;
    }
    
    public boolean containsNode(String s) {
	boolean contains = (element.equals(s));
	if (!contains) {
	    boolean con = false;
	    for(Tree c: children) {
		con = con || c.containsNode(s);
		if (con) break;
	    }
	    contains = con;
	}
	return contains;
    }

    public Tree getNode(String s) {
	Tree node = new Tree("",0);
	
	if (element.equals(s)) {
	    node = this;
	} else {
	    for (Tree c : children) {
		if (c.containsNode(s)) {
		    node = c.getNode(s);
		}
	    }
	}
	return node;
    }
    
    public String toString() {
	String res = "";
	if (element.equals("")) {
	    res += "X";
	} else {
	    res += "Node: " + element + "(" + numeric + ") and sum = " + sum + " - ";
	}
	for (Tree c : children) {
	    res += "(" + c.toString() + ")";
	}
	return res;
    }

    public int checkSums() {

	int correction = 0;
	ArrayList<Integer> cw = new ArrayList<Integer>();
	int childrenSum = 0;

	if ((children == null) || (children.size()==0)) {

	    //this is leaf, and this node is the problem
	    ArrayList<Tree> siblings = parent.getChildren();
	    for (Tree s : siblings) {
		if (s.getNumeric() != numeric) {
		    correction = s.getNumeric();
		    break;
		}
	    }
	    
	} else {
	    //check if children all have the same weight

	    for(Tree c : children) {
		cw.add(c.getSum());
		childrenSum += c.getSum();
	    }
	    //one of the children has different weight
	    //recursive call on child with different weight
	    boolean found = false;
	    for (int i=0; i<cw.size(); i++) {
		if (count(cw,cw.get(i))==1) {
		    //found it!
		    found = true;
		    correction = children.get(i).checkSums();
		    break;
		}
	    }
	    if (!found) {
		ArrayList<Tree> siblings = parent.getChildren();
		for (Tree s : siblings) {
		    if (s.getSum() != sum) {
			correction = s.getSum() - childrenSum;
			break;
		    }
		}
		
	    }
	}
	//somehow still incorrect. works for test_input, incorrect answer for puzzle input :(
	return correction;
    }

    public int count(ArrayList<Integer> l, int i) {
	int cnt = 0;
	for(int j : l) {
	    if (j==i) cnt++;
	}
	return cnt;
    }
}
