package entity;

import java.awt.AlphaComposite;
import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Key;
import object.OBJ_Shield;
import object.OBJ_Sword;

public class Player extends Entity{
	
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(8,16,32,32);  // that red square
		// or solidArea.x = 0, solidarea.width = 48; 48 - 32 = 16
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
		
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;  // where is player, the first position
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
		// PLAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1; // The more strength he has, the more damage he gives
		dexterity = 1; // The more dexterity he has, the less damage he gets
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword(gp);
		currentShield = new OBJ_Shield(gp);
		attack = getAttack();
		defense = getDefense();
	}
	
	public void setItems() {
		
		inventory.add(currentShield);
		inventory.add(currentWeapon);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
	}
	public int getAttack() {
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	
	
	public void getPlayerImage() {
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
	}
	
	public void getPlayerAttackImage() {
		aup1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
		aup2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
		adown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
		adown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
		aleft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
		aleft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
		aright1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
		aright2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	
	// KEY SETTINGS
	public void update() {
		
		if(attacking == true) {
			attacking();
		}
		else if(keyH.upPressed == true || keyH.downPressed == true ||
				keyH.rightPressed == true || keyH.leftPressed == true || keyH.enterPressed == true) {
			
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
			
			// CHECK MONSTER COLLISION
			int monsterIndex = gp.checker.checkEntity(this, gp.monster);
			contactmonster(monsterIndex);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();

			
			// IF COLLISION FALSE
			if(collisionOn == false && keyH.enterPressed == false) {
				
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			if(keyH.enterPressed == true && attackCanceled == false) {
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCanceled = false;
			gp.keyH.enterPressed = false;
			
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
		
		// This needs to be outside of key if statement
		if(invincible == true) {
			invinicibleCount++;
			if(invinicibleCount > 60) {
				invincible = false;
				invinicibleCount = 0;
			}
		}
	}
	
	// ATTACK
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			// Save the current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// Adjust player's worldX/Y for the attakArea
			switch(direction ) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			// Check monster collision with the updated worldX, worldy, solidArea
			int monsterIndex = gp.checker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			// After checking collision, restore the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	// PICK OBJECT SETTINGS
	public void pickUpObject (int i) {
		if(i != 999) {
			// pass
		}
	}
	
	
	// INTERACT WITH NPC
	public void interactNPC(int i) {
		
		if(gp.keyH.enterPressed == true) {
			if(i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			} 
		}
	}
	
	// CONTACT WITH MONSTER
	public void contactmonster(int i) {
		
		if(i!=999) {
			if(invincible == false) {
				gp.playSE(6);
				
				int damage = gp.monster[i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				
				life-= damage;
				invincible = true;
			}
		}
	}
	
	public void damageMonster(int i) {
		
		if(i != 999) {
			
			if(gp.monster[i].invincible == false) {
				
				gp.playSE(5);
				
				int damage = attack - gp.monster[i].defense;
				if(damage < 0) {
					damage = 0;
				}
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage + " DAMAGE");
				
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage("KILLED THE " + gp.monster[i].name);
					gp.ui.addMessage("Exp " + gp.monster[i].exp);
					exp += gp.monster[i].exp;
					checkLevelUp();
				}
			}
		} else {
			
		}
	}
	
	public void checkLevelUp() {
		
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "Level " + level;
		}
	}
	
	// DRAW DURING CHANGING MOVEMENT
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);
// 		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		int tempScreenX = screenX; // character stay at the position, no collision with trees
		int tempScreenY = screenY;
		
		
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = aup1;}
				if(spriteNum == 2) {image = aup2;}
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = adown1;}
				if(spriteNum == 2) {image = adown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = aleft1;}
				if(spriteNum == 2) {image = aleft2;}
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = aright1;}
				if(spriteNum == 2) {image = aright2;}
			}
			break; 	
		}
		
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		// null - if the image is already loaded into memory and does not require loading images to be processed
		// the gp.tileSize (as width and height was deleted) because of setup()
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// RESET ALPHA
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
