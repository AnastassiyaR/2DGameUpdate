package entity;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(8,16,32,32);  // that red square
		// or solidArea.x = 0, solidarea.width = 48; 48 - 32 = 16
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
		
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;  // where is player, the first position
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "up";
	}
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1");
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1");
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1");
		right2 = setup("/player/boy_right_2");
	}
	
	
	// KEY SETTINGS
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true ||
				keyH.rightPressed == true || keyH.leftPressed == true) {
			
			if(keyH.upPressed == true) {
	 			direction = "up";
	 		}
	 		else if(keyH.downPressed == true) {
	 			direction = "down";
	 		}
	 		else if(keyH.leftPressed == true) {
	 			direction = "left";
	 		}
	 		else if(keyH.rightPressed == true) {
	 			direction = "right";
	 		}
	 		
			// CHECK TILE COLLISION
			collisionOn = false; // if remove person after collision can not move
			gp.checker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.checker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.checker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// IF COLLISION FALSE
			if(collisionOn == false) {
				
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
	 		spriteCounter++;
	 		if(spriteCounter > 15) {
	 			if(spriteNum == 1) {
	 				spriteNum = 2;
	 			}
	 			else if(spriteNum == 2) {
	 				spriteNum = 1;
	 			}
	 			spriteCounter = 0;
	 		}
 		}
	}
	
	// PICK OBJECT SETTINGS
	public void pickUpObject (int i) {
		
		if(i != 999) {
			//pass
		}
		
	}
	
	//
	public void interactNPC(int i) {
		
		if(i != 999) {
			System.out.print("A");
		}
	}
	// DRAW DURING CHANGING MOVEMENT
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);
// 		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		
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
		// null - if the image is already loaded into memory and does not require loading images to be processed
		// the gp.tileSize (as width and height was deleted) because of setup()
		g2.drawImage(image, screenX, screenY, null);
	}
}
