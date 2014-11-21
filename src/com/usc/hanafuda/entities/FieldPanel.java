package com.usc.hanafuda.entities;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class FieldPanel extends JPanel{

	
	
	
	
	public FieldPanel(){
		
		this.setBackground(Color.blue);
		this.setPreferredSize(new Dimension (1150, 200));
		this.setMinimumSize(new Dimension (1150, 200));
		this.setMaximumSize(new Dimension (1150, 200));
		
	}
}
