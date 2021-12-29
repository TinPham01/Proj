package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame extends JFrame implements ActionListener {
	JFrame frame;
	private static final int window_width = 875;
	private static final int window_height = 700;
	GamePanel panel;
	JButton resetButton;
	
	public int getWidth() {
		return window_width;
	}
	
	public int getHeight() {
		return window_height;
	}
	
	public Frame() {
		frame = new JFrame();
		
		panel = new GamePanel();
		JPanel blackPanel = new JPanel();
		
		resetButton = new JButton();
		resetButton.setText("Play Again?");
		resetButton.setSize(100, 50);
		resetButton.setLocation(380, 610);
		resetButton.addActionListener(this);
		
		blackPanel.setBackground(Color.black);
		blackPanel.setBounds(45, 45, 760, 560);

		frame.add(resetButton);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == resetButton) {
			   frame.remove(panel);
			   panel = new GamePanel();
			   frame.add(panel);
			   panel.requestFocusInWindow();
			   SwingUtilities.updateComponentTreeUI(frame);
		}
	}
	
}