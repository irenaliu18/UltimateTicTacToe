/*
 * RandomPlayer
 * An AI that literally just makes a random move.
 * 
 * Created 4-3-2021
 */

package ai;

import objects.Board;
import java.awt.Point;

/**
 * A Level-1 AI that has zero strategy and literally randomly selects an available location to play in.
 * @author katytsao
 */
public class RandomPlayer extends AI {

	/**
	 * Creates a Level-1 AI
	 * @param board The DrawingPanel that the RandomPlayer plays in
	 */
	public RandomPlayer(Board board) {
		super(board);
	}
	
	/**
	 * Chooses a Spot to play in
	 * @return The Spot that the RandomPlayer chose to play in
	 */
	public Point makeMove() {
		int a, b;
		a = getGame().getNextGrid();
		do {
			b = (int)(9*Math.random() + 1);
			
			if(getGame().getBigSpot(a).isOccupied()) {
				a = (int)(9*Math.random() + 1);
			}
			
		} while(getGame().getSmallSpot(a, b).isOccupied() || getGame().getBigSpot(a).isOccupied());
		return new Point(a, b);
	}

}