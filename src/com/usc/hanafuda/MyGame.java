package com.usc.hanafuda;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MyGame extends JFrame{

	public static final boolean DEBUG = true;
	static JPanel currentPanel;

	public MyGame() {
		super("Hanafuda");
		setSize(1400, 1000);
		setLocation(100, 30);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		currentPanel = new MenuScreen(this);
		add(currentPanel);
		
		setVisible(true);
	}
	
	
	
	
	public static void main(String[] args) {
		MyGame  g = new MyGame();
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
