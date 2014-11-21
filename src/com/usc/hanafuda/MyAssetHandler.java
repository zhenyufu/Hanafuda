package com.usc.hanafuda;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MyAssetHandler {
	public final static int SIZE = 48;
//	public final static int ROW = 3;
//	public final static int COL = 16;
//	public final static int WIDTH = 194;
//	public final static int HEIGHT = 318;
	
	public final static int ROW = 4;
	public final static int COL = 12;
	public final static int WIDTH = 64;
	public final static int HEIGHT = 104;
	

	public static BufferedImage[] cardImageArray = new BufferedImage[SIZE];
	
	
	
	public static void load () {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("image111.png"));
			System.out.println("load + cardImage.jpg");
		
		
			for(int i = 0; i < ROW; i++){
				for(int j = 0; j < COL ; j++){
				//cardImageArray[i] = new BufferedImage(500, 500, image.getType());
					System.out.println(i + "," + j);
				cardImageArray[i*COL+j] = image.getSubimage(j * WIDTH, i * HEIGHT, WIDTH, HEIGHT);
				}
			}
			
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}
	
	
	
}
