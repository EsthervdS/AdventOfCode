import java.io.*;
import java.util.*;
import util.*;
import java.time.*;

public class Phases {

    public ArrayList<String> lines;
    public int[] original;
    public int N,phases,offset;
    public int[] baseSignal;
    public int[] copyOriginal;
    
    public Phases(String fileName) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	N = lines.get(0).length();
	baseSignal = new int[] {0, 1, 0, -1};

        original = new int[N];
	for (int i=0; i<N; i++) {
	    original[i] = Integer.parseInt(lines.get(0).charAt(i)+"");
	}

	phases = 100;
	int[] input = original.clone();
	
	for (int i=1; i<=phases; i++) {
	    int[] temp = getOutput(input);
	    input = temp.clone();
	}
        System.out.print("Part 1: ");
	printFirstEightDigits(input);

	int k = 1;
	offset = 0;
	for (int i=6; i>=0; i--) {
	    offset += original[i] * k;
	    k = k*10;
	}

	int[] copyOriginal = new int[N*10000];
	for (int i=0; i<N*10000; i++) {
	    copyOriginal[i] = original[i % N];
	}
	int[] toProcess = new int[N*10000-offset];
	for (int i=0; i<toProcess.length; i++) {
	    toProcess[i] = copyOriginal[ offset + i ];
	}

	for (int i=0; i<100; i++) {
	    int[] temp = new int[toProcess.length];
	    temp[toProcess.length-1] = toProcess[toProcess.length-1];
	    for (int ind=toProcess.length-2; ind>=0; ind--) {
		temp[ind] = (toProcess[ind] + temp[ind+1]) % 10;
	    }
	    toProcess = temp.clone();
	}
	System.out.print("Part 2: ");
	for (int i=0; i<8; i++) {
	    System.out.print(toProcess[i]+"");
	}
	IO.print("");
    }

    public void printFirstEightDigits(int[] inp) {
	for (int i=0; i<8; i++) {
	    System.out.print(inp[i]+"");
	}
	IO.print("");
    }
    
    public int getNewElement(int[] inp, int e) {
	int[] pattern = getPattern(e);
	int acc = 0;
	for (int i=0; i<N; i++) {
	    acc += inp[i] * pattern[i];
	}
	return Math.abs(acc) % 10;
    }
    
    public int[] getOutput(int[] inp) {
	int[] out = new int[N];
	for (int i=0; i<N; i++) {
	    out[i] = getNewElement(inp,i+1);
	}
	return out;
    }

    
    public int[] getPattern(int e) {
	//{0,1,0,-1}
	//{0,1,1,0,0,-1,-1,0}
	//get pattern based on index of element (+1)
	int[] temp = new int[N+1];
	int i=0; //index of output list
	int k=0; //index in base pattern
	while (i<N+1) {
	    for (int j=0;j<e;j++) {
		temp[i] = baseSignal[k];
		i++;
		if (i>N) break;
	    }
	    k = (k+1) % 4;
	}
	int[] out = new int[N];
	for (int j=0; j<N; j++) {
	    out[j] = temp[j+1];
	}
	return out;
    }

    
    
    public void printList(int[] l) {
	System.out.print("[");
	for(int i : l) {
	    System.out.print(i+",");
	}
	IO.print("]");
    }
    
    public static void main(String[] args) {
	Phases p = new Phases(args[0]);

     }
}
