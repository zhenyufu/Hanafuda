package com.usc.hanafuda;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MyAssetHandler {
	public final static int SIZE = 48;
	public static BufferedImage[] cardImageArray = new BufferedImage[SIZE];
	
	
	
	public static void load () {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("cardImage.jpg"));
			System.out.println("load + cardImage.jpg");
		
		
			for(int i = 0; i < SIZE; i++){
				cardImageArray[i] = new BufferedImage(50, 50, image.getType());

	              // draws the image chunk
	  
			}
			
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}
	
	
	
}
