package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class HorizontalOpponentPanel extends JPanel{

	
	
	
	
	public HorizontalOpponentPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension (1150, 100));
		this.setMinimumSize(new Dimension (1150, 100));
		this.setMaximumSize(new Dimension (1150, 100));
		
	}
}
