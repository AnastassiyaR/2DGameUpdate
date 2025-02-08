package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	public int worldX, worldY; // The first position
	public int speed;
	
	// image with an accessible buffer of image data, represents an image in memory
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY; // for objects
	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	// CHARACTER'S BEHAVIOR
	public void setAction() {
	
	}
	public void update() {
		
		setAction();
		
		collisionOn = false;
		gp.checker.checkTile(this);
		gp.checker.checkObject(this, false);
		gp.checker.checkPlayer(this);
		
		if(collisionOn == false) {
			
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		
// 		spriteCounter++;
// 		if(spriteCounter > 15) {
// 			if(spriteNum == 1) {
// 				spriteNum = 2;
// 			}
// 			else if(spriteNum == 2) {
// 				spriteNum = 1;
// 			}
// 			spriteCounter = 0;
// 		}
		
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenx = worldX - gp.player.worldX + gp.player.screenX; 
		int screeny = worldY - gp.player.worldY + gp.player.screenY;
		
		// boundary so it draws only tiles which is visible on the screen
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch(direction) {
			case "up":
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
				break; 
			}
			
			g2.drawImage(image, screenx, screeny, gp.tileSize, gp.tileSize, null);
		 
		} 
	}
	// BUFFER OF CHARACTER
	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
