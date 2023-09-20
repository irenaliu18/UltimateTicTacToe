/*
 * Spot
 * Represents one spot of a box
 * 
 * Created 4-3-2021
 */

package objects;

import java.awt.Rectangle;

/**
 * Represents a location in the game.
 * @author katytsao
 */
public class Spot extends Rectangle {

	private boolean isOccupied;
	private char occupant;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new empty spot
	 */
	public Spot() {
		super();
		isOccupied = false;
		occupant = '0';
	}
	
	/**
	 * Creates a new empty spot taking up space of a Rectangle
	 * @param x x-coordinate of top left corner of box
	 * @param y y-coordinate of top left corner of box
	 * @param w Width of the box
	 * @param h Height of the box
	 */
	public Spot(int x, int y, int w, int h) {
		super(x, y, w, h);
		isOccupied = false;
	}
	
	/**
	 * Getter for whether this Spot is occuppied
	 * @return True if it is occupied, false if it is empty
	 */
	public boolean isOccupied() {
		return isOccupied;
	}
	
//	/**
//	 * Changes the state of this Spot so it is occupied
//	 */
//	public void occupy() {
//		isOccupied = true;
//	}
	
	/**
	 * Changes the state of this Spot so it is occupied
	 * @param x The number of the player that occupied it
	 */
	public void occupy(char c) {
		occupant = c;
		isOccupied = true;
	}
	
	/**
	 * Clears this Spot so it is empty
	 */
	public void clear() {
		occupant = '0';
		isOccupied = false;
	}
	
	/**
	 * Getter for which player occupies this Spot
	 * @return The number of the player who occupies this Spot
	 */
	public char getOccupant() {
		return occupant;
	}

}