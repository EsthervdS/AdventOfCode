import util.*;
import java.util.*;

public class Containers {

    ArrayList<String> lines;
    int[] ctainers;
    int nCtainers;
    int goal;
    HashMap<Integer,Integer> comboCount;

    
    public Containers(String fileName) {
	lines = IO.readFile(fileName);
	nCtainers = lines.size();
	ctainers = new int[nCtainers];
	comboCount = new HashMap<Integer,Integer>();
	int i=0;
	for (String line : lines) {
	    ctainers[i] = Integer.parseInt(line);
	    i++;
	}

	//for (int j=0;j<i; j++) IO.print(ctainers[j]+"");
    }

    public void process() {
	//create list of all bit patterns of size nCtainers
	//check for each of them if they fit
	//if so, add 1 to total amount
	int nCombos = 0;
	String combo = firstString();
	String allOnes = lastString();
	while (!combo.equals(allOnes)) {
	    combo = next(combo);
	    int vol = volume(combo);
	    //IO.print("combo = " + combo + " and volume = " + vol);
	    if (vol == goal) {
		nCombos++;
		int nOnes = nOnes(combo);
		if (comboCount.containsKey(nOnes)) {
		    int cur = comboCount.get(nOnes);
		    comboCount.remove(nOnes);
		    comboCount.put(nOnes,cur+1);
		} else {
		    comboCount.put(nOnes,1);
		}
	    }
	}
	//get min key from comboCount
	TreeSet<Integer> sortedKeys = new TreeSet<Integer>(comboCount.keySet());
	IO.print("Part 1: " + nCombos);
	IO.print("Part 2: " + comboCount.get(sortedKeys.first()));
   }

    String firstString() {
	String res = "";
	for (int i=0; i<nCtainers; i++) res += "0";
	return res;
    }

    String lastString() {
	String res = "";
	for (int i=0; i<nCtainers; i++) res += "1";
	return res;
    }    
    
    String next(String s) {
	String res = "";
	int i=nCtainers-1;
	while (s.charAt(i) == '1') {
	    res = "0" + res;
	    i--;
	}
	res = "1" + res;
	i--;
	while (i>=0) {
	    res = s.charAt(i) + res;
	    i--;
	}
	return res;
    }

    int nOnes(String s) {
	int nO = 0;
	for (int i=0; i<nCtainers; i++) {
	    if (s.charAt(i) == '1') nO++;
	}
	return nO;
    }
    
    int volume(String s) {
	int sum = 0;
	for (int i=0; i<nCtainers; i++) {
	    if (s.charAt(i) == '1') {
		sum += ctainers[i];
	    }
	}
	return sum;
    }


    
    public boolean fits(String cts) {
	return false;
    }

    
    public static void main(String[] args) {
	Containers containers = new Containers(args[0]);
	containers.goal = 150;
	containers.process();
    }
}
