package com.usc.hanafuda.entities;



import java.io.Serializable;

import javax.swing.ImageIcon;

public class Card implements Serializable {
	enum Month {
		January, February, March, April, May, June, 
		July, August, September, October, November, December
	}
	
	enum Yaku {
		I, Ro, Ha, Ni, Ho, He, To, Chi
	}
	
	private String name;
	private Month month;
	private Yaku yaku;
	private int value;
	private ImageIcon image;
    private int id;
	public Card (int id, String n, Month m, Yaku y, ImageIcon i, int v) {
        this.id=id;
		name = n;
		month = m;
		yaku = y;
		image = i;
		value = v;
	}
	
	//DEBUG
	//Delete later; this is to be used for testing initial Client/Server
	//public Card (int randomincrement) {
	//	value = randomincrement;
	//}

	//DEBUG
	public void printName() {
		System.out.println(name);
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
	public int getValue() {
		return value;
	}
    
    public int getId() {
		return id;
	}
	
	public Month getMonth() {
		return month;
	}
	
	public Yaku getYaku() {
		return yaku;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isMatch(Card cd) {
		if (this.month == cd.getMonth()) {
				return true;
		}
		else {
			return false;
		}
	}
	
}