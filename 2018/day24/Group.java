import java.util.*;
import util.*;

public class Group {
    int nr,nUnits,hp,damage,initiative;
    String type;
    ArrayList<String> weaknesses,immunities;
    boolean infection;


    public Group(int i, String input, boolean infec) {
	//17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
	//347 units each with 6624 hit points (immune to fire; weak to bludgeoning) with an attack that does 148 slashing damage at initiative 12
	//5279 units each with 4712 hit points with an attack that does 8 cold damage at initiative 7
	nr = i;
	infection = infec;
	Scanner in = new Scanner(input);
        nUnits = in.nextInt();
	in.next(); in.next(); in.next(); // units each with
	hp = in.nextInt();
	in.next(); in.next(); //hit points
	String s = in.next();
	weaknesses =  new ArrayList<String>();
	immunities = new ArrayList<String>();
	//s is (weak or (immune or with

	if (s.charAt(0) == '(') {
	    //parse weaknesses and immunities, in arbitrary order
	    if (s.charAt(1) == 'w') {
		in.next(); // to
	        s = in.next();
		while (!((s.charAt(s.length()-1) == ')') || (s.charAt(s.length()-1) == ';'))) {
		    if (s.charAt(s.length()-1) == ',') s = s.substring(0,s.length()-1);
		    weaknesses.add(s);
		    s = in.next();
		}
		//last one
	        s = s.substring(0,s.length()-1);
		weaknesses.add(s);

		if (in.next().equals("immune")) {
		    in.next(); // to
		    s = in.next();
		    while (!((s.charAt(s.length()-1) == ')') || (s.charAt(s.length()-1) == ';'))) {
			if (s.charAt(s.length()-1) == ',') s = s.substring(0,s.length()-1);
			immunities.add(s);
			s = in.next();
		    }
		}
		
	    } else {
		//start with immunities
		in.next();
		s = in.next();
		while (!((s.charAt(s.length()-1) == ')') || (s.charAt(s.length()-1) == ';'))) {
		    if (s.charAt(s.length()-1) == ',') s = s.substring(0,s.length()-1);
		    immunities.add(s);
		    s = in.next();
		}
		//last one
	        s = s.substring(0,s.length()-1);
	        immunities.add(s);
		
		if (in.next().equals("weak")) {
		    in.next(); // to
		    s = in.next();
		    while (!((s.charAt(s.length()-1) == ')') || (s.charAt(s.length()-1) == ';'))) {
			if (s.charAt(s.length()-1) == ',') s = s.substring(0,s.length()-1);
		        weaknesses.add(s);
			s = in.next();
		    }
		    //last one
		    s = s.substring(0,s.length()-1);
		    weaknesses.add(s);
		    
		}
	    }
	}
	while (!in.next().equals("does")) { }
	
	damage = in.nextInt();
        type = in.next();
	in.next(); in.next(); in.next(); //damage at initiative
        initiative = in.nextInt();
    }
    
    public int effectivePower() {
	return nUnits * damage;
    }

    static Comparator<Group> getEffectivePowerComparator() {
        return new Comparator<Group>() {
	    public int compare(Group one, Group two) {
		if ( (one.effectivePower() > two.effectivePower()) || ((one.effectivePower() == two.effectivePower()) && (one.initiative > two.initiative)) ) {
		    return -1;
		} else if ((one.effectivePower() == two.effectivePower()) && (one.initiative == two.initiative)) {
		    return 0;
		} else {
		    return 1;
		}
	    }            
        };
    }    
    
    static Comparator<Group> getInitiativeComparator() {
        return new Comparator<Group>() {
	    public int compare(Group one, Group two) {
		if (one.initiative > two.initiative) {
		    return -1;
		} else if (one.initiative == two.initiative) {
		    return 0;
		} else {
		    return 1;
		}
	    }            
        };
    }    

    public Group select(ArrayList<Group> targets) {
	int max = -1;
	Group sel = null;
	for (Group t : targets) {
	    if (infection) {
		//System.out.print("Infection ");
	    } else {
		//System.out.print("Immune system ");
	    }
	    //IO.print(" group " + nr + " would deal defending group " + t.nr + " " + damageDone(t) + " damage");		
	    if (t.nUnits > 0) {
		if (damageDone(t) > max) {
		    max = damageDone(t);
		    sel = t;
		} else if (damageDone(t) == max && t.effectivePower() > sel.effectivePower()) {
		    max = damageDone(t);
		    sel = t;
		} else if (damageDone(t) == max && t.effectivePower() == sel.effectivePower() && t.initiative > sel.initiative) {
		    max = damageDone(t);
		    sel = t;
		}
	    }
	}
	if (max==0) {
	    return null;
	} else {
	    return sel;
	}
    }

    public void dealDamage(Group target) {
	if (target != null) {
	    
	    int d = damageDone(target);
	    int unitsWiped = ( d / target.hp );
	    if (unitsWiped > target.nUnits) unitsWiped = target.nUnits;
	    if (infection) {
		//System.out.print("Infection group " + nr);
	    } else {
	        //System.out.print("Immune system group " + nr);
	    }
	    //IO.print(" attacks defending group " + target.nr + ", killing " + unitsWiped + " units => damageDone = " + d + " divided by " + target.hp);
	    target.nUnits -= unitsWiped;
	}
    }
    
    public int damageDone(Group target) {
	//how much damge if this group attacks the target
	if (target.immunities.contains(type)) {
	    return 0;
	} else if (target.weaknesses.contains(type)) {
	    return 2*effectivePower();
	} else {
	    return effectivePower();
	}
    }
    
    public String toString() {
	String res = (infection) ? "INFECTION GROUP: " : "IMMUNESYS GROUP: ";
	res += "(" + nUnits + " units: HP = " + hp + " attack: " + type + " (" + damage + ")" + " initiative: " + initiative + ") WEAK TO: ";
	for (String w : weaknesses) res += w + " - ";
	res += "; IMMUNE TO: ";
	for (String i : weaknesses) res += i + " - ";
	return res;
    }
}
