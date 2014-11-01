package com.usc.hanafuda;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameScreen extends JPanel {

	private JLabel t = new JLabel("another player");
	private JButton backButton;
	private JTextField textField;
	private JTextArea textArea;

	public GameScreen(MyGame myGame) {

		this.setLayout(new BorderLayout());
		JPanel deckPanel = new JPanel();
		deckPanel.setLayout(new BorderLayout());
		deckPanel.setOpaque(false);
		add(deckPanel);

		deckPanel.add(t, BorderLayout.WEST);

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("Image1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel imageLabel = new JLabel(new ImageIcon(image));

		deckPanel.add(imageLabel, BorderLayout.SOUTH);

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		add(textPanel, BorderLayout.EAST);
		textPanel.setPreferredSize(new Dimension(200, 600));

		backButton = new JButton("Back");
		textPanel.add(backButton, BorderLayout.NORTH);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aa) {
				myGame.setPanel(new MenuScreen(myGame));

			}
		});

		textField = new JTextField("input");
		textPanel.add(textField, BorderLayout.SOUTH);

		textArea = new JTextArea("chat content", 7, 20);
		textPanel.add(textArea, BorderLayout.CENTER);

		
		
		
		
		
		
		
		// temp code:
		JPanel tf = new JPanel();
		tf.setOpaque(false);
		JLabel ml = new JLabel("another player");
		tf.add(ml);
		deckPanel.add(tf, BorderLayout.NORTH);

		JPanel te = new JPanel();
		te.setOpaque(false);
		JLabel mle = new JLabel("another player");
		te.add(mle);
		deckPanel.add(mle, BorderLayout.EAST);

		JPanel o = new JPanel();
		o.setOpaque(false);
		o.setLayout(new GridBagLayout());
		JLabel m = new JLabel("main deck");
		o.add(m);
		deckPanel.add(o, BorderLayout.CENTER);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(new Color(246, 244, 186));
		g.fillRect(0, 0, 1400, 1000);

		// temp:
		g.setColor(Color.black);
		g.drawRect(100, 100, 1000, 700);

	}

}
