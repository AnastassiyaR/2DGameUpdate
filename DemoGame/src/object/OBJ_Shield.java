package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity{
	

	public OBJ_Shield(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Shield";
		down1 = setup("/objects/shield", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		description = name + "!\nSHIELDDD";
		price = 75;
	}
}
