package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HorizontalOpponentPanel extends JPanel{

	private JButton showCapturedCard;
	private JLabel nameLabel;
	private JLabel numCards;
	private String playerName;
	private int cardLeft = 8;
	
	
	
	public HorizontalOpponentPanel(String playerName){
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.playerName = playerName;
		
		nameLabel = new JLabel(playerName);
		this.add(nameLabel);
		this.add(Box.createHorizontalGlue());
		showCapturedCard = new JButton("Show Captured Cards");
		showCapturedCard.setPreferredSize(new Dimension(200,100));
		this.add(showCapturedCard);
		this.add(Box.createHorizontalGlue());
		numCards = new JLabel("Cards left: " + cardLeft);
		this.add(numCards);
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension (1150, 100));
		this.setMinimumSize(new Dimension (1150, 100));
		this.setMaximumSize(new Dimension (1150, 100));
		
	}
}
