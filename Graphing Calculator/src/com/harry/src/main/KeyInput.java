package com.harry.src.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class KeyInput extends KeyAdapter {

	private Main main;
	private JFrame frame;
	
	public KeyInput(Main main, JFrame frame) {
		this.main = main;
		this.frame = frame;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_S)
			main.setStroke(main.getStroke() + 1);
		
		if(key == KeyEvent.VK_N) {
			String exp = (String)JOptionPane.showInputDialog(frame, "Enter a new expression:", "New Curve", JOptionPane.PLAIN_MESSAGE, null, null, "NEW");
			main.addNewExpression(exp);
		}
		
		if(key == KeyEvent.VK_R)
			main.clearList();
		
		if(key == KeyEvent.VK_P)
			main.popExp();
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	}
	
}
