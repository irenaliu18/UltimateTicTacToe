/*
 * Instructions page
 * 
 * Created 4-3-2021
 */

package views;

import ultimate_tictactoe.Main;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/**
 * View with the game instructions
 * @author katytsao
 */
public class Instructions extends View implements ActionListener {

	private JButton back;
	private static final long serialVersionUID = 1L;
	/**
	 * Displays the text on the instructions page
	 * @param router 
	 */
	public Instructions(Main router) {
		super(router);
		setLayout(null);
		setBackground(Color.getHSBColor(55f / 360, 0.1f, 0.98f));

		back = new JButton("Main Menu"); // return to main menu
		back.addActionListener(this);
		back.setBounds(240, 10, 100, 40);
		add(back);
		
		JLabel L1 = new JLabel("Game Modes:");
		JLabel L2 = new JLabel("The default is multiplayer-mode meaning you have to find someone to play with.");
		JLabel L3 = new JLabel("To play by yourself, simply go to Settings and enable AI Mode");
		JLabel L4 = new JLabel("If Speed Mode is enabled in Settings, each player will get a 120s countdown timer (total");
		JLabel L5 = new JLabel("time to make all moves) If the timer runs out, your opponent automatically wins so watch out!");
		
		JLabel L6 = new JLabel("Game rules: ");
		JLabel L7 = new JLabel("The goal is to win the large game by getting 3 mini-game wins in a row as well");
		JLabel L8 = new JLabel("In order to win a mini-game you have to get 3 in a row as well. \"X\" goes first");
		JLabel L9 = new JLabel("NOTE: After your opponent selects a box of a small game, ");
		JLabel L10 = new JLabel("you have to make your next move in the same small box. ");
		JLabel L11 = new JLabel("The box you should make your next move in is marked with a light green color. ");
		JLabel L12 = new JLabel("If no boxes lit up and it's your turn, you can move to any open space.");
		JLabel L13 = new JLabel("During the game you can undo moves, reset the game, and go back to the main menu.");
		
//		L1.setAlignmentX(LEFT_ALIGNMENT);
//		L2.setAlignmentX(CENTER_ALIGNMENT);
//		L3.setAlignmentX(CENTER_ALIGNMENT); 
//		L4.setAlignmentX(CENTER_ALIGNMENT);
//		L5.setAlignmentX(CENTER_ALIGNMENT);
		
		L1.setBounds(20,-190, 600,600); 
		add(L1);
		
		L2.setBounds(20, -210, 700, 700);
		add(L2);
		
		L3.setBounds(20, -190, 700, 700);
		add(L3);
		
		L4.setBounds(20, -170, 700, 700);
		add(L4);

		L5.setBounds(20,-150, 700,700); 
		add(L5);

		L6.setBounds(20,-100, 700,700); 
		add(L6);

		L7.setBounds(20,-80, 700,700); 
		add(L7);

		L8.setBounds(20,-60, 700,700); 
		add(L8);
		
		L9.setBounds(20,-40, 700,700); 
		add(L9);
		
		L10.setBounds(20,-20, 700,700); 
		add(L10);
		
		L11.setBounds(20,0, 700,700); 
		add(L11);
		
		L12.setBounds(20,20, 700,700); 
		add(L12);
		
		L13.setBounds(20, 40, 700,700); 
		add(L13);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		if (b == back) {
			push("menu");
		}
	}

}