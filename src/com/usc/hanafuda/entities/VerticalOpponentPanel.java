package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VerticalOpponentPanel extends JPanel{

	private JButton showCapturedCard;
	private JLabel nameLabel;
	private JLabel numCards;
	private String playerName;
	
	
	public VerticalOpponentPanel(String playerName){
		this.playerName = playerName;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		nameLabel = new JLabel(playerName);
		
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension (100, 100));
		this.setMinimumSize(new Dimension (100, 100));
		this.setMaximumSize(new Dimension (100, 100));
		
	}
}
