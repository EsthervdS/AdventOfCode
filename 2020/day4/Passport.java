import java.io.*;
import java.util.*;
import util.*;

class Passport {

    public HashMap<String,String> elements;

    public Passport(ArrayList<String> input) {
	//input is (set of) line(s) with key:value pairs
	int i=0;
	elements = new HashMap<String,String>();
	while (i<input.size()) {
	    String[] pairs = input.get(i).split(" ");
	    for (String s : pairs) {
		String[] pair = s.split(":");
		elements.put(pair[0],pair[1]);
	    }
	    i++;
	}
    }

    public boolean isValid1() {
	return (elements.size() == 8) || ((elements.size() == 7) && (!elements.containsKey("cid")));
    }

    public boolean isValid2() {
	boolean res = true;

	if (elements.containsKey("byr")) {
	    res = res && isBirthYear(elements.get("byr"));
	} else res = false;

	if (elements.containsKey("iyr")) {
	    res = res && isIssueYear(elements.get("iyr"));
	} else res = false;

	if (elements.containsKey("eyr")) {
	    res = res && isExpirationYear(elements.get("eyr"));
	} else res = false;

	if (elements.containsKey("hgt")) {
	    res = res && isHeight(elements.get("hgt"));
	} else res = false;

	if (elements.containsKey("hcl")) {
	    res = res && isHairColour(elements.get("hcl"));
	} else res = false;

	if (elements.containsKey("ecl")) {
	    res = res && isEyeColour(elements.get("ecl"));
	} else res = false;

	if (elements.containsKey("pid")) {
	    res = res && isPassportID(elements.get("pid"));					       
	} else res = false;
	
	return res;
    }

    public boolean isBirthYear(String s) {
	//four digits; at least 1920 and at most 2002
	int byr = Integer.parseInt(s);
	return (byr>=1920) && (byr<=2002);
    }

    public boolean isIssueYear(String s) {
	//four digits; at least 2010 and at most 2020
	int iyr = Integer.parseInt(s);
	return (iyr>=2010) && (iyr<=2020);
    }

    public boolean isExpirationYear(String s) {
	//four digits; at least 2020 and at most 2030
	int eyr = Integer.parseInt(s);
	return (eyr>=2020) && (eyr<=2030);
    }

    public boolean isHeight(String s) {
	//a number followed by either cm or in:
	boolean res = true;
	if (s.charAt(s.length()-2) == 'c' && s.charAt(s.length()-1) == 'm') {
	    //If cm, the number must be at least 150 and at most 193.
	    int hi = Integer.parseInt(s.substring(0,s.length()-2));
	    res = res && (hi>=150 && hi<=193);
	} else if (s.charAt(s.length()-2) == 'i' && s.charAt(s.length()-1) == 'n') {
	    //If in, the number must be at least 59 and at most 76.
	    int hi = Integer.parseInt(s.substring(0,s.length()-2));
	    res = res && (hi>=59 && hi<=76);
	} else res = false;
	return res;
    }

    public boolean isHairColour(String s) {
	//a # followed by exactly six characters 0-9 or a-f
	boolean res = true;
	res = res && (s.charAt(0) == '#');	
	int i=1;
	while (i<s.length()) {
	    char c = s.charAt(i);
	    res = res && ( ( (c>='0') && (c<='9') ) || ( (c>='a') && (c<='f') ) );		
	    i++;
	}
	if (i != 7) res = false;
	return res;
    }

    public boolean isEyeColour(String s) {
	//exactly one of: amb blu brn gry grn hzl oth
	ArrayList<String> cls = new ArrayList<>(Arrays.asList("amb","blu","brn","gry","grn","hzl","oth"));
	return cls.contains(s);
    }

    public boolean isPassportID(String s) {
	//a nine-digit number, including leading zeroes
	boolean res = true;
	int i = 0;
	while (i<s.length()) {
	    char c = s.charAt(i);
	    res = res && ( (c>='0') && (c<='9') );
	    i++;
	}
	if (i!=9) res = false;
	return res;
    }
    
    public String toString() {
	String res = "";
	for (String k : this.elements.keySet()) {
	    res += "("+ k + " = " + this.elements.get(k) + ") ";
	}
	return res + isValid2();
    }

}
