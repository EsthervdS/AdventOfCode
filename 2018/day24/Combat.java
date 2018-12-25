import java.util.*;
import util.*;

public class Combat {
    ArrayList<String> lines;
    ArrayList<Group> immunesystem;
    ArrayList<Group> infection;
    HashMap<Group,Group> selection;

    public Combat(String filename) {
	lines = IO.readFile(filename);
	init();
	while (immunesystem.size()>0 && infection.size()>0) {
	    printGroups();
	    targetSelection();
	    attack();
	}
	printGroups();
	int part1 = -1;
	if (immunesystem.size() == 0) {
	    part1 = remainingUnits(infection);
	} else {
	    part1 = remainingUnits(immunesystem);
	}
	IO.print("Part 1: " + part1);
    }

    public void init() {
	immunesystem = new ArrayList<Group>();
	infection = new ArrayList<Group>();
	boolean immune = true;
	int i=1;
	int itF=1;
	int itM=1;
	while(i<lines.size()) {
	    if (lines.get(i).equals("")) {
		i++;
	    }
	    if (lines.get(i).equals("Infection:")) {
		i++;
		immune = false;
	    }
	    
	    if (immune) {
		Group g = new Group(itM,lines.get(i),false);
		immunesystem.add(g);
		IO.print("Created group : " + g.toString());
		itM++;
	    } else {
		Group g = new Group(itF, lines.get(i),true);
	        infection.add(g);
		IO.print("Created group : " + g.toString());
		itF++;
	    }
	    i++;
	}
    }

    public void printGroups() {
	IO.print("Immune System:");
	for (Group g : immunesystem) {
	    IO.print("Group " + g.nr + " contains " + g.nUnits + " units");
	}
	if (immunesystem.size() == 0) {
	    IO.print("No groups remain");
	}
	IO.print("Infection:");
	for (Group g : infection) {
	    IO.print("Group " + g.nr + " contains " + g.nUnits + " units");
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
	IO.print("");
    }

    public void attack() {
	ArrayList<Group> allGroups = new ArrayList<Group>();
	allGroups.addAll(immunesystem);
	allGroups.addAll(infection);
	Collections.sort(allGroups, Group.getInitiativeComparator());

	for (Group g : allGroups) {
	    Group t = selection.get(g);
	    if ((t != null) && (g.nUnits > 0) && (t.nUnits > 0)) {
		g.dealDamage(t);
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
	    if (g.nUnits > 0) {
		c += g.nUnits;
	    }
	}
	return c;
    }
    
    public static void main(String[] args) {
	Combat c = new Combat(args[0]);
    }
}
