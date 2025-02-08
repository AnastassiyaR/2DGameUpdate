package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// To close window properly once we click X button
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		window.setResizable(false);
		window.setTitle("Blue guy adventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); // Automatic window size adjustment in the graphical interface.
		
		// To display the window on the center
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame(); // object stuff
		gamePanel.startGameThread(); // the begin of game?

	}

}
