import java.util.*;
import util.*;

public class Combat {
    ArrayList<String> lines;
    ArrayList<Group> immunesystem;
    ArrayList<Group> infection;
    HashMap<Group,Group> selection;
    boolean deadlock;
    int boost;
    
    public Combat(String filename, int b) {
	lines = IO.readFile(filename);
	boost = b;
	init(boost);
	
	deadlock = false;
	while ((immunesystem.size()>0 && infection.size()>0) && !deadlock) {
	    targetSelection();
	    attack();
	}
    }

    public void winnersUnits() {
	int unitsRemaining = -1;
	if (immunesystem.size() == 0) {
	    unitsRemaining = remainingUnits(infection);
	} else {
	    unitsRemaining = remainingUnits(immunesystem);
	}
	IO.print(unitsRemaining+"");
    }
	
    public void init(int boost) {
	immunesystem = new ArrayList<Group>();
	infection = new ArrayList<Group>();
	boolean immune = true;
	int i=1;
	int itG=1;
	while(i<lines.size()) {
	    if (lines.get(i).equals("")) {
		i++;
	    }
	    if (lines.get(i).equals("Infection:")) {
		i++;
		immune = false;
	    }
	    if (immune) {
		Group g = new Group(itG,lines.get(i),false,boost);
		immunesystem.add(g);
		//IO.print("Created group : " + g.toString());
	    } else {
		Group g = new Group(itG, lines.get(i),true,0);
	        infection.add(g);
		//IO.print("Created group : " + g.toString());
	    }
	    itG++;
	    i++;
	}
    }

    public void printGroups() {
	IO.print("Immune System:");
	for (Group g : immunesystem) {
	    IO.print("Group " + g.nr + " contains " + g.nUnits + " units with damage " + g.damage + " with effective power: " + g.effectivePower() + " and initiative: " + g.initiative);
	}
	if (immunesystem.size() == 0) {
	    IO.print("No groups remain");
	}
	IO.print("Infection:");
	for (Group g : infection) {
	    IO.print("Group " + g.nr + " contains " + g.nUnits + " units with damage " + g.damage + " with effective power: " + g.effectivePower() + " and initiative: " + g.initiative);
	}
	if (infection.size() == 0) {
	    IO.print("No groups remain");
	}
	IO.print("");
    }

    public void targetSelection() {
	selection = new HashMap<Group,Group>();
	Collections.sort(immunesystem, Group.getEffectivePowerComparator());
	Collections.sort(infection, Group.getEffectivePowerComparator());

	ArrayList<Group> infecCopy = new ArrayList<Group>(infection);
	ArrayList<Group> immunCopy = new ArrayList<Group>(immunesystem);
	Collections.sort(infecCopy,Group.getEffectivePowerComparator());
	Collections.sort(immunCopy,Group.getEffectivePowerComparator());
	for (Group g : infection) {
	    if (g.nUnits > 0) {
		Group t = g.select(immunCopy);
		selection.put(g,t);
		immunCopy.remove(t);
	    }
	}
	for (Group g : immunesystem) {
	    if (g.nUnits > 0) {
		Group t = g.select(infecCopy);
		selection.put(g,t);
		infecCopy.remove(t);
	    }
	}
    }

    public void attack() {
	ArrayList<Group> allGroups = new ArrayList<Group>();
	allGroups.addAll(immunesystem);
	allGroups.addAll(infection);
	Collections.sort(allGroups, Group.getInitiativeComparator());
	deadlock = true;
	for (Group g : allGroups) {
	    Group t = selection.get(g);
	    if ((t != null) && (g.nUnits > 0) && (t.nUnits > 0)) {
		g.dealDamage(t);
		deadlock = deadlock && (g.damageDone(t) < t.hp);
	    }
	}
	purgeEmptyGroups(immunesystem);
	purgeEmptyGroups(infection);
    }

    public void purgeEmptyGroups(ArrayList<Group> l) {
	int i=0;
	while (i<l.size()) {
	    if (l.get(i).nUnits == 0) {
		l.remove(i);
	    } else {
		i++;
	    }
	}
    }
    
    public int remainingUnits(ArrayList<Group> gs) {
	int c = 0;
	for (Group g : gs) {
	    c += g.nUnits;
	}
	return c;
    }
    
    public static void main(String[] args) {
	Combat c = new Combat(args[0],0);
	c.winnersUnits();
	int b=1;
	c = new Combat(args[0],b);
	while ( ! (c.infection.size() == 0) ) {
	    c = new Combat(args[0],b);
	    b++;
	} 
	c.winnersUnits();
   }
}
