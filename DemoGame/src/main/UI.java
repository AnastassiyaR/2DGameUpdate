package main;

import java.awt.BasicStroke;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import object.OBJ_Key;
import object.OBJ_ManaCrystal;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, pb;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCount = new ArrayList<>();
	
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0; // 0: the first state, 1: second screen
	public int slotCol = 0;
	public int slotRow = 0;
	int subState = 0;
	
	public UI(GamePanel gp) {
		
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/PurisaBold.ttf");
			pb = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		
		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		
	}
	
	
	public void addMessage(String text) {
		
		
		message.add(text);
		messageCount.add(0);
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(pb);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();

		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
		
		// OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		
		// GAMEOVER STATE
//		if(gp.gameState == gp.gameover) {
//			Gameover();
//		}
		
	}
	
//	public void Gameover() {
//		
//		
//		g2.setFont(pb);
//		
//		g2.setColor(new Color(0, 0, 0, 100));
//		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//
//		g2.setFont(g2.getFont().deriveFont(Font.BOLD,90F));
//		String text = "GAMEOVER";
//		int x = getXforCenteredText(text);
//		int y = gp.screenHeight/3;
//		
//		g2.setColor(Color.black);
//		g2.drawString(text, x, y);
//		g2.setColor(Color.white);
//		g2.drawString(text, x-5, y-5);
//		
//		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
//		text = "Retry";
//		y+=gp.tileSize*4;
//		g2.drawString(text, x, y);
//		if(commandNum == 0) {
//			g2.drawString(">", x-25, y);
//		}
//		
//		text = "Quit";
//		x+=gp.tileSize*8;
//		g2.drawString(text, x, y);
//		if(commandNum == 1) {
//			g2.drawString(">", x-25, y);
//		}
//		
//	}
	
	public void drawOptionsScreen() {
		
		g2.setColor(Color.white);
		g2.setFont(maruMonica.deriveFont(32F));
		
		// WINDOW
		int frameX = gp.tileSize*4;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		drawWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_control(frameX, frameY); break;
		case 2: finishgame(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false;
	}
	
	
	public void options_control(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY += gp.tileSize;
		g2.drawString("Attack", textX, textY); textY += gp.tileSize;
		g2.drawString("Shoot", textX, textY); textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
		g2.drawString("Pause", textX, textY); textY += gp.tileSize;
		g2.drawString("Options", textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize*6;
		textY = frameY + gp.tileSize*2;
		g2.drawString("WASD", textX, textY); textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
		g2.drawString("F", textX, textY); textY += gp.tileSize;
		g2.drawString("C", textX, textY); textY += gp.tileSize;
		g2.drawString("P", textX, textY); textY += gp.tileSize;
		g2.drawString("1", textX, textY); textY += gp.tileSize;
		
		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("BACK", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 2;
			}
		}
		
	}
	
	public void options_top(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		// MUSIC
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
		}
		// SE
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		
		// CONTROL
		textY += gp.tileSize;
		g2.drawString("Control", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 1;
				commandNum = 0;
			}
		}
		
		// END GAME
		textY += gp.tileSize;
		g2.drawString("End game", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		// BACK
		textY += gp.tileSize*4;
		g2.drawString("Back", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		
		textX = frameX + gp.tileSize*4;
		textY = frameY + gp.tileSize/2;
		
		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 150, 24);
		int volumeWidth = 30 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		// SE VOLUME
		textY += gp.tileSize;
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 150, 24);
		volumeWidth = 30 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
	}
	
	public void finishgame(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		
		String text = "Quit?";
		g2.setFont(maruMonica.deriveFont(64F));
		g2.drawString(text, textX, textY);
		
		// YES
		text = "Yes";
		g2.setFont(maruMonica.deriveFont(46F));
		textY += gp.tileSize*2;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
			}
		}
		// NO
		text = "No";
		g2.setFont(maruMonica.deriveFont(46F));
		textY += gp.tileSize*2;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}
	}
	
	
	public void drawInventory() {
		
		// FRAME
		int frameX = gp.tileSize*9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		
		drawWindow(frameX, frameY, frameWidth, frameHeight);
		
		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+3;
		
		
		// DRAW PLAYER'S ITEMS
		for(int i = 0; i < gp.player.inventory.size(); i++) {
			
			// EQUIP CURSOR
			if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
					gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		// CURSOR
		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		// DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
	
		// DESCRIPTION FRAME
		int dframeX = frameX;
		int dframeY = frameY + frameHeight;
		int dframeWidth = frameHeight;
		int dframeHeight = gp.tileSize*3;
	
		// DRAW DESCRIPTION TEXT
		int textX = dframeX + 20;
		int textY = dframeY + gp.tileSize;
		g2.setFont(maruMonica.deriveFont(28F));
		
		int itemIndex = getItemIndexOnSlot();
	
		if(itemIndex < gp.player.inventory.size()) {
			
			drawWindow(dframeX, dframeY, dframeWidth, dframeHeight);
			
			for(String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 32;
			}
			
		}
	
	}
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(maruMonica.deriveFont(Font.BOLD, 32F));
		
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) != null) {
				
				
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int count = messageCount.get(i) + 1; // messageCount++
				messageCount.set(i, count); // set the count to the array
				messageY += 50;
				
				if(messageCount.get(i) > 180) {
					message.remove(i);
					messageCount.remove(i);
				}
				
			}
		}
	}
	
	// HEARTS AND SENSE ABOUT P + CTRL
	public void drawPlayerLife() {

		
		// TEXT
		g2.setFont(pb);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,16F));
		String text = "(CTRL + C) = ATTRIBUTES\n(CTRL + P) = PAUSE";
		int x = gp.tileSize/2;
		int y = gp.tileSize*2;
		
		for(String a : text.split("\n")) {
			g2.setColor(Color.WHITE);
			g2.drawString(a, x, y);
			y += 30;
		}	
		
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		int i = 0;
		
		// DRAW ONLY BLANK HEARTS
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		// RESET
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		// DRAW CURRENT LIFE
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		
		// DRAW MAX MANA
		x = gp.screenWidth - gp.tileSize*4;
		y = gp.tileSize*2-gp.tileSize;
		i = 0;
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x+=35;
		}
		
		// DRAW MANA
		x = gp.screenWidth - gp.tileSize*4;
		y = gp.tileSize*2-gp.tileSize;
		i = 0;
		while(i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x+=35;
		}

	}
	public void drawTitleScreen() {
		g2.setFont(maruMonica);
		
		if(titleScreenState == 0) {
			g2.setColor(new Color(90, 120, 80));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,90F));
			String text = "Demo Game (UPDATE)";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			// SHADOW
			g2.setColor(Color.black);
			g2.drawString(text, x+5, y+3);
			
			// MAIN TEXT
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			// PLAYER
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,36F));
			
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*3.5;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize,y);
			}
			
			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize,y);
			}
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize,y);
			}
		}
		else if (titleScreenState == 1) {
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select your class";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Thief";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Sorcerer";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
		}
		
	}
	public void drawPauseScreen() {
		g2.setFont(maruMonica);
		
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD,90F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
	}
	
	public void drawDialogueScreen() {
		
		// WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
	
	public void drawCharacterScreen() {
		
		// CREATE A FRAME
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize - 20;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*11;
		drawWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(20F));
		
		int textX = frameX+20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		// NAMES
		g2.drawString("Level", textX, textY); textY += lineHeight;
		g2.drawString("Life", textX, textY); textY += lineHeight;
		g2.drawString("Mana", textX, textY); textY += lineHeight;
		g2.drawString("Strength", textX, textY); textY += lineHeight;
		g2.drawString("Dexterity", textX, textY); textY += lineHeight;
		g2.drawString("Attack", textX, textY); textY += lineHeight;
		g2.drawString("Defense", textX, textY); textY += lineHeight;
		g2.drawString("Exp", textX, textY); textY += lineHeight;
		g2.drawString("Coin", textX, textY); textY += lineHeight + 25;
		g2.drawString("Weapon", textX, textY); textY += lineHeight + 15;
		g2.drawString("Shield", textX, textY); textY += lineHeight;
		
		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		// Reset textY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight - 10;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
		textY += gp.tileSize;
		
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	public void drawWindow(int x, int y, int width, int height) {
		
		// other way to put color
		// r, g, b, visibility
		Color c = new Color(0, 0, 0, 200);
		g2.setColor(c);
		// 0, 0 - arc
		g2.fillRoundRect(x, y, width, height, 0, 0);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		// Defines the width of outlines of graphics
		// which are rendered with a Graphics2D
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 0, 0);
		
		
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	public int getXforAlignToRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
