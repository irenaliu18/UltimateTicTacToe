/*
 * Settings page
 * 
 * Created 4-3-2021
 */

package views;

import javax.swing.JButton;

import ai.RandomPlayer;
import ultimate_tictactoe.Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;


/**
 * View that allows users to change colors and icons and AI's.
 * @author katytsao
 */
public class Settings extends View implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JButton back, turnOnAi, turnOffAi, turnOnTimer, turnOffTimer;
	/**
	 * Displays the functions of the Settings page
	 */
	public Settings(Main router) {
		super(router);
		setLayout(null);
		setBackground(Color.getHSBColor(290f / 360, 0.15f, 0.98f));
		
		back = new JButton("Main Menu"); // return to main menu
		back.addActionListener(this);
		back.setBounds(240, 10, 100, 40);
		add(back);
		
		turnOnAi = new JButton("AI Mode");
//		turnOnAi.setPreferredSize(new Dimension(120, 30));	
	    turnOnAi.addActionListener(this);
	    turnOnAi.setBackground(Color.red);
	    turnOnAi.setOpaque(true);
	    turnOnAi.setBounds(200, 100, 180, 80);
	    add(turnOnAi);
	    
	    turnOffAi = new JButton("AI Mode");
//		turnOffAi.setPreferredSize(new Dimension(120, 30));
		turnOffAi.setBackground(Color.green);
		turnOffAi.setOpaque(true);
	    turnOffAi.setBounds(200, 100, 180, 80);
	    turnOffAi.addActionListener(this);
	    add(turnOffAi);
	    turnOffAi.setVisible(false);
	    
	    turnOnTimer = new JButton("Speed Mode");
	    turnOnTimer.setBackground(Color.red);
	    turnOnTimer.setOpaque(true);
	    turnOnTimer.setBounds(200, 300, 180, 80);
	    turnOnTimer.addActionListener(this);
	    add(turnOnTimer);
	    
	    turnOffTimer = new JButton("Speed Mode");
	    turnOffTimer.setBackground(Color.green);
	    turnOffTimer.setOpaque(true);
	    turnOffTimer.setBounds(200, 300, 180, 80);
	    turnOffTimer.addActionListener(this);
	    add(turnOffTimer);
	    turnOffTimer.setVisible(false);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Font fnt0 = new Font("arial", Font.BOLD, 18);
		g.setFont(fnt0);
		g.setColor(Color.BLACK);
		g.drawString("Green = on, red = off", 200 , 250);
//		g.drawString("Change icon color of \"O\"", 350 , 200);
//		g.drawString("AI Level", 150 , 400);
		
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		if (b == back) {
			push("menu");
		}
		else if(b==turnOnAi) turnOnAi();
		else if(b==turnOffAi) turnOffAi();
		else if(b==turnOnTimer) turnOnTimer();
		else if(b==turnOffTimer) turnOffTimer();
	}
	
	private void turnOnAi() {
		getRouter().getGame().getBoard().turnOnAi(new RandomPlayer(getRouter().getGame().getBoard()));
		turnOnAi.setVisible(false);
		turnOffAi.setVisible(true);
		repaint();
	}
	
	private void turnOffAi() {
		getRouter().getGame().getBoard().turnOffAi();
		turnOffAi.setVisible(false);
		turnOnAi.setVisible(true);
		repaint();
	}
	
	private void turnOnTimer() {
		getRouter().getGame().pushPost("t");
		turnOnTimer.setVisible(false);
		turnOffTimer.setVisible(true);
		repaint();
	}
	
	private void turnOffTimer() {
		getRouter().getGame().pushPost("t");
		turnOffTimer.setVisible(false);
		turnOnTimer.setVisible(true);
		repaint();
	}

}