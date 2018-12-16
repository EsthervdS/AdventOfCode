import java.util.*;

public class Operations {

    public static HashMap<String,HashSet<Integer>> opCounts;
    public static HashMap<Integer,String> opMap;
    public static HashMap<String,Integer> opMapRev;
    
    public static void init() {	
	opCounts = new HashMap<String,HashSet<Integer>>();
	opMap = new HashMap<Integer,String>();
	opMapRev = new HashMap<String,Integer>();
	
	String[] opStrings = {"addr","addi","mulr","muli","banr","bani","borr","bori","setr","seti","gtir","gtri","gtrr","eqir","eqri","eqrr" };
	for (int i=0; i<16; i++) {
	    opCounts.put(opStrings[i],new HashSet<Integer>());
	}
    }

    public static String displayOps() {
	String res = "";
	for (String op : opCounts.keySet()) {
	    res += (op + ": " + opCounts.get(op).toString() + "\n");

	}
	return res;
    }

    public static void computeMapping() {
	HashMap<String,HashSet<Integer>> temp = new HashMap<String,HashSet<Integer>>(opCounts);
	while (!temp.isEmpty()) {
	    for (String op : temp.keySet()) {
		if (temp.get(op).size() == 1) {
		    int opcode = temp.get(op).iterator().next();
		    opMap.put(opcode,op);
		    opMapRev.put(op,opcode);
		    for (String op2 : temp.keySet()) {
			temp.get(op2).remove(opcode);
		    }
		    temp.remove(op);
		    break;
		}
	    }
	}
    }
    
    public static int[] perform(int[] in, int opcode, int a, int b, int c) {
	int[] out = new int[4];
	String op = opMap.get(opcode);
	switch(op) {
	case "seti": {
	    out = setI(in,a,b,c);
	    break;
	}
	case "eqir": {
	    out = eqIR(in,a,b,c);
	    break;
	}
	case "setr": {
	    out = setR(in,a,b,c);
	    break;
	}
	case "gtir": {
	    out = gtIR(in,a,b,c);
	    break;
	}
	case "addi": {
	    out = addI(in,a,b,c);
	    break;
	}
	case "muli": {
	    out = mulI(in,a,b,c);
	    break;
	}
	case "mulr": {
	    out = mulR(in,a,b,c);
	    break;
	}
	case "gtrr": {
	    out = gtRR(in,a,b,c);
	    break;
	}
	case "bani": {
	    out = banI(in,a,b,c);
	    break;
	}
	case "gtri": {
	    out = gtRI(in,a,b,c);
	    break;
	}
	case "bori": {
	    out = borI(in,a,b,c);
	    break;
	}
	case "banr": {
	    out = banR(in,a,b,c);
	    break;
	}
	case "borr": {
	    out = borR(in,a,b,c);
	    break;
	}
	case "eqri": {
	    out = eqRI(in,a,b,c);
	    break;
	}
	case "eqrr": {
	    out = eqRR(in,a,b,c);
	    break;
	}
	case "addr": {
	    out = addR(in,a,b,c);
	    break;
	}
	default: break;
	}
	return out;
    }
    
    public static int[] addR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a]+in[b];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public static int[] addI(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a]+b;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] mulR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a]*in[b];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public static int[] mulI(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a]*b;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] banR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a]&in[b];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public static int[] banI(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a]&b;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] borR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a]|in[b];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public static int[] borI(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a]|b;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] setR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = in[a];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public static int[] setI(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[i] = a;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] gtIR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[c] = (a > in[b]) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] gtRI(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[c] = (in[a] > b) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] gtRR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[c] = (in[a] > in[b]) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] eqIR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[c] = (a == in[b]) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] eqRI(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[c] = (in[a] == b) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public static int[] eqRR(int[] in, int a, int b, int c) {
	int[] out = new int[4];
	for (int i=0; i<4; i++) {
	    if (i==c) {
		out[c] = (in[a] == in[b]) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public static boolean addr(int[] in, int a, int b, int c, int[] out) {
	// addr A B C => out[c] = in[a] + in[b], and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == (in[a] + in[b]));
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match addr: " + out[c] + " == (" + in[a] + " + " + in[b] + ")");
	return res;
    }

    public static boolean addi(int[] in, int a, int b, int c, int[] out) {
	// addi A B C => out[c] = in[a] + b, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == (in[a] + b));
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match addi: " + out[c] + " == (" + in[a] + " + " + b + ")");
	return res;
    }

    public static boolean mulr(int[] in, int a, int b, int c, int[] out) {
	// mulr A B C => out[c] = in[a] * in[b], and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == (in[a] * in[b]));
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match mulr: " + out[c] + " == (" + in[a] + " * " + in[b] + ")");
	return res;
    }

    public static boolean muli(int[] in, int a, int b, int c, int[] out) {
	// muli A B C => out[c] = in[a] * b, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == (in[a] * b));
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match muli: " + out[c] + " == (" + in[a] + " * " + b + ")");
	return res;
    }

    public static boolean banr(int[] in, int a, int b, int c, int[] out) {
	// banr A B C => out[c] = in[a] & in[b], and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == (in[a] & in[b]));
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match banr: " + out[c] + " == (" + in[a] + " & " + in[b] + ")");
	return res;
    }

    public static boolean bani(int[] in, int a, int b, int c, int[] out) {
	// bani A B C => out[c] = in[a] & b, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == (in[a] & b));
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match bani: " + out[c] + " == (" + in[a] + " & " + b + ")");
	return res;
    }

    public static boolean borr(int[] in, int a, int b, int c, int[] out) {
	// borr A B C => out[c] = in[a] | in[b], and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == (in[a] | in[b]));
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match borr: " + out[c] + " == (" + in[a] + " | " + in[b] + ")");
	return res;
    }

    public static boolean bori(int[] in, int a, int b, int c, int[] out) {
	// bori A B C => out[c] = in[a] | b, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == (in[a] | b));
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match bori: " + out[c] + " == (" + in[a] + " | " + b + ")");
	return res;
    }

    public static boolean setr(int[] in, int a, int b, int c, int[] out) {
	// setr A B C => out[c] = in[a], and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == in[a]);
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match setr: " + out[c] + " == " + in[a]);
	return res;
    }

    public static boolean seti(int[] in, int a, int b, int c, int[] out) {
	// seti A B C => out[c] = a, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && (out[c] == a);
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match seti: " + out[c] + " == " + a);
	return res;
    }
    
    public static boolean gtir(int[] in, int a, int b, int c, int[] out) {
	// gtir A B C => out[c] = 1 if a>in[b] else out[c] = 0, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && ( ((out[c] == 1) && (a > in[b])) || ((out[c] == 0) && (a <= in[b])) );
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match gtir: out["+c+"]=" + out[c] + " and a = " + a + " and in["+b+"] = " + in[b]);
	return res;
    }

    public static boolean gtri(int[] in, int a, int b, int c, int[] out) {
	// gtri A B C => out[c] = 1 if in[a]>b else out[c] = 0, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && ( ((out[c] == 1) && (in[a] > b)) || ((out[c] == 0) && (in[a] <= b)) );
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match gtri: out["+c+"]=" + out[c] + " and in["+a+"] = " + in[a] + " and b = " + b);
	return res;
    }

    public static boolean gtrr(int[] in, int a, int b, int c, int[] out) {
	// gtrr A B C => out[c] = 1 if in[a]>in[b] else out[c] = 0, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && ( ((out[c] == 1) && (in[a] > in[b])) || ((out[c] == 0) && (in[a] <= in[b])) );
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match gtrr: out["+c+"]=" + out[c] + " and in["+a+"] = " + in[a] + " and in["+b+"] = " + in[b]);
	return res;
    }

    public static boolean eqir(int[] in, int a, int b, int c, int[] out) {
	// eqir A B C => out[c] = 1 if a==in[b] else out[c] = 0, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && ( ((out[c] == 1) && (a == in[b])) || ((out[c] == 0) && (a != in[b])) );
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match eqir: out["+c+"]=" + out[c] + " and a = " + a + " and in["+b+"] = " + in[b]);
	return res;
    }

    public static boolean eqri(int[] in, int a, int b, int c, int[] out) {
	// eqri A B C => out[c] = 1 if in[a]==b else out[c] = 0, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && ( ((out[c] == 1) && (in[a] == b)) || ((out[c] == 0) && (in[a] != b)) );
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match eqri: out["+c+"]=" + out[c] + " and in["+a+"] = " + in[a] + " and b = " + b);
	return res;
    }

    public static boolean eqrr(int[] in, int a, int b, int c, int[] out) {
	// eqrr A B C => out[c] = 1 if in[a]==in[b] else out[c] = 0, and the other outs: out[i] = in[i]
	boolean res = true;
	for (int i=0; i<4; i++) {
	    if (i==c) {
		res = res && ( ((out[c] == 1) && (in[a] == in[b])) || ((out[c] == 0) && (in[a] != in[b])) );
	    } else {
		res = res && (out[i] == in[i]);
	    }
	    if (!res) break;
	}
	// if (res) System.out.println("Match eqrr: out["+c+"]=" + out[c] + " and in["+a+"] = " + in[a] + " and in["+b+"] = " + in[b]);
	return res;
    }

    public static int howMany(int[] in, int opcode, int a, int b, int c, int[] out) {
	int count = 0;
	if (Operations.addr(in,a,b,c,out)) {
	    opCounts.get("addr").add(opcode);
	    count++;
	}
	if (Operations.addi(in,a,b,c,out)) {
	    opCounts.get("addi").add(opcode);
	    count++;
	}
	if (Operations.mulr(in,a,b,c,out)) {
	    opCounts.get("mulr").add(opcode);
	    count++;
	}
	if (Operations.muli(in,a,b,c,out)) {
	    opCounts.get("muli").add(opcode);
	    count++;
	}
	if (Operations.banr(in,a,b,c,out)) {
	    opCounts.get("banr").add(opcode);
	    count++;
	}
	if (Operations.bani(in,a,b,c,out)) {
	    opCounts.get("bani").add(opcode);
	    count++;
	}
	if (Operations.borr(in,a,b,c,out)) {
	    opCounts.get("borr").add(opcode);
	    count++;
	}
	if (Operations.bori(in,a,b,c,out)) {
	    opCounts.get("bori").add(opcode);
	    count++;
	}
	if (Operations.setr(in,a,b,c,out)) {
	    opCounts.get("setr").add(opcode);
	    count++;
	}
	if (Operations.seti(in,a,b,c,out)) {
	    opCounts.get("seti").add(opcode);
	    count++;
	}
	if (Operations.gtir(in,a,b,c,out)) {
	    opCounts.get("gtir").add(opcode);
	    count++;
	}
	if (Operations.gtri(in,a,b,c,out)) {
	    opCounts.get("gtri").add(opcode);
	    count++;
	}
	if (Operations.gtrr(in,a,b,c,out)) {
	    opCounts.get("gtrr").add(opcode);
	    count++;
	}
	if (Operations.eqir(in,a,b,c,out)) {
	    opCounts.get("eqir").add(opcode);
	    count++;
	}
	if (Operations.eqri(in,a,b,c,out)) {
	    opCounts.get("eqri").add(opcode);
	    count++;
	}
	if (Operations.eqrr(in,a,b,c,out)) {
	    opCounts.get("eqrr").add(opcode);
	    count++;
	}
	return count;
    }

}
