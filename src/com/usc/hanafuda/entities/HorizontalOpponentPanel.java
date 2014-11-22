package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

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
	private JLabel scoreLabel, userScore;
	private String playerName;
	private int cardLeft = 8;
	private int score = 0;
	
	
	
	public HorizontalOpponentPanel(String playerName){
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.playerName = playerName;
		
		nameLabel = new JLabel(playerName);
		nameLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 40));
		this.add(nameLabel);
		this.add(Box.createHorizontalGlue());
		showCapturedCard = new JButton("Show Captured Cards");
		showCapturedCard.setPreferredSize(new Dimension(200,100));
		this.add(showCapturedCard);
		this.add(Box.createHorizontalGlue());
		scoreLabel = new JLabel("Score: ");
		scoreLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		
		this.add(scoreLabel);
		userScore = new JLabel(Integer.toString(score));
		this.add(userScore);
		this.add(Box.createHorizontalGlue());
		numCards = new JLabel("Cards left: " + cardLeft);
		numCards.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		this.add(numCards);
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension (1150, 200));
		this.setMinimumSize(new Dimension (1150, 200));
		this.setMaximumSize(new Dimension (1150, 200));
		
	}
	public void incScore(int incBy){
		score = score + incBy;
	}
	public void throwCard(){
		cardLeft--;
	}
}
