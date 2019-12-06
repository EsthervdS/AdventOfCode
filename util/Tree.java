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

    public void incAllNumerics(int i) {
	numeric += i;
	for (Tree c : children) {
	    c.incAllNumerics(i);
	}
    }

    public int sumOfNumerics() {
	int s = numeric;
	for (Tree c : children) {
	    s += c.sumOfNumerics();
	}
	return s;
    }

    public boolean isEmpty() {
	return (element.equals("") && (numeric==0));
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

}
