package com.usc.hanafuda.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.usc.hanafuda.handlers.MyAssetHandler;

public class CardButton extends JButton{
	private int x =40;
	private boolean setGlow = false;
	
	public CardButton(ImageIcon cardIconArray){
		this.setIcon(cardIconArray);
		
	}

	public CardButton() {
		// TODO Auto-generated constructor stub
	}

	public int getNewX() {
		return x;
	}

	
	public void moveUpDown(){
		if(x == 20) { 
			x = 40;
			HandPanel.aCardIsUp = false;
		}
		else if(x == 40 && 	!HandPanel.aCardIsUp) {
			x = 20; 
			HandPanel.aCardIsUp = true;
		}		
	}

	public void setCard(Card card){
		ImageIcon icon = MyAssetHandler.getIcon(card.getId());
		this.setIcon(icon);		
	}
	
	
	
	public void setGlow(){
		setGlow = true;
	}
	
	public void unsetGlow() {
		setGlow = false;
	}
	
	public boolean isGlowSet() {
		return setGlow;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(setGlow == true) { 
			g2.setStroke(new BasicStroke(5));
			g2.setFont(new Font("Arial", Font.BOLD, 20)); 
			g2.setColor(Color.green);
			g2.drawRect(5, 5, 75, 130);
		}
		
	}

	
}
