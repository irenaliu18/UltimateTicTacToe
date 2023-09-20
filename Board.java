/*
 * Holds all the data for the game
 */

package objects;

import views.Game;
import ai.AI;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JOptionPane;

/**
 * Holds all the gameboard data
 * @author katytsao
 */
public class Board {

	private Game game;
	private int numMarks;
	private char turn;
	private ArrayList<Mark> smallMarks, bigMarks;
	private Spot[] bigSpots;
	private Spot[][] smallSpots;
	private int nextGrid, clickedGrid, clickedSmallGrid;
	private Spot clickedSpot, lastSpot;
	private boolean gameOver, aiOn;
	private AI ai;

	private double margin = 0.1; // how much of the space will each small grid take? this means 0.1 margin on each side
	private double m2 = 1-margin; // the other end of the big margin
	private double f = 1 - 2*margin;

	/**
	 * Creates a new Board
	 * @param game The Game view that this Board is inside
	 */
	public Board(Game game) {
		this.game = game;
		bigSpots = new Spot[10];
		smallSpots = new Spot[10][10];
		setupRectangles();
		smallMarks = new ArrayList<Mark>();
		bigMarks = new ArrayList<Mark>();
		gameOver = false;
		aiOn = false;
		turn = 'x'; // x goes first
	}
	
	// public methods called by Game
	
	/**
	 * Draws the game
	 * @param g The Graphics object to do the drawing
	 */
	public void draw(Graphics g) {
		if(numMarks>0) {
			colorNextGrid(g);
			colorLastSpot(g);
		}
		for(int i=0; i<=9; i++) {
			drawGrid(g, bigSpots[i]);
		}
		if(numMarks>0) drawMarks(g);

		if(!gameOver && aiOn && turn=='o') {
			java.awt.Point p = ai.makeMove();
			aiMoved(p.x, p.y);
		}
	}
	/**
	 * Tries to create a new Mark at a clicked point
	 * @param x The x-coordinate of the clicked point
	 * @param y The y-coordinate of the clicked point
	 */
	public void createMark(int x, int y) {
		clickedSpot = findSpot(x, y);
		if(allowedToMark()) {
			if(turn=='x') {
				game.pushMark(new X(clickedSpot, clickedGrid, clickedSmallGrid));
			}
			else if(turn=='o'){
				game.pushMark(new O(clickedSpot, clickedGrid, clickedSmallGrid));
			}
		}
	}
	
	/**
	 * Adds a Mark to the game
	 * @param m The Mark to be added
	 */
	public void addMark(Mark m) {
		numMarks++;
		smallMarks.add(m);
		lastSpot = getSmallSpot(m.big(), m.small());
		if(m instanceof X) {
			lastSpot.occupy('x');
			turn = 'o';
		}
		else if(m instanceof O) {
			lastSpot.occupy('o');
			turn = 'x';
		}
		clickedGrid = m.big();
		nextGrid = m.small();
		checkFor3();
	}
	/**
	 * Ends the game when someone wins
	 * @param winner The character of the player who won
	 */
	public void gameWon(char winner) {
		if(gameOver) return;
		
		gameOver = true;
		if(winner=='x') {
			JOptionPane.showMessageDialog(null,	"X wins!", "Game over!", JOptionPane.INFORMATION_MESSAGE);			
		}
		else if(winner=='o') {
			JOptionPane.showMessageDialog(null,	"O wins!", "Game over!", JOptionPane.INFORMATION_MESSAGE);				
		}
	}
	/**
	 * Clears the entire board
	 */
	public void reset() {
		numMarks = 0;
		smallMarks.clear();
		bigMarks.clear();
		turn = 'x';
		setupRectangles();
		gameOver = false;
		game.restartTime();
	}
	/**
	 * Undoes the most recent move
	 */
	public void undo() {
		if(numMarks<=1) reset();
		else {
			numMarks--;
			lastSpot.clear();
			smallMarks.remove(numMarks);

			if(bigSpots[clickedGrid].isOccupied()) {
				bigSpots[clickedGrid].clear();
				bigMarks.remove(bigMarks.size()-1);
			}

			clickedGrid = smallMarks.get(numMarks-1).big();
			clickedSmallGrid = smallMarks.get(numMarks-1).small();
			lastSpot = smallSpots[clickedGrid][clickedSmallGrid];
			nextGrid = clickedSmallGrid;
			toggleTurn();
		}
	}

	// setters/getters
	public void turnOnAi(AI ai) { aiOn = true; this.ai = ai; }
	public void turnOffAi() { aiOn = false; }
	public int getNextGrid() { return nextGrid; }
	public Spot getSmallSpot(int a, int b) { return smallSpots[a][b]; }
	public Spot getBigSpot(int a) { return bigSpots[a]; }
	public char getTurn() { return turn; }
	public boolean isOver() { return gameOver; }

	// checking helpers
	private boolean allowedToMark() {
		if(clickedSpot == null) return false;
		if(numMarks == 0) return true;
		if(clickedSpot.isOccupied()) return false;
		if(bigSpots[clickedGrid].isOccupied()) return false;
		if(!bigSpots[nextGrid].isOccupied() && nextGrid!=clickedGrid) return false;
		return true;
	}
	private void checkFor3() {
		check3(1, 2, 3);
		check3(4, 5, 6);
		check3(7, 8, 9);
		check3(1, 4, 7);
		check3(2, 5, 8);
		check3(3, 6, 9);
		check3(1, 5, 9);
		check3(3, 5, 7);
	}
	private void check3(int a, int b, int c) {
		char x = smallSpots[clickedGrid][a].getOccupant();
		if (x != 0 
				&& x == smallSpots[clickedGrid][b].getOccupant() 
				&& x == smallSpots[clickedGrid][c].getOccupant()) {
			if(x=='x') {
				bigMarks.add(new X(bigSpots[clickedGrid], clickedGrid, 0));
			}
			else if(x=='o') {
				bigMarks.add(new O(bigSpots[clickedGrid], clickedGrid, 0));
			}
			bigSpots[clickedGrid].occupy(x);
			checkForBig3();
		}
	}
	private void checkForBig3() {
		checkBig3(1, 2, 3);
		checkBig3(4, 5, 6);
		checkBig3(7, 8, 9);
		checkBig3(1, 4, 7);
		checkBig3(2, 5, 8);
		checkBig3(3, 6, 9);
		checkBig3(1, 5, 9);
		checkBig3(3, 5, 7);
	}
	private void checkBig3(int a, int b, int c) {
		char x = bigSpots[a].getOccupant();
		if (x != 0 
				&& x == bigSpots[b].getOccupant() 
				&& x == bigSpots[c].getOccupant()) {
			bigSpots[clickedGrid].occupy(x);
			gameWon(x);
		}
	}

	// drawing helpers
	private void drawGrid(Graphics g, Rectangle r) {
		g.setColor(Color.BLACK);
		g.drawLine((int)(r.x + r.width*f/3 + r.width*margin), (int)(r.y + r.height*margin), 
				(int)(r.x + r.width*f/3 + r.width*margin), (int)(r.y + r.height*m2));
		g.drawLine((int)(r.x + 2*r.width*f/3 + r.width*margin), (int)(r.y + r.height*margin), 
				(int)(r.x + 2*r.width*f/3 + r.width*margin), (int)(r.y + r.height*m2));
		g.drawLine((int)(r.x + r.width*margin), (int)(r.y + r.height*f/3 + r.height*margin), 
				(int)(r.x + r.width*m2), (int)(r.y + r.height*f/3 + r.height*margin));
		g.drawLine((int)(r.x + r.width*margin), (int)(r.y + 2*r.height*f/3 + r.height*margin), 
				(int)(r.x + r.width*m2), (int)(r.y + 2*r.height*f/3 + r.height*margin));
	}
	private void drawMarks(Graphics g) {
		for(Mark m : smallMarks) m.draw(g);
		for(Mark b : bigMarks) b.draw(g);
	}
	private void colorNextGrid(Graphics g) {
		if(!bigSpots[nextGrid].isOccupied() && !gameOver) {
			g.setColor(new Color(186, 255, 191));
			g.fillRect(bigSpots[nextGrid].x, bigSpots[nextGrid].y, bigSpots[nextGrid].width, bigSpots[nextGrid].height);
		}
	}
	private void colorLastSpot(Graphics g) {
		g.setColor(new Color(216, 181, 255));
		g.fillRect(lastSpot.x+1, lastSpot.y+1, lastSpot.width, lastSpot.height);
	}

	// other helpers
	private void setupRectangles() {
		int w = 600;
		int h = 600;
		w += (int)(w*margin + 0.5);
		h += (int)(h*margin + 0.5);
		int rx = (int)(w*margin/2 + 0.5);
		int ry = (int)(h*margin/2 + 0.5);

		bigSpots[0] = new Spot(0-rx, 0-ry, w, h);
		w = (int)(w*f + 0.5);
		h = (int)(h*f + 0.5);
		bigSpots[1] = new Spot(rx + 0, 		ry + 0, 		w/3, h/3);
		bigSpots[2] = new Spot(rx + w/3,  	ry + 0, 		w/3, h/3);
		bigSpots[3] = new Spot(rx + 2*w/3, 	ry + 0, 		w/3, h/3);
		bigSpots[4] = new Spot(rx + 0, 		ry + h/3, 		w/3, h/3);
		bigSpots[5] = new Spot(rx + w/3, 	ry + h/3, 		w/3, h/3);
		bigSpots[6] = new Spot(rx + 2*w/3, 	ry + h/3, 		w/3, h/3);
		bigSpots[7] = new Spot(rx + 0, 		ry + 2*h/3, 	w/3, h/3);
		bigSpots[8] = new Spot(rx + w/3,	ry + 2*h/3, 	w/3, h/3);
		bigSpots[9] = new Spot(rx + 2*w/3, 	ry + 2*h/3, 	w/3, h/3);

		for(int i=0; i<=9; i++) {
			rx = bigSpots[i].x + (int)(bigSpots[i].width*margin);
			ry = bigSpots[i].y + (int)(bigSpots[i].height*margin);
			w = (int)(bigSpots[i].width*f);
			h = (int)(bigSpots[i].height*f);
			smallSpots[i][1] = new Spot(rx + 0+ 1, 		ry + 0 + 1, 		w/3-1, h/3-1);
			smallSpots[i][2] = new Spot(rx + w/3 + 1,  	ry + 0 + 1, 		w/3-1, h/3-1);
			smallSpots[i][3] = new Spot(rx + 2*w/3 + 1,  ry + 0 + 1, 		w/3-1, h/3-1);
			smallSpots[i][4] = new Spot(rx + 0 + 1,  	ry + h/3 + 1, 		w/3-1, h/3-1);
			smallSpots[i][5] = new Spot(rx + w/3 + 1, 	ry + h/3 + 1, 		w/3-1, h/3-1);
			smallSpots[i][6] = new Spot(rx + 2*w/3 + 1,	ry + h/3 + 1, 		w/3-1, h/3-1);
			smallSpots[i][7] = new Spot(rx + 0 + 1,		ry + 2*h/3 + 1, 	w/3-1, h/3-1);
			smallSpots[i][8] = new Spot(rx + w/3 + 1, 	ry + 2*h/3 + 1, 	w/3-1, h/3-1);
			smallSpots[i][9] = new Spot(rx + 2*w/3 + 1,	ry + 2*h/3 + 1, 	w/3-1, h/3-1);
		}
	}
	private Spot findSpot(int x, int y) {
		for(int i=1; i<=9; i++) {
			for(int j=1; j<=9; j++) {
				if(smallSpots[i][j].contains(x, y)) {
					clickedGrid = i;
					clickedSmallGrid = j;
					return smallSpots[i][j];
				}
			}
		}
		return null;
	}
	private void aiMoved(int a, int b) {
		clickedSpot = getSmallSpot(a, b);
		clickedGrid = a;
		clickedSmallGrid = b;
		clickedSpot.occupy('o');
		game.pushMark(new O(clickedSpot, clickedGrid, clickedSmallGrid));
	}
	private void toggleTurn() {
		if(turn=='x') turn = 'o';
		else if(turn=='o') turn = 'x';
	}

}
