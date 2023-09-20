/*
 * X
 * Represents an X mark
 * 
 * Created 4-3-2021
 */

package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class X extends Mark {

	public X(Rectangle box, int big, int small) {
		super(box, big, small);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		int rad = (int)(0.7*Math.min(rect().width, rect().height));
		g.drawLine(rect().x + (rect().width-rad)/2, rect().y + (rect().height-rad)/2, 
				rect().x + (rect().width+rad)/2, rect().y + (rect().height+rad)/2);
		g.drawLine(rect().x + (rect().width-rad)/2, rect().y + (rect().height+rad)/2, 
				rect().x + (rect().width+rad)/2, rect().y + (rect().height-rad)/2);
	}

}