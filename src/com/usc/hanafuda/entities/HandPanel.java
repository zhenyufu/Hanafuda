package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.usc.hanafuda.handlers.MyAssetHandler;

public class HandPanel extends JPanel{
	
	ArrayList<CardButton> cardButtonList;
	public final int gap = 110;
	
	
	public HandPanel(){
		this.setBackground(Color.yellow);
		this.setPreferredSize(new Dimension (1150, 200));
		this.setMinimumSize(new Dimension (1150, 200));
		this.setMaximumSize(new Dimension (1150, 200));
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
		cardButtonList = new ArrayList<CardButton>();
		initialDeal();
		refreshDisplay();
	}
	
	public void initialDeal(){
		for(int i = 0 ; i < 7; i++){
			CardButton j = new CardButton(MyAssetHandler.cardIconArray[i]);
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

//		g.drawImage(MyAssetHandler.cardImageArray[0], 40, 40, null);
//		g.drawImage(MyAssetHandler.cardImageArray[1], 400, 40, null);
//		g.drawImage(MyAssetHandler.cardImageArray[2], 800, 40, null);

	}	
		
}
