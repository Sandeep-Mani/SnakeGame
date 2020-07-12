package main.java;


import java.awt.Color;

import javax.swing.JFrame;

import javax.swing.WindowConstants;

public class Main {
	
	private static final int FRAME_HEIGHT = 700;
	private static final int FRAME_WIDTH = 905;
	private static final int FRAME_START_X = 10;
	private static final int FRAME_START_Y = 10;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Gameplay gameplay = new Gameplay();
		frame.setBounds(FRAME_START_X, FRAME_START_Y, FRAME_WIDTH, FRAME_HEIGHT);
		frame.setBackground(Color.DARK_GRAY);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(gameplay);
	}
}