public class InverseCaptcha {
    
    public static void main(String args[]) {
	int nDigits = args[0].length();
	String digits = args[0];
	int sum = 0;
	for (int i=1; i<=nDigits; i++) {
	    int offset = nDigits/2; // offset = 1 for part 1 of the puzzle
	    int temp;
	    if ((i+offset)==nDigits) {
		temp = nDigits;
	    } else {
		temp = ((i+offset) % nDigits);
	    }
	    if (digits.charAt(i-1) == digits.charAt(temp-1)) {
		sum += Character.getNumericValue(digits.charAt(i-1));
	    }
	}

	System.out.println(sum);

    }
}


