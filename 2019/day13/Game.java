import java.io.*;
import java.util.*;
import util.*;
import java.time.*;
import java.awt.event.*;
    
public class Game {

    public ArrayList<String> lines;
    public ArrayList<Long> program;
    public int[][] tiles;
    int xMin,xMax,yMin,yMax;
    Intcode game;
    String score;
    boolean scoreWasNonZero;
    int paddleX,ballX;
    boolean showCanvas;

    
    public Game(String fileName, int show) {
	lines = new ArrayList<String>();
	lines = IO.readFile(fileName);
	program = new ArrayList<Long>();
	Scanner sc = new Scanner(System.in);
	showCanvas = (show == 1);
	
	String[] ss = lines.get(0).split(",");
	for (String s : ss) {
	    program.add(Long.parseLong(s,10));
	}
	tiles = new int[50][50];
	int x,y,t;
	score = "";
        scoreWasNonZero = false;

	xMin = Integer.MAX_VALUE;
	yMin = Integer.MAX_VALUE;
	xMax = -1*Integer.MAX_VALUE;
	yMax = -1*Integer.MAX_VALUE;
        game = new Intcode(program,1);
	while (game.state != Intcode.HALTED) {
	    game.runUntilOutput();
	    x = (int) game.output;
	    game.runUntilOutput();
	    y = (int) game.output;
	    game.runUntilOutput();
	    t = (int) game.output;
	    tiles[x][y] = t;
	    if (x > xMax) xMax = x;
	    if (x < xMin) xMin = x;
	    if (y > yMax) yMax = y;
	    if (y < yMin) yMin = y;
	}
	if (showCanvas) initCanvas();
	IO.print("Part 1: " + nBlocks());
	long two = 2;
        program.set(0,two);
	game = new Intcode(program,0);

	if (showCanvas) showTiles(10);

	while (game.state != Intcode.HALTED) {
	    game.runUntil();
	    if (game.state == Intcode.INPUTWAIT) {
		if (showCanvas) {
		    try
			{
			    Thread.sleep(30);
			}
		    catch(InterruptedException ex)
			{
			    Thread.currentThread().interrupt();
			}
		
		    if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
			game.input = 1;
		    } else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
			game.input = -1;
		    } else {
			game.input = 0;
		    }
		} else {
		    if (paddleX < ballX) {
			game.input = 1;
		    } else if (paddleX > ballX) {
			game.input = -1;
		    } else {
			game.input = 0;
		    }
		}
		game.setInput();
	    } else if (game.state == Intcode.OUTPUTWAIT) {
		int first = (int) game.output;
		game.runUntilOutput();
		int second = (int) game.output;
		game.runUntilOutput();
		int third = (int) game.output;
		if (first == -1 && second == 0) {
		    if (!scoreWasNonZero && (third > 0)) {
			scoreWasNonZero = true;
		    }
		    score = third + "";
		    if (scoreWasNonZero && (third == 0)) {
			IO.print("Game Over! Resetting the game!");
			IO.print("Last score was " + score);
			tiles = new int[50][50];
			scoreWasNonZero = false;
			game = new Intcode(program,0);
		    }
		} else {
		    tiles[first][second] = third;
		    if (third == 3) {
			paddleX = first;
		    } else if (third == 4) {
			ballX = first;
		    }
		}
		game.state = Intcode.RUNNING;
	    }	    
	    if (showCanvas) showTiles(10);
	}
	IO.print("Part 2: " + score);
    }

    public void showTiles(int delay) {
	StdDraw.clear();
	for (int j=0; j<45; j++) {
	    for (int i=0; i<45; i++) {
		showTile(40-i,40-j,tiles[i][j]);
	    }
	}
	StdDraw.text(19,4,score);
	StdDraw.text(19,-5,nBlocks()+"");
	StdDraw.show();
	StdDraw.pause(delay);
    }

    public void showTile(int x, int y, int t)
    {
	switch(t) {
	case 0: //empty
	    break;
	case 1: //wall
	    StdDraw.filledSquare(x,y,0.5);
	    break;
	case 2: //brick
	    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);	    
	    StdDraw.filledSquare(x,y,0.5);
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.square(x,y,0.5);
	    break;
	case 3: //paddle
	    StdDraw.filledRectangle(x,y,1,0.5);
	    break;
	case 4: //ball
	    StdDraw.setPenColor(StdDraw.BOOK_RED);
	    StdDraw.filledCircle(x,y,0.5);
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.circle(x,y,0.5);
	    break;
	default : break;
	}
	StdDraw.setPenColor(StdDraw.BLACK);	
    }
    
    public int nBlocks() {
	int n = 0;
	for (int j=0; j<45; j++) {
	    for (int i=0; i<45; i++) {
		if (tiles[i][j] == 2) n++;
	    }
	}
	return n;
    }

    public void initCanvas() {
	StdDraw.enableDoubleBuffering();
	StdDraw.setPenRadius(0.001);
	StdDraw.setXscale(-10,50);
	StdDraw.setYscale(-10,50);;
	StdDraw.setPenColor(StdDraw.BLACK);
    }
        
    public static void main(String[] args) {
	Game g = new Game(args[0],1);
     }
}
