package tree;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends tree{

	GamePanel gp;
	
	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col,row);
		this.gp = gp;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/objects/drytree", gp.tileSize, gp.tileSize);
		destructible = true;
		life = 3;
	}
	public boolean CorrectItem(Entity entity) {
		boolean CorrectItem = false;
		
		if(entity.currentWeapon.type == type_axe) {
			CorrectItem = true;
		}
		return CorrectItem;
	}
	public void playSE() {
		gp.playSE(10);
	}
	
	public tree DestroyForm() {
		tree tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
	
	public Color getParticleColor() {
		Color color = new Color(65,50,30);
		return color;
	}
	public int getParticleSize() {
		int size = 6; // 6 pixels
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1; 
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20; 
		return maxLife;
	}
	
}
