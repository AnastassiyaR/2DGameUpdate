package entity;


import java.awt.Rectangle;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Bshield;
import object.OBJ_Key;
import object.OBJ_Potion;
import object.OBJ_Shield;
import object.OBJ_Sword;

public class NPC_Merchant extends Entity{
	
	public NPC_Merchant(GamePanel gp) {
		super(gp);
		gp.player.attackCanceled = true;
		
		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle();
		solidArea.x = 16;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		getImage();
		setDialogue();
		setItems();
	}
	
	public void getImage() {

		up1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
	}

	public void setDialogue() {
		
		dialogues[0] = "hiiii";
//		dialogues[1] = "You make me talk";
//		dialogues[2] = "Good job, I know you have...\na bad path,\nbut you did a great job";
//		dialogues[3] = "Gogo";
	}
	
	public void setItems() {
		
		inventory.add(new OBJ_Potion(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Sword(gp));
		inventory.add(new OBJ_Axe(gp));
		inventory.add(new OBJ_Shield(gp));
		inventory.add(new OBJ_Bshield(gp));
	}
	
	public void speak() {
		super.speak();
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}
}
