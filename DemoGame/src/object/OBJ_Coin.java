package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin extends Entity{
	
	GamePanel gp;
	
	public OBJ_Coin(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickup;
		name = "Coin";
		value = 1;
		down1 = setup("/objects/coin",gp.tileSize,gp.tileSize);
		description = "moneymoney";
		amount = 1;
	}
	
	public void use(Entity entity) {
		
		gp.playSE(1);
		gp.ui.addMessage("Coin + " + value);
		gp.player.coin += value;
		
	}

}
