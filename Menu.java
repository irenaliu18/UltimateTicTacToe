/*
 * Menu page
 * 
 * Created 4-3-2021
 */

package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import ultimate_tictactoe.Main;

/**
 * View with the options to go to Game, Instructions, Settings
 * @author katytsao
 */
public class Menu extends View implements ActionListener {
	
	private JButton settings, info, game;
	private static final long serialVersionUID = 1L;
	/** 
	 * Displays the main menu of the game 
	 * @param router
	 * 
	 */
	public Menu(Main router) {
		super(router);
		JPanel p = new JPanel(new GridBagLayout());
		setBackground(Color.getHSBColor(330f / 360, 0.15f, 0.95f));
		p.setBackground(Color.getHSBColor(330f / 360, 0.15f, 0.95f));
//		GridBagConstraints c = new GridBagConstraints();
		
//		c.insets = new Insets(10,10,25,25);
//		c.gridx = 50;
//		c.gridy = 100;
		
		settings = new JButton("Settings");
		settings.setBounds(400, 500, 100, 100);
		settings.setPreferredSize(new Dimension(100,60));
		settings.addActionListener(this);
		p.add(settings);
		
//		c.gridx = 50;
//		c.gridy = 120;
		info = new JButton("How to Play");
		info.setPreferredSize(new Dimension(100,60));
		info.addActionListener(this);
		p.add(info);
		
//		c.gridx = 50;
//		c.gridy = 140;
		game = new JButton("Play");
		game.setPreferredSize(new Dimension(100,60));
//		game.
		game.addActionListener(this);		
		p.add(game);
		BufferedImage i;
		try {
			i = ImageIO.read(new File("tictactoe.png"));
			JLabel label = new JLabel(new ImageIcon(i));
			p.add(label);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add(p);
	}
	/**
	 * paintComponent method from JPanel superclass; called at each repaint
	 */
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Font fnt0 = new Font("serif", Font.BOLD, 45);
		g.setFont(fnt0);
		g.setColor(Color.BLACK);
		g.drawString("ULTIMATE TIC-TAC-TOE", 30 , 400);
//		Toolkit t = Toolkit.getDefaultToolkit();
//		Image i = t.getImage("tictactoe.png");
//		g.drawImage(t, 300, 100, this);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		if(b==settings) {
			push("settings");
		}
		else if(b==info) {
			push("info");
		}
		else if(b==game) {
			push("game");
		}
	}

}