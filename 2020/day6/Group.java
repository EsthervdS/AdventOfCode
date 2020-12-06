import java.util.*;
import util.*;

class Group {

    public ArrayList<HashSet<Character>> questions;

    public Group(ArrayList<String> input) {
	questions = new ArrayList<HashSet<Character>>();

	for (String i : input) {
	    HashSet<Character> cur = new HashSet<Character>();
	    for (int k=0; k<i.length(); k++) {
		cur.add(i.charAt(k));
	    }
	    questions.add(cur);
	}
    }
    
    public int count1() {
	//union of all sets
	HashSet<Character> res = new HashSet<Character>(questions.get(0));
	for (int i=1; i<questions.size(); i++) res.addAll(questions.get(i));
	return res.size();
	
    }
    public int count2() {
	//intersection of all sets
	HashSet<Character> res = new HashSet<Character>(questions.get(0));
	for (int i=1; i<questions.size(); i++) res.retainAll(questions.get(i));
	return res.size();
    }
}
