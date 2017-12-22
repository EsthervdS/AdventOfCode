import java.util.*;
import util.*;

public class Ingredient {

    String name;
    int cap, dur, fla, tex, cal;

    public Ingredient(String line) {
	//Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
	String[] tokens = line.split(" ");
	name = tokens[0].substring(0,tokens[0].length()-1);

	cap = Integer.parseInt(tokens[2].substring(0,tokens[2].length()-1));
	dur = Integer.parseInt(tokens[4].substring(0,tokens[4].length()-1));
	fla = Integer.parseInt(tokens[6].substring(0,tokens[6].length()-1));
	tex = Integer.parseInt(tokens[8].substring(0,tokens[8].length()-1));
	cal = Integer.parseInt(tokens[10]);
    }

    public int capScore(int ts) {
	return (cap*ts);
    }

    public int durScore(int ts) {
	return (dur*ts);
    }

    public int flaScore(int ts) {
	return (fla*ts);
    }

    public int texScore(int ts) {
	return (tex*ts);
    }

    public int getCalories(int ts) {
	return (cal*ts);
    }
    
    public String toString() {
	return (name + ": capacity " + cap + ", durability " + dur + ", flavor " + fla + ", texture " + tex + ", calories " + cal);
    }

    
}
