package com.usc.hanafuda;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.usc.hanafuda.handlers.MyAssetHandler;
import com.usc.hanafuda.screens.MenuScreen;

public class MyGame extends JFrame{
//work!!!
	public static final boolean DEBUG = true;
	static JPanel currentPanel;
	static HClient client;
	static String playerName;
	
	public MyGame(HClient h) {
		super("Hanafuda");
		setSize(1400, 1000);
		setLocation(150, 30);
		setMinimumSize(new Dimension(1350, 650));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.client = h;
		this.playerName = h.getUserName();
		
		currentPanel = new MenuScreen(this);
		add(currentPanel);
		
		setVisible(true);
	}
	
	
	public HClient getHClient(){
		return client;
	}


	public void setPanel(JPanel panel) {
		this.remove(currentPanel);
		currentPanel = panel;
		this.add(currentPanel);
		this.repaint();
		this.revalidate();
	}
	

	
	public JPanel getCurrentPanel(){
		return currentPanel;
	}

}
