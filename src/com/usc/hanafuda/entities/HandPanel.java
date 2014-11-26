package com.usc.hanafuda.entities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import com.usc.hanafuda.HClient;
import com.usc.hanafuda.MyGame;
import com.usc.hanafuda.handlers.MyAssetHandler;
import com.usc.hanafuda.screens.GameScreen;

public class HandPanel extends JPanel{
	static boolean aCardIsUp = false;
	ArrayList<CardButton> cardButtonList;
	public final int gap = 100;
	private int score = 0;
	private JLabel playerScore;
	private JTextPane capturedCardPane;
	private String myName;
	private String opponentName;
	HClient hClient;
	public HandPanel(HClient hClient){
		this.setBackground(Color.yellow);
		this.myName = hClient.getUserName();
		this.hClient =  hClient;
		this.setPreferredSize(new Dimension (1150, 200));
		this.setMinimumSize(new Dimension (1150, 200));
		this.setMaximumSize(new Dimension (1150, 200));
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3));
		
		
		JLabel playerName = new JLabel(myName);
		playerName.setBounds(860, 10, 200, 50);
		playerName.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		this.add(playerName);
		playerScore = new JLabel("Score: " + Integer.toString(score));
		playerScore.setBounds(860, 35, 250, 100);
		playerScore.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
		this.add(playerScore);
		JButton showCapturedBtn = new JButton("Show Captured Cards");
		showCapturedBtn.setBounds(860,110,160,40);
		this.add(showCapturedBtn);
		capturedCardPane = new JTextPane();
		capturedCardPane.setEditable(false);
		capturedCardPane.insertIcon ( new ImageIcon ( "Image1.png" ) ); 
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		final JScrollPane jsp = new JScrollPane(capturedCardPane, v , h);
		showCapturedBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFrame popup = new JFrame("Your Captured Cards");
				popup.setDefaultCloseOperation(popup.DISPOSE_ON_CLOSE);
				popup.setSize(500,300);
				popup.setLocation(700,400);
				popup.setVisible(true);
				popup.setResizable(true);
				
				popup.add(jsp);
			}
		});

		cardButtonList = new ArrayList<CardButton>();
		initialDeal();
		refreshDisplay();
		
	}
	
	public void initialDeal(){
		ArrayList<Card> hand = hClient.getHand();
		
		for(int i = 0 ; i < hand.size(); i++){
			final CardButton j = new CardButton();

			
			j.setCard(hand.get(i));
			
			j.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent aa) {
					j.moveUpDown();
					
					refreshDisplay();
				}
			});
			
			cardButtonList.add(j);
		
			
			
		}
	}
	
	
	
	public void refreshDisplay(){
		
		for(int i = 0 ; i < cardButtonList.size(); i++){
			//System.out.println("card " +  40+i*60 );
			this.add(cardButtonList.get(i));
			//cardButtonList.get(i).setLocation(40+i*60 ,40);
			cardButtonList.get(i).setBounds(40+i*gap, cardButtonList.get(i).getNewX(), MyAssetHandler.WIDTH, MyAssetHandler.HEIGHT );
			
			
			
			
		}
		//cardButtonList.get(0).setLocation(40, 40);
		this.validate();
	}
	
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawString("hello", 10, 50);
//		g.drawImage(MyAssetHandler.cardImageArray[0], 40, 40, null);
//		g.drawImage(MyAssetHandler.cardImageArray[1], 400, 40, null);
//		g.drawImage(MyAssetHandler.cardImageArray[2], 800, 40, null);

	}	
	public void addToCapturedCard(String imagePath){
		capturedCardPane.insertIcon(new ImageIcon(imagePath));
		
	}
	public void incScore(int incBy){
		score = score + incBy;
		playerScore.setText(Integer.toString(score) + " ");
	}
		
}
