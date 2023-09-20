package views;

import ultimate_tictactoe.Main;
import javax.swing.JPanel;

/**
 * Abstract superclass for all view pages.
 * @author katytsao
 */
public abstract class View extends JPanel {

	private Main router;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a view page
	 * @param router The Main object to hold this view
	 */
	public View(Main router) {
		super();
		this.router = router;
		setBackground(java.awt.Color.WHITE);
		setSize(600, 600);
	}
	
	/**
	 * Pushes this view to the Main screen
	 * @param s
	 */
	public void push(String s) {
		router.push(s);
	}
	/**
	 * 
	 * @return The Main holding this view
	 */
	public Main getRouter() { return router; }

}