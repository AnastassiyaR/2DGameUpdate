package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion extends Entity{
	
	GamePanel gp;

	public OBJ_Potion(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Potion";
		value = 5;
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
		description = name + "!";
		price = 75;
	}
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink" + name;
		entity.life += value;
		gp.playSE(2);
	}
}