
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Card implements Serializable{
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
	
	public Card (String n, Month m, Yaku y, ImageIcon i, int v) {
		name = n;
		month = m;
		yaku = y;
		image = i;
		value = v;
	}
	
	//DEBUG
	//Delete later; this is to be used for testing initial Client/Server
	public Card (int randomincrement) {
		value = randomincrement;
	}

	//DEBUG
	//Delete later; this is to be used for testing initial Client/Server
	public void print(){
		System.out.println("this card has value of "+ value);
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
	public int getValue() {
		return value;
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
	
	public boolean isMatch(Card cd){
		//TODOS
		return false;
	}
	
}