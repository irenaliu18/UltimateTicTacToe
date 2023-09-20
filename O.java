/*
 * O
 * Represents an O mark
 * 
 * Created 4-3-2021
 */

package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class O extends Mark {

	public O(Rectangle box, int big, int small) {
		super(box, big, small);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		int rad = (int)(0.7*Math.min(rect().width, rect().height));
		g.drawOval(rect().x + (rect().width-rad)/2, rect().y + (rect().height-rad)/2, rad, rad);
	}

}