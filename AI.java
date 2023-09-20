/*
 * AI
 * Abstract class for an AI
 * 
 * Created 4-3-2021
 */

package ai;

import objects.Board;
import java.awt.Point;

/**
 * Abstract superclass for all future AI classes.
 * @author katytsao
 */
public abstract class AI {
	
	private Board game;

	/**
	 * Creates a new AI
	 * @param board The DrawingPanel that this AI will play in
	 */
	public AI(Board game) {
		this.game = game;
	}
	
	/**
	 * Getter for the DrawingPanel that this AI is in.
	 * @return The DrawingPanel
	 */
	public Board getGame() {
		return game;
	}
	
	/**
	 * Abstract method to be an AI's move decision.
	 * @return The Spot that the AI chooses to play in
	 */
	public abstract Point makeMove();

}