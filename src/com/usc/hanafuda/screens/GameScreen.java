package com.usc.hanafuda.screens;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.usc.hanafuda.MyGame;
import com.usc.hanafuda.entities.FieldPanel;
import com.usc.hanafuda.entities.HandPanel;
import com.usc.hanafuda.handlers.MyAssetHandler;

public class GameScreen extends JPanel {
	//
	//private JLabel t = new JLabel("another player");
	private JButton backButton;
	private JTextField textField;
	private JTextArea textArea;
	private JButton sendMessage;
	private HandPanel handPanel;
	private FieldPanel fieldPanel;
	public GameScreen(MyGame myGame) {
		
		
		this.setLayout(new BorderLayout());
		JPanel deckPanel = new JPanel();
		deckPanel.setLayout(new BorderLayout());
		deckPanel.setOpaque(false);
		add(deckPanel, BorderLayout.CENTER);

		//deckPanel.add(t, BorderLayout.WEST);

//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(new File("Image1.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		//JLabel imageLabel = new JLabel(new ImageIcon(image));

		//deckPanel.add(imageLabel, BorderLayout.SOUTH);
		handPanel = new HandPanel();
		deckPanel.add(handPanel, BorderLayout.SOUTH);

		//fieldPanel = new FieldPanel();
		//deckPanel.add(fieldPanel, BorderLayout.CENTER);
		
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		add(textPanel, BorderLayout.EAST);
		textPanel.setPreferredSize(new Dimension(250, 1000));

		backButton = new JButton("Back");
		textPanel.add(backButton, BorderLayout.NORTH);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aa) {
				myGame.setPanel(new MenuScreen(myGame));

			}
		});
		
		JPanel chatBottomPanel = new JPanel(new FlowLayout());
		
		textField = new JTextField(15);
		//textPanel.add(textField, BorderLayout.SOUTH);
		
		sendMessage = new JButton("Send");
		//textPanel.add(sendMessage, BorderLayout.SOUTH);
		sendMessage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				if(textField.getText().length()<1){
					//do nothing, no message
				}
				else{
					textArea.append("<" + "userName" + ">: " + textField.getText() + "\n"); 
					textField.setText("");
				}
			}
		});
		textField.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER){
					if(textField.getText().length()<1){
						//do nothing, no message
					}
					else{
						textArea.append("<" + "userName" + ">: " + textField.getText() + "\n"); 
						textField.setText("");
					}
				}
			}
			public void keyReleased(KeyEvent ke) {
			}
			public void keyTyped(KeyEvent ke) {
				
			}
		});
		
		chatBottomPanel.add(textField);
		chatBottomPanel.add(sendMessage);
		
		textPanel.add(chatBottomPanel, BorderLayout.SOUTH);
		textArea = new JTextArea("", 7, 20);
		textArea.setEditable(false);
		textPanel.add(textArea, BorderLayout.CENTER);

		
		
		
		
		
		
		
//		// temp code:
//		JPanel tf = new JPanel();
//		tf.setOpaque(false);
//		JLabel ml = new JLabel("another player");
//		tf.add(ml);
//		deckPanel.add(tf, BorderLayout.NORTH);
//
//		JPanel te = new JPanel();
//		te.setOpaque(false);
//		JLabel mle = new JLabel("another player");
//		te.add(mle);
//		deckPanel.add(mle, BorderLayout.EAST);
//
//		JPanel o = new JPanel();
//		o.setOpaque(false);
//		o.setLayout(new GridBagLayout());
//		JLabel m = new JLabel("main deck");
//		o.add(m);
//		deckPanel.add(o, BorderLayout.CENTER);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		//g.setColor(new Color(246, 244, 186));
		//g.fillRect(0, 0, 1400, 1000);

		// temp:
		//g.setColor(Color.black);
		//g.drawRect(100, 100, 1000, 700);
		g.drawImage(MyAssetHandler.deckImage, 200, 300, null);

	
	}

}
