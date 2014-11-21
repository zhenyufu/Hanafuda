package com.usc.hanafuda;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuScreen extends JPanel {
	
	
	private JButton singlePlayButton;
	private JButton multiPlayButton;
	private JButton howToButton;
	
	public MenuScreen(MyGame myGame) {
		
		this.setLayout(new GridBagLayout());
		JPanel center = new JPanel(new GridLayout(2,1));
		
		center.setOpaque(false);
		add(center);
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("ha.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel imageLabel = new JLabel(new ImageIcon(image));
		
		center.add(imageLabel);
		
		JPanel south = new JPanel();
		center.add(south);
		
		south.setOpaque(false);
		singlePlayButton = new JButton("Single Player"); 
		singlePlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aa) {

				myGame.setPanel(new GameScreen(myGame));
				
			}
		});
		multiPlayButton  = new JButton("Multi Player");
		howToButton = new JButton("How To Play");
		howToButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				new HowToScreen();
			}
		});
		singlePlayButton.setPreferredSize(new Dimension(150, 100));
		multiPlayButton.setPreferredSize(new Dimension(150, 100));
		howToButton.setPreferredSize(new Dimension(150, 100));
		
		south.add(singlePlayButton);
		south.add(multiPlayButton);
		south.add(howToButton);

		

		
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//g.setColor(new Color(246, 244, 186));
		
		//g.fillRect(0, 0, 1400, 1000);

	}
	

}
