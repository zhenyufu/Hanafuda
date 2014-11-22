package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
	private JTextPane capturedCardPane;
	private BufferedImage cardFaceDown;
	private JPanel cardPanel;
	
	public HorizontalOpponentPanel(String playerName){
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.playerName = playerName;
		
		nameLabel = new JLabel(playerName + " ");
		nameLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		this.add(nameLabel);
		this.add(Box.createHorizontalGlue());
		showCapturedCard = new JButton("<html>" + btnText.replaceAll("\\n", "<br>") + "</html>");
		showCapturedCard.setMaximumSize(new Dimension(40,100));
		capturedCardPane = new JTextPane();
		capturedCardPane.setEditable(false);
		capturedCardPane.insertIcon ( new ImageIcon ( "Image1.png" ) ); 
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane jsp = new JScrollPane(capturedCardPane, v , h);
		this.add(showCapturedCard);
		showCapturedCard.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFrame popup = new JFrame("Captured Cards");
				popup.setDefaultCloseOperation(popup.DISPOSE_ON_CLOSE);
				popup.setSize(500,300);
				popup.setLocation(400, 200);
				popup.setVisible(true);
				popup.setResizable(true);
				
				popup.add(jsp);
			}
		});
		cardPanel = new JPanel();
		cardPanel.setBackground(Color.LIGHT_GRAY);
		add(cardPanel);
		showOpponentHand();
		
		
		
		//this.add(jsp);
		this.add(Box.createHorizontalGlue());
		
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.LIGHT_GRAY);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		scoreLabel = new JLabel("Score: " + Integer.toString(score) + " ");
		scoreLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		eastPanel.add(scoreLabel);
		
		numCards = new JLabel("Cards left: " + cardLeft + " ");
		numCards.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		eastPanel.add(numCards);
		
		this.add(eastPanel);
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3));
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension (1150, 200));
		this.setMinimumSize(new Dimension (1150, 200));
		this.setMaximumSize(new Dimension (1150, 200));
		
		addToCapturedCard("deck.png");//function test out
		throwCard();//function test out
	}
	public void incScore(int incBy){
		score = score + incBy;
		userScore.setText(Integer.toString(score) + " ");
	}
	public void throwCard(){
		cardLeft--;
		numCards.setText("Cards left: " + cardLeft + " ");
		cardPanel.removeAll();
		showOpponentHand();
	}
	public void addToCapturedCard(String imagePath){
		capturedCardPane.insertIcon(new ImageIcon(imagePath));
		
	}
	public void showOpponentHand(){
		try {
			cardFaceDown = ImageIO.read(new File("deck.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < cardLeft; i++){
			cardPanel.add(new JLabel(new ImageIcon(cardFaceDown)));
		}
	}
	
}
