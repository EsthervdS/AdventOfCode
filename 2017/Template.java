import util.*;
import java.util.*;

public class <name> {

    ArrayList<String> lines;
    
    public <name>(String fileName) {
	lines = IO.readFile(fileName);
    }

    public void process() {

    }
    
    public static void main(String[] args) {
	<name> xx = new <name>(args[0]);
	xx.process();
    }
}
