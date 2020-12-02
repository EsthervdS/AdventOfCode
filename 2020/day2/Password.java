class Password {

    public int min, max;
    public char c;
    public String pw;
    
    public Password(int m1, int m2, char cc, String s) {
	min = m1;
	max = m2;
	c = cc;
	pw = s;
    }

    public boolean isValid() {
	boolean res = true;
	int count = (int) pw.chars().filter(ch -> ch == c).count();
	return (count>=min) && (count<=max);
    }

    public boolean isValid2() {
	return (pw.charAt(min-1) == c) ^ (pw.charAt(max-1) == c);
    }
    
}
