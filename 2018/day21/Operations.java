import java.util.*;

public class Operations {

    public String[] opStrings;
    public int nregs;

    public Operations(int n) {	
	nregs = n;
	//opStrings = {"addr","addi","mulr","muli","banr","bani","borr","bori","setr","seti","gtir","gtri","gtrr","eqir","eqri","eqrr"};
    }

    
    public long[] perform(long[] in, String op, long a, long b, long c) {
	long[] out = new long[nregs];

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
    
    public long[] addR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a]+in[(int) b];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public long[] addI(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a]+b;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] mulR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a]*in[(int) b];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public long[] mulI(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a]*b;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] banR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a]&in[(int) b];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public long[] banI(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a]&b;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] borR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a]|in[(int) b];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public long[] borI(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a]|b;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] setR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = in[(int) a];
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }
    
    public long[] setI(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[i] = a;
	    } else {
		out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] gtIR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[(int) c] = (a > in[(int) b]) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] gtRI(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[(int) c] = (in[(int) a] > b) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] gtRR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[(int) c] = (in[(int) a] > in[(int) b]) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] eqIR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[(int) c] = (a == in[(int) b]) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] eqRI(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[(int) c] = (in[(int) a] == b) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }

    public long[] eqRR(long[] in, long a, long b, long c) {
	long[] out = new long[nregs];
	for (int i=0; i<nregs; i++) {
	    if (i==c) {
		out[(int) c] = (in[(int) a] == in[(int) b]) ? 1 : 0;
	    } else {
	        out[i] = in[i];
	    }
	}
	return out;
    }



}
