package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bshield extends Entity{
	

	public OBJ_Bshield(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Blue shield";
		down1 = setup("/objects/bshield", gp.tileSize, gp.tileSize);
		description = name + "!";
		price = 75;
	}
}