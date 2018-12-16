import java.util.*;

public class Operations {

    public static HashMap<String,HashSet<Integer>> opCounts;

    public static void init() {
	opCounts = new HashMap<String,HashSet<Integer>>();
	opCounts.put("addr",new HashSet<Integer>());
	opCounts.put("addi",new HashSet<Integer>());
	opCounts.put("mulr",new HashSet<Integer>());
	opCounts.put("muli",new HashSet<Integer>());
	opCounts.put("banr",new HashSet<Integer>());
	opCounts.put("bani",new HashSet<Integer>());
	opCounts.put("borr",new HashSet<Integer>());
	opCounts.put("bori",new HashSet<Integer>());
	opCounts.put("setr",new HashSet<Integer>());
	opCounts.put("seti",new HashSet<Integer>());
	opCounts.put("gtir",new HashSet<Integer>());
	opCounts.put("gtri",new HashSet<Integer>());
	opCounts.put("gtrr",new HashSet<Integer>());
	opCounts.put("eqir",new HashSet<Integer>());
	opCounts.put("eqri",new HashSet<Integer>());
	opCounts.put("eqrr",new HashSet<Integer>());
    }

    public static String displayOps() {
	String res = "";
	for (String op : opCounts.keySet()) {
	    res += (op + ": " + opCounts.get(op).toString() + "\n");

	}
	return res;
    }

    public static int[] perform(int[] in, int opcode, int a, int b, int c) {
	int[] out = new int[4];
	String op = "";
	switch(opcode) {
	case 0: {
	    out = setI(in,a,b,c);
	    op = "seti";
	    break;
	}
	case 1: {
	    out = eqIR(in,a,b,c);
	    op = "eqir";
	    break;
	}
	case 2: {
	    out = setR(in,a,b,c);
	    op = "setr";
	    break;
	}
	case 3: {
	    out = gtIR(in,a,b,c);
	    op = "gtir";
	    break;
	}
	case 4: {
	    out = addI(in,a,b,c);
	    op = "addi";
	    break;
	}
	case 5: {
	    out = mulI(in,a,b,c);
	    op = "muli";
	    break;
	}
	case 6: {
	    out = mulR(in,a,b,c);
	    op = "mulr";
	    break;
	}
	case 7: {
	    out = gtRR(in,a,b,c);
	    op = "gtrr";
	    break;
	}
	case 8: {
	    out = banI(in,a,b,c);
	    op = "bani";
	    break;
	}
	case 9: {
	    out = gtRI(in,a,b,c);
	    op = "gtri";
	    break;
	}
	case 10: {
	    out = borI(in,a,b,c);
	    op = "bori";
	    break;
	}
	case 11: {
	    out = banR(in,a,b,c);
	    op = "banr";
	    break;
	}
	case 12: {
	    out = borR(in,a,b,c);
	    op = "borr";
	    break;
	}
	case 13: {
	    out = eqRI(in,a,b,c);
	    op = "eqri";
	    break;
	}
	case 14: {
	    out = eqRR(in,a,b,c);
	    op = "eqrr";
	    break;
	}
	case 15: {
	    out = addR(in,a,b,c);
	    op = "addr";
	    break;
	}
	default: break;
	}
	System.out.print("Performing operation " + op + " " + a + " " + b + " " + c + " on [");
	for (int i=0; i<4; i++) System.out.print(in[i]+",");
	System.out.print("] gives [");
	for (int i=0; i<4; i++) System.out.print(out[i]+",");
	System.out.println("]");
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
