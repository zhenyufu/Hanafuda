package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class HorizontalOpponentPanel extends JPanel{

	private JButton showCapturedCard;
	private JLabel nameLabel;
	private JLabel numCards;
	private JLabel scoreLabel, userScore;
	private String playerName;
	private int cardLeft = 8;
	private int score = 0;
	private String btnText = "Show\nCaptured\nCards";
	
	
	public HorizontalOpponentPanel(String playerName){
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.playerName = playerName;
		
		nameLabel = new JLabel(playerName + " ");
		nameLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		this.add(nameLabel);
		this.add(Box.createHorizontalGlue());
		showCapturedCard = new JButton("<html>" + btnText.replaceAll("\\n", "<br>") + "</html>");
		showCapturedCard.setMaximumSize(new Dimension(40,100));
		this.add(showCapturedCard);
		JTextPane capturedCardPane = new JTextPane();
		capturedCardPane.setEditable(false);
		capturedCardPane.insertIcon ( new ImageIcon ( "Image1.png" ) ); 
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane jsp = new JScrollPane(capturedCardPane, v , h);
		this.add(jsp);
		this.add(Box.createHorizontalGlue());
		scoreLabel = new JLabel("Score: ");
		scoreLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		
		this.add(scoreLabel);
		userScore = new JLabel(Integer.toString(score) + " ");
		userScore.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		this.add(userScore);
		this.add(Box.createHorizontalGlue());
		numCards = new JLabel("Cards left: " + cardLeft + " ");
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
