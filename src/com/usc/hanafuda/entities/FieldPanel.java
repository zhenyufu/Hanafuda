package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.usc.hanafuda.HClient;
import com.usc.hanafuda.handlers.MyAssetHandler;

public class FieldPanel extends JPanel {

	
		static boolean aCardIsUp = false;
		ArrayList<CardButton> cardButtonList;
		public final int gap = 100;
		private int score = 0;
		HClient hClient;
		public FieldPanel(HClient hClient){
			this.setBackground(Color.GRAY);
			
			this.hClient =  hClient;
			this.setPreferredSize(new Dimension (1150, 200));
			this.setMinimumSize(new Dimension (1150, 200));
			this.setMaximumSize(new Dimension (1150, 200));
			this.setLayout(null);
			this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3));
			cardButtonList = new ArrayList<CardButton>();
			initialDeal();
			refreshDisplay();
			
		}
		
		public void initialDeal(){
			ArrayList<Card> field = hClient.getField();
			ImageIcon deckImage = new ImageIcon("deck.png");
			CardButton deck = new CardButton(deckImage);
			deck.setBounds(40, 40, MyAssetHandler.WIDTH, MyAssetHandler.HEIGHT);
			this.add(deck);
			
			for(int i = 0 ; i < field.size(); i++){
				CardButton j = new CardButton();

				
				j.setCard(field.get(i));
				
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
				cardButtonList.get(i).setBounds(140+i*gap, cardButtonList.get(i).getNewX(), MyAssetHandler.WIDTH, MyAssetHandler.HEIGHT );
				
				
				
				
			}
			//cardButtonList.get(0).setLocation(40, 40);
			this.validate();
		}
		
		
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

		}	
			
	

}
