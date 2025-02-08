package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile, like character, water and etc.
	final int scale = 3; // so the character is not too small 16x3=48
	

	public final int tileSize = originalTileSize * scale; 	// we do public so it is available for other packages too
	
	public final int maxScreenCol = 16; // in total 16 48x48 tiles in col
	public final int maxScreenRow = 12; // in total 16 48x48 tiles in row
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50; //text has 50x50
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// FPS
	int FPS = 60;
			
	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionCheck checker = new CollisionCheck(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread; // keep the game working until the stop
	
	// ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10]; // we can display up to 10 objects at the same time.
	
	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	// GAMEPANEL
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set the size of the class JPanel
		this.setBackground(new Color(173, 216, 230));
		this.setDoubleBuffered(true); // so that the screen refreshes smoothly, without artifacts and flickering
		this.addKeyListener(keyH);
		this.setFocusable(true); // GamePanel can be focused to receive the key input
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		playMusic(0);
		gameState = playState;
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	
 	@Override
//	public void run() {
//		// running tasks in a background thread
// 		
// 		double drawInterval = 1000000000/FPS; // 0.01666 seconds
// 		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		while(gameThread != null) {
//			
//			// The time between update and repaint
//			// long currentTime = System.nanoTime();
//			// System.out.println("Time is"+currentTime);
//			
//			update();
//			
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000000;
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//				
//				// System.out.println("GAME LOOP"); for checking
//			
//			} catch (InterruptedException e) {
//			    e.printStackTrace();
//			}
//		}
//				
//	}
 	
 	public void run() {
 		
 		double drawInterval = 1000000000/FPS;
 		double delta = 0;
 		long lastTime = System.nanoTime();
 		long currentTime;
 		long timer = 0;
 		int drawCount = 0;
 		
 		while(gameThread != null) {
 			currentTime = System.nanoTime();
 			
 			delta += (currentTime - lastTime) / drawInterval;
 			timer += (currentTime - lastTime);
 			lastTime = currentTime;
 			
 			if(delta >= 1) {
 				update();
 				repaint();
 				delta--;
 				drawCount++;
 			}
 			
 			if(timer >= 1000000000) {
 				// System.out.println("FPS:"+drawCount); to check FPS
 				drawCount = 0;
 				timer = 0;
 			}
 			
 		}
 	}
 	
 	public void update() {
 		
 		if(gameState == playState) {
 			player.update();
 		}
 		if(gameState == pauseState) {
 			// nothing
 		}
 	}
 	
 	
 	public void paintComponent(Graphics g) {
 		// Graphics has many functions to draw objects on the screen
 		super.paintComponent(g);
 		Graphics2D g2 = (Graphics2D)g; // for 2D game
 		
 		// TILE
 		tileM.draw(g2);
 		
 		// OBJECT
 		// We have to know what kind of objects we draw
 		for(int i = 0; i < obj.length; i++) {
 			if(obj[i] != null) {
 				obj[i].draw(g2, this);
 			}
 		}
 		
 		// PLAYER
 		player.draw(g2);
 		
 		// UI
 		ui.draw(g2);
 		
 		g2.dispose();
 	}
 	
 	public void playMusic(int i) {
 		
 		music.setFile(i);
 		music.play();
 		music.loop();
 	}
 	
 	public void stopMusic() {
 		music.stop();
 	}
 	
 	public void playSE(int i) {
 		se.setFile(i);
 		se.play();
 		// no need loop because it is short
 	}
 
}

