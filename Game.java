/*
 * Game page
 * Shows the game and lets the user click on stuff
 * Contains the FireBase stuff
 * 
 * Created 4-3-2021
 */

package views;

import ultimate_tictactoe.Main;
import objects.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.google.firebase.database.*;


/**
 * View that holds the main game
 * @author katytsao
 */
public class Game extends View implements MouseListener, ActionListener {
	
	private int xTime, oTime;
	private Timer t;
	private boolean timerOn = false;
	private DatabaseReference ref;
	private Board board;
	private JLabel xLabel, oLabel;
	private JButton back, undo, reset;
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new Game component and draws all the grids
	 */
	public Game(Main router, DatabaseReference ref) {	
		super(router);
		addMouseListener(this);
		setSize(600, 600);
		
		back = new JButton("BACK");
		back.setPreferredSize(new Dimension(100, 30));
	    back.addActionListener(this);
	    add(back);
		undo = new JButton("UNDO");
		undo.setPreferredSize(new Dimension(100, 30));
	    undo.addActionListener(this);
	    add(undo);
	    reset = new JButton("RESET");
		reset.setPreferredSize(new Dimension(100, 30));
	    reset.addActionListener(this);
	    add(reset);

	    board = new Board(this);
			
		t = new Timer(100, this);		
		xTime = 1200;
		oTime = 1200;
		xLabel = new JLabel("x time left: " + xTime/10.0 + "s");
		xLabel.setSize(100, 20);
		xLabel.setLocation(50, 10);
		add(xLabel);
		xLabel.setVisible(false);
		oLabel = new JLabel("o time left: " + oTime/10.0 + "s");
		oLabel.setSize(100, 20);
		oLabel.setLocation(50, 30);
		add(oLabel);
		oLabel.setVisible(false);
		
		this.ref = ref.child("posts");
		this.ref.addChildEventListener(new DatabaseChangeListener());
	}
	
	/**
	 * 
	 * @return This Game's Board
	 */
	public Board getBoard() { return board; }
	
	// used methods from superclass/interfaces
	/**
	 * paintComponent method from JPanel superclass; called at each repaint
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		board.draw(g);
	}
	/**
	 * Called when any ActionEvent is fired (including buttons and Timers)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			if(b==back) push("menu");
			else if(b==undo) pushPost("u");
			else if(b==reset) pushPost("r");
		}
		if (timerOn && t.isRunning()) {			
			switch(board.getTurn()) {
			case 'x':
				xTime -= 1;
				xLabel.setText("x time left: " + xTime/10.0 + "s");
				if(xTime<=0) {
					t.stop();
					board.gameWon('o');
					break;
				}
				break;
				
			case 'o':
				oTime -= 1;
				oLabel.setText("o time left: " + oTime/10.0 + "s");
				if(oTime<=0) {
					t.stop();
					board.gameWon('x');
					break;
				}
				break;
			}
		}
		repaint();
	}
	/**
	 * Called whenever the mouse is clicked
	 */
	public void mouseClicked(MouseEvent e) {
		board.createMark(e.getX(), e.getY());
		repaint();
	}
	
	// in-game methods
	/**
	 * Pushes a new Mark to the Firebase database.
	 * @param m The Mark to be pushed
	 */
	public void pushMark(Mark m) {
		ref.push().setValueAsync(Mark.markToPost(m));
	}
	/**
	 * Pushes a Post to the Firebase database with a given id
	 */
	public void pushPost(String id) {
		ref.push().setValueAsync(new Post(id));
	}
	
	/**
	 * Resets the timers.
	 */
	public void restartTime() {
		xTime = 1200;
		oTime = 1200;
	}
	
	private void toggleTimer() {
		if(!timerOn) {
			timerOn = true;
			xLabel.setVisible(true);
			oLabel.setVisible(true);
			t.start();
		}
		else {
			t.stop();
			timerOn = false;
			xLabel.setVisible(false);
			oLabel.setVisible(false);
		}
	}
	
	// methods required by MouseListener interface (action is based on area that is clicked)
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	
	/**
	 * 
	 * Handles all changes to the database reference. Because Firebase uses a separate thread than most other processes we're using (both Swing and Processing),
	 * we need to have a strategy for ensuring that code is executed somewhere besides these methods.
	 * 
	 * @author john_shelby
	 *
	 */
	class DatabaseChangeListener implements ChildEventListener {
		
		/**
		 * Runs each time something is added to the Firebase database
		 */
		public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
			SwingUtilities.invokeLater(new Runnable() {  // This threading strategy will work with Swing programs. Just put whatever code you want inside of one of these "runnable" wrappers.
				public void run() {
					Post p = dataSnapshot.getValue(Post.class);
					if(p.id.equals("t")) toggleTimer();
					else if(p.id.equals("r")) board.reset();
					else if (p.id.equals("u")) board.undo();
					else if(p.id.equals("x") || p.id.equals("o")) {
						board.addMark(Mark.postToMark(p));
					}
					repaint();
				}
			});
		}

		// methods req by ChildEventListener
		public void onCancelled(DatabaseError arg0) {}
		public void onChildChanged(DataSnapshot arg0, String arg1) {}
		public void onChildMoved(DataSnapshot arg0, String arg1) {}
		public void onChildRemoved(DataSnapshot arg0) {}
	}
	
	
}