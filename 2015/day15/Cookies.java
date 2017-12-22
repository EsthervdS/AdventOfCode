import util.*;
import java.util.*;

public class Cookies {

    ArrayList<String> lines;
    ArrayList<Ingredient> ingredients;
    int maxScore;
    
    public Cookies(String fileName) {
	lines = IO.readFile(fileName);
	ingredients = new ArrayList<Ingredient>();
	for (String line : lines) {
	    Ingredient ing = new Ingredient(line);
	    ingredients.add(ing);
	}

	maxScore = -1;
    }

    public void process() {
	int nIng = ingredients.size();
	int[] n = new int[nIng]; // amounts of teaspoons for each ingredient
	//create configurations of amounts that add up to 100
	for (int i=0; i<=100; i++) {
	    for (int j=0; j<100-i; j++) {
		for (int ii=0; ii<100-j-i; ii++) {
		    n[0] = i;
		    n[1] = j;
		    n[2] = ii;
		    n[3] = 100-i-j-ii;
	    
		    int capScore,durScore,flaScore,texScore, cals;
		    capScore = durScore = flaScore = texScore = cals = 0;
		    for (int k=0; k<nIng; k++) {
			capScore += ingredients.get(k).capScore(n[k]);
			durScore += ingredients.get(k).durScore(n[k]);
			flaScore += ingredients.get(k).flaScore(n[k]);
			texScore += ingredients.get(k).texScore(n[k]);
			cals += ingredients.get(k).getCalories(n[k]);
		    }
		    if (capScore < 0) capScore = 0;
		    if (durScore < 0) durScore = 0;
		    if (flaScore < 0) flaScore = 0;
		    if (texScore < 0) texScore = 0;
		    
		    int totalScore = capScore * durScore * flaScore * texScore;
		    if ((totalScore > maxScore) && (cals == 500)) maxScore = totalScore;
		}
	    }
	}
	IO.print("Part 1: " + maxScore);
    }
    
    public static void main(String[] args) {
	Cookies cks = new Cookies(args[0]);
	cks.process();
    }
}
