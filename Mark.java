/*
 * Mark
 * Abstract superclass for x and o
 * 
 * Created 4-3-2021
 */

package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Represents one mark (like X or O).
 * @author katytsao
 */
public abstract class Mark {
	
	// FIELDS
	private Rectangle r;
	private int big, small;

	// CONSTRUCTOR
	/**
	 * Create a new Mark object
	 * @param box The rectangle that the mark will be inside
	 * @param big Corresponding big box
	 * @param small Corresponding small box
	 */
	public Mark(Rectangle box, int big, int small) {
		r = box;
		this.big = big;
		this.small = small;
	}
	
	// GETTERS
	public Rectangle rect() { return r; }
	public int big() { return big; }
	public int small() { return small; }
	
	
	// ABSTRACT METHODS
	/**
	 * Draw the Mark
	 * @param g The Graphics object to draw with
	 */
	public abstract void draw(Graphics g);	
	
	// STATIC METHODS
	public static Mark postToMark(Post p) {
		if(p.id.equals("x")) return new X(new Rectangle(p.x, p.y, p.w, p.h), p.big, p.small);
		else if(p.id.equals("o")) return new O(new Rectangle(p.x, p.y, p.w, p.h), p.big, p.small);
		else return null;
	}
	
	public static Post markToPost(Mark m) {
		Post p = new Post();
		Rectangle r = m.rect();
		p.x = r.x;
		p.y = r.y;
		p.w = r.width;
		p.h = r.height;
		p.big = m.big();
		p.small = m.small();
		if (m instanceof X) {
			p.id = "x";
		}
		else if (m instanceof O) {
			p.id = "o";
		}
		return p;
	}

}