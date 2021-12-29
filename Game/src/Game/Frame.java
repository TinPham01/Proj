package Game;

import java.awt.*;
import java.io.StreamCorruptedException;

import javax.swing.*;

public class Frame {
	JFrame frame;
	private static final int window_width = 875;
	private static final int window_height = 700;
	GamePanel panel;
	
	public static int getWidth() {
		return window_width;
	}
	
	public static int getHeight() {
		return window_height;
	}
	
	public Frame() {
		frame = new JFrame();
		
		panel = new GamePanel();
		JPanel blackPanel = new JPanel();
		
		blackPanel.setBackground(Color.black);
		blackPanel.setBounds(45, 45, 760, 560);

		frame.add(panel);
		frame.add(blackPanel);
		
		frame.setTitle("SNAKE GAME");
		frame.setSize(window_width,window_height);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(214,214,214));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);

	}
	
}
