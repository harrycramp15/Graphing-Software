package com.harry.src.main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	private JFrame frame;
	
	public Window(String title, int width, int height, Main main) {
		frame = createFrame(title, width, height, main);
	}
	
	private JFrame createFrame(String title, int width, int height, Main main) {
		JFrame frame = new JFrame(title);
		
		Dimension dims = new Dimension(width, height);
		frame.setPreferredSize(dims);
		frame.setMinimumSize(dims);
		frame.setMaximumSize(dims);
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(main);
		
		return frame;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}
