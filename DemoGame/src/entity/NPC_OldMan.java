package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Bshield;
import object.OBJ_Key;
import object.OBJ_Potion;
import object.OBJ_Shield;
import object.OBJ_Sword;


public class NPC_OldMan extends Entity{

	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 30;
		solidArea.height = 30;
		
		getImage();
		setDialogue();
		setAction();
	}
	
	public void getImage() {

		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
		
	}

	public void setDialogue() {
		
		dialogues[0] = "HELLO";
		dialogues[1] = "You make me talk";
		dialogues[2] = "Good job, I know you have...\na bad path,\nbut you did a great job";
		dialogues[3] = "Gogo";
	}
	// Oldman's behavior
	public void setAction() {
		
		if(onPath == true) {
			 int goalCol = 10;
	         int goalRow = 10;
	         searchPath(goalCol, goalRow);
			
			
			
		} else {
			actionLockCounter++;
			
			if(actionLockCounter == 60) {
				Random random = new Random();
				int i = random.nextInt(100)+1; // from 1 to 100
				
				if(i <= 25) {
					direction = "up";
				}
				if(i > 25 && i <= 50) {
					direction = "down";
				}
				if(i > 50 && i <= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}
				
				actionLockCounter = 0;
			}
		}
		
			
		
	}
	
	public void speak() {
		super.speak();
		
		onPath = true;
	}
}
