import util.*;
import java.util.*;

public class CountingChars {

    ArrayList<String> lines;
    
    public CountingChars(String fileName) {
	lines = IO.readFile(fileName);
    }

    public void process() {
	int codeCount = 0;
	int memCount = 0;
	int newCount = 0;

	for (String l : lines) {
	    codeCount += l.length();
	    String str = l.substring(1,l.length()-1);
	    int mc = memCount(str);
	    memCount += mc;
	    newCount += l.length() + 4 + specialCharacters(str);
	}

	IO.print("Part 1: " + (codeCount - memCount));
	IO.print("Part 2: " + (newCount - codeCount));
    }

    private int specialCharacters(String str) {
	int cnt = 0;
	int i=0;
	while (i<str.length()) {
	    char c = str.charAt(i);
	    if ((c == '\"') || (c == '\\')) cnt++;
	    i++;
	}
	return cnt;
    }
    
    private int memCount(String str) {
	int cnt = 0;
	int index = 0;
	while (index<str.length()) {
	    Character c = str.charAt(index);
	    if (c=='\\') {
		if (index<str.length()-1) {
		    if (str.charAt(index+1) == 'x') {
			index += 3;
		    } else if (str.charAt(index+1) == '\\') {
			index += 2;
			cnt++;
		    } else {
			index++;
		    }
		} else {
		    index++;
		}
	    } else {
		cnt++;
		index++;
	    }
	}
	return cnt;
    }
    
    public static void main(String[] args) {
	CountingChars cc = new CountingChars(args[0]);
	cc.process();
    }
}
