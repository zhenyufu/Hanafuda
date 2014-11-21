package com.usc.hanafuda.entities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.usc.hanafuda.handlers.MyAssetHandler;

public class HandPanel extends JPanel{

	
	
	public HandPanel(){
		this.setBackground(Color.yellow);
		this.setPreferredSize(new Dimension (1150, 200));
		this.setMinimumSize(new Dimension (1150, 200));
		this.setMaximumSize(new Dimension (1150, 200));
		this.setLayout(new BorderLayout());
		
		
	}
	
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(MyAssetHandler.cardImageArray[0], 40, 40, null);
		g.drawImage(MyAssetHandler.cardImageArray[1], 400, 40, null);
		g.drawImage(MyAssetHandler.cardImageArray[2], 800, 40, null);

	}	
		
}
