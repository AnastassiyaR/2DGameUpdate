package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	// image with an accessible buffer of image data, represents an image in memory
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage aup1, aup2, adown1, adown2, aleft1, aleft2, aright1, aright2;
	public BufferedImage image, image2, image3;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // need for collision, red squares
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int solidAreaDefaultX, solidAreaDefaultY; // for objects
	public boolean collisionOn = false;
	String dialogues[] = new String[20];
	
	// STATE
	public int worldX, worldY; // The first position
	public String direction = "down";
	public int spriteNum = 1;
	int dialogueIndex = 0;
	public boolean collision = false;
	public boolean invincible = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
			
	// COUNTER
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int invinicibleCount = 0;
	public int shotAvailableCount = 0;
	int dyingCounter = 0;
	int hpBarCount = 0;
	
	// CHARACTER ATTRIBUTES
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	
	// TYPE
	public int type; // 0: player, 1: npc, etc
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	
	// ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	

	// CHARACTER'S BEHAVIOR
	public void setAction() {}
	
	public void damageReaction() {
		
	}
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
		gp.playSE(7);
	}
	
	public void use(Entity entity) {} // OBJ_Potion
	
	public void update() {
		
		setAction();
		
		collisionOn = false;
		gp.checker.checkTile(this);
		gp.checker.checkObject(this, false);
		
		// check collision between entity
		gp.checker.checkEntity(this, gp.npc);
		gp.checker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.checker.checkPlayer(this);
		
		if(this.type == type_monster && contactPlayer == true) {
			if(gp.player.invincible == false) {
				// damageee
				
				int damage = attack - gp.player.defense;
				if(damage < 0) {
					damage = 0;
				}
				
				gp.player.life -= damage;
				
				gp.player.invincible = true;
			}
		}
		
		if(collisionOn == false) {
			
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		
		// down1 to down 2
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
 		
 		if(invincible == true) {
			invinicibleCount++;
			if(invinicibleCount > 30) {
				invincible = false;
				invinicibleCount = 0;
			}
		}
		
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
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
				break;
			case "down":
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
				break;
			case "left":
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
				break;
			case "right":
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
				break; 
			}
			
			// MONSTER HP BAR
			if(type == 2 && hpBarOn == true) {
				
				double oneScale = (double)gp.tileSize/maxLife; // Divide the hp bar
				double hpBar = oneScale*life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenx-1, screeny-16, gp.tileSize+2, 12);
				g2.setColor(new Color(255, 0,30));
				g2.fillRect(screenx, screeny - 15, (int)hpBar, 10);
				
				hpBarCount++;
				
				if(hpBarCount > 600) {
					hpBarCount = 0;
					hpBarOn = false;
				}
			}
			
			if(invincible == true) {
				hpBarOn = true;
				hpBarCount = 0;
				changeAlpha(g2,0.4F);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			}
			
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			
			g2.drawImage(image, screenx, screeny, gp.tileSize, gp.tileSize, null);
			
			changeAlpha(g2,1F);
		 
		} 
	}
	
	// DYING ANIMATION
	public void dyingAnimation(Graphics2D g2) {
		
		int i = 5;
		
		dyingCounter++;
		
		if(dyingCounter <=i) {changeAlpha(g2, 0f);}
		if(dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0f);}
		if(dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1f);}
		if(dyingCounter > i*8) {alive = false;}}
	
	
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	// BUFFER OF CHARACTER
	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
