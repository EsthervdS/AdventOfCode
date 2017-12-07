public class SpiralGrid {

    public static void main(String[] args) {
	int square = Integer.parseInt(args[0]);

	int i = (int) Math.ceil(Math.sqrt(square));
	if (i % 2 ==0) i++;
	i = (i + 1) / 2;
	
	int xi = 2*i - 1;
	int this_corner = 0, next_corner;
	next_corner = xi*xi;
	for (int k=1; k<=4; k++) {
	    this_corner = next_corner;
	    next_corner = xi*xi - k*(xi-1);
	    if ( (square <= this_corner) && (square >= next_corner) ) {
		    break;
	    }
	}
	int m = (this_corner + next_corner) / 2;
	System.out.println(i-1 + Math.abs(m-square));
    }
}
