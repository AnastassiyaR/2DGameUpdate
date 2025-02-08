package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font font, font2;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00"); // to show in 0.00 format
	
	public UI(GamePanel gp) {
		this.gp = gp;
		font = new Font("Arial", Font.PLAIN, 40);
		font2 = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(font);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.playState) {
			// Do playstate
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if(gameFinished == true) {
			
			String text;
			int textLength;
			int x;
			int y;
			
			g2.setFont(font);
			g2.setColor(Color.orange);
			text = "Good job!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			g2.setFont(font);
			g2.setColor(Color.white);
			text = "Your time is "+dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*4);
			g2.drawString(text, x, y);
			
			g2.setFont(font2);
			g2.setColor(Color.yellow);
			text = "You did it!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
			
		} else {
			g2.setFont(font);
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			g2.drawString("x "+gp.player.hasKey, 74, 65);
			
			// TIME
			playTime += (double)1/60;
			g2.drawString("Time: "+dFormat.format(playTime), gp.tileSize*11, 65);
			// MESSAGE
			if(messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, 140, 65);
				
				messageCounter++;
				
				// 120 / 60 frames per second = 2 second
				if(messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		
		}
	}
	
	public void drawPauseScreen() {
		
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
