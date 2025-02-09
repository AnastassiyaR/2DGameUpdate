package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion extends Entity{
	
	GamePanel gp;
	int value = 5;

	public OBJ_Potion(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Potion";
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
		description = name + "!";
	}
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink" + name;
		entity.life += value;
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(2);
	}
}