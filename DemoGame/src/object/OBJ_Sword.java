package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity{
	

	public OBJ_Sword(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = "Sword";
		down1 = setup("/objects/sword", gp.tileSize, gp.tileSize);
		attackValue = 1;
		description = name + "!";
		attackArea.width = 36;
		attackArea.height = 36;
		price = 75;
	}
}