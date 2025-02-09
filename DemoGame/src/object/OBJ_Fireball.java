package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile{

	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		
		name = "FireBall";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}

	private void getImage() {
		
		
	}

}
