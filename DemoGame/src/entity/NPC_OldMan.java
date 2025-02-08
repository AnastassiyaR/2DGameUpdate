package entity;

import java.util.Random;

import main.GamePanel;


public class NPC_OldMan extends Entity{

	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	
	public void getImage() {

		up1 = setup("/npc/oldman_up_1");
		up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1");
		down2 = setup("/npc/oldman_down_2");
		left1 = setup("/npc/oldman_left_1");
		left2 = setup("/npc/oldman_left_2");
		right1 = setup("/npc/oldman_right_1");
		right2 = setup("/npc/oldman_right_2");
	}

	public void setDialogue() {
		
		dialogues[0] = "HELLO";
		dialogues[1] = "You make me talk";
		dialogues[2] = "Good job, I know you have...\na bad path,\nbut you did a great job";
		dialogues[3] = "Gogo";
	}
	
	// Oldman's behavior
	public void setAction() {
		
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
	
	public void speak() {
		super.speak();
	}
}
