package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tree.tree;

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
	public final int maxWorldCol = 50; // text has 50x50
	public final int maxWorldRow = 50;
	public final int maxMap = 10;
	public int currentMap = 0;
	
	// FPS
	int FPS = 60;
			
	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionCheck checker = new CollisionCheck(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread; // keep the game working until the stop
	public PathFinder pFinder = new PathFinder(this);
	SaveLoad saveload = new SaveLoad(this);
	
	// ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	public Entity obj[][] = new Entity[maxMap][20]; // we can display up to 10 objects at the same time.
	public Entity npc[][] = new Entity[maxMap][10];
	public Entity monster[][] = new Entity[maxMap][20];
	public tree iTile[][] = new tree[maxMap][50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();

	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionsState = 5;
	public final int gameover = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;
	
	// GAMEPANEL
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set the size of the class JPanel
		this.setBackground(new Color(0, 0, 0));
		this.setDoubleBuffered(true); // so that the screen refreshes smoothly, without artifacts and flickering
		this.addKeyListener(keyH);
		this.setFocusable(true); // GamePanel can be focused to receive the key input
	}
	
	// ADDING OBJECTS
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setTree();
		gameState = titleState;
	}
	
	public void resetGame(boolean restart) {
		
		player.setDefaultPosition();
		player.restoreStatus();
		aSetter.setNPC();
		aSetter.setMonster();
		
		if(restart == true) {
			
			player.setDefaultValues();
			aSetter.setObject();
			aSetter.setTree();
		}

		
	}
	
	// START GAME
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
 	
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
 	
 	// UPDATING
 	public void update() {
 		
 		if(gameState == playState) {
 			
 			// PLAYER
 			player.update();
 			
 			// NPC
 			for(int i = 0; i < npc.length; i++) {
 				if(npc[currentMap][i] != null) {
 					npc[currentMap][i].update();
 				}
 			}
 			
 			// MONSTER
 			for(int i = 0; i < monster.length; i++) {
 				if(monster[currentMap][i] != null) {
 					if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
 						monster[currentMap][i].update();
 					}
 					if(monster[currentMap][i].alive == false) {
 						monster[currentMap][i].checkDrop();
 						monster[currentMap][i] = null;
 					}
 				}
 					
 			}
 			
 			// PROJECTILE
 			for(int i = 0; i < projectileList.size(); i++) {
 				if(projectileList.get(i) != null) {
 					if(projectileList.get(i).alive == true) {
 						projectileList.get(i).update();
 					}
 					if(projectileList.get(i).alive == false) {
 						projectileList.remove(i);
 					}
 				}	
 			}
 			
 			// PARTICLES
 			for(int i = 0; i < particleList.size(); i++) {
 				if(particleList.get(i) != null) {
 					if(particleList.get(i).alive == true) {
 						particleList.get(i).update();
 					}
 					if(particleList.get(i).alive == false) {
 						particleList.remove(i);
 					}
 				}	
 			}
 			
 			// DRY TREE
 			for(int i = 0; i < iTile.length; i++) {
 				if(iTile[currentMap][i] != null) {
 					if(iTile[currentMap][i].alive == true) {
 						iTile[currentMap][i].update();
 					}
 				}	
 			}
 		}
 		
 		if(gameState == pauseState) {}
 		
 		for(int i = 0; i < monster.length; i++) {
 			if(monster[currentMap][i] != null) {
 				monster[currentMap][i].update();
 			}
 		}
 	}
 	
 	public void paintComponent(Graphics g) {
 		super.paintComponent(g);
 		Graphics2D g2 = (Graphics2D)g;
 		
 		// TITLE SCREEN
 		if(gameState == titleState) {
 			ui.draw(g2);
 		} else {
 			
 			// TILE
 			tileM.draw(g2);
 	 	
 			// DRY TREE
 			for(int i = 0; i < iTile.length; i++) {
 				if(iTile[currentMap][i] != null) {
 					iTile[currentMap][i].draw(g2);
 				}
 			}
 			
 			// ADD ENTITIES TO THE LIST
 			entityList.add(player);
 			for(int i = 0; i < npc.length; i++) {
 				if(npc[currentMap][i] != null) {
 					entityList.add(npc[currentMap][i]);
 				}
 			}
 			for(int i = 0; i < obj.length; i++) {
 				if(obj[currentMap][i] != null) {
 					entityList.add(obj[currentMap][i]);
 				}
 			}
 			for(int i = 0; i < monster.length; i++) {
 				if(monster[currentMap][i] != null) {
 					entityList.add(monster[currentMap][i]);
 				}
 			}
 			for(int i = 0; i < projectileList.size(); i++) {
 				if(projectileList.get(i) != null) {
 					entityList.add(projectileList.get(i));
 				}
 			}
 			
 			for(int i = 0; i < particleList.size(); i++) {
 				if(particleList.get(i) != null) {
 					entityList.add(particleList.get(i));
 				}
 			}
 			
 			// SORT
 			Collections.sort(entityList, new Comparator<Entity>() {
 				
 				@Override
 				public int compare(Entity o1, Entity o2) {
 					int result = Integer.compare(o1.worldY, o2.worldY);
 					return result;
 				}
 			});
 			
 			// DRAW ENTITIES
 			for(int i = 0; i < entityList.size(); i++) {
 				entityList.get(i).draw(g2);
 			}
 			// REMOVE ENTITIES
 			entityList.clear();
 			
 			
 	 		// UI
 	 		ui.draw(g2);
 	 		
 		}
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
 	}
 
}
