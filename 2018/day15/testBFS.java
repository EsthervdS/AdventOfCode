public class testBFS {

    public static void main(String... args) {
	int[][] myMap = {
	    {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 1, 1, 1},
            {0, 0, 0, 1, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
	};
	
	Position[] path = new BFS().findPath(myMap, new Position(8, 0), new Position(8, 2));
	for (Position point : path) {
	    System.out.println(point.x + ", " + point.y);
	}
    }
}

    
